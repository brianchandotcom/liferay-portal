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
import com.liferay.counter.model.impl.CounterModelImpl;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
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
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.impl.AssetEntryImpl;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
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
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStorageLinkImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkImpl;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceImpl;
import com.liferay.portlet.journal.model.impl.JournalContentSearchImpl;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionImpl;
import com.liferay.portlet.messageboards.model.impl.MBMailingListImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.impl.SocialActivityImpl;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.model.impl.WikiNodeImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageResourceImpl;
import com.liferay.util.SimpleCounter;

import java.io.File;
import java.io.FileInputStream;

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
		String baseDir, int maxGroupCount, int maxJournalArticleSize,
		int maxUserToGroupCount, int maxMBCategoryCount,
		int maxMBMessageCountPerThread, int maxMBThreadCount,
		int maxDLFileEntrySize, int maxBlogsEntryCount) {

		try {
			_baseDir = baseDir;
			_maxBlogsEntryCount = maxBlogsEntryCount;
			_maxDLFileEntrySize = maxDLFileEntrySize;
			_maxGroupCount = maxGroupCount;
			_maxJournalArticleSize = maxJournalArticleSize;
			_maxMBThreadCount = maxMBThreadCount;
			_maxUserToGroupCount = maxUserToGroupCount;

			_maxMBMessageCountPerCategory =
				maxMBThreadCount * maxMBMessageCountPerThread;
			_maxMBMessageCount =
				maxMBCategoryCount * _maxMBMessageCountPerCategory;

			_initSimpleCounters();

			_initClassNames();
			_initDDMStructures();
			_initDLFileEntryType();
			_initJournalArticle();
			_initUserNames();

			_initCompany();
			_initAccount();
			_initDefaultUsers();
			_initGroups();
			_initRoles();
			_initVirtualHost();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AssetEntry addAssetEntry(BlogsEntry blogsEntry) {
		return newAssetEntry(
			blogsEntry.getGroupId(), blogsEntry.getCreateDate(),
			blogsEntry.getModifiedDate(), _blogsEntryClassNameId,
			blogsEntry.getEntryId(), blogsEntry.getUuid(), 0,
			ContentTypes.TEXT_HTML, blogsEntry.getTitle());
	}

	public AssetEntry addAssetEntry(DLFileEntry dlFileEntry) {
		return newAssetEntry(
			dlFileEntry.getGroupId(), dlFileEntry.getCreateDate(),
			dlFileEntry.getModifiedDate(), _dlFileEntryClassNameId,
			dlFileEntry.getFileEntryId(), dlFileEntry.getUuid(),
			dlFileEntry.getFileEntryTypeId(), dlFileEntry.getMimeType(),
			dlFileEntry.getTitle());
	}

	public AssetEntry addAssetEntry(JournalArticle journalArticle) {
		return newAssetEntry(
			journalArticle.getGroupId(), journalArticle.getCreateDate(),
			journalArticle.getModifiedDate(), _journalArticleClassNameId,
			journalArticle.getResourcePrimKey(), journalArticle.getUuid(), 0,
			ContentTypes.TEXT_HTML, journalArticle.getTitle());
	}

	public AssetEntry addAssetEntry(MBMessage mbMessage) {
		long classNameId;

		if (mbMessage.isDiscussion()) {
			classNameId = _mbDiscussionClassNameId;
		}
		else {
			classNameId = _mbMessageClassNameId;
		}

		return newAssetEntry(
			mbMessage.getGroupId(), mbMessage.getCreateDate(),
			mbMessage.getModifiedDate(), classNameId, mbMessage.getMessageId(),
			mbMessage.getUuid(), 0, ContentTypes.TEXT_HTML,
			mbMessage.getSubject());
	}

	public AssetEntry addAssetEntry(WikiPage wikiPage) {
		return newAssetEntry(
			wikiPage.getGroupId(), wikiPage.getCreateDate(),
			wikiPage.getModifiedDate(), _wikiPageClassNameId,
			wikiPage.getResourcePrimKey(), wikiPage.getUuid(), 0,
			ContentTypes.TEXT_HTML, wikiPage.getTitle());
	}

	public BlogsEntry addBlogsEntry(long groupId, int currentIndex) {
		BlogsEntry blogsEntry = new BlogsEntryImpl();

		Date date = new Date();

		blogsEntry.setUuid(PortalUUIDUtil.generate());
		blogsEntry.setEntryId(_counter.get());
		blogsEntry.setGroupId(groupId);
		blogsEntry.setCompanyId(_sampleUser.getCompanyId());
		blogsEntry.setUserId(_sampleUser.getUserId());
		blogsEntry.setUserName(_sampleUser.getFullName());
		blogsEntry.setCreateDate(date);
		blogsEntry.setModifiedDate(date);
		blogsEntry.setTitle("Test Blog " + currentIndex);
		blogsEntry.setUrlTitle("testblog" + currentIndex);
		blogsEntry.setContent(
			"This is a test blog " + currentIndex + StringPool.PERIOD);
		blogsEntry.setDisplayDate(date);

		return blogsEntry;
	}

	public BlogsStatsUser addBlogsStatsUser(long groupId, long userId) {
		BlogsStatsUser blogsStatsUser = new BlogsStatsUserImpl();

		blogsStatsUser.setStatsUserId(_counter.get());
		blogsStatsUser.setGroupId(groupId);
		blogsStatsUser.setCompanyId(_company.getCompanyId());
		blogsStatsUser.setUserId(userId);
		blogsStatsUser.setEntryCount(_maxBlogsEntryCount);
		blogsStatsUser.setLastPostDate(new Date());

		return blogsStatsUser;
	}

	public List<Layout> addCommonLayouts(long groupId) {
		List<Layout> layouts = new ArrayList<Layout>();

		Layout layout = addLayout(groupId, "welcome", "58,", "47,");

		layouts.add(layout);

		layout = addLayout(groupId, "blogs", "", "33,");

		layouts.add(layout);

		layout = addLayout(groupId, "document_library", "", "20,");

		layouts.add(layout);

		layout = addLayout(groupId, "forums", "", "19,");

		layouts.add(layout);

		layout = addLayout(groupId, "wiki", "", "36,");

		layouts.add(layout);

		return layouts;
	}

	public Contact addContact(User user) {
		Contact contact = new ContactImpl();

		Date date = new Date();

		contact.setContactId(user.getContactId());
		contact.setCompanyId(user.getCompanyId());
		contact.setUserId(user.getUserId());
		contact.setUserName(user.getFullName());
		contact.setCreateDate(date);
		contact.setModifiedDate(date);
		contact.setClassNameId(_userClassNameId);
		contact.setClassPK(user.getUserId());
		contact.setAccountId(_company.getAccountId());
		contact.setParentContactId(ContactConstants.DEFAULT_PARENT_CONTACT_ID);
		contact.setEmailAddress(user.getEmailAddress());
		contact.setFirstName(user.getFirstName());
		contact.setLastName(user.getLastName());
		contact.setMale(true);

		return contact;
	}

	public List<Counter> addCounters() {
		List counters = new ArrayList<CounterModelImpl>();

		// Counter

		CounterModelImpl counter = new CounterModelImpl();

		counter.setName(Counter.class.getName());
		counter.setCurrentId(_counter.get());

		counters.add(counter);

		// ResourcePermission

		counter = new CounterModelImpl();

		counter.setName(ResourcePermission.class.getName());
		counter.setCurrentId(_resourcePermissionCounter.get());

		counters.add(counter);

		// SocialActivity

		counter = new CounterModelImpl();

		counter.setName(SocialActivity.class.getName());
		counter.setCurrentId(_socialActivityCounter.get());

		counters.add(counter);

		return counters;
	}

	public DDLRecord addDDLRecord(DDLRecordSet ddlRecordSet) {

		DDLRecord ddlRecord = new DDLRecordImpl();

		Date date = new Date();

		ddlRecord.setUuid(PortalUUIDUtil.generate());
		ddlRecord.setRecordId(_counter.get());
		ddlRecord.setGroupId(ddlRecordSet.getGroupId());
		ddlRecord.setCompanyId(ddlRecordSet.getCompanyId());
		ddlRecord.setUserId(ddlRecordSet.getUserId());
		ddlRecord.setUserName(ddlRecordSet.getUserName());
		ddlRecord.setVersionUserId(ddlRecordSet.getUserId());
		ddlRecord.setVersionUserName(ddlRecordSet.getUserName());
		ddlRecord.setCreateDate(date);
		ddlRecord.setModifiedDate(date);
		ddlRecord.setDDMStorageId(_counter.get());
		ddlRecord.setRecordSetId(ddlRecordSet.getRecordSetId());
		ddlRecord.setVersion(DDLRecordConstants.VERSION_DEFAULT);
		ddlRecord.setDisplayIndex(DDLRecordConstants.DISPLAY_INDEX_DEFAULT);

		return ddlRecord;
	}

	public DDLRecordSet addDDLRecordSet(
		DDMStructure ddmStructure, int currentIndex) {

		DDLRecordSet ddlRecordSet = new DDLRecordSetImpl();

		Date date = new Date();

		ddlRecordSet.setUuid(PortalUUIDUtil.generate());
		ddlRecordSet.setRecordSetId(_counter.get());
		ddlRecordSet.setGroupId(ddmStructure.getGroupId());
		ddlRecordSet.setCompanyId(ddmStructure.getCompanyId());
		ddlRecordSet.setUserId(ddmStructure.getUserId());
		ddlRecordSet.setUserName(ddmStructure.getUserName());
		ddlRecordSet.setCreateDate(date);
		ddlRecordSet.setModifiedDate(date);
		ddlRecordSet.setDDMStructureId(ddmStructure.getStructureId());
		ddlRecordSet.setMinDisplayRows(
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT);
		ddlRecordSet.setScope(DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);

		String recordSetKey = "Test DDL Record Set " + currentIndex;
		String name = _replaceToken(
			_XML_FOR_NAME, _XML_FOR_NAME_TOKEN, recordSetKey);

		ddlRecordSet.setRecordSetKey(recordSetKey);
		ddlRecordSet.setName(name);

		return ddlRecordSet;
	}

	public DDLRecordVersion addDDLRecordVersion(DDLRecord ddlRecord) {
		DDLRecordVersion ddlRecordVersion = new DDLRecordVersionImpl();

		ddlRecordVersion.setRecordVersionId(_counter.get());
		ddlRecordVersion.setGroupId(ddlRecord.getGroupId());
		ddlRecordVersion.setCompanyId(ddlRecord.getCompanyId());
		ddlRecordVersion.setUserId(ddlRecord.getVersionUserId());
		ddlRecordVersion.setUserName(ddlRecord.getVersionUserName());
		ddlRecordVersion.setCreateDate(ddlRecord.getModifiedDate());
		ddlRecordVersion.setDDMStorageId(ddlRecord.getDDMStorageId());
		ddlRecordVersion.setRecordSetId(ddlRecord.getRecordSetId());
		ddlRecordVersion.setRecordId(ddlRecord.getRecordId());
		ddlRecordVersion.setVersion(ddlRecord.getVersion());
		ddlRecordVersion.setDisplayIndex(ddlRecord.getDisplayIndex());

		return ddlRecordVersion;
	}

	public DDMContent addDDMContent(DDLRecord ddlRecord, int currentIndex) {
		String xml = _replaceToken(
			_DDM_CONTENT_FOR_DDL, _DDM_CONTENT_TOKEN,
			"This is test record " + currentIndex);

		return newDDMContent(
			ddlRecord.getDDMStorageId(), ddlRecord.getGroupId(),
			ddlRecord.getCompanyId(), ddlRecord.getUserId(),
			ddlRecord.getUserName(), xml);
	}

	public DDMContent addDDMContent(DLFileEntry dlFileEntry) {
		return newDDMContent(
			_counter.get(), dlFileEntry.getGroupId(),
			dlFileEntry.getCompanyId(), dlFileEntry.getUserId(),
			dlFileEntry.getUserName(), _DDM_CONTENT_FOR_DL);
	}

	public DDMStorageLink addDDMStorageLink(
		DDMContent ddmContent, long structureId) {

		DDMStorageLink ddmStorageLink = new DDMStorageLinkImpl();

		ddmStorageLink.setUuid(PortalUUIDUtil.generate());
		ddmStorageLink.setStorageLinkId(_counter.get());
		ddmStorageLink.setClassNameId(_ddmContentClassNameId);
		ddmStorageLink.setClassPK(ddmContent.getContentId());
		ddmStorageLink.setStructureId(structureId);

		return ddmStorageLink;
	}

	public DDMStructure addDDMStructureForDDL(long groupId) {
		return newDDMStructure(
			groupId, _ddlRecordSetClassNameId, "Test DDM Structure For DDL",
			_ddm_structure_ddl, PropsValues.DYNAMIC_DATA_LISTS_STORAGE_TYPE);
	}

	public DDMStructure addDDMStructureForDL(long groupId) {
		return newDDMStructure(
			groupId, _dlFileEntryClassNameId, "TIKARAWMETADATA",
			_ddm_structure_basic_document, "xml");
	}

	public DDMStructureLink addDDMStructureLink(DDLRecordSet ddlRecordSet) {
		return newDDMStructureLink(
			_ddlRecordSetClassNameId, ddlRecordSet.getRecordSetId(),
			ddlRecordSet.getDDMStructureId());
	}

	public DDMStructureLink addDDMStructureLink(
		DLFileEntryMetadata dLFileEntryMetadata) {

		return newDDMStructureLink(
			_dlFileEntryMetaDataClassNameId,
			dLFileEntryMetadata.getFileEntryMetadataId(),
			dLFileEntryMetadata.getDDMStructureId());
	}

	public DLFileEntry addDlFileEntry(DLFolder dlFolder, int currentIndex) {
		DLFileEntry dlFileEntry = new DLFileEntryImpl();

		Date date = nextDate();

		dlFileEntry.setUuid(PortalUUIDUtil.generate());
		dlFileEntry.setFileEntryId(_counter.get());
		dlFileEntry.setGroupId(dlFolder.getGroupId());
		dlFileEntry.setCompanyId(dlFolder.getCompanyId());
		dlFileEntry.setUserId(dlFolder.getUserId());
		dlFileEntry.setUserName(dlFolder.getUserName());
		dlFileEntry.setVersionUserId(dlFolder.getUserId());
		dlFileEntry.setVersionUserName(dlFolder.getUserName());
		dlFileEntry.setCreateDate(date);
		dlFileEntry.setModifiedDate(date);
		dlFileEntry.setRepositoryId(dlFolder.getRepositoryId());
		dlFileEntry.setFolderId(dlFolder.getFolderId());
		dlFileEntry.setName("TestFile" + currentIndex);
		dlFileEntry.setExtension("txt");
		dlFileEntry.setMimeType(ContentTypes.TEXT_PLAIN);
		dlFileEntry.setTitle("TestFile" + currentIndex + ".txt");
		dlFileEntry.setFileEntryTypeId(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		dlFileEntry.setVersion(DLFileEntryConstants.VERSION_DEFAULT);
		dlFileEntry.setSize(_maxDLFileEntrySize);

		return dlFileEntry;
	}

	public DLFileEntryMetadata addDLFileEntryMetadata(
		DDMStorageLink ddmStorageLink, DLFileVersion dlFileVersion) {

		DLFileEntryMetadata dlFileEntryMetadata = new DLFileEntryMetadataImpl();

		dlFileEntryMetadata.setUuid(PortalUUIDUtil.generate());
		dlFileEntryMetadata.setFileEntryMetadataId(_counter.get());
		dlFileEntryMetadata.setDDMStorageId(ddmStorageLink.getPrimaryKey());
		dlFileEntryMetadata.setDDMStructureId(ddmStorageLink.getStructureId());
		dlFileEntryMetadata.setFileEntryTypeId(
			dlFileVersion.getFileEntryTypeId());
		dlFileEntryMetadata.setFileEntryId(dlFileVersion.getFileEntryId());
		dlFileEntryMetadata.setFileVersionId(dlFileVersion.getFileVersionId());

		return dlFileEntryMetadata;
	}

	public DLFileVersion addDLFileVersion(DLFileEntry dlFileEntry) {
		DLFileVersion dlFileVersion = new DLFileVersionImpl();

		Date date = nextDate();

		dlFileVersion.setUuid(PortalUUIDUtil.generate());
		dlFileVersion.setFileVersionId(_counter.get());
		dlFileVersion.setGroupId(dlFileEntry.getGroupId());
		dlFileVersion.setCompanyId(dlFileEntry.getCompanyId());
		dlFileVersion.setUserId(dlFileEntry.getVersionUserId());
		dlFileVersion.setUserName(dlFileEntry.getVersionUserName());
		dlFileVersion.setCreateDate(date);
		dlFileVersion.setModifiedDate(date);
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

	public DLFolder addDLFolder(
		long groupId, long parentFolderId, int currentIndex) {

		DLFolder dlFolder = new DLFolderImpl();

		Date date = nextDate();

		dlFolder.setUuid(PortalUUIDUtil.generate());
		dlFolder.setFolderId(_counter.get());
		dlFolder.setGroupId(groupId);
		dlFolder.setCompanyId(_sampleUser.getCompanyId());
		dlFolder.setUserId(_sampleUser.getUserId());
		dlFolder.setUserName(_sampleUser.getFullName());
		dlFolder.setCreateDate(date);
		dlFolder.setModifiedDate(date);
		dlFolder.setRepositoryId(groupId);
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setName("Test Folder " + currentIndex);
		dlFolder.setLastPostDate(date);
		dlFolder.setDefaultFileEntryTypeId(
			_defaultDLFileEntryType.getFileEntryTypeId());

		return dlFolder;
	}

	public DLSync addDLSync(DLFileEntry dlFileEntry) {
		return newDLSync(
			dlFileEntry.getCompanyId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getUuid(), dlFileEntry.getRepositoryId(),
			dlFileEntry.getFolderId(), dlFileEntry.getName(),
			DLSyncConstants.TYPE_FILE);
	}

	public DLSync addDLSync(DLFolder dLFolder) {
		return newDLSync(
			dLFolder.getCompanyId(), dLFolder.getFolderId(), dLFolder.getUuid(),
			dLFolder.getRepositoryId(), dLFolder.getParentFolderId(),
			dLFolder.getName(), DLSyncConstants.TYPE_FOLDER);
	}

	public Group addGroup(User user) throws Exception {
		return newGroup(
			0, _userClassNameId, user.getUserId(),
			Long.toString(user.getUserId()),
			StringPool.FORWARD_SLASH + user.getScreenName(), false);
	}

	public JournalArticle addJournalArticle(
		JournalArticleResource journalArticleResource) {

		JournalArticle journalArticle = new JournalArticleImpl();

		Date date = new Date();

		journalArticle.setUuid(PortalUUIDUtil.generate());
		journalArticle.setId(_counter.get());
		journalArticle.setResourcePrimKey(
			journalArticleResource.getResourcePrimKey());
		journalArticle.setGroupId(journalArticleResource.getGroupId());
		journalArticle.setCompanyId(_sampleUser.getCompanyId());
		journalArticle.setUserId(_sampleUser.getUserId());
		journalArticle.setUserName(_sampleUser.getFullName());
		journalArticle.setCreateDate(date);
		journalArticle.setModifiedDate(date);
		journalArticle.setClassNameId(
			JournalArticleConstants.CLASSNAME_ID_DEFAULT);
		journalArticle.setArticleId(journalArticleResource.getArticleId());
		journalArticle.setVersion(JournalArticleConstants.VERSION_DEFAULT);
		journalArticle.setTitle(_JOURNAL_ARTICLE_TITLE);
		journalArticle.setUrlTitle("Test Journal Article");
		journalArticle.setContent(_journalArticleContent);
		journalArticle.setType("general");
		journalArticle.setDisplayDate(date);
		journalArticle.setIndexable(true);

		return journalArticle;
	}

	public JournalArticleResource addJournalArticleResource(long groupId) {
		JournalArticleResource journalArticleResource =
			new JournalArticleResourceImpl();

		journalArticleResource.setUuid(PortalUUIDUtil.generate());
		journalArticleResource.setResourcePrimKey(_counter.get());
		journalArticleResource.setGroupId(groupId);
		journalArticleResource.setArticleId(String.valueOf(_counter.get()));

		return journalArticleResource;
	}

	public JournalContentSearch addJournalContentSearch(
		JournalArticle journalArticle, long layoutId, String portletId) {

		JournalContentSearch journalContentSearch =
			new JournalContentSearchImpl();

		journalContentSearch.setContentSearchId(_counter.get());
		journalContentSearch.setGroupId(journalArticle.getGroupId());
		journalContentSearch.setCompanyId(journalArticle.getCompanyId());
		journalContentSearch.setLayoutId(layoutId);
		journalContentSearch.setPortletId(portletId);
		journalContentSearch.setArticleId(journalArticle.getArticleId());

		return journalContentSearch;
	}

	public Layout addLayout(
		long groupId, String name, String column1, String column2) {

		Layout layout = new LayoutImpl();

		Date date = new Date();

		layout.setUuid(PortalUUIDUtil.generate());
		layout.setPlid(_counter.get());
		layout.setGroupId(groupId);
		layout.setCompanyId(_company.getCompanyId());
		layout.setCreateDate(date);
		layout.setModifiedDate(date);
		layout.setName(_replaceToken(_LAYOUT_NAME, _LAYOUT_NAME_TOKEN, name));
		layout.setType(LayoutConstants.TYPE_PORTLET);
		layout.setFriendlyURL(StringPool.FORWARD_SLASH + name);

		SimpleCounter simpleCounter = _layoutCounters.get(groupId);

		if (simpleCounter == null) {
			simpleCounter = new SimpleCounter();

			_layoutCounters.put(groupId, simpleCounter);
		}

		layout.setLayoutId(simpleCounter.get());

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

	public List<LayoutSet> addLayoutSets(Group group, int pageCount) {
		List<LayoutSet> layoutSets = new ArrayList<LayoutSet>(2);

		layoutSets.add(newLayoutSet(group, true, 0));
		layoutSets.add(newLayoutSet(group, false, pageCount));

		return layoutSets;
	}

	public MBCategory addMBCategory(long groupId, int currentIndex) {
		MBCategory mbCategory = new MBCategoryImpl();

		Date date = new Date();

		mbCategory.setUuid(PortalUUIDUtil.generate());
		mbCategory.setCategoryId(_counter.get());
		mbCategory.setGroupId(groupId);
		mbCategory.setCompanyId(_sampleUser.getCompanyId());
		mbCategory.setUserId(_sampleUser.getUserId());
		mbCategory.setUserName(_sampleUser.getFullName());
		mbCategory.setCreateDate(date);
		mbCategory.setModifiedDate(date);
		mbCategory.setParentCategoryId(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
		mbCategory.setName("Test Category " + currentIndex);
		mbCategory.setDisplayStyle(MBCategoryConstants.DEFAULT_DISPLAY_STYLE);
		mbCategory.setThreadCount(_maxMBThreadCount);
		mbCategory.setMessageCount(_maxMBMessageCountPerCategory);
		mbCategory.setLastPostDate(date);

		return mbCategory;
	}

	public MBDiscussion addMBDiscussion(BlogsEntry blogsEntry, long threadId) {
		return newMBDiscussion(
			_blogsEntryClassNameId, blogsEntry.getEntryId(), threadId);
	}

	public MBDiscussion addMBDiscussion(
		DLFileEntry dlFileEntry, long threadId) {

		return newMBDiscussion(
			_dlFileEntryClassNameId, dlFileEntry.getFileEntryId(), threadId);
	}

	public MBDiscussion addMBDiscussion(
		JournalArticle journalArticle, long threadId) {

		return newMBDiscussion(
			_journalArticleClassNameId, journalArticle.getResourcePrimKey(),
			threadId);
	}

	public MBDiscussion addMBDiscussion(MBMessage mbMessage) {
		return newMBDiscussion(
			mbMessage.getClassNameId(), mbMessage.getClassPK(),
			mbMessage.getThreadId());
	}

	public MBDiscussion addMBDiscussion(WikiPage wikiPage, long threadId) {
		return newMBDiscussion(
			_wikiPageClassNameId, wikiPage.getResourcePrimKey(), threadId);
	}

	public MBMailingList addMBMailingList(MBCategory mbCategory) {
		MBMailingList mbMailingList = new MBMailingListImpl();

		Date date = new Date();

		mbMailingList.setUuid(PortalUUIDUtil.generate());
		mbMailingList.setMailingListId(_counter.get());
		mbMailingList.setGroupId(mbCategory.getGroupId());
		mbMailingList.setCompanyId(mbCategory.getCompanyId());
		mbMailingList.setUserId(mbCategory.getUserId());
		mbMailingList.setUserName(mbCategory.getUserName());
		mbMailingList.setCreateDate(date);
		mbMailingList.setModifiedDate(date);
		mbMailingList.setCategoryId(mbCategory.getCategoryId());
		mbMailingList.setInProtocol("pop3");
		mbMailingList.setInServerPort(110);
		mbMailingList.setInUserName(_sampleUser.getEmailAddress());
		mbMailingList.setInPassword(_sampleUser.getPassword());
		mbMailingList.setInReadInterval(5);
		mbMailingList.setOutServerPort(25);

		return mbMailingList;
	}

	public MBMessage addMBMessage(BlogsEntry blogsEntry) {
		return addMBMessage(blogsEntry, null, 0);
	}

	public MBMessage addMBMessage(
		BlogsEntry blogsEntry, MBMessage rootMessage, int currentIndex) {

		long threadId;
		long rootMessageId = 0;
		long parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;
		String subject;
		String body;

		if (rootMessage != null) {
			threadId = rootMessage.getThreadId();
			rootMessageId = rootMessage.getMessageId();
			parentMessageId = rootMessage.getMessageId();
			subject = "N/A";
			body = "This is a test comment " + currentIndex + StringPool.PERIOD;
		}
		else {
			threadId = _counter.get();
			subject = Long.toString(blogsEntry.getEntryId());
			body = Long.toString(blogsEntry.getEntryId());
		}

		return newMBMessage(
			blogsEntry.getGroupId(), blogsEntry.getCompanyId(),
			blogsEntry.getUserId(), blogsEntry.getUserName(),
			_blogsEntryClassNameId, blogsEntry.getEntryId(),
			MBCategoryConstants.DISCUSSION_CATEGORY_ID, threadId, rootMessageId,
			parentMessageId, subject, body);
	}

	public MBMessage addMBMessage(DLFileEntry dlFileEntry) {

		String dlFileEntryIDString = Long.toString(
			dlFileEntry.getFileEntryId());

		return newMBMessage(
			dlFileEntry.getGroupId(), dlFileEntry.getCompanyId(),
			dlFileEntry.getUserId(), dlFileEntry.getUserName(),
			_dlFileEntryClassNameId, dlFileEntry.getFileEntryId(),
			MBCategoryConstants.DISCUSSION_CATEGORY_ID, _counter.get(), 0,
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, dlFileEntryIDString,
			dlFileEntryIDString);
	}

	public MBMessage addMBMessage(JournalArticle journalArticle) {
		String resourcePrimKeyString = Long.toString(
			journalArticle.getResourcePrimKey());

		return newMBMessage(
			journalArticle.getGroupId(), journalArticle.getCompanyId(),
			journalArticle.getUserId(), journalArticle.getUserName(),
			_journalArticleClassNameId, journalArticle.getResourcePrimKey(),
			MBCategoryConstants.DISCUSSION_CATEGORY_ID, _counter.get(), 0,
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			resourcePrimKeyString, resourcePrimKeyString);
	}

	public MBMessage addMBMessage(MBCategory mbCategory, int currentIndex) {
		return addMBMessage(mbCategory, null, currentIndex);
	}

	public MBMessage addMBMessage(
		MBCategory mbCategory, MBMessage rootMessage, int currentIndex) {

		long threadId;
		long rootMessageId = 0;
		long parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;

		if (rootMessage != null) {
			threadId = rootMessage.getThreadId();
			rootMessageId = rootMessage.getMessageId();
			parentMessageId = rootMessage.getMessageId();
		}
		else {
			threadId = _counter.get();
		}

		String subject = "Test Message " + currentIndex;
		String body =
			"This is a test message " + currentIndex + StringPool.PERIOD;

		return newMBMessage(
			mbCategory.getGroupId(), mbCategory.getCompanyId(),
			mbCategory.getUserId(), mbCategory.getUserName(), 0, 0,
			mbCategory.getCategoryId(), threadId, rootMessageId,
			parentMessageId, subject, body);
	}

	public MBMessage addMBMessage(WikiPage wikiPage) {
		return addMBMessage(wikiPage, null, 0);
	}

	public MBMessage addMBMessage(
		WikiPage wikiPage, MBMessage rootMessage, int currentIndex) {

		long threadId;
		long rootMessageId = 0;
		long parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;
		String subject;
		String body;

		if (rootMessage != null) {
			threadId = rootMessage.getThreadId();
			rootMessageId = rootMessage.getMessageId();
			parentMessageId = rootMessage.getMessageId();
			subject = "N/A";
			body = "This is a test comment " + currentIndex + StringPool.PERIOD;
		}
		else {
			threadId = _counter.get();
			subject = Long.toString(wikiPage.getResourcePrimKey());
			body = Long.toString(wikiPage.getResourcePrimKey());
		}

		return newMBMessage(
			wikiPage.getGroupId(), wikiPage.getCompanyId(),
			wikiPage.getUserId(), wikiPage.getUserName(), _wikiPageClassNameId,
			wikiPage.getResourcePrimKey(),
			MBCategoryConstants.DISCUSSION_CATEGORY_ID, threadId, rootMessageId,
			parentMessageId, subject, body);
	}

	public MBStatsUser addMBStatsUser(long groupId, long userId) {
		MBStatsUser mbStatsUser = new MBStatsUserImpl();

		mbStatsUser.setStatsUserId(_counter.get());
		mbStatsUser.setGroupId(groupId);
		mbStatsUser.setUserId(userId);
		mbStatsUser.setMessageCount(_maxMBMessageCount);
		mbStatsUser.setLastPostDate(new Date());

		return mbStatsUser;
	}

	public MBThread addMBThread(MBMessage rootMessage, int messageCount) {
		MBThread mbThread = new MBThreadImpl();

		mbThread.setThreadId(rootMessage.getThreadId());
		mbThread.setGroupId(rootMessage.getGroupId());
		mbThread.setCompanyId(rootMessage.getCompanyId());
		mbThread.setCategoryId(rootMessage.getCategoryId());
		mbThread.setRootMessageId(rootMessage.getRootMessageId());
		mbThread.setRootMessageUserId(rootMessage.getUserId());
		mbThread.setMessageCount(messageCount);
		mbThread.setLastPostDate(new Date());

		return mbThread;
	}

	public PortletPreferences addPortletPreferences(
		DDLRecordSet ddlRecordSet, long plid, String portletId) {

		String preferences = _replaceToken(
			_PORTLET_PREFERENCES_FOR_DDL, _PORTLET_PREFERENCES_FOR_DDL_TOKEN,
			Long.toString(ddlRecordSet.getRecordSetId()));

		return newPortletPreferences(plid, portletId, preferences);
	}

	public List<PortletPreferences> addPortletPreferences(
		JournalArticle journalArticle, long plid) {

		String preferences = _replaceToken(
			_PORTLET_PREFERENCES_FOR_JOURNAL,
			_PORTLET_PREFERENCES_FOR_JOURNAL_TOKEN_1,
			journalArticle.getArticleId());

		preferences = _replaceToken(
			preferences, _PORTLET_PREFERENCES_FOR_JOURNAL_TOKEN_2,
			Long.toString(journalArticle.getGroupId()));

		List<PortletPreferences> portletPreferencesList =
			new ArrayList<PortletPreferences>();

		PortletPreferences portletPreferences = newPortletPreferences(
			plid, "56", preferences);

		portletPreferencesList.add(portletPreferences);

		portletPreferences = newPortletPreferences(
			plid, "145", PortletConstants.DEFAULT_PREFERENCES);

		portletPreferencesList.add(portletPreferences);

		portletPreferences = newPortletPreferences(
			plid, "86", PortletConstants.DEFAULT_PREFERENCES);

		portletPreferencesList.add(portletPreferences);

		return portletPreferencesList;
	}

	public List<ResourcePermission> addResourcePermissions(
		String name, long primKey) {

		return addResourcePermissions(name, Long.toString(primKey));
	}

	public List<ResourcePermission> addResourcePermissions(
		String name, String primKey) {

		List<ResourcePermission> resourcePermissions =
			new ArrayList<ResourcePermission>(3);

		ResourcePermission resourcePermission = newResourcePermission(
			name, primKey, _ownerRole.getRoleId(), _defaultUser.getUserId());

		resourcePermissions.add(resourcePermission);

		resourcePermission = newResourcePermission(
			name, primKey, _guestRole.getRoleId(), 0);

		resourcePermissions.add(resourcePermission);

		resourcePermission = newResourcePermission(
			name, primKey, _siteMemberRole.getRoleId(), 0);

		resourcePermissions.add(resourcePermission);

		return resourcePermissions;
	}

	public SocialActivity addSocialActivity(DLFileEntry dlFileEntry) {
		SocialActivity socialActivity = new SocialActivityImpl();

		socialActivity.setActivityId(_socialActivityCounter.get());
		socialActivity.setGroupId(dlFileEntry.getGroupId());
		socialActivity.setCompanyId(dlFileEntry.getCompanyId());
		socialActivity.setUserId(dlFileEntry.getUserId());
		socialActivity.setCreateDate(System.currentTimeMillis());
		socialActivity.setClassNameId(_dlFileEntryClassNameId);
		socialActivity.setClassPK(dlFileEntry.getFileEntryId());
		socialActivity.setType(DLActivityKeys.ADD_FILE_ENTRY);
		socialActivity.setExtraData(
			"{\"title\":\""+ dlFileEntry.getTitle() +"\"}");

		return socialActivity;
	}

	public User addUser(int currentIndex) {
		String[] names = nextName(currentIndex - 1);

		return newUser(
			names[0], names[1], "test" + _userScreenNameIncrementer.get(),
			false);
	}

	public List<Long> addUserToGroupIds(long groupId) {
		List<Long> groupIds = new ArrayList<Long>(_maxUserToGroupCount + 1);

		groupIds.add(_guestGroup.getGroupId());

		if ((groupId + _maxUserToGroupCount) > _maxGroupCount) {
			groupId = groupId - _maxUserToGroupCount + 1;
		}

		for (int i = 0; i < _maxUserToGroupCount; i++) {
			groupIds.add(groupId + i);
		}

		return groupIds;
	}

	public WikiNode addWikiNode(long groupId, int currentIndex) {
		WikiNode wikiNode = new WikiNodeImpl();

		Date date = new Date();

		wikiNode.setUuid(PortalUUIDUtil.generate());
		wikiNode.setNodeId(_counter.get());
		wikiNode.setGroupId(groupId);
		wikiNode.setCompanyId(_sampleUser.getCompanyId());
		wikiNode.setUserId(_sampleUser.getUserId());
		wikiNode.setUserName(_sampleUser.getFullName());
		wikiNode.setCreateDate(date);
		wikiNode.setModifiedDate(date);
		wikiNode.setName("Test Node " + currentIndex);
		wikiNode.setLastPostDate(date);

		return wikiNode;
	}

	public WikiPage addWikiPage(WikiNode wikiNode, int currentIndex) {

		WikiPage wikiPage = new WikiPageImpl();

		Date date = new Date();

		wikiPage.setUuid(PortalUUIDUtil.generate());
		wikiPage.setPageId(_counter.get());
		wikiPage.setResourcePrimKey(_counter.get());
		wikiPage.setGroupId(wikiNode.getGroupId());
		wikiPage.setCompanyId(wikiNode.getCompanyId());
		wikiPage.setUserId(wikiNode.getUserId());
		wikiPage.setUserName(wikiNode.getUserName());
		wikiPage.setCreateDate(date);
		wikiPage.setModifiedDate(date);
		wikiPage.setNodeId(wikiNode.getNodeId());
		wikiPage.setTitle("Test Page " + currentIndex);
		wikiPage.setVersion(WikiPageConstants.VERSION_DEFAULT);
		wikiPage.setContent(
			"This is a test page " + currentIndex + StringPool.PERIOD);
		wikiPage.setFormat(WikiPageConstants.DEFAULT_FORMAT);
		wikiPage.setHead(true);

		return wikiPage;
	}

	public WikiPageResource addWikiPageResource(WikiPage wikiPage) {

		WikiPageResource wikiPageResource = new WikiPageResourceImpl();

		wikiPageResource.setUuid(PortalUUIDUtil.generate());
		wikiPageResource.setResourcePrimKey(wikiPage.getResourcePrimKey());
		wikiPageResource.setNodeId(wikiPage.getNodeId());
		wikiPageResource.setTitle(wikiPage.getTitle());

		return wikiPageResource;
	}

	public Account getAccount() {
		return _account;
	}

	public Role getAdministratorRole() {
		return _administratorRole;
	}

	public List<ClassName> getClassNames() {
		return _classNames;
	}

	public Company getCompany() {
		return _company;
	}

	public String getDateLong(Date date) {
		return String.valueOf(date.getTime());
	}

	public DLFileEntryType getDefaultDLFileEntryType() {
		return _defaultDLFileEntryType;
	}

	public User getDefaultUser() {
		return _defaultUser;
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

	protected AssetEntry newAssetEntry(
		long groupId, Date createDate, Date modifiedDate, long classNameId,
		long classPK, String uuid, long classTypeId, String mimeType,
		String title) {

		AssetEntry assetEntry = new AssetEntryImpl();

		assetEntry.setEntryId(_counter.get());
		assetEntry.setGroupId(groupId);
		assetEntry.setCompanyId(_sampleUser.getCompanyId());
		assetEntry.setUserId(_sampleUser.getUserId());
		assetEntry.setUserName(_sampleUser.getFullName());
		assetEntry.setCreateDate(createDate);
		assetEntry.setModifiedDate(modifiedDate);
		assetEntry.setClassNameId(classNameId);
		assetEntry.setClassPK(classPK);
		assetEntry.setClassUuid(uuid);
		assetEntry.setClassTypeId(classTypeId);
		assetEntry.setVisible(true);
		assetEntry.setMimeType(mimeType);
		assetEntry.setTitle(title);

		return assetEntry;
	}

	protected DDMContent newDDMContent(
		long contentId, long groupId, long companyId, long userId,
		String userName, String xml) {

		DDMContent ddmContent = new DDMContentImpl();

		Date date = nextDate();

		ddmContent.setUuid(PortalUUIDUtil.generate());
		ddmContent.setContentId(contentId);
		ddmContent.setGroupId(groupId);
		ddmContent.setCompanyId(companyId);
		ddmContent.setUserId(userId);
		ddmContent.setUserName(userName);
		ddmContent.setCreateDate(date);
		ddmContent.setModifiedDate(date);
		ddmContent.setName(DDMStorageLink.class.getName());
		ddmContent.setXml(xml);

		return ddmContent;
	}

	protected DDMStructure newDDMStructure(
		long groupId, long classNameId, String structureKey, String xsd,
		String storageType) {

		DDMStructure ddmStructure = new DDMStructureImpl();

		Date date = nextDate();

		ddmStructure.setUuid(PortalUUIDUtil.generate());
		ddmStructure.setStructureId(_counter.get());
		ddmStructure.setGroupId(groupId);
		ddmStructure.setCompanyId(_sampleUser.getCompanyId());
		ddmStructure.setUserId(_sampleUser.getUserId());
		ddmStructure.setUserName(_sampleUser.getFullName());
		ddmStructure.setCreateDate(date);
		ddmStructure.setModifiedDate(date);
		ddmStructure.setParentStructureId(
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID);
		ddmStructure.setClassNameId(classNameId);
		ddmStructure.setStructureKey(structureKey);
		ddmStructure.setXsd(xsd);
		ddmStructure.setStorageType(storageType);
		ddmStructure.setType(DDMStructureConstants.TYPE_DEFAULT);

		String name = _replaceToken(
			_XML_FOR_NAME, _XML_FOR_NAME_TOKEN, structureKey);

		ddmStructure.setName(name);

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

	protected DLSync newDLSync(
		long companyId, long fileId, String fileUuid, long repositoryId,
		long parentFolderId, String name, String type) {

		DLSync dlSync = new DLSyncImpl();

		Date date = nextDate();

		dlSync.setSyncId(_counter.get());
		dlSync.setCompanyId(companyId);
		dlSync.setCreateDate(date);
		dlSync.setModifiedDate(date);
		dlSync.setFileId(fileId);
		dlSync.setFileUuid(fileUuid);
		dlSync.setRepositoryId(repositoryId);
		dlSync.setParentFolderId(parentFolderId);
		dlSync.setName(name);
		dlSync.setEvent(DLSyncConstants.EVENT_ADD);
		dlSync.setType(type);

		return dlSync;
	}

	protected Group newGroup(
			long groupId, long classNameId, long classPK, String name,
			String friendlyURL, boolean site)
		throws Exception {

		Group group = new GroupImpl();

		group.setCompanyId(_sampleUser.getCompanyId());
		group.setCreatorUserId(_sampleUser.getCompanyId());
		group.setClassNameId(classNameId);
		group.setTreePath(group.buildTreePath());
		group.setName(name);
		group.setFriendlyURL(friendlyURL);
		group.setSite(site);
		group.setActive(true);

		if (groupId == 0) {
			groupId = _counter.get();
		}

		group.setGroupId(groupId);

		if (classPK == 0) {
			classPK = groupId;
		}

		group.setClassPK(classPK);

		return group;
	}

	protected LayoutSet newLayoutSet(
		Group group, boolean privateLayout, int pageCount) {

		LayoutSet layoutSet = new LayoutSetImpl();

		Date date = new Date();

		layoutSet.setLayoutSetId(_counter.get());
		layoutSet.setGroupId(group.getGroupId());
		layoutSet.setCompanyId(group.getCompanyId());
		layoutSet.setCreateDate(date);
		layoutSet.setModifiedDate(date);
		layoutSet.setPrivateLayout(privateLayout);
		layoutSet.setThemeId("classic");
		layoutSet.setColorSchemeId("01");
		layoutSet.setWapThemeId("mobile");
		layoutSet.setWapColorSchemeId("01");
		layoutSet.setPageCount(pageCount);

		return layoutSet;
	}

	protected MBDiscussion newMBDiscussion(
		long classNameId, long classPK, long threadId) {

		MBDiscussion mbDiscussion = new MBDiscussionImpl();

		mbDiscussion.setDiscussionId(_counter.get());
		mbDiscussion.setClassNameId(classNameId);
		mbDiscussion.setClassPK(classPK);

		if (threadId == 0) {
			threadId = _counter.get();
		}

		mbDiscussion.setThreadId(threadId);

		return mbDiscussion;
	}

	protected MBMessage newMBMessage(
		long groupId, long companyId, long userId, String userName,
		long classNameId, long classPK, long categoryId, long threadId,
		long rootMessageId, long parentMessageId, String subject, String body) {

		MBMessage mbMessage = new MBMessageImpl();

		Date date = new Date();

		long messageId = _counter.get();

		mbMessage.setUuid(PortalUUIDUtil.generate());
		mbMessage.setMessageId(messageId);
		mbMessage.setGroupId(groupId);
		mbMessage.setCompanyId(companyId);
		mbMessage.setUserId(userId);
		mbMessage.setUserName(userName);
		mbMessage.setCreateDate(date);
		mbMessage.setModifiedDate(date);
		mbMessage.setClassNameId(classNameId);
		mbMessage.setClassPK(classPK);
		mbMessage.setCategoryId(categoryId);
		mbMessage.setThreadId(threadId);
		mbMessage.setParentMessageId(parentMessageId);
		mbMessage.setSubject(subject);
		mbMessage.setBody(body);
		mbMessage.setFormat(MBMessageConstants.DEFAULT_FORMAT);

		if (rootMessageId == 0) {
			rootMessageId = messageId;
		}

		mbMessage.setRootMessageId(rootMessageId);

		return mbMessage;
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
		resourcePermission.setCompanyId(_company.getCompanyId());
		resourcePermission.setName(name);
		resourcePermission.setScope(ResourceConstants.SCOPE_INDIVIDUAL);
		resourcePermission.setPrimKey(primKey);
		resourcePermission.setRoleId(roleId);
		resourcePermission.setOwnerId(ownerId);
		resourcePermission.setActionIds(1);

		return resourcePermission;
	}

	protected Role newRole(String name, int type) {
		Role role = new RoleImpl();

		long roleId = _counter.get();

		role.setRoleId(roleId);
		role.setCompanyId(_company.getCompanyId());
		role.setClassNameId(_roleClassNameId);
		role.setClassPK(roleId);
		role.setName(name);
		role.setType(type);

		return role;
	}

	protected User newUser(
		String firstName, String lastName, String screenName,
		boolean defaultUser) {

		User user = new UserImpl();

		Date date = new Date();

		user.setUuid(PortalUUIDUtil.generate());
		user.setUserId(_counter.get());
		user.setCompanyId(_company.getCompanyId());
		user.setCreateDate(date);
		user.setModifiedDate(date);
		user.setDefaultUser(defaultUser);
		user.setContactId(_counter.get());
		user.setPassword(_PASSWORD);
		user.setReminderQueryQuestion(_QUERY_QUESTION);
		user.setLanguageId(_DEFAULT_LANGUAGE_ID);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLoginDate(date);
		user.setLastLoginDate(date);
		user.setAgreedToTermsOfUse(true);
		user.setEmailAddressVerified(true);

		if (Validator.isNull(screenName)) {
			screenName = String.valueOf(user.getUserId());
		}

		user.setScreenName(screenName);
		user.setReminderQueryAnswer(screenName);
		user.setGreeting("Welcome " + screenName + StringPool.EXCLAMATION);
		user.setEmailAddress(screenName + "@liferay.com");

		return user;
	}

	protected Date nextDate() {
		return new Date(_futureTime + (_dlDateCounter.get() * Time.SECOND));
	}

	protected String[] nextName(long currentIndex) {
		currentIndex = currentIndex % (_lastNames.size() * _firstNames.size());

		int indexForFirstName = (int)currentIndex / _lastNames.size();
		int indexForLastName = (int)currentIndex % _lastNames.size();

		String[] names = new String[2];

		names[0] = _firstNames.get(indexForFirstName);
		names[1] = _lastNames.get(indexForLastName);

		return names;
	}

	private void _initAccount() {
		_account = new AccountImpl();

		Date date = new Date();

		_account.setAccountId(_company.getAccountId());
		_account.setCompanyId(_company.getCompanyId());
		_account.setCreateDate(date);
		_account.setModifiedDate(date);
		_account.setName("Liferay");
		_account.setLegalName("Liferay, Inc.");
	}

	private void _initClassNames() {
		_classNames = new ArrayList<ClassName>();

		List<String> models = ModelHintsUtil.getModels();

		for (String model : models) {
			ClassName className = new ClassNameImpl();

			long classNameId = _counter.get();

			className.setClassNameId(classNameId);

			className.setValue(model);

			_classNames.add(className);

			if (model.equals(BlogsEntry.class.getName())) {
				_blogsEntryClassNameId = classNameId;
			}
			else if (model.equals(DDLRecordSet.class.getName())) {
				_ddlRecordSetClassNameId = classNameId;
			}
			else if (model.equals(DDMContent.class.getName())) {
				_ddmContentClassNameId = classNameId;
			}
			else if (model.equals(DLFileEntry.class.getName())) {
				_dlFileEntryClassNameId = classNameId;
			}
			else if (model.equals(DLFileEntryMetadata.class.getName())) {
				_dlFileEntryMetaDataClassNameId = classNameId;
			}
			else if (model.equals(Group.class.getName())) {
				_groupClassNameId = classNameId;
			}
			else if (model.equals(JournalArticle.class.getName())) {
				_journalArticleClassNameId = classNameId;
			}
			else if (model.equals(MBMessage.class.getName())) {
				_mbMessageClassNameId = classNameId;
			}
			else if (model.equals(MBDiscussion.class.getName())) {
				_mbDiscussionClassNameId = classNameId;
			}
			else if (model.equals(Role.class.getName())) {
				_roleClassNameId = classNameId;
			}
			else if (model.equals(User.class.getName())) {
				_userClassNameId = classNameId;
			}
			else if (model.equals(WikiPage.class.getName())) {
				_wikiPageClassNameId = classNameId;
			}
		}
	}

	private void _initCompany() {
		_company = new CompanyImpl();

		_company.setCompanyId(_counter.get());
		_company.setAccountId(_counter.get());
		_company.setWebId("liferay.com");
		_company.setMx("liferay.com");
		_company.setActive(true);
	}

	private void _initDDMStructures() throws Exception {
		_ddm_structure_ddl = StringUtil.read(
			new FileInputStream(
				new File(
					_baseDir, _DEPENDENCIES_DIR + "ddm_structure_ddl.xml")));
		_ddm_structure_basic_document = StringUtil.read(
			new FileInputStream(
				new File(
					_baseDir,
					_DEPENDENCIES_DIR + "ddm_structure_basic_document.xml")));
	}

	private void _initDefaultUsers() {
		_defaultUser = newUser(
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, true);
		_guestUser = newUser("Test", "Test", "Test", false);
		_sampleUser = newUser("Test0", "Test0", "Test0", false);
	}

	private void _initDLFileEntryType() {
		_defaultDLFileEntryType = new DLFileEntryTypeImpl();

		Date date = new Date();

		_defaultDLFileEntryType.setUuid(PortalUUIDUtil.generate());
		_defaultDLFileEntryType.setFileEntryTypeId(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		_defaultDLFileEntryType.setCreateDate(date);
		_defaultDLFileEntryType.setModifiedDate(date);
		_defaultDLFileEntryType.setName(
			DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT);
	}

	private void _initGroups() throws Exception {
		_guestGroup = newGroup(
			0, _groupClassNameId, 0, GroupConstants.GUEST, "/guest", true);

		_groups = new ArrayList<Group>(_maxGroupCount);

		for (int i = 1; i <= _maxGroupCount; i++) {
			String name = "community" + Long.toString(i);

			Group group = newGroup(
				i, _groupClassNameId, i, name, StringPool.FORWARD_SLASH + name,
				true);

			_groups.add(group);
		}
	}

	private void _initJournalArticle() throws Exception {
		int articleSize = _maxJournalArticleSize;

		if (_maxJournalArticleSize <= 0) {
			articleSize = 1;
		}

		char[] chars = new char[articleSize];

		for (int i = 0; i < articleSize; i++) {
			chars[i] = (char)(CharPool.LOWER_CASE_A + (i % 26));
		}

		_journalArticleContent = _replaceToken(
			_JOURNAL_ARTICLE_CONTENT, _JOURNAL_ARTICLE_CONTENT_TOKEN,
			new String(chars));
	}

	private void _initRoles() {
		_roles = new ArrayList<Role>();

		// Administrator

		_administratorRole = newRole(
			RoleConstants.ADMINISTRATOR, RoleConstants.TYPE_REGULAR);

		_roles.add(_administratorRole);

		// Guest

		_guestRole = newRole(RoleConstants.GUEST, RoleConstants.TYPE_REGULAR);

		_roles.add(_guestRole);

		// Organization Administrator

		_organizationAdministratorRole = newRole(
			RoleConstants.ORGANIZATION_ADMINISTRATOR,
			RoleConstants.TYPE_ORGANIZATION);

		_roles.add(_organizationAdministratorRole);

		// Organization Owner

		_organizationOwnerRole = newRole(
			RoleConstants.ORGANIZATION_OWNER, RoleConstants.TYPE_ORGANIZATION);

		_roles.add(_organizationOwnerRole);

		// Organization User

		_organizationUserRole = newRole(
			RoleConstants.ORGANIZATION_USER, RoleConstants.TYPE_ORGANIZATION);

		_roles.add(_organizationUserRole);

		// Owner

		_ownerRole = newRole(RoleConstants.OWNER, RoleConstants.TYPE_REGULAR);

		_roles.add(_ownerRole);

		// Power User

		_powerUserRole = newRole(
			RoleConstants.POWER_USER, RoleConstants.TYPE_REGULAR);

		_roles.add(_powerUserRole);

		// Site Administrator

		_siteAdministratorRole = newRole(
			RoleConstants.SITE_ADMINISTRATOR, RoleConstants.TYPE_SITE);

		_roles.add(_siteAdministratorRole);

		// Site Member

		_siteMemberRole = newRole(
			RoleConstants.SITE_MEMBER, RoleConstants.TYPE_SITE);

		_roles.add(_siteMemberRole);

		// Site Owner

		_siteOwnerRole = newRole(
			RoleConstants.SITE_OWNER, RoleConstants.TYPE_SITE);

		_roles.add(_siteOwnerRole);

		// User

		_userRole = newRole(RoleConstants.USER, RoleConstants.TYPE_REGULAR);

		_roles.add(_userRole);
	}

	private void _initSimpleCounters() {
		_counter = new SimpleCounter(_maxGroupCount + 1);
		_dlDateCounter = new SimpleCounter();
		_layoutCounters = new HashMap<Long, SimpleCounter>();
		_resourcePermissionCounter = new SimpleCounter();
		_socialActivityCounter = new SimpleCounter();
		_userScreenNameIncrementer = new SimpleCounter();
	}

	private void _initUserNames() throws Exception {
		_firstNames = ListUtil.fromFile(
			new File(_baseDir, _DEPENDENCIES_DIR + "first_names.txt"));
		_lastNames = ListUtil.fromFile(
			new File(_baseDir, _DEPENDENCIES_DIR + "last_names.txt"));
	}

	private void _initVirtualHost() throws Exception {
		_virtualHost = new VirtualHostImpl();

		_virtualHost.setVirtualHostId(_counter.get());
		_virtualHost.setCompanyId(_company.getCompanyId());
		_virtualHost.setHostname("localhost");
	}

	private String _replaceToken(String string, String token, String value) {
		int startIndex = 0;
		int endIndex = -1;

		StringBundler sb = new StringBundler();

		while ((endIndex = string.indexOf(token, startIndex)) != -1 ) {
			sb.append(string.substring(startIndex, endIndex));
			sb.append(value);

			startIndex = endIndex + token.length();
		}

		if (startIndex < (string.length() - 1)) {
			sb.append(string.substring(startIndex));
		}

		return sb.toString();
	}

	private static final String _DDM_CONTENT_FOR_DDL =
		"<?xml version=\"1.0\"?><root><dynamic-element name=\"text2102\">" +
			"<dynamic-content><![CDATA[@DDM_CONTENT@]]></dynamic-content>" +
				"</dynamic-element></root>";
	private static final String _DDM_CONTENT_FOR_DL =
		"<?xml version=\"1.0\"?><root><dynamic-element default-language-id=" +
			"\"en_US\" name=\"HttpHeaders_CONTENT_TYPE\"><dynamic-content " +
				"language-id=\"en_US\"><![CDATA[text/plain]]>" +
					"</dynamic-content></dynamic-element><dynamic-element " +
						"default-language-id=\"en_US\" name=\"" +
							"HttpHeaders_CONTENT_ENCODING\"><dynamic-content " +
								"language-id=\"en_US\"><![CDATA[ISO-8859-1]]>"+
									"</dynamic-content></dynamic-element>" +
										"</root>";
	private static final String _DDM_CONTENT_TOKEN = "@DDM_CONTENT@";
	private static final String _DEFAULT_LANGUAGE_ID = "en_US";
	private static final String _DEPENDENCIES_DIR=
		"../portal-impl/src/com/liferay/portal/tools/samplesqlbuilder/" +
			"dependencies/";
	private static final String _JOURNAL_ARTICLE_CONTENT =
		"<?xml version=\"1.0\"?><root available-locales=\"en_US\" " +
			"default-locale=\"en_US\"><static-content language-id=\"en_US\">" +
				"<![CDATA[<p>@JOURNAL_ARTICLE_CONTENT@</p>]]>" +
					"</static-content></root>";
	private static final String _JOURNAL_ARTICLE_CONTENT_TOKEN =
		"@JOURNAL_ARTICLE_CONTENT@";
	private static final String _JOURNAL_ARTICLE_TITLE =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root available-locales=" +
			"\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\"" +
				">Test Journal Article</Title></root>";
	private static final String _LAYOUT_NAME =
		"<?xml version=\"1.0\"?><root><name>@LAYOUT_NAME@</name></root>";
	private static final String _LAYOUT_NAME_TOKEN = "@LAYOUT_NAME@";
	private static final String _PASSWORD = "test";
	private static final String _PORTLET_PREFERENCES_FOR_DDL =
		"<portlet-preferences><preference><name>recordSetId</name><value>" +
			"@RECORD_SET_ID@</value></preference></portlet-preferences>";
	private static final String _PORTLET_PREFERENCES_FOR_DDL_TOKEN =
		"@RECORD_SET_ID@";
	private static final String _PORTLET_PREFERENCES_FOR_JOURNAL =
		"<portlet-preferences><preference><name>articleId</name><value>" +
			"@ARTICLE_ID@</value></preference><preference><name>groupId" +
				"</name><value>@GROUP_ID@</value></preference>" +
					"</portlet-preferences>";
	private static final String _PORTLET_PREFERENCES_FOR_JOURNAL_TOKEN_1 =
		"@ARTICLE_ID@";
	private static final String _PORTLET_PREFERENCES_FOR_JOURNAL_TOKEN_2 =
		"@GROUP_ID@";
	private static final String _QUERY_QUESTION = "what is your screen name?";
	private static final String _XML_FOR_NAME =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root available-locales=" +
			"\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">" +
				"@NAME@</Name></root>";
	private static final String _XML_FOR_NAME_TOKEN = "@NAME@";

	private Account _account;
	private Role _administratorRole;
	private String _baseDir;
	private long _blogsEntryClassNameId;
	private List<ClassName> _classNames;
	private Company _company;
	private SimpleCounter _counter;
	private long _ddlRecordSetClassNameId;

	private String _ddm_structure_basic_document;

	private String _ddm_structure_ddl;
	private long _ddmContentClassNameId;
	private DLFileEntryType _defaultDLFileEntryType;
	private User _defaultUser;
	private SimpleCounter _dlDateCounter;
	private long _dlFileEntryClassNameId;
	private long _dlFileEntryMetaDataClassNameId;
	private List<String> _firstNames;
	private long _futureTime = System.currentTimeMillis() + Time.YEAR;
	private long _groupClassNameId;
	private List<Group> _groups;
	private Group _guestGroup;
	private Role _guestRole;
	private User _guestUser;
	private long _journalArticleClassNameId;
	private String _journalArticleContent;
	private List<String> _lastNames;
	private Map<Long, SimpleCounter> _layoutCounters;
	private int _maxBlogsEntryCount;
	private int _maxDLFileEntrySize;
	private int _maxGroupCount;
	private int _maxJournalArticleSize;
	private int _maxMBMessageCount;
	private int _maxMBMessageCountPerCategory;
	private int _maxMBThreadCount;
	private int _maxUserToGroupCount;
	private long _mbDiscussionClassNameId;
	private long _mbMessageClassNameId;
	private Role _organizationAdministratorRole;
	private Role _organizationOwnerRole;
	private Role _organizationUserRole;
	private Role _ownerRole;
	private Role _powerUserRole;
	private SimpleCounter _resourcePermissionCounter;
	private long _roleClassNameId;
	private List<Role> _roles;
	private User _sampleUser;
	private Role _siteAdministratorRole;
	private Role _siteMemberRole;
	private Role _siteOwnerRole;
	private SimpleCounter _socialActivityCounter;
	private long _userClassNameId;
	private Role _userRole;
	private SimpleCounter _userScreenNameIncrementer;
	private VirtualHost _virtualHost;
	private long _wikiPageClassNameId;

}