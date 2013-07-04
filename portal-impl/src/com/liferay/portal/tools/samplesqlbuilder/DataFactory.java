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
import com.liferay.counter.model.CounterModel;
import com.liferay.counter.model.impl.CounterModelImpl;
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
import com.liferay.portal.model.AccountModel;
import com.liferay.portal.model.ClassNameModel;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyModel;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.ContactModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.GroupModel;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutFriendlyURLModel;
import com.liferay.portal.model.LayoutModel;
import com.liferay.portal.model.LayoutSetModel;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletPreferencesModel;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.ResourcePermissionModel;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.RoleModel;
import com.liferay.portal.model.SubscriptionConstants;
import com.liferay.portal.model.SubscriptionModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserModel;
import com.liferay.portal.model.VirtualHostModel;
import com.liferay.portal.model.impl.AccountModelImpl;
import com.liferay.portal.model.impl.ClassNameModelImpl;
import com.liferay.portal.model.impl.CompanyModelImpl;
import com.liferay.portal.model.impl.ContactModelImpl;
import com.liferay.portal.model.impl.GroupModelImpl;
import com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl;
import com.liferay.portal.model.impl.LayoutModelImpl;
import com.liferay.portal.model.impl.LayoutSetModelImpl;
import com.liferay.portal.model.impl.PortletPreferencesModelImpl;
import com.liferay.portal.model.impl.ResourcePermissionModelImpl;
import com.liferay.portal.model.impl.RoleModelImpl;
import com.liferay.portal.model.impl.SubscriptionModelImpl;
import com.liferay.portal.model.impl.UserModelImpl;
import com.liferay.portal.model.impl.VirtualHostModelImpl;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetCategoryModel;
import com.liferay.portlet.asset.model.AssetEntryModel;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagModel;
import com.liferay.portlet.asset.model.AssetTagStatsModel;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.model.AssetVocabularyModel;
import com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl;
import com.liferay.portlet.asset.model.impl.AssetEntryModelImpl;
import com.liferay.portlet.asset.model.impl.AssetTagModelImpl;
import com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl;
import com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.BlogsEntryModel;
import com.liferay.portlet.blogs.model.BlogsStatsUserModel;
import com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl;
import com.liferay.portlet.blogs.social.BlogsActivityKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadataModel;
import com.liferay.portlet.documentlibrary.model.DLFileEntryModel;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeModel;
import com.liferay.portlet.documentlibrary.model.DLFileVersionModel;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderModel;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordModel;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSetConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSetModel;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersionModel;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetModelImpl;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.model.DDMContentModel;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLinkModel;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLinkModel;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureModel;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentModelImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStorageLinkModelImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleModel;
import com.liferay.portlet.journal.model.JournalArticleResourceModel;
import com.liferay.portlet.journal.model.JournalContentSearchModel;
import com.liferay.portlet.journal.model.impl.JournalArticleModelImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceModelImpl;
import com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl;
import com.liferay.portlet.journal.social.JournalActivityKeys;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBCategoryModel;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBDiscussionModel;
import com.liferay.portlet.messageboards.model.MBMailingListModel;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBMessageModel;
import com.liferay.portlet.messageboards.model.MBStatsUserModel;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadFlagModel;
import com.liferay.portlet.messageboards.model.MBThreadModel;
import com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionModelImpl;
import com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadFlagModelImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl;
import com.liferay.portlet.messageboards.social.MBActivityKeys;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityModel;
import com.liferay.portlet.social.model.impl.SocialActivityModelImpl;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiNodeModel;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.model.WikiPageModel;
import com.liferay.portlet.wiki.model.WikiPageResourceModel;
import com.liferay.portlet.wiki.model.impl.WikiNodeModelImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageModelImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageResourceModelImpl;
import com.liferay.portlet.wiki.social.WikiActivityKeys;
import com.liferay.util.PwdGenerator;
import com.liferay.util.SimpleCounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.text.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class DataFactory {

	public DataFactory(
			String baseDir, int maxAssetCategoryCount,
			int maxAssetEntryToAssetCategoryCount,
			int maxAssetEntryToAssetTagCount,
			int maxAssetPublisherFilterRuleCount,
			int maxAssetPublisherPageCount, int maxAssetTagCount,
			int maxAssetVocabularyCount, int maxBlogsEntryCount,
			int maxDDLCustomFieldCount, int maxGroupsCount,
			int maxJournalArticleCount, int maxJournalArticleSize,
			int maxMBCategoryCount, int maxMBThreadCount, int maxMBMessageCount,
			int maxUserToGroupCount)
		throws Exception {

		_baseDir = baseDir;
		_maxAssetCategoryCount = maxAssetCategoryCount;
		_maxAssetEntryToAssetCategoryCount = maxAssetEntryToAssetCategoryCount;
		_maxAssetEntryToAssetTagCount = maxAssetEntryToAssetTagCount;
		_maxAssetPublisherFilterRuleCount = maxAssetPublisherFilterRuleCount;
		_maxAssetPublisherPageCount = maxAssetPublisherPageCount;
		_maxAssetTagCount = maxAssetTagCount;
		_maxAssetVocabularyCount = maxAssetVocabularyCount;
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

		_classNameModels = new ArrayList<ClassNameModel>();

		List<String> models = ModelHintsUtil.getModels();

		for (String model : models) {
			ClassNameModel classNameModel = new ClassNameModelImpl();

			long classNameId = _counter.get();

			classNameModel.setClassNameId(classNameId);

			classNameModel.setValue(model);

			_classNameModels.add(classNameModel);

			_classNameModelsMap.put(model, classNameId);
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
		initAssetTags();
		initCompany();
		initDLFileEntryType();
		initGroups();
		initJournalArticle(maxJournalArticleSize);
		initRoles();
		initUserNames();
		initUsers();
		initVirtualHost();
	}

	public AccountModel getAccount() {
		return _accountModel;
	}

	public RoleModel getAdministratorRole() {
		return _administratorRoleModel;
	}

	public List<AssetCategoryModel> getAssetCategories() {
		List<AssetCategoryModel> allAssetCategoryModels =
			new ArrayList<AssetCategoryModel>();

		for (
			List<AssetCategoryModel> assetCategoryModels :
			_assetCategoryModelsArray) {

			allAssetCategoryModels.addAll(assetCategoryModels);
		}

		return allAssetCategoryModels;
	}

	public List<Long> getAssetCategoryIds(long groupId) {
		SimpleCounter counter = _assetCategoryCounters.get(groupId);

		if (counter == null) {
			counter = new SimpleCounter(0);

			_assetCategoryCounters.put(groupId, counter);
		}

		List<AssetCategoryModel> assetCategoryModels =
			_assetCategoryModelsArray[(int)groupId - 1];

		if ((assetCategoryModels == null) || assetCategoryModels.isEmpty()) {
			return Collections.emptyList();
		}

		List<Long> assetCategoryIds = new ArrayList<Long>(
			_maxAssetEntryToAssetCategoryCount);

		for (int i = 0; i < _maxAssetEntryToAssetCategoryCount; i++) {
			int index = (int)counter.get() % assetCategoryModels.size();

			AssetCategoryModel assetCategoryModel = assetCategoryModels.get(
				index);

			assetCategoryIds.add(assetCategoryModel.getCategoryId());
		}

		return assetCategoryIds;
	}

	public List<Long> getAssetTagIds(long groupId) {
		SimpleCounter counter = _assetTagCounters.get(groupId);

		if (counter == null) {
			counter = new SimpleCounter(0);

			_assetTagCounters.put(groupId, counter);
		}

		List<AssetTagModel> assetTagModels =
			_assetTagModelsArray[(int)groupId - 1];

		if ((assetTagModels == null) || assetTagModels.isEmpty()) {
			return Collections.emptyList();
		}

		List<Long> assetTagIds = new ArrayList<Long>(
			_maxAssetEntryToAssetTagCount);

		for (int i = 0; i < _maxAssetEntryToAssetTagCount; i++) {
			int index = (int)counter.get() % assetTagModels.size();

			AssetTagModel assetTagModel = assetTagModels.get(index);

			assetTagIds.add(assetTagModel.getTagId());
		}

		return assetTagIds;
	}

	public List<AssetTagModel> getAssetTags() {
		List<AssetTagModel> allAssetTagModels = new ArrayList<AssetTagModel>();

		for (List<AssetTagModel> assetTagModels : _assetTagModelsArray) {
			allAssetTagModels.addAll(assetTagModels);
		}

		return allAssetTagModels;
	}

	public List<AssetTagStatsModel> getAssetTagStatsList() {
		List<AssetTagStatsModel> allAssetTagStatsModels =
			new ArrayList<AssetTagStatsModel>();

		for (
			List<AssetTagStatsModel> assetTagStatsModels :
			_assetTagStatsModelsArray) {

			allAssetTagStatsModels.addAll(assetTagStatsModels);
		}

		return allAssetTagStatsModels;
	}

	public List<AssetVocabularyModel> getAssetVocabularies() {
		List<AssetVocabularyModel> allAssetVocabularyModels =
			new ArrayList<AssetVocabularyModel>();

		allAssetVocabularyModels.add(_defaultAssetVocabularyModel);

		for (List<AssetVocabularyModel> assetVocabularyModels :
				_assetVocabularyModelsArray) {

			allAssetVocabularyModels.addAll(assetVocabularyModels);
		}

		return allAssetVocabularyModels;
	}

	public long getBlogsEntryClassNameId() {
		return _classNameModelsMap.get(BlogsEntry.class.getName());
	}

	public List<ClassNameModel> getClassNames() {
		return _classNameModels;
	}

	public CompanyModel getCompany() {
		return _companyModel;
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
		return _classNameModelsMap.get(DDLRecordSet.class.getName());
	}

	public DDMStructureModel getDefaultDLDDMStructure() {
		return _defaultDLDDMStructureModel;
	}

	public long getDefaultDLDDMStructureId() {
		return _defaultDLDDMStructureModel.getStructureId();
	}

	public DLFileEntryTypeModel getDefaultDLFileEntryType() {
		return _defaultDLFileEntryTypeModel;
	}

	public UserModel getDefaultUser() {
		return _defaultUserModel;
	}

	public long getDLFileEntryClassNameId() {
		return _classNameModelsMap.get(DLFileEntry.class.getName());
	}

	public GroupModel getGlobalGroup() {
		return _globalGroupModel;
	}

	public long getGroupClassNameId() {
		return _classNameModelsMap.get(Group.class.getName());
	}

	public List<GroupModel> getGroups() {
		return _groupModels;
	}

	public GroupModel getGuestGroup() {
		return _guestGroupModel;
	}

	public UserModel getGuestUser() {
		return _guestUserModel;
	}

	public long getJournalArticleClassNameId() {
		return _classNameModelsMap.get(JournalArticle.class.getName());
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
		return _classNameModelsMap.get(Layout.class.getName());
	}

	public List<Long> getNewUserGroupIds(long groupId) {
		List<Long> groupIds = new ArrayList<Long>(_maxUserToGroupCount + 1);

		groupIds.add(_guestGroupModel.getGroupId());

		if ((groupId + _maxUserToGroupCount) > _maxGroupsCount) {
			groupId = groupId - _maxUserToGroupCount + 1;
		}

		for (int i = 0; i < _maxUserToGroupCount; i++) {
			groupIds.add(groupId + i);
		}

		return groupIds;
	}

	public RoleModel getPowerUserRole() {
		return _powerUserRoleModel;
	}

	public List<RoleModel> getRoles() {
		return _roleModels;
	}

	public UserModel getSampleUser() {
		return _sampleUserModel;
	}

	public RoleModel getUserRole() {
		return _userRoleModel;
	}

	public VirtualHostModel getVirtualHost() {
		return _virtualHostModel;
	}

	public long getWikiPageClassNameId() {
		return _classNameModelsMap.get(WikiPage.class.getName());
	}

	public void initAssetCateogries() {
		_assetCategoryModelsArray =
			(List<AssetCategoryModel>[])new List<?>[_maxGroupsCount];
		_assetVocabularyModelsArray =
			(List<AssetVocabularyModel>[])new List<?>[_maxGroupsCount];
		_defaultAssetVocabularyModel = newAssetVocabulary(
			_globalGroupId, _defaultUserId, null,
			PropsValues.ASSET_VOCABULARY_DEFAULT);

		StringBundler sb = new StringBundler(4);

		for (int i = 1; i <= _maxGroupsCount; i++) {
			List<AssetVocabularyModel> assetVocabularyModels =
				new ArrayList<AssetVocabularyModel>(_maxAssetVocabularyCount);
			List<AssetCategoryModel> assetCategoryModels =
				new ArrayList<AssetCategoryModel>(
					_maxAssetVocabularyCount * _maxAssetCategoryCount);

			long lastRightCategoryId = 2;

			for (int j = 0; j < _maxAssetVocabularyCount; j++) {
				sb.setIndex(0);

				sb.append("TestVocabulary_");
				sb.append(i);
				sb.append(StringPool.UNDERLINE);
				sb.append(j);

				AssetVocabularyModel assetVocabularyModel = newAssetVocabulary(
					i, _sampleUserId, _SAMPLE_USER_NAME, sb.toString());

				assetVocabularyModels.add(assetVocabularyModel);

				for (int k = 0; k < _maxAssetCategoryCount; k++) {
					sb.setIndex(0);

					sb.append("TestCategory_");
					sb.append(assetVocabularyModel.getVocabularyId());
					sb.append(StringPool.UNDERLINE);
					sb.append(k);

					AssetCategoryModel assetCategoryModel = newAssetCategory(
						i, lastRightCategoryId, sb.toString(),
						assetVocabularyModel.getVocabularyId());

					lastRightCategoryId += 2;

					assetCategoryModels.add(assetCategoryModel);
				}
			}

			_assetCategoryModelsArray[i - 1] = assetCategoryModels;
			_assetVocabularyModelsArray[i - 1] = assetVocabularyModels;
		}
	}

	public void initAssetTags() {
		_assetTagModelsArray =
			(List<AssetTagModel>[])new List<?>[_maxGroupsCount];
		_assetTagStatsModelsArray =
			(List<AssetTagStatsModel>[])new List<?>[_maxGroupsCount];

		for (int i = 1; i <= _maxGroupsCount; i++) {
			List<AssetTagModel> assetTagModels = new ArrayList<AssetTagModel>(
				_maxAssetTagCount);
			List<AssetTagStatsModel> assetTagStatsModels =
				new ArrayList<AssetTagStatsModel>(_maxAssetTagCount * 3);

			for (int j = 0; j < _maxAssetTagCount; j++) {
				AssetTagModel assetTagModel = new AssetTagModelImpl();

				assetTagModel.setTagId(_counter.get());
				assetTagModel.setGroupId(i);
				assetTagModel.setCompanyId(_companyId);
				assetTagModel.setUserId(_sampleUserId);
				assetTagModel.setUserName(_SAMPLE_USER_NAME);
				assetTagModel.setCreateDate(new Date());
				assetTagModel.setModifiedDate(new Date());
				assetTagModel.setName("TestTag_" + i + "_" + j);

				assetTagModels.add(assetTagModel);

				AssetTagStatsModel assetTagStatsModel = newAssetTagStats(
					assetTagModel.getTagId(),
					_classNameModelsMap.get(BlogsEntry.class.getName()));

				assetTagStatsModels.add(assetTagStatsModel);

				assetTagStatsModel = newAssetTagStats(
					assetTagModel.getTagId(),
					_classNameModelsMap.get(JournalArticle.class.getName()));

				assetTagStatsModels.add(assetTagStatsModel);

				assetTagStatsModel = newAssetTagStats(
					assetTagModel.getTagId(),
					_classNameModelsMap.get(WikiPage.class.getName()));

				assetTagStatsModels.add(assetTagStatsModel);
			}

			_assetTagModelsArray[i - 1] = assetTagModels;
			_assetTagStatsModelsArray[i - 1] = assetTagStatsModels;
		}
	}

	public void initCompany() {
		_companyModel = new CompanyModelImpl();

		_companyModel.setCompanyId(_companyId);
		_companyModel.setAccountId(_accountId);
		_companyModel.setWebId("liferay.com");
		_companyModel.setMx("liferay.com");
		_companyModel.setActive(true);

		_accountModel = new AccountModelImpl();

		_accountModel.setAccountId(_accountId);
		_accountModel.setCompanyId(_companyId);
		_accountModel.setCreateDate(new Date());
		_accountModel.setModifiedDate(new Date());
		_accountModel.setName("Liferay");
		_accountModel.setLegalName("Liferay, Inc.");
	}

	public void initDLFileEntryType() {
		_defaultDLFileEntryTypeModel = new DLFileEntryTypeModelImpl();

		_defaultDLFileEntryTypeModel.setUuid(SequentialUUID.generate());
		_defaultDLFileEntryTypeModel.setFileEntryTypeId(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		_defaultDLFileEntryTypeModel.setCreateDate(nextFutureDate());
		_defaultDLFileEntryTypeModel.setModifiedDate(nextFutureDate());
		_defaultDLFileEntryTypeModel.setFileEntryTypeKey(
			DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT.toUpperCase());

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append("<name language-id=\"en_US\">");
		sb.append(DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT);
		sb.append("</name></root>");

		_defaultDLFileEntryTypeModel.setName(sb.toString());

		_defaultDLDDMStructureModel = newDDMStructure(
			_guestGroupId, getDLFileEntryClassNameId(), "TIKARAWMETADATA",
			_dlDDMStructureContent);
	}

	public void initGroups() throws Exception {
		long groupClassNameId = getGroupClassNameId();

		_globalGroupModel = newGroup(
			_globalGroupId, _classNameModelsMap.get(Company.class.getName()),
			_companyId, GroupConstants.GLOBAL, false);

		_guestGroupModel = newGroup(
			_guestGroupId, groupClassNameId, _guestGroupId,
			GroupConstants.GUEST, true);

		_groupModels = new ArrayList<GroupModel>(_maxGroupsCount);

		for (int i = 1; i <= _maxGroupsCount; i++) {
			GroupModel groupModel = newGroup(
				i, groupClassNameId, i, "Site " + i, true);

			_groupModels.add(groupModel);
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
		_roleModels = new ArrayList<RoleModel>();

		// Administrator

		_administratorRoleModel = newRole(
			RoleConstants.ADMINISTRATOR, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_administratorRoleModel);

		// Guest

		_guestRoleModel = newRole(
			RoleConstants.GUEST, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_guestRoleModel);

		// Organization Administrator

		RoleModel organizationAdministratorRoleModel = newRole(
			RoleConstants.ORGANIZATION_ADMINISTRATOR,
			RoleConstants.TYPE_ORGANIZATION);

		_roleModels.add(organizationAdministratorRoleModel);

		// Organization Owner

		RoleModel organizationOwnerRoleModel = newRole(
			RoleConstants.ORGANIZATION_OWNER, RoleConstants.TYPE_ORGANIZATION);

		_roleModels.add(organizationOwnerRoleModel);

		// Organization User

		RoleModel organizationUserRoleModel = newRole(
			RoleConstants.ORGANIZATION_USER, RoleConstants.TYPE_ORGANIZATION);

		_roleModels.add(organizationUserRoleModel);

		// Owner

		_ownerRoleModel = newRole(
			RoleConstants.OWNER, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_ownerRoleModel);

		// Power User

		_powerUserRoleModel = newRole(
			RoleConstants.POWER_USER, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_powerUserRoleModel);

		// Site Administrator

		RoleModel siteAdministratorRoleModel = newRole(
			RoleConstants.SITE_ADMINISTRATOR, RoleConstants.TYPE_SITE);

		_roleModels.add(siteAdministratorRoleModel);

		// Site Member

		_siteMemberRoleModel = newRole(
			RoleConstants.SITE_MEMBER, RoleConstants.TYPE_SITE);

		_roleModels.add(_siteMemberRoleModel);

		// Site Owner

		RoleModel siteOwnerRoleModel = newRole(
			RoleConstants.SITE_OWNER, RoleConstants.TYPE_SITE);

		_roleModels.add(siteOwnerRoleModel);

		// User

		_userRoleModel = newRole(
			RoleConstants.USER, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_userRoleModel);
	}

	public void initUserNames() throws IOException {
		_firstNames = ListUtil.fromFile(
			new File(_baseDir, _DEPENDENCIES_DIR + "first_names.txt"));
		_lastNames = ListUtil.fromFile(
			new File(_baseDir, _DEPENDENCIES_DIR + "last_names.txt"));
	}

	public void initUsers() {
		_defaultUserModel = newUser(
			_defaultUserId, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, true);
		_guestUserModel = newUser(
			_counter.get(), "Test", "Test", "Test", false);
		_sampleUserModel = newUser(
			_sampleUserId, _SAMPLE_USER_NAME, _SAMPLE_USER_NAME,
			_SAMPLE_USER_NAME, false);
	}

	public void initVirtualHost() {
		_virtualHostModel = new VirtualHostModelImpl();

		_virtualHostModel.setVirtualHostId(_counter.get());
		_virtualHostModel.setCompanyId(_companyId);
		_virtualHostModel.setHostname("localhost");
	}

	public AssetEntryModel newAssetEntry(BlogsEntryModel blogsEntryModel) {
		return newAssetEntry(
			blogsEntryModel.getGroupId(), blogsEntryModel.getCreateDate(),
			blogsEntryModel.getModifiedDate(), getBlogsEntryClassNameId(),
			blogsEntryModel.getEntryId(), blogsEntryModel.getUuid(), 0, true,
			ContentTypes.TEXT_HTML, blogsEntryModel.getTitle());
	}

	public AssetEntryModel newAssetEntry(DLFileEntryModel dLFileEntryModel) {
		return newAssetEntry(
			dLFileEntryModel.getGroupId(), dLFileEntryModel.getCreateDate(),
			dLFileEntryModel.getModifiedDate(), getDLFileEntryClassNameId(),
			dLFileEntryModel.getFileEntryId(), dLFileEntryModel.getUuid(),
			dLFileEntryModel.getFileEntryTypeId(), true,
			dLFileEntryModel.getMimeType(), dLFileEntryModel.getTitle());
	}

	public AssetEntryModel newAssetEntry(DLFolderModel dLFolderModel) {
		return newAssetEntry(
			dLFolderModel.getGroupId(), dLFolderModel.getCreateDate(),
			dLFolderModel.getModifiedDate(),
			_classNameModelsMap.get(DLFolder.class.getName()),
			dLFolderModel.getFolderId(), dLFolderModel.getUuid(), 0, true, null,
			dLFolderModel.getName());
	}

	public AssetEntryModel newAssetEntry(
		JournalArticleModel journalArticleModel) {

		return newAssetEntry(
			journalArticleModel.getGroupId(),
			journalArticleModel.getCreateDate(),
			journalArticleModel.getModifiedDate(),
			getJournalArticleClassNameId(),
			journalArticleModel.getResourcePrimKey(),
			journalArticleModel.getUuid(), 0, true, ContentTypes.TEXT_HTML,
			journalArticleModel.getTitle());
	}

	public AssetEntryModel newAssetEntry(MBMessageModel mbMessageModel) {
		long classNameId = 0;
		boolean visible = false;

		if (mbMessageModel.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID) {

			classNameId = _classNameModelsMap.get(MBDiscussion.class.getName());
		}
		else {
			classNameId = _classNameModelsMap.get(MBMessage.class.getName());
			visible = true;
		}

		return newAssetEntry(
			mbMessageModel.getGroupId(), mbMessageModel.getCreateDate(),
			mbMessageModel.getModifiedDate(), classNameId,
			mbMessageModel.getMessageId(), mbMessageModel.getUuid(), 0, visible,
			ContentTypes.TEXT_HTML, mbMessageModel.getSubject());
	}

	public AssetEntryModel newAssetEntry(MBThreadModel mbThreadModel) {
		return newAssetEntry(
			mbThreadModel.getGroupId(), mbThreadModel.getCreateDate(),
			mbThreadModel.getModifiedDate(),
			_classNameModelsMap.get(MBThread.class.getName()),
			mbThreadModel.getThreadId(), mbThreadModel.getUuid(), 0, false,
			StringPool.BLANK, String.valueOf(mbThreadModel.getRootMessageId()));
	}

	public AssetEntryModel newAssetEntry(WikiPageModel wikiPageModel) {
		return newAssetEntry(
			wikiPageModel.getGroupId(), wikiPageModel.getCreateDate(),
			wikiPageModel.getModifiedDate(), getWikiPageClassNameId(),
			wikiPageModel.getResourcePrimKey(), wikiPageModel.getUuid(), 0,
			true, ContentTypes.TEXT_HTML, wikiPageModel.getTitle());
	}

	public List<PortletPreferencesModel> newAssetPublisherPortletPreferences(
		long plid) {

		List<PortletPreferencesModel> portletPreferencesModels =
			new ArrayList<PortletPreferencesModel>(4);

		portletPreferencesModels.add(
			newPortletPreferences(
				plid, PortletKeys.BLOGS, PortletConstants.DEFAULT_PREFERENCES));
		portletPreferencesModels.add(
			newPortletPreferences(
				plid, PortletKeys.DOCKBAR,
				PortletConstants.DEFAULT_PREFERENCES));
		portletPreferencesModels.add(
			newPortletPreferences(
				plid, PortletKeys.JOURNAL,
				PortletConstants.DEFAULT_PREFERENCES));
		portletPreferencesModels.add(
			newPortletPreferences(
				plid, PortletKeys.WIKI, PortletConstants.DEFAULT_PREFERENCES));

		return portletPreferencesModels;
	}

	public BlogsEntryModel newBlogsEntry(long groupId, int index) {
		BlogsEntryModel blogsEntryModel = new BlogsEntryModelImpl();

		blogsEntryModel.setUuid(SequentialUUID.generate());
		blogsEntryModel.setEntryId(_counter.get());
		blogsEntryModel.setGroupId(groupId);
		blogsEntryModel.setCompanyId(_companyId);
		blogsEntryModel.setUserId(_sampleUserId);
		blogsEntryModel.setUserName(_SAMPLE_USER_NAME);
		blogsEntryModel.setCreateDate(new Date());
		blogsEntryModel.setModifiedDate(new Date());
		blogsEntryModel.setTitle("Test Blog " + index);
		blogsEntryModel.setUrlTitle("testblog" + index);
		blogsEntryModel.setContent("This is test blog " + index + ".");
		blogsEntryModel.setDisplayDate(new Date());
		blogsEntryModel.setStatusDate(new Date());

		return blogsEntryModel;
	}

	public BlogsStatsUserModel newBlogsStatsUser(long groupId) {
		BlogsStatsUserModel blogsStatsUserModel = new BlogsStatsUserModelImpl();

		blogsStatsUserModel.setStatsUserId(_counter.get());
		blogsStatsUserModel.setGroupId(groupId);
		blogsStatsUserModel.setCompanyId(_companyId);
		blogsStatsUserModel.setUserId(_sampleUserId);
		blogsStatsUserModel.setEntryCount(_maxBlogsEntryCount);
		blogsStatsUserModel.setLastPostDate(new Date());

		return blogsStatsUserModel;
	}

	public ContactModel newContact(UserModel userModel) {
		ContactModel contactModel = new ContactModelImpl();

		contactModel.setContactId(userModel.getContactId());
		contactModel.setCompanyId(userModel.getCompanyId());
		contactModel.setUserId(userModel.getUserId());

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String fullName = fullNameGenerator.getFullName(
			userModel.getFirstName(), userModel.getMiddleName(),
			userModel.getLastName());

		contactModel.setUserName(fullName);
		contactModel.setCreateDate(new Date());
		contactModel.setModifiedDate(new Date());
		contactModel.setClassNameId(
			_classNameModelsMap.get(User.class.getName()));
		contactModel.setClassPK(userModel.getUserId());
		contactModel.setAccountId(_accountId);
		contactModel.setParentContactId(
			ContactConstants.DEFAULT_PARENT_CONTACT_ID);
		contactModel.setEmailAddress(userModel.getEmailAddress());
		contactModel.setFirstName(userModel.getFirstName());
		contactModel.setLastName(userModel.getLastName());
		contactModel.setMale(true);
		contactModel.setBirthday(new Date());

		return contactModel;
	}

	public List<CounterModel> newCounters() {
		List<CounterModel> counterModels = new ArrayList<CounterModel>();

		// Counter

		CounterModel counterModel = new CounterModelImpl();

		counterModel.setName(Counter.class.getName());
		counterModel.setCurrentId(_counter.get());

		counterModels.add(counterModel);

		// ResourcePermission

		counterModel = new CounterModelImpl();

		counterModel.setName(ResourcePermission.class.getName());
		counterModel.setCurrentId(_resourcePermissionCounter.get());

		counterModels.add(counterModel);

		// SocialActivity

		counterModel = new CounterModelImpl();

		counterModel.setName(SocialActivity.class.getName());
		counterModel.setCurrentId(_socialActivityCounter.get());

		counterModels.add(counterModel);

		return counterModels;
	}

	public DDMStructureModel newDDLDDMStructure(long groupId) {
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
			groupId, _classNameModelsMap.get(DDLRecordSet.class.getName()),
			"Test DDM Structure", sb.toString());
	}

	public DDLRecordModel newDDLRecord(DDLRecordSetModel dDLRecordSetModel) {
		DDLRecordModel ddlRecordModel = new DDLRecordModelImpl();

		ddlRecordModel.setUuid(SequentialUUID.generate());
		ddlRecordModel.setRecordId(_counter.get());
		ddlRecordModel.setGroupId(dDLRecordSetModel.getGroupId());
		ddlRecordModel.setCompanyId(_companyId);
		ddlRecordModel.setUserId(_sampleUserId);
		ddlRecordModel.setUserName(_SAMPLE_USER_NAME);
		ddlRecordModel.setVersionUserId(_sampleUserId);
		ddlRecordModel.setVersionUserName(_SAMPLE_USER_NAME);
		ddlRecordModel.setCreateDate(new Date());
		ddlRecordModel.setModifiedDate(new Date());
		ddlRecordModel.setDDMStorageId(_counter.get());
		ddlRecordModel.setRecordSetId(dDLRecordSetModel.getRecordSetId());
		ddlRecordModel.setVersion(DDLRecordConstants.VERSION_DEFAULT);
		ddlRecordModel.setDisplayIndex(
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT);

		return ddlRecordModel;
	}

	public DDLRecordSetModel newDDLRecordSet(
		DDMStructureModel ddmStructureModel, int currentIndex) {

		DDLRecordSetModel ddlRecordSetModel = new DDLRecordSetModelImpl();

		ddlRecordSetModel.setUuid(SequentialUUID.generate());
		ddlRecordSetModel.setRecordSetId(_counter.get());
		ddlRecordSetModel.setGroupId(ddmStructureModel.getGroupId());
		ddlRecordSetModel.setCompanyId(_companyId);
		ddlRecordSetModel.setUserId(_sampleUserId);
		ddlRecordSetModel.setUserName(_SAMPLE_USER_NAME);
		ddlRecordSetModel.setCreateDate(new Date());
		ddlRecordSetModel.setModifiedDate(new Date());
		ddlRecordSetModel.setDDMStructureId(ddmStructureModel.getStructureId());
		ddlRecordSetModel.setRecordSetKey(String.valueOf(_counter.get()));

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append("<name language-id=\"en_US\">Test DDL Record Set ");
		sb.append(currentIndex);
		sb.append("</name></root>");

		ddlRecordSetModel.setName(sb.toString());

		ddlRecordSetModel.setMinDisplayRows(
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT);
		ddlRecordSetModel.setScope(
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);

		return ddlRecordSetModel;
	}

	public DDLRecordVersionModel newDDLRecordVersion(
		DDLRecordModel dDLRecordModel) {

		DDLRecordVersionModel ddlRecordVersionModel =
			new DDLRecordVersionModelImpl();

		ddlRecordVersionModel.setRecordVersionId(_counter.get());
		ddlRecordVersionModel.setGroupId(dDLRecordModel.getGroupId());
		ddlRecordVersionModel.setCompanyId(_companyId);
		ddlRecordVersionModel.setUserId(_sampleUserId);
		ddlRecordVersionModel.setUserName(_SAMPLE_USER_NAME);
		ddlRecordVersionModel.setCreateDate(dDLRecordModel.getModifiedDate());
		ddlRecordVersionModel.setDDMStorageId(dDLRecordModel.getDDMStorageId());
		ddlRecordVersionModel.setRecordSetId(dDLRecordModel.getRecordSetId());
		ddlRecordVersionModel.setRecordId(dDLRecordModel.getRecordId());
		ddlRecordVersionModel.setVersion(dDLRecordModel.getVersion());
		ddlRecordVersionModel.setDisplayIndex(dDLRecordModel.getDisplayIndex());
		ddlRecordVersionModel.setStatus(WorkflowConstants.STATUS_APPROVED);
		ddlRecordVersionModel.setStatusDate(dDLRecordModel.getModifiedDate());

		return ddlRecordVersionModel;
	}

	public DDMContentModel newDDMContent(
		DDLRecordModel ddlRecordModel, int currentIndex) {

		StringBundler sb = new StringBundler(3 + _maxDDLCustomFieldCount * 10);

		sb.append("<?xml version=\"1.0\"?><root>");

		for (int i = 0; i < _maxDDLCustomFieldCount; i++) {
			sb.append("<dynamic-element default-language-id=\"en_US\" name=\"");
			sb.append(nextDDLCustomFieldName(ddlRecordModel.getGroupId(), i));
			sb.append("\"><dynamic-content language-id=\"en_US\">");
			sb.append("<![CDATA[Test Record ");
			sb.append(currentIndex);
			sb.append("]]></dynamic-content></dynamic-element>");
		}

		sb.append("<dynamic-element default-language-id=\"en_US\" name=\"_");
		sb.append(
			"fieldsDisplay\"><dynamic-content language-id=\"en_US\"><![CDATA[");

		for (int i = 0; i < _maxDDLCustomFieldCount; i++) {
			sb.append(nextDDLCustomFieldName(ddlRecordModel.getGroupId(), i));
			sb.append(DDMImpl.INSTANCE_SEPARATOR);
			sb.append(PwdGenerator.getPassword(4));
			sb.append(StringPool.COMMA);
		}

		sb.setStringAt(
			"]]></dynamic-content></dynamic-element></root>", sb.index() - 1);

		return newDDMContent(
			ddlRecordModel.getDDMStorageId(), ddlRecordModel.getGroupId(),
			sb.toString());
	}

	public DDMContentModel newDDMContent(DLFileEntryModel dlFileEntryModel) {
		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\"?><root><dynamic-element ");
		sb.append("name=\"CONTENT_TYPE\"><dynamic-content>");
		sb.append("<![CDATA[text/plain]]></dynamic-content></dynamic-element>");
		sb.append("<dynamic-element <![CDATA[ISO-8859-1]]></dynamic-content>");
		sb.append("</dynamic-element></root>");

		return newDDMContent(
			_counter.get(), dlFileEntryModel.getGroupId(), sb.toString());
	}

	public DDMStorageLinkModel newDDMStorageLink(
		long ddmStorageLinkId, DDMContentModel ddmContentModel,
		long structureId) {

		DDMStorageLinkModel ddmStorageLinkModel = new DDMStorageLinkModelImpl();

		ddmStorageLinkModel.setUuid(SequentialUUID.generate());
		ddmStorageLinkModel.setStorageLinkId(ddmStorageLinkId);
		ddmStorageLinkModel.setClassNameId(
			_classNameModelsMap.get(DDMContent.class.getName()));
		ddmStorageLinkModel.setClassPK(ddmContentModel.getContentId());
		ddmStorageLinkModel.setStructureId(structureId);

		return ddmStorageLinkModel;
	}

	public DDMStructureLinkModel newDDMStructureLink(
		DDLRecordSetModel ddlRecordSetModel) {

		return newDDMStructureLink(
			_classNameModelsMap.get(DDLRecordSet.class.getName()),
			ddlRecordSetModel.getRecordSetId(),
			ddlRecordSetModel.getDDMStructureId());
	}

	public DDMStructureLinkModel newDDMStructureLink(
		DLFileEntryMetadataModel dLFileEntryMetadataModel) {

		return newDDMStructureLink(
			_classNameModelsMap.get(DLFileEntryMetadata.class.getName()),
			dLFileEntryMetadataModel.getFileEntryMetadataId(),
			dLFileEntryMetadataModel.getDDMStructureId());
	}

	public DLFileEntryModel newDlFileEntry(
		DLFolderModel dlFolerModel, int index) {

		DLFileEntryModel dlFileEntryModel = new DLFileEntryModelImpl();

		dlFileEntryModel.setUuid(SequentialUUID.generate());
		dlFileEntryModel.setFileEntryId(_counter.get());
		dlFileEntryModel.setGroupId(dlFolerModel.getGroupId());
		dlFileEntryModel.setCompanyId(_companyId);
		dlFileEntryModel.setUserId(_sampleUserId);
		dlFileEntryModel.setUserName(_SAMPLE_USER_NAME);
		dlFileEntryModel.setVersionUserId(_sampleUserId);
		dlFileEntryModel.setVersionUserName(_SAMPLE_USER_NAME);
		dlFileEntryModel.setCreateDate(nextFutureDate());
		dlFileEntryModel.setModifiedDate(nextFutureDate());
		dlFileEntryModel.setRepositoryId(dlFolerModel.getRepositoryId());
		dlFileEntryModel.setFolderId(dlFolerModel.getFolderId());
		dlFileEntryModel.setName("TestFile" + index);
		dlFileEntryModel.setExtension("txt");
		dlFileEntryModel.setMimeType(ContentTypes.TEXT_PLAIN);
		dlFileEntryModel.setTitle("TestFile" + index + ".txt");
		dlFileEntryModel.setFileEntryTypeId(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		dlFileEntryModel.setVersion(DLFileEntryConstants.VERSION_DEFAULT);
		dlFileEntryModel.setSize(_maxDLFileEntrySize);

		return dlFileEntryModel;
	}

	public DLFileEntryMetadataModel newDLFileEntryMetadata(
		long ddmStorageLinkId, long ddmStructureId,
		DLFileVersionModel dlFileVersionModel) {

		DLFileEntryMetadataModel dlFileEntryMetadataModel =
			new DLFileEntryMetadataModelImpl();

		dlFileEntryMetadataModel.setUuid(SequentialUUID.generate());
		dlFileEntryMetadataModel.setFileEntryMetadataId(_counter.get());
		dlFileEntryMetadataModel.setDDMStorageId(ddmStorageLinkId);
		dlFileEntryMetadataModel.setDDMStructureId(ddmStructureId);
		dlFileEntryMetadataModel.setFileEntryTypeId(
			dlFileVersionModel.getFileEntryTypeId());
		dlFileEntryMetadataModel.setFileEntryId(
			dlFileVersionModel.getFileEntryId());
		dlFileEntryMetadataModel.setFileVersionId(
			dlFileVersionModel.getFileVersionId());

		return dlFileEntryMetadataModel;
	}

	public DLFileVersionModel newDLFileVersion(
		DLFileEntryModel dlFileEntryModel) {

		DLFileVersionModel dlFileVersionModel = new DLFileVersionModelImpl();

		dlFileVersionModel.setUuid(SequentialUUID.generate());
		dlFileVersionModel.setFileVersionId(_counter.get());
		dlFileVersionModel.setGroupId(dlFileEntryModel.getGroupId());
		dlFileVersionModel.setCompanyId(_companyId);
		dlFileVersionModel.setUserId(_sampleUserId);
		dlFileVersionModel.setUserName(_SAMPLE_USER_NAME);
		dlFileVersionModel.setCreateDate(nextFutureDate());
		dlFileVersionModel.setModifiedDate(nextFutureDate());
		dlFileVersionModel.setRepositoryId(dlFileEntryModel.getRepositoryId());
		dlFileVersionModel.setFolderId(dlFileEntryModel.getFolderId());
		dlFileVersionModel.setFileEntryId(dlFileEntryModel.getFileEntryId());
		dlFileVersionModel.setExtension(dlFileEntryModel.getExtension());
		dlFileVersionModel.setMimeType(dlFileEntryModel.getMimeType());
		dlFileVersionModel.setTitle(dlFileEntryModel.getTitle());
		dlFileVersionModel.setFileEntryTypeId(
			dlFileEntryModel.getFileEntryTypeId());
		dlFileVersionModel.setVersion(dlFileEntryModel.getVersion());
		dlFileVersionModel.setSize(dlFileEntryModel.getSize());

		return dlFileVersionModel;
	}

	public DLFolderModel newDLFolder(
		long groupId, long parentFolderId, int index) {

		DLFolderModel dlFolderModel = new DLFolderModelImpl();

		dlFolderModel.setUuid(SequentialUUID.generate());
		dlFolderModel.setFolderId(_counter.get());
		dlFolderModel.setGroupId(groupId);
		dlFolderModel.setCompanyId(_companyId);
		dlFolderModel.setUserId(_sampleUserId);
		dlFolderModel.setUserName(_SAMPLE_USER_NAME);
		dlFolderModel.setCreateDate(nextFutureDate());
		dlFolderModel.setModifiedDate(nextFutureDate());
		dlFolderModel.setRepositoryId(groupId);
		dlFolderModel.setParentFolderId(parentFolderId);
		dlFolderModel.setName("Test Folder " + index);
		dlFolderModel.setLastPostDate(nextFutureDate());
		dlFolderModel.setDefaultFileEntryTypeId(
			_defaultDLFileEntryTypeModel.getFileEntryTypeId());
		dlFolderModel.setStatusDate(nextFutureDate());

		return dlFolderModel;
	}

	public GroupModel newGroup(UserModel userModel) throws Exception {
		return newGroup(
			_counter.get(), _classNameModelsMap.get(User.class.getName()),
			userModel.getUserId(), userModel.getScreenName(), false);
	}

	public IntegerWrapper newInteger() {
		return new IntegerWrapper();
	}

	public JournalArticleModel newJournalArticle(
		JournalArticleResourceModel journalArticleResourceModel,
		int articleIndex, int versionIndex) {

		JournalArticleModel journalArticleModel = new JournalArticleModelImpl();

		journalArticleModel.setUuid(SequentialUUID.generate());
		journalArticleModel.setId(_counter.get());
		journalArticleModel.setResourcePrimKey(
			journalArticleResourceModel.getResourcePrimKey());
		journalArticleModel.setGroupId(
			journalArticleResourceModel.getGroupId());
		journalArticleModel.setCompanyId(_companyId);
		journalArticleModel.setUserId(_sampleUserId);
		journalArticleModel.setUserName(_SAMPLE_USER_NAME);
		journalArticleModel.setCreateDate(new Date());
		journalArticleModel.setModifiedDate(new Date());
		journalArticleModel.setClassNameId(
			JournalArticleConstants.CLASSNAME_ID_DEFAULT);
		journalArticleModel.setArticleId(
			journalArticleResourceModel.getArticleId());
		journalArticleModel.setVersion(versionIndex);

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

		journalArticleModel.setTitle(title);
		journalArticleModel.setUrlTitle(urlTitle);

		journalArticleModel.setContent(_journalArticleContent);
		journalArticleModel.setType("general");
		journalArticleModel.setDisplayDate(new Date());
		journalArticleModel.setExpirationDate(nextFutureDate());
		journalArticleModel.setReviewDate(new Date());
		journalArticleModel.setIndexable(true);
		journalArticleModel.setStatusDate(new Date());

		return journalArticleModel;
	}

	public JournalArticleResourceModel newJournalArticleResource(long groupId) {
		JournalArticleResourceModel journalArticleResourceModel =
			new JournalArticleResourceModelImpl();

		journalArticleResourceModel.setUuid(SequentialUUID.generate());
		journalArticleResourceModel.setResourcePrimKey(_counter.get());
		journalArticleResourceModel.setGroupId(groupId);
		journalArticleResourceModel.setArticleId(
			String.valueOf(_counter.get()));

		return journalArticleResourceModel;
	}

	public JournalContentSearchModel newJournalContentSearch(
		JournalArticleModel journalArticleModel, long layoutId) {

		JournalContentSearchModel journalContentSearchModel =
			new JournalContentSearchModelImpl();

		journalContentSearchModel.setContentSearchId(_counter.get());
		journalContentSearchModel.setGroupId(journalArticleModel.getGroupId());
		journalContentSearchModel.setCompanyId(_companyId);
		journalContentSearchModel.setLayoutId(layoutId);
		journalContentSearchModel.setPortletId(PortletKeys.JOURNAL_CONTENT);
		journalContentSearchModel.setArticleId(
			journalArticleModel.getArticleId());

		return journalContentSearchModel;
	}

	public LayoutModel newLayout(
		long groupId, String name, String column1, String column2) {

		SimpleCounter simpleCounter = _layoutCounters.get(groupId);

		if (simpleCounter == null) {
			simpleCounter = new SimpleCounter();

			_layoutCounters.put(groupId, simpleCounter);
		}

		LayoutModel layoutModel = new LayoutModelImpl();

		layoutModel.setUuid(SequentialUUID.generate());
		layoutModel.setPlid(_counter.get());
		layoutModel.setGroupId(groupId);
		layoutModel.setCompanyId(_companyId);
		layoutModel.setUserId(_sampleUserId);
		layoutModel.setUserName(_SAMPLE_USER_NAME);
		layoutModel.setCreateDate(new Date());
		layoutModel.setModifiedDate(new Date());
		layoutModel.setLayoutId(simpleCounter.get());
		layoutModel.setName(
			"<?xml version=\"1.0\"?><root><name>" + name + "</name></root>");
		layoutModel.setType(LayoutConstants.TYPE_PORTLET);
		layoutModel.setFriendlyURL(StringPool.FORWARD_SLASH + name);

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "2_columns_ii");
		typeSettingsProperties.setProperty("column-1", column1);
		typeSettingsProperties.setProperty("column-2", column2);

		String typeSettings = StringUtil.replace(
			typeSettingsProperties.toString(), "\n", "\\n");

		layoutModel.setTypeSettings(typeSettings);

		return layoutModel;
	}

	public LayoutFriendlyURLModel newLayoutFriendlyURL(
		LayoutModel layoutModel) {

		LayoutFriendlyURLModel layoutFriendlyURLModel =
			new LayoutFriendlyURLModelImpl();

		layoutFriendlyURLModel.setUuid(SequentialUUID.generate());
		layoutFriendlyURLModel.setLayoutFriendlyURLId(_counter.get());
		layoutFriendlyURLModel.setGroupId(layoutModel.getGroupId());
		layoutFriendlyURLModel.setCompanyId(_companyId);
		layoutFriendlyURLModel.setUserId(_sampleUserId);
		layoutFriendlyURLModel.setUserName(_SAMPLE_USER_NAME);
		layoutFriendlyURLModel.setCreateDate(new Date());
		layoutFriendlyURLModel.setModifiedDate(new Date());
		layoutFriendlyURLModel.setPlid(layoutModel.getPlid());
		layoutFriendlyURLModel.setFriendlyURL(layoutModel.getFriendlyURL());
		layoutFriendlyURLModel.setLanguageId("en_US");

		return layoutFriendlyURLModel;
	}

	public List<LayoutSetModel> newLayoutSets(
		long groupId, int publicLayoutSetPageCount) {

		List<LayoutSetModel> layoutSetModels = new ArrayList<LayoutSetModel>(2);

		layoutSetModels.add(newLayoutSet(groupId, true, 0));
		layoutSetModels.add(
			newLayoutSet(groupId, false, publicLayoutSetPageCount));

		return layoutSetModels;
	}

	public MBCategoryModel newMBCategory(long groupId, int index) {
		MBCategoryModel mbCategoryModel = new MBCategoryModelImpl();

		mbCategoryModel.setUuid(SequentialUUID.generate());
		mbCategoryModel.setCategoryId(_counter.get());
		mbCategoryModel.setGroupId(groupId);
		mbCategoryModel.setCompanyId(_companyId);
		mbCategoryModel.setUserId(_sampleUserId);
		mbCategoryModel.setUserName(_SAMPLE_USER_NAME);
		mbCategoryModel.setCreateDate(new Date());
		mbCategoryModel.setModifiedDate(new Date());
		mbCategoryModel.setParentCategoryId(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
		mbCategoryModel.setName("Test Category " + index);
		mbCategoryModel.setDisplayStyle(
			MBCategoryConstants.DEFAULT_DISPLAY_STYLE);
		mbCategoryModel.setThreadCount(_maxMBThreadCount);
		mbCategoryModel.setMessageCount(_maxMBThreadCount * _maxMBMessageCount);
		mbCategoryModel.setLastPostDate(new Date());
		mbCategoryModel.setStatusDate(new Date());

		return mbCategoryModel;
	}

	public MBDiscussionModel newMBDiscussion(
		long groupId, long classNameId, long classPK, long threadId) {

		MBDiscussionModel mbDiscussionModel = new MBDiscussionModelImpl();

		mbDiscussionModel.setUuid(SequentialUUID.generate());
		mbDiscussionModel.setDiscussionId(_counter.get());
		mbDiscussionModel.setGroupId(groupId);
		mbDiscussionModel.setCompanyId(_companyId);
		mbDiscussionModel.setUserId(_sampleUserId);
		mbDiscussionModel.setUserName(_SAMPLE_USER_NAME);
		mbDiscussionModel.setCreateDate(new Date());
		mbDiscussionModel.setModifiedDate(new Date());
		mbDiscussionModel.setClassNameId(classNameId);
		mbDiscussionModel.setClassPK(classPK);
		mbDiscussionModel.setThreadId(threadId);

		return mbDiscussionModel;
	}

	public MBMailingListModel newMBMailingList(
		MBCategoryModel mbCategoryModel) {

		MBMailingListModel mbMailingListModel = new MBMailingListModelImpl();

		mbMailingListModel.setUuid(SequentialUUID.generate());
		mbMailingListModel.setMailingListId(_counter.get());
		mbMailingListModel.setGroupId(mbCategoryModel.getGroupId());
		mbMailingListModel.setCompanyId(_companyId);
		mbMailingListModel.setUserId(_sampleUserId);
		mbMailingListModel.setUserName(_SAMPLE_USER_NAME);
		mbMailingListModel.setCreateDate(new Date());
		mbMailingListModel.setModifiedDate(new Date());
		mbMailingListModel.setCategoryId(mbCategoryModel.getCategoryId());
		mbMailingListModel.setInProtocol("pop3");
		mbMailingListModel.setInServerPort(110);
		mbMailingListModel.setInUserName(_sampleUserModel.getEmailAddress());
		mbMailingListModel.setInPassword(_sampleUserModel.getPassword());
		mbMailingListModel.setInReadInterval(5);
		mbMailingListModel.setOutServerPort(25);

		return mbMailingListModel;
	}

	public MBMessageModel newMBMessage(MBThreadModel mbThreadModel, int index) {
		long messageId = 0;
		long parentMessageId = 0;

		if (index == 1) {
			messageId = mbThreadModel.getRootMessageId();
			parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;
		}
		else {
			messageId = _counter.get();
			parentMessageId = mbThreadModel.getRootMessageId();
		}

		return newMBMessage(
			mbThreadModel.getGroupId(), 0, 0, mbThreadModel.getCategoryId(),
			mbThreadModel.getThreadId(), messageId,
			mbThreadModel.getRootMessageId(), parentMessageId,
			"Test Message " + index, "This is test message " + index + ".");
	}

	public MBMessageModel newMBMessage(
		MBThreadModel mbThreadModel, long classNameId, long classPK,
		int index) {

		long messageId = 0;
		long parentMessageId = 0;
		String subject = null;
		String body = null;

		if (index == 0) {
			messageId = mbThreadModel.getRootMessageId();
			parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;
			subject = String.valueOf(classPK);
			body = String.valueOf(classPK);
		}
		else {
			messageId = _counter.get();
			parentMessageId = mbThreadModel.getRootMessageId();
			subject = "N/A";
			body = "This is test comment " + index + ".";
		}

		return newMBMessage(
			mbThreadModel.getGroupId(), classNameId, classPK,
			MBCategoryConstants.DISCUSSION_CATEGORY_ID,
			mbThreadModel.getThreadId(), messageId,
			mbThreadModel.getRootMessageId(), parentMessageId, subject, body);
	}

	public MBStatsUserModel newMBStatsUser(long groupId) {
		MBStatsUserModel mbStatsUserModel = new MBStatsUserModelImpl();

		mbStatsUserModel.setStatsUserId(_counter.get());
		mbStatsUserModel.setGroupId(groupId);
		mbStatsUserModel.setUserId(_sampleUserId);
		mbStatsUserModel.setMessageCount(
			_maxMBCategoryCount * _maxMBThreadCount * _maxMBMessageCount);
		mbStatsUserModel.setLastPostDate(new Date());

		return mbStatsUserModel;
	}

	public MBThreadModel newMBThread(
		long threadId, long groupId, long rootMessageId, int messageCount) {

		if (messageCount == 0) {
			messageCount = 1;
		}

		return newMBThread(
			threadId, groupId, MBCategoryConstants.DISCUSSION_CATEGORY_ID,
			rootMessageId, messageCount);
	}

	public MBThreadModel newMBThread(MBCategoryModel mbCategoryModel) {
		return newMBThread(
			_counter.get(), mbCategoryModel.getGroupId(),
			mbCategoryModel.getCategoryId(), _counter.get(),
			_maxMBMessageCount);
	}

	public MBThreadFlagModel newMBThreadFlag(MBThreadModel mbThreadModel) {
		MBThreadFlagModel mbThreadFlagModel = new MBThreadFlagModelImpl();

		mbThreadFlagModel.setUuid(SequentialUUID.generate());
		mbThreadFlagModel.setThreadFlagId(_counter.get());
		mbThreadFlagModel.setGroupId(mbThreadModel.getGroupId());
		mbThreadFlagModel.setCompanyId(_companyId);
		mbThreadFlagModel.setUserId(_sampleUserId);
		mbThreadFlagModel.setUserName(_SAMPLE_USER_NAME);
		mbThreadFlagModel.setCreateDate(new Date());
		mbThreadFlagModel.setModifiedDate(new Date());
		mbThreadFlagModel.setThreadId(mbThreadModel.getThreadId());

		return mbThreadFlagModel;
	}

	public List<PortletPreferencesModel> newPortletPreferences(long plid) {
		List<PortletPreferencesModel> portletPreferencesModels =
			new ArrayList<PortletPreferencesModel>(2);

		portletPreferencesModels.add(
			newPortletPreferences(
				plid, PortletKeys.DOCKBAR,
				PortletConstants.DEFAULT_PREFERENCES));
		portletPreferencesModels.add(
			newPortletPreferences(
				plid, PortletKeys.PORTLET_CONFIGURATION,
				PortletConstants.DEFAULT_PREFERENCES));

		return portletPreferencesModels;
	}

	public PortletPreferencesModel newPortletPreferences(
			long plid, long groupId, String portletId, int currentIndex)
		throws Exception {

		List<AssetCategoryModel> assetCategoryModels =
			_assetCategoryModelsArray[(int)groupId - 1];

		boolean assetPublisherFilterEnabled = false;

		if ((_maxAssetPublisherPageCount * _maxAssetPublisherFilterRuleCount) >
				0) {

			assetPublisherFilterEnabled = true;
		}

		if (!assetPublisherFilterEnabled || (currentIndex == 1) ||
			(assetCategoryModels == null) || assetCategoryModels.isEmpty()) {

			return newPortletPreferences(
				plid, portletId, PortletConstants.DEFAULT_PREFERENCES);
		}

		SimpleCounter counter = _assetPublisherRuleCounter.get(groupId);

		if (counter == null) {
			counter = new SimpleCounter(0);

			_assetPublisherRuleCounter.put(groupId, counter);
		}

		javax.portlet.PortletPreferences jxPortletPreferences =
			new com.liferay.portlet.PortletPreferencesImpl();

		for (int i = 0; i < _maxAssetPublisherFilterRuleCount; i++) {
			int index = (int)counter.get() % assetCategoryModels.size();

			AssetCategoryModel assetCategoryModel = assetCategoryModels.get(
				index);

			jxPortletPreferences.setValue("queryAndOperator" + i, "false");
			jxPortletPreferences.setValue("queryContains" + i, "false");
			jxPortletPreferences.setValue("queryName" + i, "assetCategories");
			jxPortletPreferences.setValue(
				"queryValues" + i,
				String.valueOf(assetCategoryModel.getCategoryId()));
		}

		return newPortletPreferences(
			plid, portletId,
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));
	}

	public PortletPreferencesModel newPortletPreferences(
			long plid, String portletId, DDLRecordSetModel ddlRecordSetModel)
		throws Exception {

		javax.portlet.PortletPreferences jxPortletPreferences =
			new com.liferay.portlet.PortletPreferencesImpl();

		jxPortletPreferences.setValue("editable", "true");
		jxPortletPreferences.setValue(
			"recordSetId", String.valueOf(ddlRecordSetModel.getRecordSetId()));
		jxPortletPreferences.setValue("spreadsheet", "false");

		return newPortletPreferences(
			plid, portletId,
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));
	}

	public PortletPreferencesModel newPortletPreferences(
			long plid, String portletId,
			JournalArticleResourceModel journalArticleResourceModel)
		throws Exception {

		javax.portlet.PortletPreferences jxPortletPreferences =
			new com.liferay.portlet.PortletPreferencesImpl();

		jxPortletPreferences.setValue(
			"articleId", journalArticleResourceModel.getArticleId());
		jxPortletPreferences.setValue("enableCommentRatings", "false");
		jxPortletPreferences.setValue("enableComments", "false");
		jxPortletPreferences.setValue("enablePrint", "false");
		jxPortletPreferences.setValue("enableRatings", "false");
		jxPortletPreferences.setValue("enableRelatedAssets", "true");
		jxPortletPreferences.setValue("enableViewCountIncrement", "false");
		jxPortletPreferences.setValue(
			"groupId",
			String.valueOf(journalArticleResourceModel.getGroupId()));
		jxPortletPreferences.setValue("showAvailableLocales", "false");

		return newPortletPreferences(
			plid, portletId,
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));
	}

	public List<LayoutModel> newPublicLayouts(long groupId) {
		List<LayoutModel> layoutModels = new ArrayList<LayoutModel>();

		layoutModels.add(newLayout(groupId, "welcome", "58,", "47,"));
		layoutModels.add(newLayout(groupId, "blogs", "", "33,"));
		layoutModels.add(newLayout(groupId, "document_library", "", "20,"));
		layoutModels.add(newLayout(groupId, "forums", "", "19,"));
		layoutModels.add(newLayout(groupId, "wiki", "", "36,"));

		return layoutModels;
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		AssetCategoryModel assetCategoryModel) {

		return newResourcePermissions(
			AssetCategory.class.getName(),
			String.valueOf(assetCategoryModel.getCategoryId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		AssetTagModel assetTagModel) {

		return newResourcePermissions(
			AssetTag.class.getName(), String.valueOf(assetTagModel.getTagId()),
			_sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		AssetVocabularyModel assetVocabularyModel) {

		if (assetVocabularyModel.getUserId() == _defaultUserId) {
			List<ResourcePermissionModel> resourcePermissionModels =
				new ArrayList<ResourcePermissionModel>(1);

			resourcePermissionModels.add(
				newResourcePermission(
					AssetVocabulary.class.getName(),
					String.valueOf(assetVocabularyModel.getVocabularyId()),
					_ownerRoleModel.getRoleId(), _defaultUserId));

			return resourcePermissionModels;
		}

		return newResourcePermissions(
			AssetVocabulary.class.getName(),
			String.valueOf(assetVocabularyModel.getVocabularyId()),
			_sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		BlogsEntryModel blogsEntryModel) {

		return newResourcePermissions(
			BlogsEntry.class.getName(),
			String.valueOf(blogsEntryModel.getEntryId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		DDLRecordSetModel ddlRecordSetModel) {

		List<ResourcePermissionModel> resourcePermissionModels =
			new ArrayList<ResourcePermissionModel>(1);

		resourcePermissionModels.add(
			newResourcePermission(
				DDLRecordSet.class.getName(),
				String.valueOf(ddlRecordSetModel.getRecordSetId()),
				_ownerRoleModel.getRoleId(), _defaultUserId));

		return resourcePermissionModels;
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		DDMStructureModel ddmStructureModel) {

		List<ResourcePermissionModel> resourcePermissionModels =
			new ArrayList<ResourcePermissionModel>(1);

		resourcePermissionModels.add(
			newResourcePermission(
				DDMStructure.class.getName(),
				String.valueOf(ddmStructureModel.getStructureId()),
				_ownerRoleModel.getRoleId(), _defaultUserId));

		return resourcePermissionModels;
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		DLFileEntryModel dlFileEntryModel) {

		return newResourcePermissions(
			DLFileEntry.class.getName(),
			String.valueOf(dlFileEntryModel.getFileEntryId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		DLFolderModel dlFolderModel) {

		return newResourcePermissions(
			DLFolder.class.getName(),
			String.valueOf(dlFolderModel.getFolderId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		JournalArticleResourceModel journalArticleResourceModel) {

		return newResourcePermissions(
			JournalArticle.class.getName(),
			String.valueOf(journalArticleResourceModel.getResourcePrimKey()),
			_sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		LayoutModel layoutModel) {

		return newResourcePermissions(
			Layout.class.getName(), String.valueOf(layoutModel.getPlid()),
			_sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		MBCategoryModel mbCategoryModel) {

		return newResourcePermissions(
			MBCategory.class.getName(),
			String.valueOf(mbCategoryModel.getCategoryId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		MBMessageModel mbMessageModel) {

		return newResourcePermissions(
			MBMessage.class.getName(),
			String.valueOf(mbMessageModel.getMessageId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		PortletPreferencesModel portletPreferencesModel) {

		String portletId = portletPreferencesModel.getPortletId();

		String name = portletId;

		int index = portletId.indexOf(StringPool.UNDERLINE);

		if (index > 0) {
			name = portletId.substring(0, index);
		}

		String primKey = PortletPermissionUtil.getPrimaryKey(
			portletPreferencesModel.getPlid(), portletId);

		return newResourcePermissions(name, primKey, 0);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		WikiNodeModel wikiNodeModel) {

		return newResourcePermissions(
			WikiNode.class.getName(), String.valueOf(wikiNodeModel.getNodeId()),
			_sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissions(
		WikiPageModel wikiPageModel) {

		return newResourcePermissions(
			WikiPage.class.getName(),
			String.valueOf(wikiPageModel.getResourcePrimKey()), _sampleUserId);
	}

	public SocialActivityModel newSocialActivity(
		BlogsEntryModel blogsEntryModel) {

		return newSocialActivity(
			blogsEntryModel.getGroupId(),
			_classNameModelsMap.get(BlogsEntry.class.getName()),
			blogsEntryModel.getEntryId(), BlogsActivityKeys.ADD_ENTRY,
			"{\"title\":\""+ blogsEntryModel.getTitle() +"\"}");
	}

	public SocialActivityModel newSocialActivity(
		DLFileEntryModel dlFileEntryModel) {

		return newSocialActivity(
			dlFileEntryModel.getGroupId(), getDLFileEntryClassNameId(),
			dlFileEntryModel.getFileEntryId(), DLActivityKeys.ADD_FILE_ENTRY,
			StringPool.BLANK);
	}

	public SocialActivityModel newSocialActivity(
		JournalArticleModel journalArticleModel) {

		int type = JournalActivityKeys.UPDATE_ARTICLE;

		if (journalArticleModel.getVersion() ==
				JournalArticleConstants.VERSION_DEFAULT) {

			type = JournalActivityKeys.ADD_ARTICLE;
		}

		return newSocialActivity(
			journalArticleModel.getGroupId(), getJournalArticleClassNameId(),
			journalArticleModel.getResourcePrimKey(), type,
			"{\"title\":\""+ journalArticleModel.getUrlTitle() +"\"}");
	}

	public SocialActivityModel newSocialActivity(
		MBMessageModel mbMessageModel) {

		long classNameId = mbMessageModel.getClassNameId();
		long classPk = mbMessageModel.getClassPK();

		int type = 0;
		String extraData = null;

		if (classNameId == _classNameModelsMap.get(WikiPage.class.getName())) {
			extraData = "{\"version\":1}";

			type = WikiActivityKeys.ADD_PAGE;
		}
		else if (classNameId == 0) {
			extraData = "{\"title\":\"" + mbMessageModel.getSubject() + "\"}";

			type = MBActivityKeys.ADD_MESSAGE;

			classNameId = _classNameModelsMap.get(MBMessage.class.getName());
			classPk = mbMessageModel.getMessageId();
		}
		else {
			StringBundler sb = new StringBundler(5);

			sb.append("{\"messageId\":\"");
			sb.append(mbMessageModel.getMessageId());
			sb.append("\", \"title\":");
			sb.append(mbMessageModel.getSubject());
			sb.append("}");

			extraData = sb.toString();

			type = SocialActivityConstants.TYPE_ADD_COMMENT;
		}

		return newSocialActivity(
			mbMessageModel.getGroupId(), classNameId, classPk, type, extraData);
	}

	public SubscriptionModel newSubscription(BlogsEntryModel blogsEntryModel) {
		return newSubscription(
			_classNameModelsMap.get(BlogsEntry.class.getName()),
			blogsEntryModel.getEntryId());
	}

	public SubscriptionModel newSubscription(MBThreadModel mBThreadModel) {
		return newSubscription(
			_classNameModelsMap.get(MBThread.class.getName()),
			mBThreadModel.getThreadId());
	}

	public SubscriptionModel newSubscription(WikiPageModel wikiPageModel) {
		return newSubscription(
			_classNameModelsMap.get(WikiPage.class.getName()),
			wikiPageModel.getResourcePrimKey());
	}

	public UserModel newUser(int index) {
		String[] userName = nextUserName(index - 1);

		return newUser(
			_counter.get(), userName[0], userName[1],
			"test" + _userScreenNameCounter.get(), false);
	}

	public WikiNodeModel newWikiNode(long groupId, int index) {
		WikiNodeModel wikiNodeModel = new WikiNodeModelImpl();

		wikiNodeModel.setUuid(SequentialUUID.generate());
		wikiNodeModel.setNodeId(_counter.get());
		wikiNodeModel.setGroupId(groupId);
		wikiNodeModel.setCompanyId(_companyId);
		wikiNodeModel.setUserId(_sampleUserId);
		wikiNodeModel.setUserName(_SAMPLE_USER_NAME);
		wikiNodeModel.setCreateDate(new Date());
		wikiNodeModel.setModifiedDate(new Date());
		wikiNodeModel.setName("Test Node " + index);
		wikiNodeModel.setLastPostDate(new Date());
		wikiNodeModel.setStatusDate(new Date());

		return wikiNodeModel;
	}

	public WikiPageModel newWikiPage(WikiNodeModel wikiNodeModel, int index) {
		WikiPageModel wikiPageModel = new WikiPageModelImpl();

		wikiPageModel.setUuid(SequentialUUID.generate());
		wikiPageModel.setPageId(_counter.get());
		wikiPageModel.setResourcePrimKey(_counter.get());
		wikiPageModel.setGroupId(wikiNodeModel.getGroupId());
		wikiPageModel.setCompanyId(_companyId);
		wikiPageModel.setUserId(_sampleUserId);
		wikiPageModel.setUserName(_SAMPLE_USER_NAME);
		wikiPageModel.setCreateDate(new Date());
		wikiPageModel.setModifiedDate(new Date());
		wikiPageModel.setNodeId(wikiNodeModel.getNodeId());
		wikiPageModel.setTitle("Test Page " + index);
		wikiPageModel.setVersion(WikiPageConstants.VERSION_DEFAULT);
		wikiPageModel.setContent("This is test page " + index + ".");
		wikiPageModel.setFormat(WikiPageConstants.DEFAULT_FORMAT);
		wikiPageModel.setHead(true);

		return wikiPageModel;
	}

	public WikiPageResourceModel newWikiPageResource(
		WikiPageModel wikiPageModel) {

		WikiPageResourceModel wikiPageResourceModel =
			new WikiPageResourceModelImpl();

		wikiPageResourceModel.setUuid(SequentialUUID.generate());
		wikiPageResourceModel.setResourcePrimKey(
			wikiPageModel.getResourcePrimKey());
		wikiPageResourceModel.setNodeId(wikiPageModel.getNodeId());
		wikiPageResourceModel.setTitle(wikiPageModel.getTitle());

		return wikiPageResourceModel;
	}

	public String[] nextUserName(long index) {
		String[] userName = new String[2];

		userName[0] = _firstNames.get(
			(int)(index / _lastNames.size()) % _firstNames.size());
		userName[1] = _lastNames.get((int)(index % _lastNames.size()));

		return userName;
	}

	protected AssetCategoryModel newAssetCategory(
		long groupId, long lastRightCategoryId, String name,
		long vocabularyId) {

		AssetCategoryModel assetCategoryModel = new AssetCategoryModelImpl();

		assetCategoryModel.setUuid(SequentialUUID.generate());
		assetCategoryModel.setCategoryId(_counter.get());
		assetCategoryModel.setGroupId(groupId);
		assetCategoryModel.setCompanyId(_companyId);
		assetCategoryModel.setUserId(_sampleUserId);
		assetCategoryModel.setUserName(_SAMPLE_USER_NAME);
		assetCategoryModel.setCreateDate(new Date());
		assetCategoryModel.setModifiedDate(new Date());
		assetCategoryModel.setParentCategoryId(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
		assetCategoryModel.setLeftCategoryId(lastRightCategoryId++);
		assetCategoryModel.setRightCategoryId(lastRightCategoryId++);
		assetCategoryModel.setName(name);

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append("<Title language-id=\"en_US\">");
		sb.append(name);
		sb.append("</Title></root>");

		assetCategoryModel.setTitle(sb.toString());

		assetCategoryModel.setVocabularyId(vocabularyId);

		return assetCategoryModel;
	}

	protected AssetEntryModel newAssetEntry(
		long groupId, Date createDate, Date modifiedDate, long classNameId,
		long classPK, String uuid, long classTypeId, boolean visible,
		String mimeType, String title) {

		AssetEntryModel assetEntryModel = new AssetEntryModelImpl();

		assetEntryModel.setEntryId(_counter.get());
		assetEntryModel.setGroupId(groupId);
		assetEntryModel.setCompanyId(_companyId);
		assetEntryModel.setUserId(_sampleUserId);
		assetEntryModel.setUserName(_SAMPLE_USER_NAME);
		assetEntryModel.setCreateDate(createDate);
		assetEntryModel.setModifiedDate(modifiedDate);
		assetEntryModel.setClassNameId(classNameId);
		assetEntryModel.setClassPK(classPK);
		assetEntryModel.setClassUuid(uuid);
		assetEntryModel.setClassTypeId(classTypeId);
		assetEntryModel.setVisible(visible);
		assetEntryModel.setStartDate(createDate);
		assetEntryModel.setEndDate(nextFutureDate());
		assetEntryModel.setPublishDate(createDate);
		assetEntryModel.setExpirationDate(nextFutureDate());
		assetEntryModel.setMimeType(mimeType);
		assetEntryModel.setTitle(title);

		return assetEntryModel;
	}

	protected AssetTagStatsModel newAssetTagStats(
		long tagId, long classNameId) {

		AssetTagStatsModel assetTagStatsModel = new AssetTagStatsModelImpl();

		assetTagStatsModel.setTagStatsId(_counter.get());
		assetTagStatsModel.setTagId(tagId);
		assetTagStatsModel.setClassNameId(classNameId);

		return assetTagStatsModel;
	}

	protected AssetVocabularyModel newAssetVocabulary(
		long grouId, long userId, String userName, String name) {

		AssetVocabularyModel assetVocabularyModel =
			new AssetVocabularyModelImpl();

		assetVocabularyModel.setUuid(SequentialUUID.generate());
		assetVocabularyModel.setVocabularyId(_counter.get());
		assetVocabularyModel.setGroupId(grouId);
		assetVocabularyModel.setCompanyId(_companyId);
		assetVocabularyModel.setUserId(userId);
		assetVocabularyModel.setUserName(userName);
		assetVocabularyModel.setCreateDate(new Date());
		assetVocabularyModel.setModifiedDate(new Date());
		assetVocabularyModel.setName(name);

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append("<Title language-id=\"en_US\">");
		sb.append(name);
		sb.append("</Title></root>");

		assetVocabularyModel.setTitle(sb.toString());

		assetVocabularyModel.setSettings(
			"multiValued=true\\nselectedClassNameIds=0");

		return assetVocabularyModel;
	}

	protected DDMContentModel newDDMContent(
		long contentId, long groupId, String xml) {

		DDMContentModel ddmContentModel = new DDMContentModelImpl();

		ddmContentModel.setUuid(SequentialUUID.generate());
		ddmContentModel.setContentId(contentId);
		ddmContentModel.setGroupId(groupId);
		ddmContentModel.setCompanyId(_companyId);
		ddmContentModel.setUserId(_sampleUserId);
		ddmContentModel.setUserName(_SAMPLE_USER_NAME);
		ddmContentModel.setCreateDate(nextFutureDate());
		ddmContentModel.setModifiedDate(nextFutureDate());
		ddmContentModel.setName(DDMStorageLink.class.getName());
		ddmContentModel.setXml(xml);

		return ddmContentModel;
	}

	protected DDMStructureModel newDDMStructure(
		long groupId, long classNameId, String structureKey, String xsd) {

		DDMStructureModel dDMStructureModel = new DDMStructureModelImpl();

		dDMStructureModel.setUuid(SequentialUUID.generate());
		dDMStructureModel.setStructureId(_counter.get());
		dDMStructureModel.setGroupId(groupId);
		dDMStructureModel.setCompanyId(_companyId);
		dDMStructureModel.setUserId(_sampleUserId);
		dDMStructureModel.setUserName(_SAMPLE_USER_NAME);
		dDMStructureModel.setCreateDate(nextFutureDate());
		dDMStructureModel.setModifiedDate(nextFutureDate());
		dDMStructureModel.setClassNameId(classNameId);
		dDMStructureModel.setStructureKey(structureKey);

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append("<name language-id=\"en_US\">");
		sb.append(structureKey);
		sb.append("</name></root>");

		dDMStructureModel.setName(sb.toString());

		dDMStructureModel.setXsd(xsd);
		dDMStructureModel.setStorageType("xml");

		return dDMStructureModel;
	}

	protected DDMStructureLinkModel newDDMStructureLink(
		long classNameId, long classPK, long structureId) {

		DDMStructureLinkModel ddmStructureLinkModel =
			new DDMStructureLinkModelImpl();

		ddmStructureLinkModel.setStructureLinkId(_counter.get());
		ddmStructureLinkModel.setClassNameId(classNameId);
		ddmStructureLinkModel.setClassPK(classPK);
		ddmStructureLinkModel.setStructureId(structureId);

		return ddmStructureLinkModel;
	}

	protected GroupModel newGroup(
			long groupId, long classNameId, long classPK, String name,
			boolean site)
		throws Exception {

		GroupModel groupModel = new GroupModelImpl();

		groupModel.setUuid(SequentialUUID.generate());
		groupModel.setGroupId(groupId);
		groupModel.setCompanyId(_companyId);
		groupModel.setCreatorUserId(_sampleUserId);
		groupModel.setClassNameId(classNameId);
		groupModel.setClassPK(classPK);
		groupModel.setTreePath(
			StringPool.SLASH + groupModel.getGroupId() + StringPool.SLASH);
		groupModel.setName(name);
		groupModel.setFriendlyURL(
			StringPool.FORWARD_SLASH +
				FriendlyURLNormalizerUtil.normalize(name));
		groupModel.setSite(site);
		groupModel.setActive(true);

		return groupModel;
	}

	protected LayoutSetModel newLayoutSet(
		long groupId, boolean privateLayout, int pageCount) {

		LayoutSetModel layoutSetModel = new LayoutSetModelImpl();

		layoutSetModel.setLayoutSetId(_counter.get());
		layoutSetModel.setGroupId(groupId);
		layoutSetModel.setCompanyId(_companyId);
		layoutSetModel.setCreateDate(new Date());
		layoutSetModel.setModifiedDate(new Date());
		layoutSetModel.setPrivateLayout(privateLayout);
		layoutSetModel.setThemeId("classic");
		layoutSetModel.setColorSchemeId("01");
		layoutSetModel.setWapThemeId("mobile");
		layoutSetModel.setWapColorSchemeId("01");
		layoutSetModel.setPageCount(pageCount);

		return layoutSetModel;
	}

	protected MBMessageModel newMBMessage(
		long groupId, long classNameId, long classPK, long categoryId,
		long threadId, long messageId, long rootMessageId, long parentMessageId,
		String subject, String body) {

		MBMessageModel mBMessageModel = new MBMessageModelImpl();

		mBMessageModel.setUuid(SequentialUUID.generate());
		mBMessageModel.setMessageId(messageId);
		mBMessageModel.setGroupId(groupId);
		mBMessageModel.setCompanyId(_companyId);
		mBMessageModel.setUserId(_sampleUserId);
		mBMessageModel.setUserName(_SAMPLE_USER_NAME);
		mBMessageModel.setCreateDate(new Date());
		mBMessageModel.setModifiedDate(new Date());
		mBMessageModel.setClassNameId(classNameId);
		mBMessageModel.setClassPK(classPK);
		mBMessageModel.setCategoryId(categoryId);
		mBMessageModel.setThreadId(threadId);
		mBMessageModel.setRootMessageId(rootMessageId);
		mBMessageModel.setParentMessageId(parentMessageId);
		mBMessageModel.setSubject(subject);
		mBMessageModel.setBody(body);
		mBMessageModel.setFormat(MBMessageConstants.DEFAULT_FORMAT);
		mBMessageModel.setStatusDate(new Date());

		return mBMessageModel;
	}

	protected MBThreadModel newMBThread(
		long threadId, long groupId, long categoryId, long rootMessageId,
		int messageCount) {

		MBThreadModel mbThreadModel = new MBThreadModelImpl();

		mbThreadModel.setUuid(SequentialUUID.generate());
		mbThreadModel.setThreadId(threadId);
		mbThreadModel.setGroupId(groupId);
		mbThreadModel.setCompanyId(_companyId);
		mbThreadModel.setUserId(_sampleUserId);
		mbThreadModel.setUserName(_SAMPLE_USER_NAME);
		mbThreadModel.setCreateDate(new Date());
		mbThreadModel.setModifiedDate(new Date());
		mbThreadModel.setCategoryId(categoryId);
		mbThreadModel.setRootMessageId(rootMessageId);
		mbThreadModel.setRootMessageUserId(_sampleUserId);
		mbThreadModel.setMessageCount(messageCount);
		mbThreadModel.setLastPostByUserId(_sampleUserId);
		mbThreadModel.setLastPostDate(new Date());
		mbThreadModel.setStatusDate(new Date());

		return mbThreadModel;
	}

	protected PortletPreferencesModel newPortletPreferences(
		long plid, String portletId, String preferences) {

		PortletPreferencesModel portletPreferencesModel =
			new PortletPreferencesModelImpl();

		portletPreferencesModel.setPortletPreferencesId(_counter.get());
		portletPreferencesModel.setOwnerId(PortletKeys.PREFS_OWNER_ID_DEFAULT);
		portletPreferencesModel.setOwnerType(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT);
		portletPreferencesModel.setPlid(plid);
		portletPreferencesModel.setPortletId(portletId);
		portletPreferencesModel.setPreferences(preferences);

		return portletPreferencesModel;
	}

	protected ResourcePermissionModel newResourcePermission(
		String name, String primKey, long roleId, long ownerId) {

		ResourcePermissionModel resourcePermissionModel =
			new ResourcePermissionModelImpl();

		resourcePermissionModel.setResourcePermissionId(
			_resourcePermissionCounter.get());
		resourcePermissionModel.setCompanyId(_companyId);
		resourcePermissionModel.setName(name);
		resourcePermissionModel.setScope(ResourceConstants.SCOPE_INDIVIDUAL);
		resourcePermissionModel.setPrimKey(primKey);
		resourcePermissionModel.setRoleId(roleId);
		resourcePermissionModel.setOwnerId(ownerId);
		resourcePermissionModel.setActionIds(1);

		return resourcePermissionModel;
	}

	protected List<ResourcePermissionModel> newResourcePermissions(
		String name, String primKey, long ownerId) {

		List<ResourcePermissionModel> resourcePermissionModels =
			new ArrayList<ResourcePermissionModel>(3);

		resourcePermissionModels.add(
			newResourcePermission(
				name, primKey, _guestRoleModel.getRoleId(), 0));
		resourcePermissionModels.add(
			newResourcePermission(
				name, primKey, _ownerRoleModel.getRoleId(), ownerId));
		resourcePermissionModels.add(
			newResourcePermission(
				name, primKey, _siteMemberRoleModel.getRoleId(), 0));

		return resourcePermissionModels;
	}

	protected RoleModel newRole(String name, int type) {
		RoleModel roleModel = new RoleModelImpl();

		roleModel.setUuid(SequentialUUID.generate());
		roleModel.setRoleId(_counter.get());
		roleModel.setCompanyId(_companyId);
		roleModel.setUserId(_sampleUserId);
		roleModel.setUserName(_SAMPLE_USER_NAME);
		roleModel.setCreateDate(new Date());
		roleModel.setModifiedDate(new Date());
		roleModel.setClassNameId(_classNameModelsMap.get(Role.class.getName()));
		roleModel.setClassPK(roleModel.getRoleId());
		roleModel.setName(name);
		roleModel.setType(type);

		return roleModel;
	}

	protected SocialActivityModel newSocialActivity(
		long groupId, long classNameId, long classPK, int type,
		String extraData) {

		SocialActivityModel socialActivityModel = new SocialActivityModelImpl();

		socialActivityModel.setActivityId(_socialActivityCounter.get());
		socialActivityModel.setGroupId(groupId);
		socialActivityModel.setCompanyId(_companyId);
		socialActivityModel.setUserId(_sampleUserId);
		socialActivityModel.setCreateDate(_CURRENT_TIME + _timeCounter.get());
		socialActivityModel.setClassNameId(classNameId);
		socialActivityModel.setClassPK(classPK);
		socialActivityModel.setType(type);
		socialActivityModel.setExtraData(extraData);

		return socialActivityModel;
	}

	protected SubscriptionModel newSubscription(
		long classNameId, long classPK) {

		SubscriptionModel subscriptionModel = new SubscriptionModelImpl();

		subscriptionModel.setSubscriptionId(_counter.get());
		subscriptionModel.setCompanyId(_companyId);
		subscriptionModel.setUserId(_sampleUserId);
		subscriptionModel.setUserName(_SAMPLE_USER_NAME);
		subscriptionModel.setCreateDate(new Date());
		subscriptionModel.setModifiedDate(new Date());
		subscriptionModel.setClassNameId(classNameId);
		subscriptionModel.setClassPK(classPK);
		subscriptionModel.setFrequency(SubscriptionConstants.FREQUENCY_INSTANT);

		return subscriptionModel;
	}

	protected UserModel newUser(
		long userId, String firstName, String lastName, String screenName,
		boolean defaultUser) {

		if (Validator.isNull(screenName)) {
			screenName = String.valueOf(userId);
		}

		UserModel userModel = new UserModelImpl();

		userModel.setUuid(SequentialUUID.generate());
		userModel.setUserId(userId);
		userModel.setCompanyId(_companyId);
		userModel.setCreateDate(new Date());
		userModel.setModifiedDate(new Date());
		userModel.setDefaultUser(defaultUser);
		userModel.setContactId(_counter.get());
		userModel.setPassword("test");
		userModel.setPasswordModifiedDate(new Date());
		userModel.setReminderQueryQuestion("What is your screen name?");
		userModel.setReminderQueryAnswer(screenName);
		userModel.setEmailAddress(screenName + "@liferay.com");
		userModel.setScreenName(screenName);
		userModel.setLanguageId("en_US");
		userModel.setGreeting("Welcome " + screenName + StringPool.EXCLAMATION);
		userModel.setFirstName(firstName);
		userModel.setLastName(lastName);
		userModel.setLoginDate(new Date());
		userModel.setLastLoginDate(new Date());
		userModel.setLastFailedLoginDate(new Date());
		userModel.setLockoutDate(new Date());
		userModel.setAgreedToTermsOfUse(true);
		userModel.setEmailAddressVerified(true);

		return userModel;
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

	private long _accountId;
	private AccountModel _accountModel;
	private RoleModel _administratorRoleModel;
	private Map<Long, SimpleCounter> _assetCategoryCounters =
		new HashMap<Long, SimpleCounter>();
	private List<AssetCategoryModel>[] _assetCategoryModelsArray;
	private Map<Long, SimpleCounter> _assetPublisherRuleCounter =
		new HashMap<Long, SimpleCounter>();
	private Map<Long, SimpleCounter> _assetTagCounters =
		new HashMap<Long, SimpleCounter>();
	private List<AssetTagModel>[] _assetTagModelsArray;
	private List<AssetTagStatsModel>[] _assetTagStatsModelsArray;
	private List<AssetVocabularyModel>[] _assetVocabularyModelsArray;
	private String _baseDir;
	private List<ClassNameModel> _classNameModels;
	private Map<String, Long> _classNameModelsMap = new HashMap<String, Long>();
	private long _companyId;
	private CompanyModel _companyModel;
	private SimpleCounter _counter;
	private AssetVocabularyModel _defaultAssetVocabularyModel;
	private DDMStructureModel _defaultDLDDMStructureModel;
	private DLFileEntryTypeModel _defaultDLFileEntryTypeModel;
	private long _defaultUserId;
	private UserModel _defaultUserModel;
	private String _dlDDMStructureContent;
	private List<String> _firstNames;
	private SimpleCounter _futureDateCounter;
	private long _globalGroupId;
	private GroupModel _globalGroupModel;
	private List<GroupModel> _groupModels;
	private long _guestGroupId;
	private GroupModel _guestGroupModel;
	private RoleModel _guestRoleModel;
	private UserModel _guestUserModel;
	private String _journalArticleContent;
	private List<String> _lastNames;
	private Map<Long, SimpleCounter> _layoutCounters =
		new HashMap<Long, SimpleCounter>();
	private int _maxAssetCategoryCount;
	private int _maxAssetEntryToAssetCategoryCount;
	private int _maxAssetEntryToAssetTagCount;
	private int _maxAssetPublisherFilterRuleCount;
	private int _maxAssetPublisherPageCount;
	private int _maxAssetTagCount;
	private int _maxAssetVocabularyCount;
	private int _maxBlogsEntryCount;
	private int _maxDDLCustomFieldCount;
	private int _maxDLFileEntrySize;
	private int _maxGroupsCount;
	private int _maxJournalArticleCount;
	private int _maxMBCategoryCount;
	private int _maxMBMessageCount;
	private int _maxMBThreadCount;
	private int _maxUserToGroupCount;
	private RoleModel _ownerRoleModel;
	private RoleModel _powerUserRoleModel;
	private SimpleCounter _resourcePermissionCounter;
	private List<RoleModel> _roleModels;
	private long _sampleUserId;
	private UserModel _sampleUserModel;
	private Format _simpleDateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private RoleModel _siteMemberRoleModel;
	private SimpleCounter _socialActivityCounter;
	private SimpleCounter _timeCounter;
	private RoleModel _userRoleModel;
	private SimpleCounter _userScreenNameCounter;
	private VirtualHostModel _virtualHostModel;

}