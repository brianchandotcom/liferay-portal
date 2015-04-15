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

package com.liferay.portal.tools.samplesqlbuilder;

import com.liferay.counter.model.Counter;
import com.liferay.counter.model.CounterModel;
import com.liferay.counter.model.impl.CounterModelImpl;
import com.liferay.journal.web.constants.JournalPortletKeys;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.metadata.RawMetadataProcessor;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
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
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.PortletPreferencesFactoryImpl;
import com.liferay.portlet.PortletPreferencesImpl;
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
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLinkModel;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureModel;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateModel;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentModelImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStorageLinkModelImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateModelImpl;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
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
import com.liferay.util.SimpleCounter;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiNodeModel;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.model.WikiPageModel;
import com.liferay.wiki.model.WikiPageResourceModel;
import com.liferay.wiki.model.impl.WikiNodeModelImpl;
import com.liferay.wiki.model.impl.WikiPageModelImpl;
import com.liferay.wiki.model.impl.WikiPageResourceModelImpl;
import com.liferay.wiki.social.WikiActivityKeys;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.text.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class DataFactory {

	public DataFactory(Properties properties) throws Exception {
		initContext(properties);

		_counter = new SimpleCounter(_maxGroupsCount + 1);
		_timeCounter = new SimpleCounter();
		_futureDateCounter = new SimpleCounter();
		_resourcePermissionCounter = new SimpleCounter();
		_socialActivityCounter = new SimpleCounter();
		_userScreenNameCounter = new SimpleCounter();

		_classNameModels = new ArrayList<>();

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

		List<String> lines = new ArrayList<>();

		StringUtil.readLines(
			getResourceInputStream("ddm_structure_basic_document.json"), lines);

		_dlDDMStructureContent = StringUtil.merge(lines, StringPool.SPACE);

		lines.clear();

		StringUtil.readLines(
			getResourceInputStream("ddm_structure_basic_web_content.json"),
			lines);

		_journalDDMStructureContent = StringUtil.merge(lines, StringPool.SPACE);

		lines.clear();

		String defaultAssetPublisherPreference = StringUtil.read(
			getResourceInputStream("default_asset_publisher_preference.xml"));

		_defaultAssetPublisherPortletPreference =
			(PortletPreferencesImpl)_portletPreferencesFactory.fromDefaultXML(
				defaultAssetPublisherPreference);

		initAssetCategoryModels();
		initAssetTagModels();
		initCompanyModel();
		initDLFileEntryTypeModel();
		initGroupModels();

		int maxJournalArticleSize = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.journal.article.size"));

		initJournalArticleContent(maxJournalArticleSize);

		initRoleModels();
		initUserNames();
		initUserModels();
		initVirtualHostModel(
			properties.getProperty("sample.sql.virtual.hostname"));
	}

	public AccountModel getAccountModel() {
		return _accountModel;
	}

	public RoleModel getAdministratorRoleModel() {
		return _administratorRoleModel;
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

		List<Long> assetCategoryIds = new ArrayList<>(
			_maxAssetEntryToAssetCategoryCount);

		for (int i = 0; i < _maxAssetEntryToAssetCategoryCount; i++) {
			int index = (int)counter.get() % assetCategoryModels.size();

			AssetCategoryModel assetCategoryModel = assetCategoryModels.get(
				index);

			assetCategoryIds.add(assetCategoryModel.getCategoryId());
		}

		return assetCategoryIds;
	}

	public List<AssetCategoryModel> getAssetCategoryModels() {
		List<AssetCategoryModel> allAssetCategoryModels = new ArrayList<>();

		for (List<AssetCategoryModel> assetCategoryModels :
				_assetCategoryModelsArray) {

			allAssetCategoryModels.addAll(assetCategoryModels);
		}

		return allAssetCategoryModels;
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

		List<Long> assetTagIds = new ArrayList<>(_maxAssetEntryToAssetTagCount);

		for (int i = 0; i < _maxAssetEntryToAssetTagCount; i++) {
			int index = (int)counter.get() % assetTagModels.size();

			AssetTagModel assetTagModel = assetTagModels.get(index);

			assetTagIds.add(assetTagModel.getTagId());
		}

		return assetTagIds;
	}

	public List<AssetTagModel> getAssetTagModels() {
		List<AssetTagModel> allAssetTagModels = new ArrayList<>();

		for (List<AssetTagModel> assetTagModels : _assetTagModelsArray) {
			allAssetTagModels.addAll(assetTagModels);
		}

		return allAssetTagModels;
	}

	public List<AssetTagStatsModel> getAssetTagStatsModels() {
		List<AssetTagStatsModel> allAssetTagStatsModels = new ArrayList<>();

		for (List<AssetTagStatsModel> assetTagStatsModels :
				_assetTagStatsModelsArray) {

			allAssetTagStatsModels.addAll(assetTagStatsModels);
		}

		return allAssetTagStatsModels;
	}

	public List<AssetVocabularyModel> getAssetVocabularyModels() {
		List<AssetVocabularyModel> allAssetVocabularyModels = new ArrayList<>();

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

	public long getClassNameId(String className) {
		return _classNameModelsMap.get(className);
	}

	public List<ClassNameModel> getClassNameModels() {
		return _classNameModels;
	}

	public CompanyModel getCompanyModel() {
		return _companyModel;
	}

	public SimpleCounter getCounter() {
		return _counter;
	}

	public long getCounterNext() {
		return _counter.get();
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

	public long getDefaultDLDDMStructureId() {
		return _defaultDLDDMStructureModel.getStructureId();
	}

	public DDMStructureModel getDefaultDLDDMStructureModel() {
		return _defaultDLDDMStructureModel;
	}

	public DLFileEntryTypeModel getDefaultDLFileEntryTypeModel() {
		return _defaultDLFileEntryTypeModel;
	}

	public DDMStructureModel getDefaultJournalDDMStructureModel() {
		return _defaultJournalDDMStructureModel;
	}

	public DDMTemplateModel getDefaultJournalDDMTemplateModel() {
		return _defaultJournalDDMTemplateModel;
	}

	public UserModel getDefaultUserModel() {
		return _defaultUserModel;
	}

	public long getDLFileEntryClassNameId() {
		return _classNameModelsMap.get(DLFileEntry.class.getName());
	}

	public GroupModel getGlobalGroupModel() {
		return _globalGroupModel;
	}

	public long getGroupClassNameId() {
		return _classNameModelsMap.get(Group.class.getName());
	}

	public List<GroupModel> getGroupModels() {
		return _groupModels;
	}

	public GroupModel getGuestGroupModel() {
		return _guestGroupModel;
	}

	public UserModel getGuestUserModel() {
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

	public int getMaxAssetPublisherPageCount() {
		return _maxAssetPublisherPageCount;
	}

	public int getMaxBlogsEntryCommentCount() {
		return _maxBlogsEntryCommentCount;
	}

	public int getMaxDDLRecordCount() {
		return _maxDDLRecordCount;
	}

	public int getMaxDDLRecordSetCount() {
		return _maxDDLRecordSetCount;
	}

	public int getMaxDLFolderDepth() {
		return _maxDLFolderDepth;
	}

	public int getMaxGroupCount() {
		return _maxGroupsCount;
	}

	public int getMaxJournalArticleCount() {
		return _maxJournalArticleCount;
	}

	public int getMaxJournalArticlePageCount() {
		return _maxJournalArticlePageCount;
	}

	public int getMaxJournalArticleVersionCount() {
		return _maxJournalArticleVersionCount;
	}

	public int getMaxWikiPageCommentCount() {
		return _maxWikiPageCommentCount;
	}

	public List<Long> getNewUserGroupIds(long groupId) {
		List<Long> groupIds = new ArrayList<>(_maxUserToGroupCount + 1);

		groupIds.add(_guestGroupModel.getGroupId());

		if ((groupId + _maxUserToGroupCount) > _maxGroupsCount) {
			groupId = groupId - _maxUserToGroupCount + 1;
		}

		for (int i = 0; i < _maxUserToGroupCount; i++) {
			groupIds.add(groupId + i);
		}

		return groupIds;
	}

	public RoleModel getPowerUserRoleModel() {
		return _powerUserRoleModel;
	}

	public List<RoleModel> getRoleModels() {
		return _roleModels;
	}

	public UserModel getSampleUserModel() {
		return _sampleUserModel;
	}

	public List<Integer> getSequence(int size) {
		List<Integer> sequence = new ArrayList<>(size);

		for (int i = 1; i <= size; i++) {
			sequence.add(i);
		}

		return sequence;
	}

	public RoleModel getUserRoleModel() {
		return _userRoleModel;
	}

	public VirtualHostModel getVirtualHostModel() {
		return _virtualHostModel;
	}

	public long getWikiPageClassNameId() {
		return _classNameModelsMap.get(WikiPage.class.getName());
	}

	public void initAssetCategoryModels() {
		_assetCategoryModelsArray =
			(List<AssetCategoryModel>[])new List<?>[_maxGroupsCount];
		_assetVocabularyModelsArray =
			(List<AssetVocabularyModel>[])new List<?>[_maxGroupsCount];
		_defaultAssetVocabularyModel = newAssetVocabularyModel(
			_globalGroupId, _defaultUserId, null,
			PropsValues.ASSET_VOCABULARY_DEFAULT);

		StringBundler sb = new StringBundler(4);

		for (int i = 1; i <= _maxGroupsCount; i++) {
			List<AssetVocabularyModel> assetVocabularyModels = new ArrayList<>(
				_maxAssetVocabularyCount);
			List<AssetCategoryModel> assetCategoryModels = new ArrayList<>(
				_maxAssetVocabularyCount * _maxAssetCategoryCount);

			long lastRightCategoryId = 2;

			for (int j = 0; j < _maxAssetVocabularyCount; j++) {
				sb.setIndex(0);

				sb.append("TestVocabulary_");
				sb.append(i);
				sb.append(StringPool.UNDERLINE);
				sb.append(j);

				AssetVocabularyModel assetVocabularyModel =
					newAssetVocabularyModel(
						i, _sampleUserId, _SAMPLE_USER_NAME, sb.toString());

				assetVocabularyModels.add(assetVocabularyModel);

				for (int k = 0; k < _maxAssetCategoryCount; k++) {
					sb.setIndex(0);

					sb.append("TestCategory_");
					sb.append(assetVocabularyModel.getVocabularyId());
					sb.append(StringPool.UNDERLINE);
					sb.append(k);

					AssetCategoryModel assetCategoryModel =
						newAssetCategoryModel(
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

	public void initAssetTagModels() {
		_assetTagModelsArray =
			(List<AssetTagModel>[])new List<?>[_maxGroupsCount];
		_assetTagStatsModelsArray =
			(List<AssetTagStatsModel>[])new List<?>[_maxGroupsCount];

		for (int i = 1; i <= _maxGroupsCount; i++) {
			List<AssetTagModel> assetTagModels = new ArrayList<>(
				_maxAssetTagCount);
			List<AssetTagStatsModel> assetTagStatsModels = new ArrayList<>(
				_maxAssetTagCount * 3);

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

				AssetTagStatsModel assetTagStatsModel = newAssetTagStatsModel(
					assetTagModel.getTagId(),
					_classNameModelsMap.get(BlogsEntry.class.getName()));

				assetTagStatsModels.add(assetTagStatsModel);

				assetTagStatsModel = newAssetTagStatsModel(
					assetTagModel.getTagId(),
					_classNameModelsMap.get(JournalArticle.class.getName()));

				assetTagStatsModels.add(assetTagStatsModel);

				assetTagStatsModel = newAssetTagStatsModel(
					assetTagModel.getTagId(),
					_classNameModelsMap.get(WikiPage.class.getName()));

				assetTagStatsModels.add(assetTagStatsModel);
			}

			_assetTagModelsArray[i - 1] = assetTagModels;
			_assetTagStatsModelsArray[i - 1] = assetTagStatsModels;
		}
	}

	public void initCompanyModel() {
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

	public void initContext(Properties properties) {
		String timeZoneId = properties.getProperty("sample.sql.db.time.zone");

		if (Validator.isNotNull(timeZoneId)) {
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);

			if (timeZone != null) {
				TimeZone.setDefault(timeZone);

				_simpleDateFormat =
					FastDateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", timeZone);
			}
		}

		_assetPublisherQueryName = GetterUtil.getString(
			properties.getProperty("sample.sql.asset.publisher.query.name"));

		if (!_assetPublisherQueryName.equals("assetCategories")) {
			_assetPublisherQueryName = "assetTags";
		}

		_maxAssetCategoryCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.asset.category.count"));
		_maxAssetEntryToAssetCategoryCount = GetterUtil.getInteger(
			properties.getProperty(
				"sample.sql.max.asset.entry.to.asset.category.count"));
		_maxAssetEntryToAssetTagCount = GetterUtil.getInteger(
			properties.getProperty(
				"sample.sql.max.asset.entry.to.asset.tag.count"));
		_maxAssetPublisherPageCount = GetterUtil.getInteger(
			properties.getProperty(
				"sample.sql.max.asset.publisher.page.count"));
		_maxAssetTagCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.asset.tag.count"));
		_maxAssetVocabularyCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.asset.vocabulary.count"));
		_maxBlogsEntryCommentCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.blogs.entry.comment.count"));
		_maxBlogsEntryCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.blogs.entry.count"));
		_maxDDLCustomFieldCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.ddl.custom.field.count"));
		_maxDDLRecordCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.ddl.record.count"));
		_maxDDLRecordSetCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.ddl.record.set.count"));
		_maxDLFileEntryCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.dl.file.entry.count"));
		_maxDLFileEntrySize = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.dl.file.entry.size"));
		_maxDLFolderCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.dl.folder.count"));
		_maxDLFolderDepth = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.dl.folder.depth"));
		_maxGroupsCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.group.count"));
		_maxJournalArticleCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.journal.article.count"));
		_maxJournalArticlePageCount = GetterUtil.getInteger(
			properties.getProperty(
				"sample.sql.max.journal.article.page.count"));
		_maxJournalArticleVersionCount = GetterUtil.getInteger(
			properties.getProperty(
				"sample.sql.max.journal.article.version.count"));
		_maxMBCategoryCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.mb.category.count"));
		_maxMBMessageCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.mb.message.count"));
		_maxMBThreadCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.mb.thread.count"));
		_maxUserCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.user.count"));
		_maxUserToGroupCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.user.to.group.count"));
		_maxWikiNodeCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.wiki.node.count"));
		_maxWikiPageCommentCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.wiki.page.comment.count"));
		_maxWikiPageCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.wiki.page.count"));
	}

	public void initDLFileEntryTypeModel() {
		_defaultDLFileEntryTypeModel = new DLFileEntryTypeModelImpl();

		_defaultDLFileEntryTypeModel.setUuid(SequentialUUID.generate());
		_defaultDLFileEntryTypeModel.setFileEntryTypeId(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		_defaultDLFileEntryTypeModel.setCreateDate(nextFutureDate());
		_defaultDLFileEntryTypeModel.setModifiedDate(nextFutureDate());
		_defaultDLFileEntryTypeModel.setFileEntryTypeKey(
			StringUtil.toUpperCase(
				DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT));

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append("<name language-id=\"en_US\">");
		sb.append(DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT);
		sb.append("</name></root>");

		_defaultDLFileEntryTypeModel.setName(sb.toString());

		_defaultDLDDMStructureModel = newDDMStructureModel(
			_globalGroupId, _defaultUserId, getDLFileEntryClassNameId(),
			RawMetadataProcessor.TIKA_RAW_METADATA, _dlDDMStructureContent);
		_defaultJournalDDMStructureModel = newDDMStructureModel(
			_globalGroupId, _defaultUserId, getJournalArticleClassNameId(),
			"BASIC-WEB-CONTENT", _journalDDMStructureContent);
		_defaultJournalDDMTemplateModel = newDDMTemplateModel(
			_globalGroupId, _defaultUserId,
			_defaultJournalDDMStructureModel.getStructureId(),
			getJournalArticleClassNameId());
	}

	public void initGroupModels() throws Exception {
		long groupClassNameId = getGroupClassNameId();

		_globalGroupModel = newGroupModel(
			_globalGroupId, _classNameModelsMap.get(Company.class.getName()),
			_companyId, GroupConstants.GLOBAL, false);

		_guestGroupModel = newGroupModel(
			_guestGroupId, groupClassNameId, _guestGroupId,
			GroupConstants.GUEST, true);

		_groupModels = new ArrayList<>(_maxGroupsCount);

		for (int i = 1; i <= _maxGroupsCount; i++) {
			GroupModel groupModel = newGroupModel(
				i, groupClassNameId, i, "Site " + i, true);

			_groupModels.add(groupModel);
		}
	}

	public void initJournalArticleContent(int maxJournalArticleSize) {
		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\"?><root available-locales=\"en_US\" ");
		sb.append("default-locale=\"en_US\"><dynamic-element name=\"content");
		sb.append("\" type=\"text_area\" index-type=\"keyword\" index=\"0\">");
		sb.append("<dynamic-content language-id=\"en_US\"><![CDATA[");

		if (maxJournalArticleSize <= 0) {
			maxJournalArticleSize = 1;
		}

		char[] chars = new char[maxJournalArticleSize];

		for (int i = 0; i < maxJournalArticleSize; i++) {
			chars[i] = (char)(CharPool.LOWER_CASE_A + (i % 26));
		}

		sb.append(new String(chars));

		sb.append("]]></dynamic-content></dynamic-element></root>");

		_journalArticleContent = sb.toString();
	}

	public void initRoleModels() {
		_roleModels = new ArrayList<>();

		// Administrator

		_administratorRoleModel = newRoleModel(
			RoleConstants.ADMINISTRATOR, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_administratorRoleModel);

		// Guest

		_guestRoleModel = newRoleModel(
			RoleConstants.GUEST, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_guestRoleModel);

		// Organization Administrator

		RoleModel organizationAdministratorRoleModel = newRoleModel(
			RoleConstants.ORGANIZATION_ADMINISTRATOR,
			RoleConstants.TYPE_ORGANIZATION);

		_roleModels.add(organizationAdministratorRoleModel);

		// Organization Owner

		RoleModel organizationOwnerRoleModel = newRoleModel(
			RoleConstants.ORGANIZATION_OWNER, RoleConstants.TYPE_ORGANIZATION);

		_roleModels.add(organizationOwnerRoleModel);

		// Organization User

		RoleModel organizationUserRoleModel = newRoleModel(
			RoleConstants.ORGANIZATION_USER, RoleConstants.TYPE_ORGANIZATION);

		_roleModels.add(organizationUserRoleModel);

		// Owner

		_ownerRoleModel = newRoleModel(
			RoleConstants.OWNER, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_ownerRoleModel);

		// Power User

		_powerUserRoleModel = newRoleModel(
			RoleConstants.POWER_USER, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_powerUserRoleModel);

		// Site Administrator

		RoleModel siteAdministratorRoleModel = newRoleModel(
			RoleConstants.SITE_ADMINISTRATOR, RoleConstants.TYPE_SITE);

		_roleModels.add(siteAdministratorRoleModel);

		// Site Member

		_siteMemberRoleModel = newRoleModel(
			RoleConstants.SITE_MEMBER, RoleConstants.TYPE_SITE);

		_roleModels.add(_siteMemberRoleModel);

		// Site Owner

		RoleModel siteOwnerRoleModel = newRoleModel(
			RoleConstants.SITE_OWNER, RoleConstants.TYPE_SITE);

		_roleModels.add(siteOwnerRoleModel);

		// User

		_userRoleModel = newRoleModel(
			RoleConstants.USER, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_userRoleModel);
	}

	public void initUserModels() {
		_defaultUserModel = newUserModel(
			_defaultUserId, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, true);
		_guestUserModel = newUserModel(
			_counter.get(), "Test", "Test", "Test", false);
		_sampleUserModel = newUserModel(
			_sampleUserId, _SAMPLE_USER_NAME, _SAMPLE_USER_NAME,
			_SAMPLE_USER_NAME, false);
	}

	public void initUserNames() throws IOException {
		_firstNames = new ArrayList<>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(getResourceInputStream("first_names.txt")));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			_firstNames.add(line);
		}

		unsyncBufferedReader.close();

		_lastNames = new ArrayList<>();

		unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(getResourceInputStream("last_names.txt")));

		while ((line = unsyncBufferedReader.readLine()) != null) {
			_lastNames.add(line);
		}

		unsyncBufferedReader.close();
	}

	public void initVirtualHostModel(String hostname) {
		_virtualHostModel = new VirtualHostModelImpl();

		_virtualHostModel.setVirtualHostId(_counter.get());
		_virtualHostModel.setCompanyId(_companyId);
		_virtualHostModel.setHostname(hostname);
	}

	public AssetEntryModel newAssetEntryModel(BlogsEntryModel blogsEntryModel) {
		return newAssetEntryModel(
			blogsEntryModel.getGroupId(), blogsEntryModel.getCreateDate(),
			blogsEntryModel.getModifiedDate(), getBlogsEntryClassNameId(),
			blogsEntryModel.getEntryId(), blogsEntryModel.getUuid(), 0, true,
			true, ContentTypes.TEXT_HTML, blogsEntryModel.getTitle());
	}

	public AssetEntryModel newAssetEntryModel(
		DLFileEntryModel dLFileEntryModel) {

		return newAssetEntryModel(
			dLFileEntryModel.getGroupId(), dLFileEntryModel.getCreateDate(),
			dLFileEntryModel.getModifiedDate(), getDLFileEntryClassNameId(),
			dLFileEntryModel.getFileEntryId(), dLFileEntryModel.getUuid(),
			dLFileEntryModel.getFileEntryTypeId(), true, true,
			dLFileEntryModel.getMimeType(), dLFileEntryModel.getTitle());
	}

	public AssetEntryModel newAssetEntryModel(DLFolderModel dLFolderModel) {
		return newAssetEntryModel(
			dLFolderModel.getGroupId(), dLFolderModel.getCreateDate(),
			dLFolderModel.getModifiedDate(),
			_classNameModelsMap.get(DLFolder.class.getName()),
			dLFolderModel.getFolderId(), dLFolderModel.getUuid(), 0, true, true,
			null, dLFolderModel.getName());
	}

	public AssetEntryModel newAssetEntryModel(
		JournalArticleModel journalArticleModel) {

		long resourcePrimKey = journalArticleModel.getResourcePrimKey();

		String resourceUuid = _journalArticleResourceUUIDs.get(resourcePrimKey);

		return newAssetEntryModel(
			journalArticleModel.getGroupId(),
			journalArticleModel.getCreateDate(),
			journalArticleModel.getModifiedDate(),
			getJournalArticleClassNameId(), resourcePrimKey, resourceUuid,
			_defaultJournalDDMStructureModel.getStructureId(),
			journalArticleModel.isIndexable(), true, ContentTypes.TEXT_HTML,
			journalArticleModel.getTitle());
	}

	public AssetEntryModel newAssetEntryModel(MBMessageModel mbMessageModel) {
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

		return newAssetEntryModel(
			mbMessageModel.getGroupId(), mbMessageModel.getCreateDate(),
			mbMessageModel.getModifiedDate(), classNameId,
			mbMessageModel.getMessageId(), mbMessageModel.getUuid(), 0, true,
			visible, ContentTypes.TEXT_HTML, mbMessageModel.getSubject());
	}

	public AssetEntryModel newAssetEntryModel(MBThreadModel mbThreadModel) {
		return newAssetEntryModel(
			mbThreadModel.getGroupId(), mbThreadModel.getCreateDate(),
			mbThreadModel.getModifiedDate(),
			_classNameModelsMap.get(MBThread.class.getName()),
			mbThreadModel.getThreadId(), mbThreadModel.getUuid(), 0, true,
			false, StringPool.BLANK,
			String.valueOf(mbThreadModel.getRootMessageId()));
	}

	public AssetEntryModel newAssetEntryModel(WikiPageModel wikiPageModel) {
		return newAssetEntryModel(
			wikiPageModel.getGroupId(), wikiPageModel.getCreateDate(),
			wikiPageModel.getModifiedDate(), getWikiPageClassNameId(),
			wikiPageModel.getResourcePrimKey(), wikiPageModel.getUuid(), 0,
			true, true, ContentTypes.TEXT_HTML, wikiPageModel.getTitle());
	}

	public List<PortletPreferencesModel>
		newAssetPublisherPortletPreferencesModels(long plid) {

		List<PortletPreferencesModel> portletPreferencesModels =
			new ArrayList<>(4);

		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, PortletKeys.BLOGS, PortletConstants.DEFAULT_PREFERENCES));
		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, PortletKeys.DOCKBAR,
				PortletConstants.DEFAULT_PREFERENCES));
		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, JournalPortletKeys.JOURNAL,
				PortletConstants.DEFAULT_PREFERENCES));
		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, WikiPortletKeys.WIKI,
				PortletConstants.DEFAULT_PREFERENCES));

		return portletPreferencesModels;
	}

	public List<BlogsEntryModel> newBlogsEntryModels(long groupId) {
		List<BlogsEntryModel> blogEntryModels = new ArrayList<>(
			_maxBlogsEntryCount);

		for (int i = 1; i <= _maxBlogsEntryCount; i++) {
			blogEntryModels.add(newBlogsEntryModel(groupId, i));
		}

		return blogEntryModels;
	}

	public BlogsStatsUserModel newBlogsStatsUserModel(long groupId) {
		BlogsStatsUserModel blogsStatsUserModel = new BlogsStatsUserModelImpl();

		blogsStatsUserModel.setStatsUserId(_counter.get());
		blogsStatsUserModel.setGroupId(groupId);
		blogsStatsUserModel.setCompanyId(_companyId);
		blogsStatsUserModel.setUserId(_sampleUserId);
		blogsStatsUserModel.setEntryCount(_maxBlogsEntryCount);
		blogsStatsUserModel.setLastPostDate(new Date());

		return blogsStatsUserModel;
	}

	public ContactModel newContactModel(UserModel userModel) {
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

	public List<CounterModel> newCounterModels() {
		List<CounterModel> counterModels = new ArrayList<>();

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

	public DDMStructureModel newDDLDDMStructureModel(long groupId) {
		StringBundler sb = new StringBundler(3 + _maxDDLCustomFieldCount * 9);

		sb.append("{\"availableLanguageIds\": [\"en_US\"],");
		sb.append("\"defaultLanguageId\": \"en_US\", \"fields\": [");

		for (int i = 0; i < _maxDDLCustomFieldCount; i++) {
			if (i != 0) {
				sb.append(",");
			}

			sb.append("{\"dataType\": \"string\", \"indexType\": \"keyword\"");
			sb.append(", \"label\": {\"en_US\": \"Text");
			sb.append(i);
			sb.append("\"}, \"name\": \"");
			sb.append(nextDDLCustomFieldName(groupId, i));
			sb.append("\", \"readOnly\": false, \"repeatable\": false,");
			sb.append("\"required\": false, \"showLabel\": true, \"type\": ");
			sb.append("\"text\"}");
		}

		sb.append("]}");

		return newDDMStructureModel(
			groupId, _sampleUserId,
			_classNameModelsMap.get(DDLRecordSet.class.getName()),
			"Test DDM Structure", sb.toString());
	}

	public List<PortletPreferencesModel>
		newDDLPortletPreferencesModels(long plid) {

		List<PortletPreferencesModel> portletPreferencesModels =
			new ArrayList<>(2);

		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, PortletKeys.DOCKBAR,
				PortletConstants.DEFAULT_PREFERENCES));
		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, PortletKeys.DYNAMIC_DATA_LIST_DISPLAY,
				PortletConstants.DEFAULT_PREFERENCES));
		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, PortletKeys.DYNAMIC_DATA_LISTS,
				PortletConstants.DEFAULT_PREFERENCES));
		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, PortletKeys.DYNAMIC_DATA_MAPPING,
				PortletConstants.DEFAULT_PREFERENCES));

		return portletPreferencesModels;
	}

	public DDLRecordModel newDDLRecordModel(
		DDLRecordSetModel dDLRecordSetModel) {

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

	public DDLRecordSetModel newDDLRecordSetModel(
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

	public DDLRecordVersionModel newDDLRecordVersionModel(
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

	public DDMContentModel newDDMContentModel(
		DDLRecordModel ddlRecordModel, int currentIndex) {

		StringBundler sb = new StringBundler(3 + _maxDDLCustomFieldCount * 8);

		sb.append("{\"availableLanguageIds\": [\"en_US\"],");
		sb.append("\"defaultLanguageId\": \"en_US\", \"fieldValues\": [");

		for (int i = 0; i < _maxDDLCustomFieldCount; i++) {
			if (i != 0) {
				sb.append(",");
			}

			sb.append("{\"instanceId\": \"");
			sb.append(StringUtil.randomId());
			sb.append("\", \"name\": \"");
			sb.append(nextDDLCustomFieldName(ddlRecordModel.getGroupId(), i));
			sb.append("\", \"value\": {\"en_US\": \"Test Record ");
			sb.append(currentIndex);
			sb.append("\"}}");
		}

		sb.append("]}");

		return newDDMContentModel(
			ddlRecordModel.getDDMStorageId(), ddlRecordModel.getGroupId(),
			sb.toString());
	}

	public DDMContentModel newDDMContentModel(
		DLFileEntryModel dlFileEntryModel) {

		StringBundler sb = new StringBundler(6);

		sb.append("{\"availableLanguageIds\": [\"en_US\"],");
		sb.append("\"defaultLanguageId\": \"en_US\", \"fieldValues\": [{");
		sb.append("\"instanceId\": \"");
		sb.append(StringUtil.randomId());
		sb.append("\", \"name\": \"CONTENT_TYPE\", \"value\": {\"en_US\": ");
		sb.append("\"text/plain\"}}]}");

		return newDDMContentModel(
			_counter.get(), dlFileEntryModel.getGroupId(), sb.toString());
	}

	public DDMStorageLinkModel newDDMStorageLinkModel(
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

	public DDMStructureLinkModel newDDMStructureLinkModel(
		DDLRecordSetModel ddlRecordSetModel) {

		return newDDMStructureLinkModel(
			_classNameModelsMap.get(DDLRecordSet.class.getName()),
			ddlRecordSetModel.getRecordSetId(),
			ddlRecordSetModel.getDDMStructureId());
	}

	public DDMStructureLinkModel newDDMStructureLinkModel(
		DLFileEntryMetadataModel dLFileEntryMetadataModel) {

		return newDDMStructureLinkModel(
			_classNameModelsMap.get(DLFileEntryMetadata.class.getName()),
			dLFileEntryMetadataModel.getFileEntryMetadataId(),
			dLFileEntryMetadataModel.getDDMStructureId());
	}

	public DLFileEntryMetadataModel newDLFileEntryMetadataModel(
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

	public List<DLFileEntryModel> newDlFileEntryModels(
		DLFolderModel dlFolerModel) {

		List<DLFileEntryModel> dlFileEntryModels = new ArrayList<>(
			_maxDLFileEntryCount);

		for (int i = 1; i <= _maxDLFileEntryCount; i++) {
			dlFileEntryModels.add(newDlFileEntryModel(dlFolerModel, i));
		}

		return dlFileEntryModels;
	}

	public DLFileVersionModel newDLFileVersionModel(
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
		dlFileVersionModel.setFileName(dlFileEntryModel.getFileName());
		dlFileVersionModel.setExtension(dlFileEntryModel.getExtension());
		dlFileVersionModel.setMimeType(dlFileEntryModel.getMimeType());
		dlFileVersionModel.setTitle(dlFileEntryModel.getTitle());
		dlFileVersionModel.setFileEntryTypeId(
			dlFileEntryModel.getFileEntryTypeId());
		dlFileVersionModel.setVersion(dlFileEntryModel.getVersion());
		dlFileVersionModel.setSize(dlFileEntryModel.getSize());

		return dlFileVersionModel;
	}

	public List<DLFolderModel> newDLFolderModels(
		long groupId, long parentFolderId) {

		List<DLFolderModel> dlFolderModels = new ArrayList<>(_maxDLFolderCount);

		for (int i = 1; i <= _maxDLFolderCount; i++) {
			dlFolderModels.add(newDLFolderModel(groupId, parentFolderId, i));
		}

		return dlFolderModels;
	}

	public GroupModel newGroupModel(UserModel userModel) throws Exception {
		return newGroupModel(
			_counter.get(), _classNameModelsMap.get(User.class.getName()),
			userModel.getUserId(), userModel.getScreenName(), false);
	}

	public IntegerWrapper newInteger() {
		return new IntegerWrapper();
	}

	public JournalArticleModel newJournalArticleModel(
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
		journalArticleModel.setDDMStructureKey(
			_defaultJournalDDMStructureModel.getStructureKey());
		journalArticleModel.setDDMTemplateKey(
			_defaultJournalDDMTemplateModel.getTemplateKey());
		journalArticleModel.setDisplayDate(new Date());
		journalArticleModel.setExpirationDate(nextFutureDate());
		journalArticleModel.setReviewDate(new Date());
		journalArticleModel.setIndexable(true);
		journalArticleModel.setStatusDate(new Date());

		return journalArticleModel;
	}

	public JournalArticleResourceModel newJournalArticleResourceModel(
		long groupId) {

		JournalArticleResourceModel journalArticleResourceModel =
			new JournalArticleResourceModelImpl();

		journalArticleResourceModel.setUuid(SequentialUUID.generate());
		journalArticleResourceModel.setResourcePrimKey(_counter.get());
		journalArticleResourceModel.setGroupId(groupId);
		journalArticleResourceModel.setArticleId(
			String.valueOf(_counter.get()));

		_journalArticleResourceUUIDs.put(
			journalArticleResourceModel.getPrimaryKey(),
			journalArticleResourceModel.getUuid());

		return journalArticleResourceModel;
	}

	public JournalContentSearchModel newJournalContentSearchModel(
		JournalArticleModel journalArticleModel, long layoutId) {

		JournalContentSearchModel journalContentSearchModel =
			new JournalContentSearchModelImpl();

		journalContentSearchModel.setContentSearchId(_counter.get());
		journalContentSearchModel.setGroupId(journalArticleModel.getGroupId());
		journalContentSearchModel.setCompanyId(_companyId);
		journalContentSearchModel.setLayoutId(layoutId);
		journalContentSearchModel.setPortletId(
			"com_liferay_journal_content_web_portlet_JournalContentPortlet");
		journalContentSearchModel.setArticleId(
			journalArticleModel.getArticleId());

		return journalContentSearchModel;
	}

	public List<PortletPreferencesModel>
		newJournalPortletPreferencesModels(long plid) {

		List<PortletPreferencesModel> portletPreferencesModels =
			new ArrayList<>(2);

		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, PortletKeys.DOCKBAR,
				PortletConstants.DEFAULT_PREFERENCES));
		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, JournalPortletKeys.JOURNAL,
				PortletConstants.DEFAULT_PREFERENCES));

		return portletPreferencesModels;
	}

	public LayoutFriendlyURLModel newLayoutFriendlyURLModel(
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

	public LayoutModel newLayoutModel(
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

	public List<LayoutModel> newLayoutModels(long groupId) {
		List<LayoutModel> layoutModels = new ArrayList<>();

		layoutModels.add(newLayoutModel(groupId, "welcome", "58,", "47,"));
		layoutModels.add(newLayoutModel(groupId, "blogs", "", "33,"));
		layoutModels.add(
			newLayoutModel(groupId, "document_library", "", "20,"));
		layoutModels.add(newLayoutModel(groupId, "forums", "", "19,"));
		layoutModels.add(
			newLayoutModel(groupId, "wiki", "", "36_WAR_wikiweb,"));

		return layoutModels;
	}

	public List<LayoutSetModel> newLayoutSetModels(
		long groupId, int layoutSetPageCount) {

		List<LayoutSetModel> layoutSetModels = new ArrayList<>(2);

		layoutSetModels.add(newLayoutSetModel(groupId, layoutSetPageCount));

		return layoutSetModels;
	}

	public List<MBCategoryModel> newMBCategoryModels(long groupId) {
		List<MBCategoryModel> mbCategoryModels = new ArrayList<>(
			_maxMBCategoryCount);

		for (int i = 1; i <= _maxMBCategoryCount; i++) {
			mbCategoryModels.add(newMBCategoryModel(groupId, i));
		}

		return mbCategoryModels;
	}

	public MBDiscussionModel newMBDiscussionModel(
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

	public MBMailingListModel newMBMailingListModel(
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

	public MBMessageModel newMBMessageModel(
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

		return newMBMessageModel(
			mbThreadModel.getGroupId(), classNameId, classPK,
			MBCategoryConstants.DISCUSSION_CATEGORY_ID,
			mbThreadModel.getThreadId(), messageId,
			mbThreadModel.getRootMessageId(), parentMessageId, subject, body);
	}

	public List<MBMessageModel> newMBMessageModels(
		MBThreadModel mbThreadModel) {

		List<MBMessageModel> mbMessageModels = new ArrayList<>(
			_maxMBMessageCount);

		mbMessageModels.add(
			newMBMessageModel(
				mbThreadModel.getGroupId(), 0, 0, mbThreadModel.getCategoryId(),
				mbThreadModel.getThreadId(), mbThreadModel.getRootMessageId(),
				mbThreadModel.getRootMessageId(),
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, "Test Message 1",
				"This is test message 1."));

		for (int i = 2; i <= _maxMBMessageCount; i++) {
			mbMessageModels.add(
				newMBMessageModel(
					mbThreadModel.getGroupId(), 0, 0,
					mbThreadModel.getCategoryId(), mbThreadModel.getThreadId(),
					_counter.get(), mbThreadModel.getRootMessageId(),
					mbThreadModel.getRootMessageId(), "Test Message " + i,
					"This is test message " + i + "."));
		}

		return mbMessageModels;
	}

	public List<MBMessageModel> newMBMessageModels(
		MBThreadModel mbThreadModel, long classNameId, long classPK,
		int maxMessageCount) {

		List<MBMessageModel> mbMessageModels = new ArrayList<>(maxMessageCount);

		for (int i = 1; i <= maxMessageCount; i++) {
			mbMessageModels.add(
				newMBMessageModel(mbThreadModel, classNameId, classPK, i));
		}

		return mbMessageModels;
	}

	public MBStatsUserModel newMBStatsUserModel(long groupId) {
		MBStatsUserModel mbStatsUserModel = new MBStatsUserModelImpl();

		mbStatsUserModel.setStatsUserId(_counter.get());
		mbStatsUserModel.setGroupId(groupId);
		mbStatsUserModel.setUserId(_sampleUserId);
		mbStatsUserModel.setMessageCount(
			_maxMBCategoryCount * _maxMBThreadCount * _maxMBMessageCount);
		mbStatsUserModel.setLastPostDate(new Date());

		return mbStatsUserModel;
	}

	public MBThreadFlagModel newMBThreadFlagModel(MBThreadModel mbThreadModel) {
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

	public MBThreadModel newMBThreadModel(
		long threadId, long groupId, long rootMessageId, int messageCount) {

		if (messageCount == 0) {
			messageCount = 1;
		}

		return newMBThreadModel(
			threadId, groupId, MBCategoryConstants.DISCUSSION_CATEGORY_ID,
			rootMessageId, messageCount);
	}

	public List<MBThreadModel> newMBThreadModels(
		MBCategoryModel mbCategoryModel) {

		List<MBThreadModel> mbThreadModels = new ArrayList<>(_maxMBThreadCount);

		for (int i = 0; i < _maxMBThreadCount; i++) {
			mbThreadModels.add(
				newMBThreadModel(
					_counter.get(), mbCategoryModel.getGroupId(),
					mbCategoryModel.getCategoryId(), _counter.get(),
					_maxMBMessageCount));
		}

		return mbThreadModels;
	}

	public PortletPreferencesModel newPortletPreferencesModel(
			long plid, long groupId, String portletId, int currentIndex)
		throws Exception {

		if (currentIndex == 1) {
			return newPortletPreferencesModel(
				plid, portletId, PortletConstants.DEFAULT_PREFERENCES);
		}

		SimpleCounter counter = _assetPublisherQueryCounter.get(groupId);

		if (counter == null) {
			counter = new SimpleCounter(0);

			_assetPublisherQueryCounter.put(groupId, counter);
		}

		String[] assetPublisherQueryValues = null;

		if (_assetPublisherQueryName.equals("assetCategories")) {
			List<AssetCategoryModel> assetCategoryModels =
				_assetCategoryModelsArray[(int)groupId - 1];

			if ((assetCategoryModels == null) ||
				assetCategoryModels.isEmpty()) {

				return newPortletPreferencesModel(
					plid, portletId, PortletConstants.DEFAULT_PREFERENCES);
			}

			assetPublisherQueryValues =
				getAssetPublisherAssetCategoriesQueryValues(
					assetCategoryModels, (int)counter.get());
		}
		else {
			List<AssetTagModel> assetTagModels =
				_assetTagModelsArray[(int)groupId - 1];

			if ((assetTagModels == null) || assetTagModels.isEmpty()) {
				return newPortletPreferencesModel(
					plid, portletId, PortletConstants.DEFAULT_PREFERENCES);
			}

			assetPublisherQueryValues = getAssetPublisherAssetTagsQueryValues(
				assetTagModels, (int)counter.get());
		}

		PortletPreferences jxPortletPreferences =
			(PortletPreferences)_defaultAssetPublisherPortletPreference.clone();

		jxPortletPreferences.setValue("queryAndOperator0", "false");
		jxPortletPreferences.setValue("queryContains0", "true");
		jxPortletPreferences.setValue("queryName0", _assetPublisherQueryName);
		jxPortletPreferences.setValues(
			"queryValues0",
			new String[] {
				assetPublisherQueryValues[0], assetPublisherQueryValues[1],
				assetPublisherQueryValues[2]
			});
		jxPortletPreferences.setValue("queryAndOperator1", "false");
		jxPortletPreferences.setValue("queryContains1", "false");
		jxPortletPreferences.setValue("queryName1", _assetPublisherQueryName);
		jxPortletPreferences.setValue(
			"queryValues1", assetPublisherQueryValues[3]);

		return newPortletPreferencesModel(
			plid, portletId,
			_portletPreferencesFactory.toXML(jxPortletPreferences));
	}

	public PortletPreferencesModel newPortletPreferencesModel(
			long plid, String portletId, DDLRecordSetModel ddlRecordSetModel)
		throws Exception {

		PortletPreferences jxPortletPreferences = new PortletPreferencesImpl();

		jxPortletPreferences.setValue("editable", "true");
		jxPortletPreferences.setValue(
			"recordSetId", String.valueOf(ddlRecordSetModel.getRecordSetId()));
		jxPortletPreferences.setValue("spreadsheet", "false");

		return newPortletPreferencesModel(
			plid, portletId,
			_portletPreferencesFactory.toXML(jxPortletPreferences));
	}

	public PortletPreferencesModel newPortletPreferencesModel(
			long plid, String portletId,
			JournalArticleResourceModel journalArticleResourceModel)
		throws Exception {

		PortletPreferences jxPortletPreferences = new PortletPreferencesImpl();

		jxPortletPreferences.setValue(
			"articleId", journalArticleResourceModel.getArticleId());
		jxPortletPreferences.setValue(
			"groupId",
			String.valueOf(journalArticleResourceModel.getGroupId()));

		return newPortletPreferencesModel(
			plid, portletId,
			_portletPreferencesFactory.toXML(jxPortletPreferences));
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		AssetCategoryModel assetCategoryModel) {

		return newResourcePermissionModels(
			AssetCategory.class.getName(),
			String.valueOf(assetCategoryModel.getCategoryId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		AssetTagModel assetTagModel) {

		return newResourcePermissionModels(
			AssetTag.class.getName(), String.valueOf(assetTagModel.getTagId()),
			_sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		AssetVocabularyModel assetVocabularyModel) {

		if (assetVocabularyModel.getUserId() == _defaultUserId) {
			List<ResourcePermissionModel> resourcePermissionModels =
				new ArrayList<>(1);

			resourcePermissionModels.add(
				newResourcePermissionModel(
					AssetVocabulary.class.getName(),
					String.valueOf(assetVocabularyModel.getVocabularyId()),
					_ownerRoleModel.getRoleId(), _defaultUserId));

			return resourcePermissionModels;
		}

		return newResourcePermissionModels(
			AssetVocabulary.class.getName(),
			String.valueOf(assetVocabularyModel.getVocabularyId()),
			_sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		BlogsEntryModel blogsEntryModel) {

		return newResourcePermissionModels(
			BlogsEntry.class.getName(),
			String.valueOf(blogsEntryModel.getEntryId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		DDLRecordSetModel ddlRecordSetModel) {

		List<ResourcePermissionModel> resourcePermissionModels =
			new ArrayList<>(1);

		resourcePermissionModels.add(
			newResourcePermissionModel(
				DDLRecordSet.class.getName(),
				String.valueOf(ddlRecordSetModel.getRecordSetId()),
				_ownerRoleModel.getRoleId(), _defaultUserId));

		return resourcePermissionModels;
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		DDMStructureModel ddmStructureModel) {

		List<ResourcePermissionModel> resourcePermissionModels =
			new ArrayList<>(1);

		resourcePermissionModels.add(
			newResourcePermissionModel(
				DDMStructure.class.getName(),
				String.valueOf(ddmStructureModel.getStructureId()),
				_ownerRoleModel.getRoleId(), _defaultUserId));

		return resourcePermissionModels;
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		DLFileEntryModel dlFileEntryModel) {

		return newResourcePermissionModels(
			DLFileEntry.class.getName(),
			String.valueOf(dlFileEntryModel.getFileEntryId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		DLFolderModel dlFolderModel) {

		return newResourcePermissionModels(
			DLFolder.class.getName(),
			String.valueOf(dlFolderModel.getFolderId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		JournalArticleResourceModel journalArticleResourceModel) {

		return newResourcePermissionModels(
			JournalArticle.class.getName(),
			String.valueOf(journalArticleResourceModel.getResourcePrimKey()),
			_sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		LayoutModel layoutModel) {

		return newResourcePermissionModels(
			Layout.class.getName(), String.valueOf(layoutModel.getPlid()),
			_sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		MBCategoryModel mbCategoryModel) {

		return newResourcePermissionModels(
			MBCategory.class.getName(),
			String.valueOf(mbCategoryModel.getCategoryId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		MBMessageModel mbMessageModel) {

		return newResourcePermissionModels(
			MBMessage.class.getName(),
			String.valueOf(mbMessageModel.getMessageId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		PortletPreferencesModel portletPreferencesModel) {

		String portletId = portletPreferencesModel.getPortletId();

		String name = portletId;

		int index = portletId.indexOf(StringPool.UNDERLINE);

		if (index > 0) {
			name = portletId.substring(0, index);
		}

		String primKey = PortletPermissionUtil.getPrimaryKey(
			portletPreferencesModel.getPlid(), portletId);

		return newResourcePermissionModels(name, primKey, 0);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		String name, long primKey) {

		return newResourcePermissionModels(
			name, String.valueOf(primKey), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		WikiNodeModel wikiNodeModel) {

		return newResourcePermissionModels(
			WikiNode.class.getName(), String.valueOf(wikiNodeModel.getNodeId()),
			_sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		WikiPageModel wikiPageModel) {

		return newResourcePermissionModels(
			WikiPage.class.getName(),
			String.valueOf(wikiPageModel.getResourcePrimKey()), _sampleUserId);
	}

	public SocialActivityModel newSocialActivityModel(
		BlogsEntryModel blogsEntryModel) {

		return newSocialActivityModel(
			blogsEntryModel.getGroupId(),
			_classNameModelsMap.get(BlogsEntry.class.getName()),
			blogsEntryModel.getEntryId(), BlogsActivityKeys.ADD_ENTRY,
			"{\"title\":\""+ blogsEntryModel.getTitle() +"\"}");
	}

	public SocialActivityModel newSocialActivityModel(
		DLFileEntryModel dlFileEntryModel) {

		return newSocialActivityModel(
			dlFileEntryModel.getGroupId(), getDLFileEntryClassNameId(),
			dlFileEntryModel.getFileEntryId(), DLActivityKeys.ADD_FILE_ENTRY,
			StringPool.BLANK);
	}

	public SocialActivityModel newSocialActivityModel(
		JournalArticleModel journalArticleModel) {

		int type = JournalActivityKeys.UPDATE_ARTICLE;

		if (journalArticleModel.getVersion() ==
				JournalArticleConstants.VERSION_DEFAULT) {

			type = JournalActivityKeys.ADD_ARTICLE;
		}

		return newSocialActivityModel(
			journalArticleModel.getGroupId(), getJournalArticleClassNameId(),
			journalArticleModel.getResourcePrimKey(), type,
			"{\"title\":\""+ journalArticleModel.getUrlTitle() +"\"}");
	}

	public SocialActivityModel newSocialActivityModel(
		MBMessageModel mbMessageModel) {

		long classNameId = mbMessageModel.getClassNameId();
		long classPK = mbMessageModel.getClassPK();

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
			classPK = mbMessageModel.getMessageId();
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

		return newSocialActivityModel(
			mbMessageModel.getGroupId(), classNameId, classPK, type, extraData);
	}

	public SubscriptionModel newSubscriptionModel(
		BlogsEntryModel blogsEntryModel) {

		return newSubscriptionModel(
			_classNameModelsMap.get(BlogsEntry.class.getName()),
			blogsEntryModel.getEntryId());
	}

	public SubscriptionModel newSubscriptionModel(MBThreadModel mBThreadModel) {
		return newSubscriptionModel(
			_classNameModelsMap.get(MBThread.class.getName()),
			mBThreadModel.getThreadId());
	}

	public SubscriptionModel newSubscriptionModel(WikiPageModel wikiPageModel) {
		return newSubscriptionModel(
			_classNameModelsMap.get(WikiPage.class.getName()),
			wikiPageModel.getResourcePrimKey());
	}

	public List<UserModel> newUserModels() {
		List<UserModel> userModels = new ArrayList<>(_maxUserCount);

		for (int i = 0; i < _maxUserCount; i++) {
			String[] userName = nextUserName(i);
			userModels.add(
				newUserModel(
					_counter.get(), userName[0], userName[1],
					"test" + _userScreenNameCounter.get(), false));
		}

		return userModels;
	}

	public List<WikiNodeModel> newWikiNodeModels(long groupId) {
		List<WikiNodeModel> wikiNodeModels = new ArrayList<>(_maxWikiNodeCount);

		for (int i = 1; i <= _maxWikiNodeCount; i++) {
			wikiNodeModels.add(newWikiNodeModel(groupId, i));
		}

		return wikiNodeModels;
	}

	public List<WikiPageModel> newWikiPageModels(WikiNodeModel wikiNodeModel) {
		List<WikiPageModel> wikiPageModels = new ArrayList<>(_maxWikiPageCount);

		for (int i = 1; i <= _maxWikiPageCount; i++) {
			wikiPageModels.add(newWikiPageModel(wikiNodeModel, i));
		}

		return wikiPageModels;
	}

	public WikiPageResourceModel newWikiPageResourceModel(
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

	protected String[] getAssetPublisherAssetCategoriesQueryValues(
		List<AssetCategoryModel> assetCategoryModels, int index) {

		AssetCategoryModel assetCategoryModel0 = assetCategoryModels.get(
			index % assetCategoryModels.size());
		AssetCategoryModel assetCategoryModel1 = assetCategoryModels.get(
			(index + _maxAssetEntryToAssetCategoryCount) %
				assetCategoryModels.size());
		AssetCategoryModel assetCategoryModel2 = assetCategoryModels.get(
			(index + _maxAssetEntryToAssetCategoryCount * 2) %
				assetCategoryModels.size());
		AssetCategoryModel assetCategoryModel3 = assetCategoryModels.get(
			(index + _maxAssetEntryToAssetCategoryCount * 3) %
				assetCategoryModels.size());

		return new String[] {
			String.valueOf(assetCategoryModel0.getCategoryId()),
			String.valueOf(assetCategoryModel1.getCategoryId()),
			String.valueOf(assetCategoryModel2.getCategoryId()),
			String.valueOf(assetCategoryModel3.getCategoryId())
		};
	}

	protected String[] getAssetPublisherAssetTagsQueryValues(
		List<AssetTagModel> assetTagModels, int index) {

		AssetTagModel assetTagModel0 = assetTagModels.get(
			index % assetTagModels.size());
		AssetTagModel assetTagModel1 = assetTagModels.get(
			(index + _maxAssetEntryToAssetTagCount) % assetTagModels.size());
		AssetTagModel assetTagModel2 = assetTagModels.get(
			(index + _maxAssetEntryToAssetTagCount * 2) %
				assetTagModels.size());
		AssetTagModel assetTagModel3 = assetTagModels.get(
			(index + _maxAssetEntryToAssetTagCount * 3) %
				assetTagModels.size());

		return new String[] {
			assetTagModel0.getName(), assetTagModel1.getName(),
			assetTagModel2.getName(), assetTagModel3.getName()
		};
	}

	protected InputStream getResourceInputStream(String resourceName) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			_DEPENDENCIES_DIR + resourceName);
	}

	protected AssetCategoryModel newAssetCategoryModel(
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

	protected AssetEntryModel newAssetEntryModel(
		long groupId, Date createDate, Date modifiedDate, long classNameId,
		long classPK, String uuid, long classTypeId, boolean listable,
		boolean visible, String mimeType, String title) {

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
		assetEntryModel.setListable(listable);
		assetEntryModel.setVisible(visible);
		assetEntryModel.setStartDate(createDate);
		assetEntryModel.setEndDate(nextFutureDate());
		assetEntryModel.setPublishDate(createDate);
		assetEntryModel.setExpirationDate(nextFutureDate());
		assetEntryModel.setMimeType(mimeType);
		assetEntryModel.setTitle(title);

		return assetEntryModel;
	}

	protected AssetTagStatsModel newAssetTagStatsModel(
		long tagId, long classNameId) {

		AssetTagStatsModel assetTagStatsModel = new AssetTagStatsModelImpl();

		assetTagStatsModel.setTagStatsId(_counter.get());
		assetTagStatsModel.setTagId(tagId);
		assetTagStatsModel.setClassNameId(classNameId);

		return assetTagStatsModel;
	}

	protected AssetVocabularyModel newAssetVocabularyModel(
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

	protected BlogsEntryModel newBlogsEntryModel(long groupId, int index) {
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
		blogsEntryModel.setSubtitle("Subtitle of Test Blog " + index);
		blogsEntryModel.setUrlTitle("testblog" + index);
		blogsEntryModel.setContent("This is test blog " + index + ".");
		blogsEntryModel.setDisplayDate(new Date());
		blogsEntryModel.setStatusDate(new Date());

		return blogsEntryModel;
	}

	protected DDMContentModel newDDMContentModel(
		long contentId, long groupId, String data) {

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
		ddmContentModel.setData(data);

		return ddmContentModel;
	}

	protected DDMStructureLinkModel newDDMStructureLinkModel(
		long classNameId, long classPK, long structureId) {

		DDMStructureLinkModel ddmStructureLinkModel =
			new DDMStructureLinkModelImpl();

		ddmStructureLinkModel.setStructureLinkId(_counter.get());
		ddmStructureLinkModel.setClassNameId(classNameId);
		ddmStructureLinkModel.setClassPK(classPK);
		ddmStructureLinkModel.setStructureId(structureId);

		return ddmStructureLinkModel;
	}

	protected DDMStructureModel newDDMStructureModel(
		long groupId, long userId, long classNameId, String structureKey,
		String definition) {

		DDMStructureModel dDMStructureModel = new DDMStructureModelImpl();

		dDMStructureModel.setUuid(SequentialUUID.generate());
		dDMStructureModel.setStructureId(_counter.get());
		dDMStructureModel.setGroupId(groupId);
		dDMStructureModel.setCompanyId(_companyId);
		dDMStructureModel.setUserId(userId);
		dDMStructureModel.setUserName(_SAMPLE_USER_NAME);
		dDMStructureModel.setCreateDate(nextFutureDate());
		dDMStructureModel.setModifiedDate(nextFutureDate());
		dDMStructureModel.setClassNameId(classNameId);
		dDMStructureModel.setStructureKey(structureKey);
		dDMStructureModel.setVersion(DDMStructureConstants.VERSION_DEFAULT);

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append("<name language-id=\"en_US\">");
		sb.append(structureKey);
		sb.append("</name></root>");

		dDMStructureModel.setName(sb.toString());

		dDMStructureModel.setDefinition(definition);
		dDMStructureModel.setStorageType(StorageType.JSON.toString());

		return dDMStructureModel;
	}

	protected DDMTemplateModel newDDMTemplateModel(
		long groupId, long userId, long structureId, long sourceClassNameId) {

		DDMTemplateModel ddmTemplateModel = new DDMTemplateModelImpl();

		ddmTemplateModel.setUuid(SequentialUUID.generate());
		ddmTemplateModel.setTemplateId(_counter.get());
		ddmTemplateModel.setGroupId(groupId);
		ddmTemplateModel.setCompanyId(_companyId);
		ddmTemplateModel.setUserId(userId);
		ddmTemplateModel.setCreateDate(nextFutureDate());
		ddmTemplateModel.setModifiedDate(nextFutureDate());
		ddmTemplateModel.setClassNameId(
			_classNameModelsMap.get(DDMStructure.class.getName()));
		ddmTemplateModel.setClassPK(structureId);
		ddmTemplateModel.setResourceClassNameId(structureId);
		ddmTemplateModel.setTemplateKey(String.valueOf(_counter.get()));
		ddmTemplateModel.setVersion(DDMTemplateConstants.VERSION_DEFAULT);

		StringBundler sb = new StringBundler(3);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ");
		sb.append("available-locales=\"en_US\" default-locale=\"en_US\">");
		sb.append(
			"<name language-id=\"en_US\">Basic Web Content</name></root>");

		ddmTemplateModel.setName(sb.toString());

		ddmTemplateModel.setType(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);
		ddmTemplateModel.setMode(DDMTemplateConstants.TEMPLATE_MODE_CREATE);
		ddmTemplateModel.setLanguage(TemplateConstants.LANG_TYPE_FTL);
		ddmTemplateModel.setScript("${content.getData()}");
		ddmTemplateModel.setCacheable(false);
		ddmTemplateModel.setSmallImage(false);

		return ddmTemplateModel;
	}

	protected DLFileEntryModel newDlFileEntryModel(
		DLFolderModel dlFolerModel, int index) {

		DLFileEntryModel dlFileEntryModel = new DLFileEntryModelImpl();

		dlFileEntryModel.setUuid(SequentialUUID.generate());
		dlFileEntryModel.setFileEntryId(_counter.get());
		dlFileEntryModel.setGroupId(dlFolerModel.getGroupId());
		dlFileEntryModel.setCompanyId(_companyId);
		dlFileEntryModel.setUserId(_sampleUserId);
		dlFileEntryModel.setUserName(_SAMPLE_USER_NAME);
		dlFileEntryModel.setCreateDate(nextFutureDate());
		dlFileEntryModel.setModifiedDate(nextFutureDate());
		dlFileEntryModel.setRepositoryId(dlFolerModel.getRepositoryId());
		dlFileEntryModel.setFolderId(dlFolerModel.getFolderId());
		dlFileEntryModel.setName("TestFile" + index);
		dlFileEntryModel.setFileName("TestFile" + index + ".txt");
		dlFileEntryModel.setExtension("txt");
		dlFileEntryModel.setMimeType(ContentTypes.TEXT_PLAIN);
		dlFileEntryModel.setTitle("TestFile" + index + ".txt");
		dlFileEntryModel.setFileEntryTypeId(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		dlFileEntryModel.setVersion(DLFileEntryConstants.VERSION_DEFAULT);
		dlFileEntryModel.setSize(_maxDLFileEntrySize);

		return dlFileEntryModel;
	}

	protected DLFolderModel newDLFolderModel(
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

	protected GroupModel newGroupModel(
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
		groupModel.setGroupKey(name);
		groupModel.setName(name);
		groupModel.setManualMembership(true);
		groupModel.setMembershipRestriction(
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION);
		groupModel.setFriendlyURL(
			StringPool.FORWARD_SLASH +
				FriendlyURLNormalizerUtil.normalize(name));
		groupModel.setSite(site);
		groupModel.setActive(true);

		return groupModel;
	}

	protected LayoutSetModel newLayoutSetModel(long groupId, int pageCount) {
		LayoutSetModel layoutSetModel = new LayoutSetModelImpl();

		layoutSetModel.setLayoutSetId(_counter.get());
		layoutSetModel.setGroupId(groupId);
		layoutSetModel.setCompanyId(_companyId);
		layoutSetModel.setCreateDate(new Date());
		layoutSetModel.setModifiedDate(new Date());
		layoutSetModel.setThemeId("classic");
		layoutSetModel.setColorSchemeId("01");
		layoutSetModel.setWapThemeId("mobile");
		layoutSetModel.setWapColorSchemeId("01");
		layoutSetModel.setPageCount(pageCount);

		return layoutSetModel;
	}

	protected MBCategoryModel newMBCategoryModel(long groupId, int index) {
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

	protected MBMessageModel newMBMessageModel(
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

	protected MBThreadModel newMBThreadModel(
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

	protected PortletPreferencesModel newPortletPreferencesModel(
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

	protected ResourcePermissionModel newResourcePermissionModel(
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

	protected List<ResourcePermissionModel> newResourcePermissionModels(
		String name, String primKey, long ownerId) {

		List<ResourcePermissionModel> resourcePermissionModels =
			new ArrayList<>(3);

		resourcePermissionModels.add(
			newResourcePermissionModel(
				name, primKey, _guestRoleModel.getRoleId(), 0));
		resourcePermissionModels.add(
			newResourcePermissionModel(
				name, primKey, _ownerRoleModel.getRoleId(), ownerId));
		resourcePermissionModels.add(
			newResourcePermissionModel(
				name, primKey, _siteMemberRoleModel.getRoleId(), 0));

		return resourcePermissionModels;
	}

	protected RoleModel newRoleModel(String name, int type) {
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

	protected SocialActivityModel newSocialActivityModel(
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

	protected SubscriptionModel newSubscriptionModel(
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

	protected UserModel newUserModel(
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

	protected WikiNodeModel newWikiNodeModel(long groupId, int index) {
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

	protected WikiPageModel newWikiPageModel(
		WikiNodeModel wikiNodeModel, int index) {

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
		wikiPageModel.setFormat("creole");
		wikiPageModel.setHead(true);

		return wikiPageModel;
	}

	protected String nextDDLCustomFieldName(
		long groupId, int customFieldIndex) {

		StringBundler sb = new StringBundler(4);

		sb.append("custom_field_text_");
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

	private static final String _DEPENDENCIES_DIR =
		"com/liferay/portal/tools/samplesqlbuilder/dependencies/";

	private static final long _FUTURE_TIME =
		System.currentTimeMillis() + Time.YEAR;

	private static final String _SAMPLE_USER_NAME = "Sample";

	private static final PortletPreferencesFactory _portletPreferencesFactory =
		new PortletPreferencesFactoryImpl();

	private final long _accountId;
	private AccountModel _accountModel;
	private RoleModel _administratorRoleModel;
	private final Map<Long, SimpleCounter> _assetCategoryCounters =
		new HashMap<>();
	private List<AssetCategoryModel>[] _assetCategoryModelsArray;
	private final Map<Long, SimpleCounter> _assetPublisherQueryCounter =
		new HashMap<>();
	private String _assetPublisherQueryName;
	private final Map<Long, SimpleCounter> _assetTagCounters = new HashMap<>();
	private List<AssetTagModel>[] _assetTagModelsArray;
	private List<AssetTagStatsModel>[] _assetTagStatsModelsArray;
	private List<AssetVocabularyModel>[] _assetVocabularyModelsArray;
	private final List<ClassNameModel> _classNameModels;
	private final Map<String, Long> _classNameModelsMap = new HashMap<>();
	private final long _companyId;
	private CompanyModel _companyModel;
	private final SimpleCounter _counter;
	private final PortletPreferencesImpl
		_defaultAssetPublisherPortletPreference;
	private AssetVocabularyModel _defaultAssetVocabularyModel;
	private DDMStructureModel _defaultDLDDMStructureModel;
	private DLFileEntryTypeModel _defaultDLFileEntryTypeModel;
	private DDMStructureModel _defaultJournalDDMStructureModel;
	private DDMTemplateModel _defaultJournalDDMTemplateModel;
	private final long _defaultUserId;
	private UserModel _defaultUserModel;
	private final String _dlDDMStructureContent;
	private List<String> _firstNames;
	private final SimpleCounter _futureDateCounter;
	private final long _globalGroupId;
	private GroupModel _globalGroupModel;
	private List<GroupModel> _groupModels;
	private final long _guestGroupId;
	private GroupModel _guestGroupModel;
	private RoleModel _guestRoleModel;
	private UserModel _guestUserModel;
	private String _journalArticleContent;
	private final Map<Long, String> _journalArticleResourceUUIDs =
		new HashMap<>();
	private final String _journalDDMStructureContent;
	private List<String> _lastNames;
	private final Map<Long, SimpleCounter> _layoutCounters = new HashMap<>();
	private int _maxAssetCategoryCount;
	private int _maxAssetEntryToAssetCategoryCount;
	private int _maxAssetEntryToAssetTagCount;
	private int _maxAssetPublisherPageCount;
	private int _maxAssetTagCount;
	private int _maxAssetVocabularyCount;
	private int _maxBlogsEntryCommentCount;
	private int _maxBlogsEntryCount;
	private int _maxDDLCustomFieldCount;
	private int _maxDDLRecordCount;
	private int _maxDDLRecordSetCount;
	private int _maxDLFileEntryCount;
	private int _maxDLFileEntrySize;
	private int _maxDLFolderCount;
	private int _maxDLFolderDepth;
	private int _maxGroupsCount;
	private int _maxJournalArticleCount;
	private int _maxJournalArticlePageCount;
	private int _maxJournalArticleVersionCount;
	private int _maxMBCategoryCount;
	private int _maxMBMessageCount;
	private int _maxMBThreadCount;
	private int _maxUserCount;
	private int _maxUserToGroupCount;
	private int _maxWikiNodeCount;
	private int _maxWikiPageCommentCount;
	private int _maxWikiPageCount;
	private RoleModel _ownerRoleModel;
	private RoleModel _powerUserRoleModel;
	private final SimpleCounter _resourcePermissionCounter;
	private List<RoleModel> _roleModels;
	private final long _sampleUserId;
	private UserModel _sampleUserModel;
	private Format _simpleDateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private RoleModel _siteMemberRoleModel;
	private final SimpleCounter _socialActivityCounter;
	private final SimpleCounter _timeCounter;
	private RoleModel _userRoleModel;
	private final SimpleCounter _userScreenNameCounter;
	private VirtualHostModel _virtualHostModel;

}