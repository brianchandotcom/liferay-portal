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

package com.liferay.portal.tools.sample.sql.builder;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetCategoryModel;
import com.liferay.asset.kernel.model.AssetEntryModel;
import com.liferay.asset.kernel.model.AssetTagModel;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyModel;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.model.BlogsEntryModel;
import com.liferay.blogs.model.BlogsStatsUserModel;
import com.liferay.blogs.model.impl.BlogsEntryModelImpl;
import com.liferay.blogs.model.impl.BlogsStatsUserModelImpl;
import com.liferay.blogs.social.BlogsActivityKeys;
import com.liferay.commerce.currency.model.CommerceCurrencyModel;
import com.liferay.commerce.currency.model.impl.CommerceCurrencyModelImpl;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLocalizationModel;
import com.liferay.commerce.product.model.CPDefinitionModel;
import com.liferay.commerce.product.model.CPInstanceModel;
import com.liferay.commerce.product.model.CPTaxCategoryModel;
import com.liferay.commerce.product.model.CProductModel;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceCatalogModel;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelModel;
import com.liferay.commerce.product.model.impl.CPDefinitionLocalizationModelImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionModelImpl;
import com.liferay.commerce.product.model.impl.CPInstanceModelImpl;
import com.liferay.commerce.product.model.impl.CPTaxCategoryModelImpl;
import com.liferay.commerce.product.model.impl.CProductModelImpl;
import com.liferay.commerce.product.model.impl.CommerceCatalogModelImpl;
import com.liferay.commerce.product.model.impl.CommerceChannelModelImpl;
import com.liferay.counter.kernel.model.Counter;
import com.liferay.counter.kernel.model.CounterModel;
import com.liferay.counter.model.impl.CounterModelImpl;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryMetadataModel;
import com.liferay.document.library.kernel.model.DLFileEntryModel;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileEntryTypeModel;
import com.liferay.document.library.kernel.model.DLFileVersionModel;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderModel;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.constants.DDLRecordConstants;
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordModel;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetModel;
import com.liferay.dynamic.data.lists.model.DDLRecordVersionModel;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionModelImpl;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttributeModel;
import com.liferay.dynamic.data.mapping.model.DDMFieldModel;
import com.liferay.dynamic.data.mapping.model.DDMStorageLinkModel;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayoutModel;
import com.liferay.dynamic.data.mapping.model.DDMStructureLinkModel;
import com.liferay.dynamic.data.mapping.model.DDMStructureModel;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersionModel;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateLinkModel;
import com.liferay.dynamic.data.mapping.model.DDMTemplateModel;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersionModel;
import com.liferay.dynamic.data.mapping.model.impl.DDMFieldAttributeModelImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMFieldModelImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLinkModelImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureModelImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionModelImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateModelImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateVersionModelImpl;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollectionModel;
import com.liferay.fragment.model.FragmentEntryLinkModel;
import com.liferay.fragment.model.FragmentEntryModel;
import com.liferay.fragment.model.impl.FragmentCollectionModelImpl;
import com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl;
import com.liferay.fragment.model.impl.FragmentEntryModelImpl;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalizationModel;
import com.liferay.friendly.url.model.FriendlyURLEntryMappingModel;
import com.liferay.friendly.url.model.FriendlyURLEntryModel;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryLocalizationModelImpl;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryMappingModelImpl;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryModelImpl;
import com.liferay.hello.world.web.internal.constants.HelloWorldPortletKeys;
import com.liferay.journal.constants.JournalActivityKeys;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleLocalizationModel;
import com.liferay.journal.model.JournalArticleModel;
import com.liferay.journal.model.JournalArticleResourceModel;
import com.liferay.journal.model.JournalContentSearchModel;
import com.liferay.journal.model.impl.JournalArticleLocalizationModelImpl;
import com.liferay.journal.model.impl.JournalArticleModelImpl;
import com.liferay.journal.model.impl.JournalArticleResourceModelImpl;
import com.liferay.journal.model.impl.JournalContentSearchModelImpl;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureModel;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRelModel;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureModelImpl;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureRelModelImpl;
import com.liferay.login.web.constants.LoginPortletKeys;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBCategoryModel;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBDiscussionModel;
import com.liferay.message.boards.model.MBMailingListModel;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBMessageModel;
import com.liferay.message.boards.model.MBStatsUserModel;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.model.MBThreadFlagModel;
import com.liferay.message.boards.model.MBThreadModel;
import com.liferay.message.boards.model.impl.MBCategoryModelImpl;
import com.liferay.message.boards.model.impl.MBDiscussionModelImpl;
import com.liferay.message.boards.model.impl.MBMailingListModelImpl;
import com.liferay.message.boards.model.impl.MBMessageModelImpl;
import com.liferay.message.boards.model.impl.MBStatsUserModelImpl;
import com.liferay.message.boards.model.impl.MBThreadFlagModelImpl;
import com.liferay.message.boards.model.impl.MBThreadModelImpl;
import com.liferay.message.boards.social.MBActivityKeys;
import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.metadata.RawMetadataProcessor;
import com.liferay.portal.kernel.model.AccountModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassNameModel;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyModel;
import com.liferay.portal.kernel.model.ContactConstants;
import com.liferay.portal.kernel.model.ContactModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.GroupModel;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURLModel;
import com.liferay.portal.kernel.model.LayoutModel;
import com.liferay.portal.kernel.model.LayoutSetModel;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.PortletPreferenceValueModel;
import com.liferay.portal.kernel.model.PortletPreferencesModel;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.model.ReleaseModel;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.ResourcePermissionModel;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserModel;
import com.liferay.portal.kernel.model.UserPersonalSite;
import com.liferay.portal.kernel.model.VirtualHostModel;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.impl.AccountModelImpl;
import com.liferay.portal.model.impl.ClassNameModelImpl;
import com.liferay.portal.model.impl.CompanyModelImpl;
import com.liferay.portal.model.impl.ContactModelImpl;
import com.liferay.portal.model.impl.GroupModelImpl;
import com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl;
import com.liferay.portal.model.impl.LayoutModelImpl;
import com.liferay.portal.model.impl.LayoutSetModelImpl;
import com.liferay.portal.model.impl.PortletPreferenceValueImpl;
import com.liferay.portal.model.impl.PortletPreferenceValueModelImpl;
import com.liferay.portal.model.impl.PortletPreferencesModelImpl;
import com.liferay.portal.model.impl.ReleaseModelImpl;
import com.liferay.portal.model.impl.ResourcePermissionModelImpl;
import com.liferay.portal.model.impl.RoleModelImpl;
import com.liferay.portal.model.impl.UserModelImpl;
import com.liferay.portal.model.impl.VirtualHostModelImpl;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.search.web.internal.folder.facet.constants.FolderFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.search.options.constants.SearchOptionsPortletKeys;
import com.liferay.portal.search.web.internal.search.results.constants.SearchResultsPortletKeys;
import com.liferay.portal.search.web.internal.site.facet.constants.SiteFacetPortletKeys;
import com.liferay.portal.search.web.internal.suggestions.constants.SuggestionsPortletKeys;
import com.liferay.portal.search.web.internal.tag.facet.constants.TagFacetPortletKeys;
import com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetPortletKeys;
import com.liferay.portal.search.web.internal.user.facet.constants.UserFacetPortletKeys;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletPreferencesFactoryImpl;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl;
import com.liferay.portlet.asset.model.impl.AssetEntryModelImpl;
import com.liferay.portlet.asset.model.impl.AssetTagModelImpl;
import com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.social.model.impl.SocialActivityModelImpl;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.impl.SegmentsEntryImpl;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.model.SocialActivityModel;
import com.liferay.subscription.constants.SubscriptionConstants;
import com.liferay.subscription.model.SubscriptionModel;
import com.liferay.subscription.model.impl.SubscriptionModelImpl;
import com.liferay.util.SimpleCounter;
import com.liferay.wiki.constants.WikiPageConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiNodeModel;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageModel;
import com.liferay.wiki.model.WikiPageResourceModel;
import com.liferay.wiki.model.impl.WikiNodeModelImpl;
import com.liferay.wiki.model.impl.WikiPageModelImpl;
import com.liferay.wiki.model.impl.WikiPageResourceModelImpl;
import com.liferay.wiki.social.WikiActivityKeys;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.sql.Types;

import java.text.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class DataFactory {

	public DataFactory() throws Exception {
		_simpleDateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", TimeZone.getDefault());

		_counter = new SimpleCounter(BenchmarksPropsValues.MAX_GROUP_COUNT + 1);
		_timeCounter = new SimpleCounter();
		_futureDateCounter = new SimpleCounter();
		_resourcePermissionCounter = new SimpleCounter();
		_socialActivityCounter = new SimpleCounter();
		_userScreenNameCounter = new SimpleCounter();

		List<String> models = ModelHintsUtil.getModels();

		models.add(Layout.class.getName());
		models.add(UserPersonalSite.class.getName());

		models.add(_getMBDiscussionCombinedClassName(BlogsEntry.class));
		models.add(_getMBDiscussionCombinedClassName(WikiPage.class));

		for (String model : models) {
			ClassNameModel classNameModel = new ClassNameModelImpl();

			classNameModel.setClassNameId(_counter.get());
			classNameModel.setValue(model);

			_classNameModels.put(model, classNameModel);
		}

		_assetClassNameIds = new long[] {
			getClassNameId(BlogsEntry.class),
			getClassNameId(JournalArticle.class), getClassNameId(WikiPage.class)
		};

		_accountId = _counter.get();
		_companyId = _counter.get();
		_defaultDLDDMStructureId = _counter.get();
		_defaultDLDDMStructureVersionId = _counter.get();
		_defaultJournalDDMStructureId = _counter.get();
		_defaultJournalDDMStructureVersionId = _counter.get();
		_defaultJournalDDMTemplateId = _counter.get();
		_defaultUserId = _counter.get();
		_globalGroupId = _counter.get();
		_guestGroupId = _counter.get();
		_sampleUserId = _counter.get();
		_userPersonalSiteGroupId = _counter.get();

		_dlDDMStructureContent = _readFile("ddm_structure_basic_document.json");
		_dlDDMStructureLayoutContent = _readFile(
			"ddm_structure_layout_basic_document.json");
		_journalDDMStructureContent = _readFile(
			"ddm_structure_basic_web_content.json");
		_journalDDMStructureLayoutContent = _readFile(
			"ddm_structure_layout_basic_web_content.json");
		_layoutPageTemplateStructureRelData = _readFile(
			"layout_page_template_structure_rel_data.json");

		_defaultAssetPublisherPortletPreferencesImpl =
			(PortletPreferencesImpl)_portletPreferencesFactory.fromDefaultXML(
				_readFile("default_asset_publisher_preference.xml"));

		initAssetCategoryModels();
		initAssetTagModels();

		initJournalArticleContent();

		initRoleModels();
		initUserNames();
	}

	public RoleModel getAdministratorRoleModel() {
		return _administratorRoleModel;
	}

	public List<Long> getAssetCategoryIds(AssetEntryModel assetEntryModel) {
		Map<Long, List<AssetCategoryModel>> assetCategoryModelsMap =
			_assetCategoryModelsMaps[(int)assetEntryModel.getGroupId() - 1];

		if ((assetCategoryModelsMap == null) ||
			assetCategoryModelsMap.isEmpty()) {

			return Collections.emptyList();
		}

		List<AssetCategoryModel> assetCategoryModels =
			assetCategoryModelsMap.get(assetEntryModel.getClassNameId());

		if ((assetCategoryModels == null) || assetCategoryModels.isEmpty()) {
			return Collections.emptyList();
		}

		if (_assetCategoryCounters == null) {
			_assetCategoryCounters =
				(Map<Long, SimpleCounter>[])
					new HashMap<?, ?>[BenchmarksPropsValues.MAX_GROUP_COUNT];
		}

		SimpleCounter counter = getSimpleCounter(
			_assetCategoryCounters, assetEntryModel.getGroupId(),
			assetEntryModel.getClassNameId());

		List<Long> assetCategoryIds = new ArrayList<>(
			BenchmarksPropsValues.MAX_ASSET_ENTRY_TO_ASSET_CATEGORY_COUNT);

		for (int i = 0;
			 i < BenchmarksPropsValues.MAX_ASSET_ENTRY_TO_ASSET_CATEGORY_COUNT;
			 i++) {

			int index = (int)counter.get() % assetCategoryModels.size();

			AssetCategoryModel assetCategoryModel = assetCategoryModels.get(
				index);

			assetCategoryIds.add(assetCategoryModel.getCategoryId());
		}

		return assetCategoryIds;
	}

	public List<AssetCategoryModel> getAssetCategoryModels() {
		List<AssetCategoryModel> allAssetCategoryModels = new ArrayList<>();

		for (Map<Long, List<AssetCategoryModel>> assetCategoryModelsMap :
				_assetCategoryModelsMaps) {

			for (List<AssetCategoryModel> assetCategoryModels :
					assetCategoryModelsMap.values()) {

				allAssetCategoryModels.addAll(assetCategoryModels);
			}
		}

		return allAssetCategoryModels;
	}

	public List<Long> getAssetTagIds(AssetEntryModel assetEntryModel) {
		Map<Long, List<AssetTagModel>> assetTagModelsMap =
			_assetTagModelsMaps[(int)assetEntryModel.getGroupId() - 1];

		if ((assetTagModelsMap == null) || assetTagModelsMap.isEmpty()) {
			return Collections.emptyList();
		}

		List<AssetTagModel> assetTagModels = assetTagModelsMap.get(
			assetEntryModel.getClassNameId());

		if ((assetTagModels == null) || assetTagModels.isEmpty()) {
			return Collections.emptyList();
		}

		if (_assetTagCounters == null) {
			_assetTagCounters =
				(Map<Long, SimpleCounter>[])
					new HashMap<?, ?>[BenchmarksPropsValues.MAX_GROUP_COUNT];
		}

		SimpleCounter counter = getSimpleCounter(
			_assetTagCounters, assetEntryModel.getGroupId(),
			assetEntryModel.getClassNameId());

		List<Long> assetTagIds = new ArrayList<>(
			BenchmarksPropsValues.MAX_ASSET_ENTRY_TO_ASSET_TAG_COUNT);

		for (int i = 0;
			 i < BenchmarksPropsValues.MAX_ASSET_ENTRY_TO_ASSET_TAG_COUNT;
			 i++) {

			int index = (int)counter.get() % assetTagModels.size();

			AssetTagModel assetTagModel = assetTagModels.get(index);

			assetTagIds.add(assetTagModel.getTagId());
		}

		return assetTagIds;
	}

	public List<AssetTagModel> getAssetTagModels() {
		List<AssetTagModel> allAssetTagModels = new ArrayList<>();

		for (Map<Long, List<AssetTagModel>> assetTagModelsMap :
				_assetTagModelsMaps) {

			for (List<AssetTagModel> assetTagModels :
					assetTagModelsMap.values()) {

				allAssetTagModels.addAll(assetTagModels);
			}
		}

		return allAssetTagModels;
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
		return getClassNameId(BlogsEntry.class);
	}

	public long getClassNameId(Class<?> clazz) {
		ClassNameModel classNameModel = _classNameModels.get(clazz.getName());

		return classNameModel.getClassNameId();
	}

	public Collection<ClassNameModel> getClassNameModels() {
		return _classNameModels.values();
	}

	public long getCounterNext() {
		return _counter.get();
	}

	public long getDefaultDLDDMStructureId() {
		return _defaultDLDDMStructureId;
	}

	public long getDLFileEntryClassNameId() {
		return getClassNameId(DLFileEntry.class);
	}

	public long getJournalArticleClassNameId() {
		return getClassNameId(JournalArticle.class);
	}

	public String getJournalArticleLayoutColumn(String portletPrefix) {
		StringBundler sb = new StringBundler(
			3 * BenchmarksPropsValues.MAX_JOURNAL_ARTICLE_COUNT);

		for (int i = 1; i <= BenchmarksPropsValues.MAX_JOURNAL_ARTICLE_COUNT;
			 i++) {

			sb.append(portletPrefix);
			sb.append(i);
			sb.append(StringPool.COMMA);
		}

		return sb.toString();
	}

	public int getMaxAssetPublisherPageCount() {
		return BenchmarksPropsValues.MAX_ASSETPUBLISHER_PAGE_COUNT;
	}

	public int getMaxBlogsEntryCommentCount() {
		return BenchmarksPropsValues.MAX_BLOGS_ENTRY_COMMENT_COUNT;
	}

	public int getMaxCommerceProductCount() {
		return BenchmarksPropsValues.MAX_COMMERCE_PRODUCT_COUNT;
	}

	public int getMaxCommerceProductDefinitionCount() {
		return BenchmarksPropsValues.MAX_COMMERCE_PRODUCT_DEFINITION_COUNT;
	}

	public int getMaxCommerceProductInstanceCount() {
		return BenchmarksPropsValues.MAX_COMMERCE_PRODUCT_INSTANCE_COUNT;
	}

	public int getMaxDDLRecordCount() {
		return BenchmarksPropsValues.MAX_DDL_RECORD_COUNT;
	}

	public int getMaxDDLRecordSetCount() {
		return BenchmarksPropsValues.MAX_DDL_RECORD_SET_COUNT;
	}

	public int getMaxDLFolderDepth() {
		return BenchmarksPropsValues.MAX_DL_FOLDER_DEPTH;
	}

	public int getMaxGroupCount() {
		return BenchmarksPropsValues.MAX_GROUP_COUNT;
	}

	public int getMaxJournalArticleCount() {
		return BenchmarksPropsValues.MAX_JOURNAL_ARTICLE_COUNT;
	}

	public int getMaxJournalArticlePageCount() {
		return BenchmarksPropsValues.MAX_JOURNAL_ARTICLE_PAGE_COUNT;
	}

	public int getMaxJournalArticleVersionCount() {
		return BenchmarksPropsValues.MAX_JOURNAL_ARTICLE_VERSION_COUNT;
	}

	public int getMaxWikiPageCommentCount() {
		return BenchmarksPropsValues.MAX_WIKI_PAGE_COMMENT_COUNT;
	}

	public List<Long> getNewUserGroupIds(
		long groupId, GroupModel guestGroupModel) {

		List<Long> groupIds = new ArrayList<>(
			BenchmarksPropsValues.MAX_USER_TO_GROUP_COUNT + 1);

		groupIds.add(guestGroupModel.getGroupId());

		if ((groupId + BenchmarksPropsValues.MAX_USER_TO_GROUP_COUNT) >
				BenchmarksPropsValues.MAX_GROUP_COUNT) {

			groupId =
				groupId - BenchmarksPropsValues.MAX_USER_TO_GROUP_COUNT + 1;
		}

		for (int i = 0; i < BenchmarksPropsValues.MAX_USER_TO_GROUP_COUNT;
			 i++) {

			groupIds.add(groupId + i);
		}

		return groupIds;
	}

	public long getNextAssetClassNameId(long groupId) {
		Integer index = _assetClassNameIdsIndexes.get(groupId);

		if (index == null) {
			index = 0;
		}

		long classNameId =
			_assetClassNameIds[index % _assetClassNameIds.length];

		_assetClassNameIdsIndexes.put(groupId, ++index);

		return classNameId;
	}

	public String getPortletId(String portletPrefix) {
		return portletPrefix.concat(PortletIdCodec.generateInstanceId());
	}

	public RoleModel getPowerUserRoleModel() {
		return _powerUserRoleModel;
	}

	public List<RoleModel> getRoleModels() {
		return _roleModels;
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

	public long getWikiPageClassNameId() {
		return getClassNameId(WikiPage.class);
	}

	public void initAssetCategoryModels() {
		_assetCategoryModelsMaps =
			(Map<Long, List<AssetCategoryModel>>[])
				new HashMap<?, ?>[BenchmarksPropsValues.MAX_GROUP_COUNT];
		_assetVocabularyModelsArray =
			(List<AssetVocabularyModel>[])
				new List<?>[BenchmarksPropsValues.MAX_GROUP_COUNT];
		_defaultAssetVocabularyModel = newAssetVocabularyModel(
			_globalGroupId, _defaultUserId, null,
			PropsValues.ASSET_VOCABULARY_DEFAULT);

		StringBundler sb = new StringBundler(4);

		for (int i = 1; i <= BenchmarksPropsValues.MAX_GROUP_COUNT; i++) {
			List<AssetVocabularyModel> assetVocabularyModels = new ArrayList<>(
				BenchmarksPropsValues.MAX_ASSET_VUCABULARY_COUNT);
			List<AssetCategoryModel> assetCategoryModels = new ArrayList<>(
				BenchmarksPropsValues.MAX_ASSET_VUCABULARY_COUNT *
					BenchmarksPropsValues.MAX_ASSET_CATEGORY_COUNT);

			for (int j = 0;
				 j < BenchmarksPropsValues.MAX_ASSET_VUCABULARY_COUNT; j++) {

				sb.setIndex(0);

				sb.append("TestVocabulary_");
				sb.append(i);
				sb.append(StringPool.UNDERLINE);
				sb.append(j);

				AssetVocabularyModel assetVocabularyModel =
					newAssetVocabularyModel(
						i, _sampleUserId, _SAMPLE_USER_NAME, sb.toString());

				assetVocabularyModels.add(assetVocabularyModel);

				for (int k = 0;
					 k < BenchmarksPropsValues.MAX_ASSET_CATEGORY_COUNT; k++) {

					sb.setIndex(0);

					sb.append("TestCategory_");
					sb.append(assetVocabularyModel.getVocabularyId());
					sb.append(StringPool.UNDERLINE);
					sb.append(k);

					assetCategoryModels.add(
						newAssetCategoryModel(
							i, sb.toString(),
							assetVocabularyModel.getVocabularyId()));
				}
			}

			_assetVocabularyModelsArray[i - 1] = assetVocabularyModels;

			Map<Long, List<AssetCategoryModel>> assetCategoryModelsMap =
				new HashMap<>();

			int pageSize =
				assetCategoryModels.size() / _assetClassNameIds.length;

			for (int j = 0; j < _assetClassNameIds.length; j++) {
				int fromIndex = j * pageSize;

				int toIndex = (j + 1) * pageSize;

				if (j == (_assetClassNameIds.length - 1)) {
					toIndex = assetCategoryModels.size();
				}

				assetCategoryModelsMap.put(
					_assetClassNameIds[j],
					assetCategoryModels.subList(fromIndex, toIndex));
			}

			_assetCategoryModelsMaps[i - 1] = assetCategoryModelsMap;
		}
	}

	public void initAssetTagModels() {
		_assetTagModelsArray =
			(List<AssetTagModel>[])
				new List<?>[BenchmarksPropsValues.MAX_GROUP_COUNT];
		_assetTagModelsMaps =
			(Map<Long, List<AssetTagModel>>[])
				new HashMap<?, ?>[BenchmarksPropsValues.MAX_GROUP_COUNT];

		for (int i = 1; i <= BenchmarksPropsValues.MAX_GROUP_COUNT; i++) {
			List<AssetTagModel> assetTagModels = new ArrayList<>(
				BenchmarksPropsValues.MAX_ASSET_TAG_COUNT);

			for (int j = 0; j < BenchmarksPropsValues.MAX_ASSET_TAG_COUNT;
				 j++) {

				AssetTagModel assetTagModel = new AssetTagModelImpl();

				assetTagModel.setUuid(SequentialUUID.generate());
				assetTagModel.setTagId(_counter.get());
				assetTagModel.setGroupId(i);
				assetTagModel.setCompanyId(_companyId);
				assetTagModel.setUserId(_sampleUserId);
				assetTagModel.setUserName(_SAMPLE_USER_NAME);
				assetTagModel.setCreateDate(new Date());
				assetTagModel.setModifiedDate(new Date());
				assetTagModel.setName(
					StringBundler.concat("TestTag_", i, "_", j));
				assetTagModel.setLastPublishDate(new Date());

				assetTagModels.add(assetTagModel);
			}

			_assetTagModelsArray[i - 1] = assetTagModels;

			Map<Long, List<AssetTagModel>> assetTagModelsMap = new HashMap<>();

			int pageSize = assetTagModels.size() / _assetClassNameIds.length;

			for (int j = 0; j < _assetClassNameIds.length; j++) {
				int fromIndex = j * pageSize;

				int toIndex = (j + 1) * pageSize;

				if (j == (_assetClassNameIds.length - 1)) {
					toIndex = assetTagModels.size();
				}

				assetTagModelsMap.put(
					_assetClassNameIds[j],
					assetTagModels.subList(fromIndex, toIndex));
			}

			_assetTagModelsMaps[i - 1] = assetTagModelsMap;
		}
	}

	public void initJournalArticleContent() {
		int maxJournalArticleSize =
			BenchmarksPropsValues.MAX_JOURNAL_ARTICLE_SIZE;

		StringBundler sb = new StringBundler(6);

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

		_roleModels.add(
			newRoleModel(
				RoleConstants.ORGANIZATION_ADMINISTRATOR,
				RoleConstants.TYPE_ORGANIZATION));

		// Organization Owner

		_roleModels.add(
			newRoleModel(
				RoleConstants.ORGANIZATION_OWNER,
				RoleConstants.TYPE_ORGANIZATION));

		// Organization User

		_roleModels.add(
			newRoleModel(
				RoleConstants.ORGANIZATION_USER,
				RoleConstants.TYPE_ORGANIZATION));

		// Owner

		_ownerRoleModel = newRoleModel(
			RoleConstants.OWNER, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_ownerRoleModel);

		// Power User

		_powerUserRoleModel = newRoleModel(
			RoleConstants.POWER_USER, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_powerUserRoleModel);

		// Site Administrator

		_roleModels.add(
			newRoleModel(
				RoleConstants.SITE_ADMINISTRATOR, RoleConstants.TYPE_SITE));

		// Site Member

		_siteMemberRoleModel = newRoleModel(
			RoleConstants.SITE_MEMBER, RoleConstants.TYPE_SITE);

		_roleModels.add(_siteMemberRoleModel);

		// Site Owner

		_roleModels.add(
			newRoleModel(RoleConstants.SITE_OWNER, RoleConstants.TYPE_SITE));

		// User

		_userRoleModel = newRoleModel(
			RoleConstants.USER, RoleConstants.TYPE_REGULAR);

		_roleModels.add(_userRoleModel);
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

	public AccountModel newAccountModel() {
		AccountModel accountModel = new AccountModelImpl();

		// PK fields

		accountModel.setAccountId(_accountId);

		// Audit fields

		accountModel.setCompanyId(_companyId);
		accountModel.setCreateDate(new Date());
		accountModel.setModifiedDate(new Date());

		// Other fields

		accountModel.setName("Liferay");
		accountModel.setLegalName("Liferay, Inc.");

		return accountModel;
	}

	public AssetEntryModel newAssetEntryModel(BlogsEntryModel blogsEntryModel) {
		return newAssetEntryModel(
			blogsEntryModel.getGroupId(), blogsEntryModel.getCreateDate(),
			blogsEntryModel.getModifiedDate(), getClassNameId(BlogsEntry.class),
			blogsEntryModel.getEntryId(), blogsEntryModel.getUuid(), 0, true,
			true, ContentTypes.TEXT_HTML, blogsEntryModel.getTitle());
	}

	public AssetEntryModel newAssetEntryModel(
		DLFileEntryModel dLFileEntryModel) {

		return newAssetEntryModel(
			dLFileEntryModel.getGroupId(), dLFileEntryModel.getCreateDate(),
			dLFileEntryModel.getModifiedDate(),
			getClassNameId(DLFileEntry.class),
			dLFileEntryModel.getFileEntryId(), dLFileEntryModel.getUuid(),
			dLFileEntryModel.getFileEntryTypeId(), true, true,
			dLFileEntryModel.getMimeType(), dLFileEntryModel.getTitle());
	}

	public AssetEntryModel newAssetEntryModel(DLFolderModel dLFolderModel) {
		return newAssetEntryModel(
			dLFolderModel.getGroupId(), dLFolderModel.getCreateDate(),
			dLFolderModel.getModifiedDate(), getClassNameId(DLFolder.class),
			dLFolderModel.getFolderId(), dLFolderModel.getUuid(), 0, true, true,
			null, dLFolderModel.getName());
	}

	public AssetEntryModel newAssetEntryModel(MBMessageModel mbMessageModel) {
		long classNameId = 0;
		boolean visible = false;

		if (mbMessageModel.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID) {

			classNameId = getClassNameId(MBDiscussion.class);
		}
		else {
			classNameId = getClassNameId(MBMessage.class);
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
			mbThreadModel.getModifiedDate(), getClassNameId(MBThread.class),
			mbThreadModel.getThreadId(), mbThreadModel.getUuid(), 0, true,
			false, StringPool.BLANK,
			String.valueOf(mbThreadModel.getRootMessageId()));
	}

	public AssetEntryModel newAssetEntryModel(
		ObjectValuePair<JournalArticleModel, JournalArticleLocalizationModel>
			objectValuePair) {

		JournalArticleModel journalArticleModel = objectValuePair.getKey();
		JournalArticleLocalizationModel journalArticleLocalizationModel =
			objectValuePair.getValue();

		long resourcePrimKey = journalArticleModel.getResourcePrimKey();

		String resourceUUID = _journalArticleResourceUUIDs.get(resourcePrimKey);

		return newAssetEntryModel(
			journalArticleModel.getGroupId(),
			journalArticleModel.getCreateDate(),
			journalArticleModel.getModifiedDate(),
			getClassNameId(JournalArticle.class), resourcePrimKey, resourceUUID,
			_defaultJournalDDMStructureId, journalArticleModel.isIndexable(),
			true, ContentTypes.TEXT_HTML,
			journalArticleLocalizationModel.getTitle());
	}

	public AssetEntryModel newAssetEntryModel(WikiPageModel wikiPageModel) {
		return newAssetEntryModel(
			wikiPageModel.getGroupId(), wikiPageModel.getCreateDate(),
			wikiPageModel.getModifiedDate(), getClassNameId(WikiPage.class),
			wikiPageModel.getResourcePrimKey(), wikiPageModel.getUuid(), 0,
			true, true, ContentTypes.TEXT_HTML, wikiPageModel.getTitle());
	}

	public List<PortletPreferencesModel>
		newAssetPublisherPortletPreferencesModels(long plid) {

		List<PortletPreferencesModel> portletPreferencesModels =
			new ArrayList<>(3);

		portletPreferencesModels.add(
			newPortletPreferencesModel(plid, BlogsPortletKeys.BLOGS));
		portletPreferencesModels.add(
			newPortletPreferencesModel(plid, JournalPortletKeys.JOURNAL));
		portletPreferencesModels.add(
			newPortletPreferencesModel(plid, WikiPortletKeys.WIKI));

		return portletPreferencesModels;
	}

	public List<PortletPreferenceValueModel>
			newAssetPublisherPortletPreferenceValueModels(
				PortletPreferencesModel portletPreferencesModel, long groupId,
				int currentIndex)
		throws Exception {

		if (currentIndex == 1) {
			return Collections.emptyList();
		}

		String assetPublisherQueryName = "assetCategories";

		if ((currentIndex % 2) == 0) {
			assetPublisherQueryName = "assetTags";
		}

		ObjectValuePair<String[], Integer> objectValuePair = null;

		Integer startIndex = _assetPublisherQueryStartIndexes.get(groupId);

		if (startIndex == null) {
			startIndex = 0;
		}

		if (assetPublisherQueryName.equals("assetCategories")) {
			Map<Long, List<AssetCategoryModel>> assetCategoryModelsMap =
				_assetCategoryModelsMaps[(int)groupId - 1];

			List<AssetCategoryModel> assetCategoryModels =
				assetCategoryModelsMap.get(getNextAssetClassNameId(groupId));

			if ((assetCategoryModels == null) ||
				assetCategoryModels.isEmpty()) {

				return Collections.emptyList();
			}

			objectValuePair = getAssetPublisherAssetCategoriesQueryValues(
				assetCategoryModels, startIndex);
		}
		else {
			Map<Long, List<AssetTagModel>> assetTagModelsMap =
				_assetTagModelsMaps[(int)groupId - 1];

			List<AssetTagModel> assetTagModels = assetTagModelsMap.get(
				getNextAssetClassNameId(groupId));

			if ((assetTagModels == null) || assetTagModels.isEmpty()) {
				return Collections.emptyList();
			}

			objectValuePair = getAssetPublisherAssetTagsQueryValues(
				assetTagModels, startIndex);
		}

		String[] assetPublisherQueryValues = objectValuePair.getKey();

		PortletPreferences jxPortletPreferences =
			(PortletPreferences)
				_defaultAssetPublisherPortletPreferencesImpl.clone();

		jxPortletPreferences.setValue("queryAndOperator0", "false");
		jxPortletPreferences.setValue("queryContains0", "true");
		jxPortletPreferences.setValue("queryName0", assetPublisherQueryName);
		jxPortletPreferences.setValues(
			"queryValues0",
			new String[] {
				assetPublisherQueryValues[0], assetPublisherQueryValues[1],
				assetPublisherQueryValues[2]
			});
		jxPortletPreferences.setValue("queryAndOperator1", "false");
		jxPortletPreferences.setValue("queryContains1", "false");
		jxPortletPreferences.setValue("queryName1", assetPublisherQueryName);
		jxPortletPreferences.setValue(
			"queryValues1", assetPublisherQueryValues[3]);

		List<PortletPreferenceValueModel> portletPreferenceValueModels =
			new ArrayList<>();

		Map<String, String[]> map = jxPortletPreferences.getMap();

		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String[] values = entry.getValue();

			if (values == null) {
				continue;
			}

			for (int i = 0; i < values.length; i++) {
				portletPreferenceValueModels.add(
					newPortletPreferenceValueModel(
						portletPreferencesModel, entry.getKey(), i, values[i]));
			}
		}

		return portletPreferenceValueModels;
	}

	public List<BlogsEntryModel> newBlogsEntryModels(long groupId) {
		List<BlogsEntryModel> blogEntryModels = new ArrayList<>(
			BenchmarksPropsValues.MAX_BLOGS_ENTRY_COUNT);

		for (int i = 1; i <= BenchmarksPropsValues.MAX_BLOGS_ENTRY_COUNT; i++) {
			blogEntryModels.add(newBlogsEntryModel(groupId, i));
		}

		return blogEntryModels;
	}

	public BlogsStatsUserModel newBlogsStatsUserModel(long groupId) {
		BlogsStatsUserModel blogsStatsUserModel = new BlogsStatsUserModelImpl();

		// PK fields

		blogsStatsUserModel.setStatsUserId(_counter.get());

		// Group instance

		blogsStatsUserModel.setGroupId(groupId);

		// Audit fields

		blogsStatsUserModel.setCompanyId(_companyId);
		blogsStatsUserModel.setUserId(_sampleUserId);

		// Other fields

		blogsStatsUserModel.setEntryCount(
			BenchmarksPropsValues.MAX_BLOGS_ENTRY_COUNT);
		blogsStatsUserModel.setLastPostDate(new Date());

		return blogsStatsUserModel;
	}

	public GroupModel newCommerceCatalogGroupModel(
		CommerceCatalogModel commerceCatalogModel) {

		return newGroupModel(
			_counter.get(), getClassNameId(CommerceCatalog.class),
			commerceCatalogModel.getCommerceCatalogId(),
			commerceCatalogModel.getName(), false);
	}

	public CommerceCatalogModel newCommerceCatalogModel(
		CommerceCurrencyModel commerceCurrencyModel) {

		CommerceCatalogModel commerceCatalogModel =
			new CommerceCatalogModelImpl();

		// PK fields

		commerceCatalogModel.setCommerceCatalogId(_counter.get());

		// Audit fields

		commerceCatalogModel.setCompanyId(_companyId);
		commerceCatalogModel.setUserName(_SAMPLE_USER_NAME);
		commerceCatalogModel.setCreateDate(new Date());
		commerceCatalogModel.setModifiedDate(new Date());

		// Other fields

		commerceCatalogModel.setName("Master");
		commerceCatalogModel.setCommerceCurrencyCode(
			commerceCurrencyModel.getCode());
		commerceCatalogModel.setCatalogDefaultLanguageId("en_US");
		commerceCatalogModel.setSystem(true);

		return commerceCatalogModel;
	}

	public ResourcePermissionModel newCommerceCatalogResourcePermissionModel(
		CommerceCatalogModel commerceCatalogModel) {

		return newResourcePermissionModel(
			CommerceCatalog.class.getName(),
			String.valueOf(commerceCatalogModel.getCommerceCatalogId()),
			_guestRoleModel.getRoleId(), _sampleUserId);
	}

	public GroupModel newCommerceChannelGroupModel(
		CommerceChannelModel commerceChannelModel) {

		return newGroupModel(
			_counter.get(), getClassNameId(CommerceChannel.class),
			commerceChannelModel.getCommerceChannelId(),
			commerceChannelModel.getName(), false);
	}

	public CommerceChannelModel newCommerceChannelModel(
		CommerceCurrencyModel commerceCurrencyModel) {

		CommerceChannelModel commerceChannelModel =
			new CommerceChannelModelImpl();

		// PK fields

		commerceChannelModel.setCommerceChannelId(_counter.get());

		// Audit fields

		commerceChannelModel.setCompanyId(_companyId);
		commerceChannelModel.setUserId(_sampleUserId);
		commerceChannelModel.setUserName(_SAMPLE_USER_NAME);
		commerceChannelModel.setCreateDate(new Date());
		commerceChannelModel.setModifiedDate(new Date());

		// Other fields

		commerceChannelModel.setSiteGroupId(1);
		commerceChannelModel.setName(_SAMPLE_USER_NAME + " Channel");
		commerceChannelModel.setType("site");
		commerceChannelModel.setTypeSettings(String.valueOf(_guestGroupId));
		commerceChannelModel.setCommerceCurrencyCode(
			commerceCurrencyModel.getCode());

		return commerceChannelModel;
	}

	public CommerceCurrencyModel newCommerceCurrencyModel() {
		CommerceCurrencyModel commerceCurrencyModel =
			new CommerceCurrencyModelImpl();

		commerceCurrencyModel.setUuid(SequentialUUID.generate());

		// PK fields

		commerceCurrencyModel.setCommerceCurrencyId(_counter.get());

		// Audit fields

		commerceCurrencyModel.setCompanyId(_companyId);
		commerceCurrencyModel.setUserId(_sampleUserId);
		commerceCurrencyModel.setUserName(_SAMPLE_USER_NAME);
		commerceCurrencyModel.setCreateDate(new Date());
		commerceCurrencyModel.setModifiedDate(new Date());

		// Other fields

		commerceCurrencyModel.setCode("USD");

		String name = StringBundler.concat(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root available-locales",
			"=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">",
			"US Dollar</Name></root>");

		commerceCurrencyModel.setName(name);

		commerceCurrencyModel.setRate(BigDecimal.valueOf(1));

		String formatPattern = StringBundler.concat(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root available-locales",
			"=\"en_US\" default-locale=\"en_US\"><FormatPattern language-id",
			"=\"en_US\">$###,##0.00</FormatPattern></root>");

		commerceCurrencyModel.setFormatPattern(formatPattern);

		commerceCurrencyModel.setMaxFractionDigits(2);
		commerceCurrencyModel.setMinFractionDigits(2);
		commerceCurrencyModel.setRoundingMode("HALF_EVEN");
		commerceCurrencyModel.setPrimary(true);
		commerceCurrencyModel.setPriority(1);
		commerceCurrencyModel.setActive(true);
		commerceCurrencyModel.setLastPublishDate(new Date());

		return commerceCurrencyModel;
	}

	public CompanyModel newCompanyModel() {
		CompanyModel companyModel = new CompanyModelImpl();

		// PK fields

		companyModel.setCompanyId(_companyId);

		// Other fields

		companyModel.setAccountId(_accountId);
		companyModel.setWebId("liferay.com");
		companyModel.setMx("liferay.com");
		companyModel.setActive(true);

		return companyModel;
	}

	public ContactModel newContactModel(UserModel userModel) {
		ContactModel contactModel = new ContactModelImpl();

		// PK fields

		contactModel.setContactId(userModel.getContactId());

		// Audit fields

		contactModel.setCompanyId(userModel.getCompanyId());
		contactModel.setUserId(userModel.getUserId());

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		contactModel.setUserName(
			fullNameGenerator.getFullName(
				userModel.getFirstName(), userModel.getMiddleName(),
				userModel.getLastName()));

		contactModel.setCreateDate(new Date());
		contactModel.setModifiedDate(new Date());

		// Other fields

		contactModel.setClassNameId(getClassNameId(User.class));
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

	public LayoutModel newContentLayoutModel(
		long groupId, String name, String fragmentEntries) {

		SimpleCounter simpleCounter = _layoutCounters.get(groupId);

		if (simpleCounter == null) {
			simpleCounter = new SimpleCounter();

			_layoutCounters.put(groupId, simpleCounter);
		}

		LayoutModel layoutModel = new LayoutModelImpl();

		// UUID

		layoutModel.setUuid(SequentialUUID.generate());

		// PK fields

		layoutModel.setPlid(_counter.get());

		// Group instance

		layoutModel.setGroupId(groupId);

		// Audit fields

		layoutModel.setCompanyId(_companyId);
		layoutModel.setUserId(_sampleUserId);
		layoutModel.setUserName(_SAMPLE_USER_NAME);
		layoutModel.setCreateDate(new Date());
		layoutModel.setModifiedDate(new Date());

		// Other fields

		layoutModel.setLayoutId(simpleCounter.get());
		layoutModel.setName(
			"<?xml version=\"1.0\"?><root><name>" + name + "</name></root>");
		layoutModel.setType(LayoutConstants.TYPE_CONTENT);

		UnicodeProperties typeSettingsUnicodeProperties = new UnicodeProperties(
			true);

		typeSettingsUnicodeProperties.setProperty(
			"fragmentEntries", fragmentEntries);

		layoutModel.setTypeSettings(
			StringUtil.replace(
				typeSettingsUnicodeProperties.toString(), '\n', "\\n"));

		layoutModel.setFriendlyURL(StringPool.FORWARD_SLASH + name);
		layoutModel.setLastPublishDate(new Date());

		return layoutModel;
	}

	public List<LayoutModel> newContentLayoutModels(long groupId) {
		List<LayoutModel> layoutModels = new ArrayList<>();

		for (int i = 0; i < BenchmarksPropsValues.MAX_CONTENT_LAYOUT_COUNT;
			 i++) {

			layoutModels.add(
				newContentLayoutModel(
					groupId, i + "_web_content", "web_content"));
		}

		return layoutModels;
	}

	public List<CounterModel> newCounterModels() {
		List<CounterModel> counterModels = new ArrayList<>();

		// Counter

		CounterModel counterModel = new CounterModelImpl();

		counterModel.setName(Counter.class.getName());
		counterModel.setCurrentId(_counter.get());

		counterModels.add(counterModel);

		// FriendlyURLEntryLocalization

		counterModel = new CounterModelImpl();

		counterModel.setName(FriendlyURLEntryLocalization.class.getName());
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

	public CPDefinitionLocalizationModel newCPDefinitionLocalizationModel(
		CPDefinitionModel cpDefinitionModel) {

		CPDefinitionLocalizationModel cpDefinitionLocalizationModel =
			new CPDefinitionLocalizationModelImpl();

		// Localized entity

		long cpDefinitionId = cpDefinitionModel.getCPDefinitionId();

		cpDefinitionLocalizationModel.setName("Definition " + cpDefinitionId);
		cpDefinitionLocalizationModel.setShortDescription(
			"Short description for definition " + cpDefinitionId);
		cpDefinitionLocalizationModel.setDescription(
			"A longer and more verbose description for definition with ID " +
				cpDefinitionId);
		cpDefinitionLocalizationModel.setMetaTitle(
			"A meta-title for definition " + cpDefinitionId);
		cpDefinitionLocalizationModel.setMetaDescription(
			"A meta-description for definition " + cpDefinitionId);
		cpDefinitionLocalizationModel.setMetaKeywords(
			"Meta-keywords for definition " + cpDefinitionId);

		// Autogenerated fields

		cpDefinitionLocalizationModel.setCompanyId(_companyId);
		cpDefinitionLocalizationModel.setCPDefinitionId(cpDefinitionId);
		cpDefinitionLocalizationModel.setCpDefinitionLocalizationId(
			_counter.get());
		cpDefinitionLocalizationModel.setLanguageId("en_US");

		return cpDefinitionLocalizationModel;
	}

	public CPDefinitionModel newCPDefinitionModel(
		CPTaxCategoryModel cpTaxCategoryModel, CProductModel cProductModel,
		GroupModel commerceCatalogGroupModel, int version) {

		CPDefinitionModel cpDefinitionModel = new CPDefinitionModelImpl();

		// UUID

		cpDefinitionModel.setUuid(SequentialUUID.generate());

		// PK fields

		long cpDefinitionId = _counter.get();

		cpDefinitionModel.setCPDefinitionId(cpDefinitionId);

		// Group instance

		cpDefinitionModel.setGroupId(commerceCatalogGroupModel.getGroupId());

		// Audit fields

		cpDefinitionModel.setCompanyId(_companyId);
		cpDefinitionModel.setUserId(_sampleUserId);
		cpDefinitionModel.setUserName(_SAMPLE_USER_NAME);
		cpDefinitionModel.setCreateDate(new Date());
		cpDefinitionModel.setModifiedDate(new Date());

		// Other fields

		cpDefinitionModel.setCProductId(cProductModel.getCProductId());
		cpDefinitionModel.setCPTaxCategoryId(
			cpTaxCategoryModel.getCPTaxCategoryId());
		cpDefinitionModel.setProductTypeName("simple");
		cpDefinitionModel.setAvailableIndividually(true);
		cpDefinitionModel.setIgnoreSKUCombinations(true);
		cpDefinitionModel.setShippable(true);
		cpDefinitionModel.setFreeShipping(false);
		cpDefinitionModel.setShipSeparately(true);
		cpDefinitionModel.setShippingExtraPrice(3.0);
		cpDefinitionModel.setWidth(0);
		cpDefinitionModel.setHeight(0);
		cpDefinitionModel.setDepth(0);
		cpDefinitionModel.setWeight(0);
		cpDefinitionModel.setTaxExempt(false);
		cpDefinitionModel.setTelcoOrElectronics(false);
		cpDefinitionModel.setDDMStructureKey(null);
		cpDefinitionModel.setPublished(true);
		cpDefinitionModel.setDisplayDate(new Date());
		cpDefinitionModel.setExpirationDate(null);
		cpDefinitionModel.setLastPublishDate(null);
		cpDefinitionModel.setSubscriptionEnabled(false);
		cpDefinitionModel.setSubscriptionLength(0);
		cpDefinitionModel.setSubscriptionType(null);
		cpDefinitionModel.setSubscriptionTypeSettings(null);
		cpDefinitionModel.setMaxSubscriptionCycles(0);
		cpDefinitionModel.setVersion(version);
		cpDefinitionModel.setStatus(WorkflowConstants.STATUS_APPROVED);
		cpDefinitionModel.setStatusByUserId(_sampleUserId);
		cpDefinitionModel.setStatusByUserName(_SAMPLE_USER_NAME);
		cpDefinitionModel.setStatusDate(new Date());

		if (version ==
				(BenchmarksPropsValues.MAX_COMMERCE_PRODUCT_DEFINITION_COUNT -
					1)) {

			cProductModel.setPublishedCPDefinitionId(cpDefinitionId);
		}

		return cpDefinitionModel;
	}

	public AssetEntryModel newCPDefinitionModelAssetEntryModel(
		CPDefinitionModel cpDefinitionModel,
		GroupModel commerceCatalogGroupModel) {

		return newAssetEntryModel(
			commerceCatalogGroupModel.getGroupId(), new Date(), new Date(),
			getClassNameId(CPDefinition.class),
			cpDefinitionModel.getCPDefinitionId(), SequentialUUID.generate(), 0,
			true, true, "text/plain",
			"Definition " + cpDefinitionModel.getCPDefinitionId());
	}

	public CPInstanceModel newCPInstanceModel(
		CPDefinitionModel cpDefinitionModel,
		GroupModel commerceCatalogGroupModel, int index) {

		CPInstanceModel cpInstanceModel = new CPInstanceModelImpl();

		// UUID

		cpInstanceModel.setUuid(SequentialUUID.generate());

		// PK fields

		cpInstanceModel.setCPInstanceId(_counter.get());

		// Group instance

		cpInstanceModel.setGroupId(commerceCatalogGroupModel.getGroupId());

		// Audit fields

		cpInstanceModel.setCompanyId(_companyId);
		cpInstanceModel.setUserId(_sampleUserId);
		cpInstanceModel.setUserName(_SAMPLE_USER_NAME);
		cpInstanceModel.setCreateDate(new Date());
		cpInstanceModel.setModifiedDate(new Date());

		// Other fields

		long cpDefinitionId = cpDefinitionModel.getCPDefinitionId();

		cpInstanceModel.setCPDefinitionId(cpDefinitionId);

		cpInstanceModel.setCPInstanceUuid(SequentialUUID.generate());

		String instanceKey = cpDefinitionId + StringPool.POUND + index;

		cpInstanceModel.setSku("SKU" + instanceKey);
		cpInstanceModel.setGtin("GTIN" + instanceKey);
		cpInstanceModel.setManufacturerPartNumber("MPN" + instanceKey);

		cpInstanceModel.setPurchasable(true);
		cpInstanceModel.setWidth((index * 2) + 1);
		cpInstanceModel.setHeight(index + 5);
		cpInstanceModel.setDepth(index);
		cpInstanceModel.setWeight((index * 3) + 1);
		cpInstanceModel.setPrice(BigDecimal.valueOf(index + 10.1));
		cpInstanceModel.setPromoPrice(BigDecimal.valueOf(index + 9.2));
		cpInstanceModel.setCost(BigDecimal.valueOf(index + 6.4));
		cpInstanceModel.setPublished(true);
		cpInstanceModel.setDisplayDate(new Date());
		cpInstanceModel.setExpirationDate(null);
		cpInstanceModel.setLastPublishDate(null);
		cpInstanceModel.setOverrideSubscriptionInfo(false);
		cpInstanceModel.setSubscriptionEnabled(false);
		cpInstanceModel.setSubscriptionLength(0);
		cpInstanceModel.setSubscriptionType(null);
		cpInstanceModel.setSubscriptionTypeSettings(null);
		cpInstanceModel.setMaxSubscriptionCycles(0);
		cpInstanceModel.setStatus(WorkflowConstants.STATUS_APPROVED);
		cpInstanceModel.setStatusByUserId(_sampleUserId);
		cpInstanceModel.setStatusByUserName(_SAMPLE_USER_NAME);
		cpInstanceModel.setStatusDate(new Date());

		return cpInstanceModel;
	}

	public CProductModel newCProductModel(
		GroupModel commerceCatalogGroupModel) {

		CProductModel cProductModel = new CProductModelImpl();

		// UUID

		cProductModel.setUuid(SequentialUUID.generate());

		// PK fields

		cProductModel.setCProductId(_counter.get());

		// Group instance

		cProductModel.setGroupId(commerceCatalogGroupModel.getGroupId());

		// Audit fields

		cProductModel.setCompanyId(_companyId);
		cProductModel.setUserId(_sampleUserId);
		cProductModel.setUserName(_SAMPLE_USER_NAME);
		cProductModel.setCreateDate(new Date());
		cProductModel.setModifiedDate(new Date());

		// Other fields

		cProductModel.setLatestVersion(
			BenchmarksPropsValues.MAX_COMMERCE_PRODUCT_DEFINITION_COUNT);

		return cProductModel;
	}

	public CPTaxCategoryModel newCPTaxCategoryModel() {
		CPTaxCategoryModel cpTaxCategoryModel = new CPTaxCategoryModelImpl();

		// PK fields

		cpTaxCategoryModel.setCPTaxCategoryId(_counter.get());

		// Audit fields

		cpTaxCategoryModel.setCompanyId(_companyId);
		cpTaxCategoryModel.setUserId(_sampleUserId);
		cpTaxCategoryModel.setUserName(_SAMPLE_USER_NAME);
		cpTaxCategoryModel.setCreateDate(new Date());
		cpTaxCategoryModel.setModifiedDate(new Date());

		// Other fields

		cpTaxCategoryModel.setName(
			StringBundler.concat(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root ",
				"available-locales=\"en_US\" default-locale=\"en_US\"><Name ",
				"language-id=\"en_US\">Normal Product</Name></root>"));

		cpTaxCategoryModel.setDescription(null);

		return cpTaxCategoryModel;
	}

	public DDMStructureLayoutModel newDDLDDMStructureLayoutModel(
		long groupId, DDMStructureVersionModel ddmStructureVersionModel) {

		StringBundler sb = new StringBundler(
			3 + (BenchmarksPropsValues.MAX_DDL_CUSTOM_FIELD_COUNT * 4));

		sb.append("{\"defaultLanguageId\": \"en_US\", \"pages\": [{\"rows\": ");
		sb.append("[");

		for (int i = 0; i < BenchmarksPropsValues.MAX_DDL_CUSTOM_FIELD_COUNT;
			 i++) {

			sb.append("{\"columns\": [{\"fieldNames\": [\"");
			sb.append(nextDDLCustomFieldName(groupId, i));
			sb.append("\"], \"size\": 12}]}");
			sb.append(", ");
		}

		if (BenchmarksPropsValues.MAX_DDL_CUSTOM_FIELD_COUNT > 0) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("], \"title\": {\"en_US\": \"\"}}],\"paginationMode\": ");
		sb.append("\"single-page\"}");

		return newDDMStructureLayoutModel(
			_globalGroupId, _defaultUserId,
			ddmStructureVersionModel.getStructureVersionId(), sb.toString());
	}

	public DDMStructureModel newDDLDDMStructureModel(long groupId) {
		StringBundler sb = new StringBundler(
			3 + (BenchmarksPropsValues.MAX_DDL_CUSTOM_FIELD_COUNT * 9));

		sb.append("{\"availableLanguageIds\": [\"en_US\"],");
		sb.append("\"defaultLanguageId\": \"en_US\", \"fields\": [");

		for (int i = 0; i < BenchmarksPropsValues.MAX_DDL_CUSTOM_FIELD_COUNT;
			 i++) {

			sb.append("{\"dataType\": \"string\", \"indexType\": ");
			sb.append("\"keyword\", \"label\": {\"en_US\": \"Text");
			sb.append(i);
			sb.append("\"}, \"name\": \"");
			sb.append(nextDDLCustomFieldName(groupId, i));
			sb.append("\", \"readOnly\": false, \"repeatable\": false,");
			sb.append("\"required\": false, \"showLabel\": true, \"type\": ");
			sb.append("\"text\"}");
			sb.append(",");
		}

		if (BenchmarksPropsValues.MAX_DDL_CUSTOM_FIELD_COUNT > 0) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("]}");

		return newDDMStructureModel(
			groupId, _sampleUserId, getClassNameId(DDLRecordSet.class),
			"Test DDM Structure", sb.toString(), _counter.get());
	}

	public List<PortletPreferencesModel> newDDLPortletPreferencesModels(
		long plid) {

		List<PortletPreferencesModel> portletPreferencesModels =
			new ArrayList<>(3);

		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, DDLPortletKeys.DYNAMIC_DATA_LISTS_DISPLAY));
		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, DDLPortletKeys.DYNAMIC_DATA_LISTS));
		portletPreferencesModels.add(
			newPortletPreferencesModel(
				plid, DDMPortletKeys.DYNAMIC_DATA_MAPPING));

		return portletPreferencesModels;
	}

	public List<PortletPreferenceValueModel> newDDLPortletPreferenceValueModels(
		PortletPreferencesModel portletPreferencesModel,
		DDLRecordSetModel ddlRecordSetModel) {

		return Arrays.asList(
			newPortletPreferenceValueModel(
				portletPreferencesModel, "editable", 0, "true"),
			newPortletPreferenceValueModel(
				portletPreferencesModel, "recordSetId", 0,
				String.valueOf(ddlRecordSetModel.getRecordSetId())),
			newPortletPreferenceValueModel(
				portletPreferencesModel, "spreadsheet", 0, "false"));
	}

	public DDLRecordModel newDDLRecordModel(
		DDLRecordSetModel dDLRecordSetModel) {

		DDLRecordModel ddlRecordModel = new DDLRecordModelImpl();

		// UUID

		ddlRecordModel.setUuid(SequentialUUID.generate());

		// PK fields

		ddlRecordModel.setRecordId(_counter.get());

		// Group instance

		ddlRecordModel.setGroupId(dDLRecordSetModel.getGroupId());

		// Audit fields

		ddlRecordModel.setCompanyId(_companyId);
		ddlRecordModel.setUserId(_sampleUserId);
		ddlRecordModel.setUserName(_SAMPLE_USER_NAME);
		ddlRecordModel.setVersionUserId(_sampleUserId);
		ddlRecordModel.setVersionUserName(_SAMPLE_USER_NAME);
		ddlRecordModel.setCreateDate(new Date());
		ddlRecordModel.setModifiedDate(new Date());

		// Other fields

		ddlRecordModel.setDDMStorageId(_counter.get());
		ddlRecordModel.setRecordSetId(dDLRecordSetModel.getRecordSetId());
		ddlRecordModel.setVersion(DDLRecordConstants.VERSION_DEFAULT);
		ddlRecordModel.setDisplayIndex(
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT);
		ddlRecordModel.setLastPublishDate(new Date());

		return ddlRecordModel;
	}

	public DDLRecordSetModel newDDLRecordSetModel(
		DDMStructureModel ddmStructureModel, int currentIndex) {

		DDLRecordSetModel ddlRecordSetModel = new DDLRecordSetModelImpl();

		// UUID

		ddlRecordSetModel.setUuid(SequentialUUID.generate());

		// PK fields

		ddlRecordSetModel.setRecordSetId(_counter.get());

		// Group instance

		ddlRecordSetModel.setGroupId(ddmStructureModel.getGroupId());

		// Audit fields

		ddlRecordSetModel.setCompanyId(_companyId);
		ddlRecordSetModel.setUserId(_sampleUserId);
		ddlRecordSetModel.setUserName(_SAMPLE_USER_NAME);
		ddlRecordSetModel.setCreateDate(new Date());
		ddlRecordSetModel.setModifiedDate(new Date());

		// Other fields

		ddlRecordSetModel.setDDMStructureId(ddmStructureModel.getStructureId());
		ddlRecordSetModel.setRecordSetKey(String.valueOf(_counter.get()));

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\"?><root available-locales=\"en_US\" ");
		sb.append("default-locale=\"en_US\"><name language-id=\"en_US\">");
		sb.append("Test DDL Record Set ");
		sb.append(currentIndex);
		sb.append("</name></root>");

		ddlRecordSetModel.setName(sb.toString());

		ddlRecordSetModel.setMinDisplayRows(
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT);
		ddlRecordSetModel.setScope(
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);
		ddlRecordSetModel.setSettings(StringPool.BLANK);
		ddlRecordSetModel.setLastPublishDate(new Date());

		return ddlRecordSetModel;
	}

	public DDLRecordVersionModel newDDLRecordVersionModel(
		DDLRecordModel dDLRecordModel) {

		DDLRecordVersionModel ddlRecordVersionModel =
			new DDLRecordVersionModelImpl();

		// PK fields

		ddlRecordVersionModel.setRecordVersionId(_counter.get());

		// Group instance

		ddlRecordVersionModel.setGroupId(dDLRecordModel.getGroupId());

		// Audit fields

		ddlRecordVersionModel.setCompanyId(_companyId);
		ddlRecordVersionModel.setUserId(_sampleUserId);
		ddlRecordVersionModel.setUserName(_SAMPLE_USER_NAME);
		ddlRecordVersionModel.setCreateDate(dDLRecordModel.getModifiedDate());

		// Other fields

		ddlRecordVersionModel.setDDMStorageId(dDLRecordModel.getDDMStorageId());
		ddlRecordVersionModel.setRecordSetId(dDLRecordModel.getRecordSetId());
		ddlRecordVersionModel.setRecordId(dDLRecordModel.getRecordId());
		ddlRecordVersionModel.setVersion(dDLRecordModel.getVersion());
		ddlRecordVersionModel.setDisplayIndex(dDLRecordModel.getDisplayIndex());
		ddlRecordVersionModel.setStatus(WorkflowConstants.STATUS_APPROVED);
		ddlRecordVersionModel.setStatusDate(dDLRecordModel.getModifiedDate());

		return ddlRecordVersionModel;
	}

	public List<DDMFieldAttributeModel> newDDMFieldAttributeModels(
		DLFileEntryModel dlFileEntryModel, List<DDMFieldModel> ddmFieldModels,
		DDMStorageLinkModel ddmStorageLinkModel) {

		DDMFieldModel ddmFieldModel = ddmFieldModels.get(0);

		DDMFieldAttributeModel ddmFieldAttributeModel1 =
			new DDMFieldAttributeModelImpl();

		ddmFieldAttributeModel1.setFieldAttributeId(_counter.get());
		ddmFieldAttributeModel1.setCompanyId(_companyId);
		ddmFieldAttributeModel1.setFieldId(ddmFieldModel.getFieldId());
		ddmFieldAttributeModel1.setStorageId(ddmStorageLinkModel.getClassPK());
		ddmFieldAttributeModel1.setAttributeName("availableLanguageIds");
		ddmFieldAttributeModel1.setLanguageId(StringPool.BLANK);
		ddmFieldAttributeModel1.setSmallAttributeValue("en_US");

		DDMFieldAttributeModel ddmFieldAttributeModel2 =
			new DDMFieldAttributeModelImpl();

		ddmFieldAttributeModel2.setFieldAttributeId(_counter.get());
		ddmFieldAttributeModel2.setCompanyId(_companyId);
		ddmFieldAttributeModel2.setFieldId(ddmFieldModel.getFieldId());
		ddmFieldAttributeModel2.setStorageId(ddmStorageLinkModel.getClassPK());
		ddmFieldAttributeModel2.setAttributeName("defaultLanguageId");
		ddmFieldAttributeModel2.setLanguageId(StringPool.BLANK);
		ddmFieldAttributeModel2.setSmallAttributeValue("en_US");

		ddmFieldModel = ddmFieldModels.get(1);

		DDMFieldAttributeModel ddmFieldAttributeModel3 =
			new DDMFieldAttributeModelImpl();

		ddmFieldAttributeModel3.setFieldAttributeId(_counter.get());
		ddmFieldAttributeModel3.setCompanyId(_companyId);
		ddmFieldAttributeModel3.setFieldId(ddmFieldModel.getFieldId());
		ddmFieldAttributeModel3.setStorageId(ddmStorageLinkModel.getClassPK());
		ddmFieldAttributeModel3.setAttributeName(StringPool.BLANK);
		ddmFieldAttributeModel3.setLanguageId("en_US");
		ddmFieldAttributeModel3.setSmallAttributeValue("text/plain");

		return Arrays.asList(
			ddmFieldAttributeModel1, ddmFieldAttributeModel2,
			ddmFieldAttributeModel3);
	}

	public List<DDMFieldAttributeModel> newDDMFieldAttributeModels(
		int currentIndex, DDLRecordModel ddlRecordModel,
		List<DDMFieldModel> ddmFieldModels,
		DDMStorageLinkModel ddmStorageLinkModel) {

		List<DDMFieldAttributeModel> ddmFieldAttributeModels =
			new ArrayList<>();

		DDMFieldModel ddmFieldModel = ddmFieldModels.get(0);

		DDMFieldAttributeModel ddmFieldAttributeModel1 =
			new DDMFieldAttributeModelImpl();

		ddmFieldAttributeModel1.setFieldAttributeId(_counter.get());
		ddmFieldAttributeModel1.setCompanyId(_companyId);
		ddmFieldAttributeModel1.setFieldId(ddmFieldModel.getFieldId());
		ddmFieldAttributeModel1.setStorageId(ddmStorageLinkModel.getClassPK());
		ddmFieldAttributeModel1.setAttributeName("availableLanguageIds");
		ddmFieldAttributeModel1.setLanguageId(StringPool.BLANK);
		ddmFieldAttributeModel1.setSmallAttributeValue("en_US");

		ddmFieldAttributeModels.add(ddmFieldAttributeModel1);

		DDMFieldAttributeModel ddmFieldAttributeModel2 =
			new DDMFieldAttributeModelImpl();

		ddmFieldAttributeModel2.setFieldAttributeId(_counter.get());
		ddmFieldAttributeModel2.setCompanyId(_companyId);
		ddmFieldAttributeModel2.setFieldId(ddmFieldModel.getFieldId());
		ddmFieldAttributeModel2.setStorageId(ddmStorageLinkModel.getClassPK());
		ddmFieldAttributeModel2.setAttributeName("defaultLanguageId");
		ddmFieldAttributeModel2.setLanguageId(StringPool.BLANK);
		ddmFieldAttributeModel2.setSmallAttributeValue("en_US");

		ddmFieldAttributeModels.add(ddmFieldAttributeModel2);

		for (int i = 1; i < ddmFieldModels.size(); i++) {
			ddmFieldModel = ddmFieldModels.get(i);

			DDMFieldAttributeModel ddmFieldAttributeModel =
				new DDMFieldAttributeModelImpl();

			ddmFieldAttributeModel.setFieldAttributeId(_counter.get());
			ddmFieldAttributeModel.setCompanyId(_companyId);
			ddmFieldAttributeModel.setFieldId(ddmFieldModel.getFieldId());
			ddmFieldAttributeModel.setStorageId(
				ddmStorageLinkModel.getClassPK());
			ddmFieldAttributeModel.setAttributeName(StringPool.BLANK);
			ddmFieldAttributeModel.setLanguageId("en_US");
			ddmFieldAttributeModel.setSmallAttributeValue(
				"Test Record " + currentIndex);

			ddmFieldAttributeModels.add(ddmFieldAttributeModel);
		}

		return ddmFieldAttributeModels;
	}

	public List<DDMFieldModel> newDDMFieldModels(
		DLFileEntryModel dlFileEntryModel,
		DDMStorageLinkModel ddmStorageLinkModel) {

		DDMFieldModel ddmFieldModel1 = new DDMFieldModelImpl();

		ddmFieldModel1.setFieldId(_counter.get());
		ddmFieldModel1.setCompanyId(_companyId);
		ddmFieldModel1.setParentFieldId(0);
		ddmFieldModel1.setStorageId(ddmStorageLinkModel.getClassPK());
		ddmFieldModel1.setStructureVersionId(_defaultDLDDMStructureVersionId);
		ddmFieldModel1.setFieldName(StringPool.BLANK);
		ddmFieldModel1.setFieldType(StringPool.BLANK);
		ddmFieldModel1.setInstanceId(StringPool.BLANK);
		ddmFieldModel1.setLocalizable(false);
		ddmFieldModel1.setPriority(0);

		DDMFieldModel ddmFieldModel2 = new DDMFieldModelImpl();

		ddmFieldModel2.setFieldId(_counter.get());
		ddmFieldModel2.setCompanyId(_companyId);
		ddmFieldModel2.setParentFieldId(0);
		ddmFieldModel2.setStorageId(ddmStorageLinkModel.getClassPK());
		ddmFieldModel2.setStructureVersionId(_defaultDLDDMStructureVersionId);
		ddmFieldModel2.setFieldName("CONTENT_TYPE");
		ddmFieldModel2.setFieldType("string");
		ddmFieldModel2.setInstanceId(StringUtil.randomId());
		ddmFieldModel2.setLocalizable(true);
		ddmFieldModel2.setPriority(1);

		return Arrays.asList(ddmFieldModel1, ddmFieldModel2);
	}

	public List<DDMFieldModel> newDDMFieldModels(
		int currentIndex, DDLRecordModel ddlRecordModel,
		DDMStorageLinkModel ddmStorageLinkModel) {

		List<DDMFieldModel> ddmFieldModels = new ArrayList<>(
			BenchmarksPropsValues.MAX_DDL_CUSTOM_FIELD_COUNT + 1);

		DDMFieldModel ddmFieldModel = new DDMFieldModelImpl();

		ddmFieldModel.setFieldId(_counter.get());
		ddmFieldModel.setCompanyId(_companyId);
		ddmFieldModel.setParentFieldId(0);
		ddmFieldModel.setStorageId(ddmStorageLinkModel.getClassPK());

		long structureVersionId = ddmStorageLinkModel.getStructureVersionId();

		ddmFieldModel.setStructureVersionId(structureVersionId);

		ddmFieldModel.setFieldName(StringPool.BLANK);
		ddmFieldModel.setFieldType(StringPool.BLANK);
		ddmFieldModel.setInstanceId(StringPool.BLANK);
		ddmFieldModel.setLocalizable(false);
		ddmFieldModel.setPriority(0);

		ddmFieldModels.add(ddmFieldModel);

		for (int i = 1; i <= BenchmarksPropsValues.MAX_DDL_CUSTOM_FIELD_COUNT;
			 i++) {

			ddmFieldModel = new DDMFieldModelImpl();

			ddmFieldModel.setFieldId(_counter.get());
			ddmFieldModel.setCompanyId(_companyId);
			ddmFieldModel.setParentFieldId(0);
			ddmFieldModel.setStorageId(ddmStorageLinkModel.getClassPK());
			ddmFieldModel.setStructureVersionId(structureVersionId);
			ddmFieldModel.setFieldName(
				nextDDLCustomFieldName(ddlRecordModel.getGroupId(), i - 1));
			ddmFieldModel.setFieldType("string");
			ddmFieldModel.setInstanceId(StringUtil.randomString());
			ddmFieldModel.setLocalizable(true);
			ddmFieldModel.setPriority(i);

			ddmFieldModels.add(ddmFieldModel);
		}

		return ddmFieldModels;
	}

	public DDMStorageLinkModel newDDMStorageLinkModel(
		DDLRecordModel ddlRecordModel, long ddmStorageLinkId, long structureId,
		long structureVersionId) {

		return newDDMStorageLinkModel(
			ddmStorageLinkId, ddlRecordModel.getDDMStorageId(), structureId,
			structureVersionId);
	}

	public DDMStorageLinkModel newDDMStorageLinkModel(
		DLFileEntryModel dlFileEntryModel, long ddmStorageLinkId,
		long structureId) {

		return newDDMStorageLinkModel(
			ddmStorageLinkId, _counter.get(), structureId,
			_defaultDLDDMStructureVersionId);
	}

	public DDMStorageLinkModel newDDMStorageLinkModel(
		JournalArticleModel journalArticleModel, long structureId) {

		DDMStorageLinkModel ddmStorageLinkModel = new DDMStorageLinkModelImpl();

		// UUID

		ddmStorageLinkModel.setUuid(SequentialUUID.generate());

		// PK fields

		ddmStorageLinkModel.setStorageLinkId(_counter.get());

		// Other fields

		ddmStorageLinkModel.setClassNameId(
			getClassNameId(JournalArticle.class));
		ddmStorageLinkModel.setClassPK(journalArticleModel.getId());
		ddmStorageLinkModel.setStructureId(structureId);
		ddmStorageLinkModel.setStructureVersionId(
			_defaultJournalDDMStructureVersionId);

		return ddmStorageLinkModel;
	}

	public DDMStructureLinkModel newDDMStructureLinkModel(
		DDLRecordSetModel ddlRecordSetModel) {

		return newDDMStructureLinkModel(
			getClassNameId(DDLRecordSet.class),
			ddlRecordSetModel.getRecordSetId(),
			ddlRecordSetModel.getDDMStructureId());
	}

	public DDMStructureLinkModel newDDMStructureLinkModel(
		DLFileEntryMetadataModel dLFileEntryMetadataModel) {

		return newDDMStructureLinkModel(
			getClassNameId(DLFileEntryMetadata.class),
			dLFileEntryMetadataModel.getFileEntryMetadataId(),
			dLFileEntryMetadataModel.getDDMStructureId());
	}

	public DDMStructureVersionModel newDDMStructureVersionModel(
		DDMStructureModel ddmStructureModel) {

		return newDDMStructureVersionModel(ddmStructureModel, _counter.get());
	}

	public DDMStructureVersionModel newDDMStructureVersionModel(
		DDMStructureModel ddmStructureModel, long structureVersionId) {

		DDMStructureVersionModel ddmStructureVersionModel =
			new DDMStructureVersionModelImpl();

		// PK fields

		ddmStructureVersionModel.setStructureVersionId(structureVersionId);

		// Group instance

		ddmStructureVersionModel.setGroupId(ddmStructureModel.getGroupId());

		// Audit fields

		ddmStructureVersionModel.setCompanyId(_companyId);
		ddmStructureVersionModel.setUserId(ddmStructureModel.getUserId());
		ddmStructureVersionModel.setUserName(_SAMPLE_USER_NAME);
		ddmStructureVersionModel.setCreateDate(nextFutureDate());

		// Other fields

		ddmStructureVersionModel.setStructureId(
			ddmStructureModel.getStructureId());
		ddmStructureVersionModel.setVersion(
			DDMStructureConstants.VERSION_DEFAULT);

		StringBundler sb = new StringBundler(4);

		sb.append("<?xml version=\"1.0\"?><root available-locales=\"en_US\" ");
		sb.append("default-locale=\"en_US\"><name language-id=\"en_US\">");
		sb.append(ddmStructureModel.getStructureKey());
		sb.append("</name></root>");

		ddmStructureVersionModel.setName(sb.toString());

		ddmStructureVersionModel.setDefinition(
			ddmStructureModel.getDefinition());
		ddmStructureVersionModel.setStorageType(StorageType.DEFAULT.toString());
		ddmStructureVersionModel.setStatusByUserId(
			ddmStructureModel.getUserId());
		ddmStructureVersionModel.setStatusByUserName(_SAMPLE_USER_NAME);
		ddmStructureVersionModel.setStatusDate(nextFutureDate());

		return ddmStructureVersionModel;
	}

	public DDMTemplateLinkModel newDDMTemplateLinkModel(
		JournalArticleModel journalArticleModel, long templateId) {

		DDMTemplateLinkModel ddmTemplateLinkModel =
			new DDMTemplateLinkModelImpl();

		// PK fields

		ddmTemplateLinkModel.setTemplateLinkId(_counter.get());

		// Audit fields

		ddmTemplateLinkModel.setCompanyId(_companyId);

		// Other fields

		ddmTemplateLinkModel.setClassNameId(
			getClassNameId(JournalArticle.class));
		ddmTemplateLinkModel.setClassPK(journalArticleModel.getId());
		ddmTemplateLinkModel.setTemplateId(templateId);

		return ddmTemplateLinkModel;
	}

	public DDMStructureLayoutModel newDefaultDLDDMStructureLayoutModel() {
		return newDDMStructureLayoutModel(
			_globalGroupId, _defaultUserId, _defaultDLDDMStructureVersionId,
			_dlDDMStructureLayoutContent);
	}

	public DDMStructureModel newDefaultDLDDMStructureModel() {
		return newDDMStructureModel(
			_globalGroupId, _defaultUserId, getClassNameId(DLFileEntry.class),
			RawMetadataProcessor.TIKA_RAW_METADATA, _dlDDMStructureContent,
			_defaultDLDDMStructureId);
	}

	public DDMStructureVersionModel newDefaultDLDDMStructureVersionModel(
		DDMStructureModel ddmStructureModel) {

		return newDDMStructureVersionModel(
			ddmStructureModel, _defaultDLDDMStructureVersionId);
	}

	public DDMStructureLayoutModel newDefaultJournalDDMStructureLayoutModel() {
		return newDDMStructureLayoutModel(
			_globalGroupId, _defaultUserId,
			_defaultJournalDDMStructureVersionId,
			_journalDDMStructureLayoutContent);
	}

	public DDMStructureModel newDefaultJournalDDMStructureModel() {
		return newDDMStructureModel(
			_globalGroupId, _defaultUserId,
			getClassNameId(JournalArticle.class), _JOURNAL_STRUCTURE_KEY,
			_journalDDMStructureContent, _defaultJournalDDMStructureId);
	}

	public DDMStructureVersionModel newDefaultJournalDDMStructureVersionModel(
		DDMStructureModel ddmStructureModel) {

		return newDDMStructureVersionModel(
			ddmStructureModel, _defaultJournalDDMStructureVersionId);
	}

	public DDMTemplateModel newDefaultJournalDDMTemplateModel() {
		return newDDMTemplateModel(
			_globalGroupId, _defaultUserId, _defaultJournalDDMStructureId,
			getClassNameId(JournalArticle.class), _defaultJournalDDMTemplateId);
	}

	public DDMTemplateVersionModel newDefaultJournalDDMTemplateVersionModel() {
		DDMTemplateVersionModelImpl ddmTemplateVersionModelImpl =
			new DDMTemplateVersionModelImpl();

		// PK fields

		ddmTemplateVersionModelImpl.setTemplateVersionId(_counter.get());

		// Group instance

		ddmTemplateVersionModelImpl.setGroupId(_globalGroupId);

		// Audit fields

		ddmTemplateVersionModelImpl.setCompanyId(_companyId);
		ddmTemplateVersionModelImpl.setUserId(_defaultUserId);
		ddmTemplateVersionModelImpl.setCreateDate(nextFutureDate());

		// Other fields

		ddmTemplateVersionModelImpl.setClassNameId(
			getClassNameId(DDMStructure.class));
		ddmTemplateVersionModelImpl.setClassPK(_defaultJournalDDMStructureId);
		ddmTemplateVersionModelImpl.setTemplateId(_defaultJournalDDMTemplateId);
		ddmTemplateVersionModelImpl.setVersion(
			DDMTemplateConstants.VERSION_DEFAULT);

		StringBundler sb = new StringBundler(4);

		sb.append("<?xml version=\"1.0\"?><root available-locales=\"en_US\" ");
		sb.append("default-locale=\"en_US\"><name language-id=\"en_US\">");
		sb.append(_JOURNAL_STRUCTURE_KEY);
		sb.append("</name></root>");

		ddmTemplateVersionModelImpl.setName(sb.toString());

		ddmTemplateVersionModelImpl.setStatusByUserId(_defaultUserId);
		ddmTemplateVersionModelImpl.setStatusDate(nextFutureDate());

		return ddmTemplateVersionModelImpl;
	}

	public UserModel newDefaultUserModel() {
		return newUserModel(
			_defaultUserId, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, true);
	}

	public DLFileEntryMetadataModel newDLFileEntryMetadataModel(
		long ddmStorageLinkId, long ddmStructureId,
		DLFileVersionModel dlFileVersionModel) {

		DLFileEntryMetadataModel dlFileEntryMetadataModel =
			new DLFileEntryMetadataModelImpl();

		// UUID

		dlFileEntryMetadataModel.setUuid(SequentialUUID.generate());

		// PK fields

		dlFileEntryMetadataModel.setFileEntryMetadataId(_counter.get());

		// Other fields

		dlFileEntryMetadataModel.setDDMStorageId(ddmStorageLinkId);
		dlFileEntryMetadataModel.setDDMStructureId(ddmStructureId);
		dlFileEntryMetadataModel.setFileEntryId(
			dlFileVersionModel.getFileEntryId());
		dlFileEntryMetadataModel.setFileVersionId(
			dlFileVersionModel.getFileVersionId());

		return dlFileEntryMetadataModel;
	}

	public List<DLFileEntryModel> newDlFileEntryModels(
		DLFolderModel dlFolderModel) {

		List<DLFileEntryModel> dlFileEntryModels = new ArrayList<>(
			BenchmarksPropsValues.MAX_DL_FILE_ENTRY_COUNT);

		for (int i = 1; i <= BenchmarksPropsValues.MAX_DL_FILE_ENTRY_COUNT;
			 i++) {

			dlFileEntryModels.add(newDlFileEntryModel(dlFolderModel, i));
		}

		return dlFileEntryModels;
	}

	public DLFileEntryTypeModel newDLFileEntryTypeModel() {
		DLFileEntryTypeModel defaultDLFileEntryTypeModel =
			new DLFileEntryTypeModelImpl();

		// UUID

		defaultDLFileEntryTypeModel.setUuid(SequentialUUID.generate());

		// PK fields

		defaultDLFileEntryTypeModel.setFileEntryTypeId(
			_defaultDLFileEntryTypeId);

		// Audit fields

		defaultDLFileEntryTypeModel.setCreateDate(nextFutureDate());
		defaultDLFileEntryTypeModel.setModifiedDate(nextFutureDate());

		// Other fields

		defaultDLFileEntryTypeModel.setFileEntryTypeKey(
			StringUtil.toUpperCase(
				DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT));

		StringBundler sb = new StringBundler(4);

		sb.append("<?xml version=\"1.0\"?><root available-locales=\"en_US\" ");
		sb.append("default-locale=\"en_US\"><name language-id=\"en_US\">");
		sb.append(DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT);
		sb.append("</name></root>");

		defaultDLFileEntryTypeModel.setName(sb.toString());

		defaultDLFileEntryTypeModel.setLastPublishDate(nextFutureDate());

		return defaultDLFileEntryTypeModel;
	}

	public DLFileVersionModel newDLFileVersionModel(
		DLFileEntryModel dlFileEntryModel) {

		DLFileVersionModel dlFileVersionModel = new DLFileVersionModelImpl();

		// UUID

		dlFileVersionModel.setUuid(SequentialUUID.generate());

		// PK fields

		dlFileVersionModel.setFileVersionId(_counter.get());

		// Group instance

		dlFileVersionModel.setGroupId(dlFileEntryModel.getGroupId());

		// Audit fields

		dlFileVersionModel.setCompanyId(_companyId);
		dlFileVersionModel.setUserId(_sampleUserId);
		dlFileVersionModel.setUserName(_SAMPLE_USER_NAME);
		dlFileVersionModel.setCreateDate(nextFutureDate());
		dlFileVersionModel.setModifiedDate(nextFutureDate());

		// Other fields

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
		dlFileVersionModel.setLastPublishDate(nextFutureDate());

		return dlFileVersionModel;
	}

	public List<DLFolderModel> newDLFolderModels(
		long groupId, long parentFolderId) {

		List<DLFolderModel> dlFolderModels = new ArrayList<>(
			BenchmarksPropsValues.MAX_DL_FOLDER_COUNT);

		for (int i = 1; i <= BenchmarksPropsValues.MAX_DL_FOLDER_COUNT; i++) {
			dlFolderModels.add(newDLFolderModel(groupId, parentFolderId, i));
		}

		return dlFolderModels;
	}

	public FragmentCollectionModel newFragmentCollectionModel(long groupId) {
		FragmentCollectionModel fragmentCollectionModel =
			new FragmentCollectionModelImpl();

		// UUID

		fragmentCollectionModel.setUuid(SequentialUUID.generate());

		// PK fields

		fragmentCollectionModel.setFragmentCollectionId(_counter.get());

		// Group instance

		fragmentCollectionModel.setGroupId(groupId);

		// Audit fields

		fragmentCollectionModel.setCompanyId(_companyId);
		fragmentCollectionModel.setUserId(_sampleUserId);
		fragmentCollectionModel.setCreateDate(new Date());
		fragmentCollectionModel.setModifiedDate(new Date());

		// Other fields

		fragmentCollectionModel.setFragmentCollectionKey("fragmentcollection");
		fragmentCollectionModel.setName("fragmentcollection");

		return fragmentCollectionModel;
	}

	public FragmentEntryLinkModel newFragmentEntryLinkModel(
		LayoutModel layoutModel, FragmentEntryModel fragmentEntryModel) {

		FragmentEntryLinkModel fragmentEntryLinkModel =
			new FragmentEntryLinkModelImpl();

		// UUID

		fragmentEntryLinkModel.setUuid(SequentialUUID.generate());

		// PK fields

		fragmentEntryLinkModel.setFragmentEntryLinkId(_counter.get());

		// Group instance

		fragmentEntryLinkModel.setGroupId(fragmentEntryModel.getGroupId());

		// Audit fields

		fragmentEntryLinkModel.setCompanyId(_companyId);
		fragmentEntryLinkModel.setUserId(_sampleUserId);
		fragmentEntryLinkModel.setUserName(_SAMPLE_USER_NAME);
		fragmentEntryLinkModel.setCreateDate(new Date());
		fragmentEntryLinkModel.setModifiedDate(new Date());

		// Other fields

		fragmentEntryLinkModel.setFragmentEntryId(
			fragmentEntryModel.getFragmentEntryId());
		fragmentEntryLinkModel.setClassNameId(getClassNameId(Layout.class));
		fragmentEntryLinkModel.setClassPK(layoutModel.getPlid());
		fragmentEntryLinkModel.setCss(fragmentEntryModel.getCss());
		fragmentEntryLinkModel.setHtml(fragmentEntryModel.getHtml());
		fragmentEntryLinkModel.setJs(fragmentEntryModel.getJs());
		fragmentEntryLinkModel.setEditableValues(StringPool.BLANK);
		fragmentEntryLinkModel.setNamespace(StringUtil.randomId());
		fragmentEntryLinkModel.setPosition(0);

		return fragmentEntryLinkModel;
	}

	public FragmentEntryModel newFragmentEntryModel(
			long groupId, FragmentCollectionModel fragmentCollectionModel)
		throws Exception {

		FragmentEntryModel fragmentEntryModel = new FragmentEntryModelImpl();

		// UUID

		fragmentEntryModel.setUuid(SequentialUUID.generate());

		// PK fields

		fragmentEntryModel.setFragmentEntryId(_counter.get());

		// Group instance

		fragmentEntryModel.setGroupId(groupId);

		// Audit fields

		fragmentEntryModel.setCompanyId(_companyId);
		fragmentEntryModel.setUserId(_sampleUserId);
		fragmentEntryModel.setUserName(_SAMPLE_USER_NAME);
		fragmentEntryModel.setCreateDate(new Date());
		fragmentEntryModel.setModifiedDate(new Date());

		// Other fields

		fragmentEntryModel.setFragmentCollectionId(
			fragmentCollectionModel.getFragmentCollectionId());
		fragmentEntryModel.setFragmentEntryKey("web_content");
		fragmentEntryModel.setName("web_content");
		fragmentEntryModel.setCss(StringPool.BLANK);
		fragmentEntryModel.setHtml(_readFile("web_content.html"));
		fragmentEntryModel.setJs(StringPool.BLANK);
		fragmentEntryModel.setType(FragmentConstants.TYPE_COMPONENT);
		fragmentEntryModel.setStatus(WorkflowConstants.STATUS_APPROVED);

		// Autogenerated fields

		fragmentEntryModel.setHeadId(_counter.get());

		return fragmentEntryModel;
	}

	public FriendlyURLEntryLocalizationModel
		newFriendlyURLEntryLocalizationModel(
			FriendlyURLEntryModel friendlyURLEntryModel,
			BlogsEntryModel blogsEntryModel) {

		FriendlyURLEntryLocalizationModel friendlyURLEntryLocalizationModel =
			new FriendlyURLEntryLocalizationModelImpl();

		// PK fields

		friendlyURLEntryLocalizationModel.setFriendlyURLEntryLocalizationId(
			_counter.get());

		// Group instance

		friendlyURLEntryLocalizationModel.setGroupId(
			friendlyURLEntryModel.getGroupId());

		// Audit fields

		friendlyURLEntryLocalizationModel.setCompanyId(
			friendlyURLEntryModel.getCompanyId());

		// Other fields

		friendlyURLEntryLocalizationModel.setClassNameId(
			friendlyURLEntryModel.getClassNameId());
		friendlyURLEntryLocalizationModel.setClassPK(
			friendlyURLEntryModel.getClassPK());
		friendlyURLEntryLocalizationModel.setUrlTitle(
			blogsEntryModel.getUrlTitle());

		// Autogenerated fields

		friendlyURLEntryLocalizationModel.setFriendlyURLEntryId(
			friendlyURLEntryModel.getFriendlyURLEntryId());
		friendlyURLEntryLocalizationModel.setLanguageId(
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));

		return friendlyURLEntryLocalizationModel;
	}

	public FriendlyURLEntryMappingModel newFriendlyURLEntryMapping(
		FriendlyURLEntryModel friendlyURLEntryModel) {

		FriendlyURLEntryMappingModel friendlyURLEntryMappingModel =
			new FriendlyURLEntryMappingModelImpl();

		// PK fields

		friendlyURLEntryMappingModel.setFriendlyURLEntryMappingId(
			_counter.get());

		//  Other fields

		friendlyURLEntryMappingModel.setClassNameId(
			friendlyURLEntryModel.getClassNameId());
		friendlyURLEntryMappingModel.setClassPK(
			friendlyURLEntryModel.getClassPK());
		friendlyURLEntryMappingModel.setFriendlyURLEntryId(
			friendlyURLEntryModel.getFriendlyURLEntryId());

		return friendlyURLEntryMappingModel;
	}

	public FriendlyURLEntryModel newFriendlyURLEntryModel(
		BlogsEntryModel blogsEntryModel) {

		FriendlyURLEntryModel friendlyURLEntryModel =
			new FriendlyURLEntryModelImpl();

		// UUID

		friendlyURLEntryModel.setUuid(SequentialUUID.generate());

		// PK fields

		friendlyURLEntryModel.setFriendlyURLEntryId(_counter.get());

		// Group instance

		friendlyURLEntryModel.setGroupId(blogsEntryModel.getGroupId());

		// Audit fields

		friendlyURLEntryModel.setCompanyId(_companyId);
		friendlyURLEntryModel.setCreateDate(new Date());
		friendlyURLEntryModel.setModifiedDate(new Date());

		// Other fields

		friendlyURLEntryModel.setClassNameId(getClassNameId(BlogsEntry.class));
		friendlyURLEntryModel.setClassPK(blogsEntryModel.getEntryId());

		// Autogenerated fields

		friendlyURLEntryModel.setDefaultLanguageId("en_US");

		return friendlyURLEntryModel;
	}

	public GroupModel newGlobalGroupModel() {
		return newGroupModel(
			_globalGroupId, getClassNameId(Company.class), _companyId,
			GroupConstants.GLOBAL, false);
	}

	public List<LayoutModel> newGroupLayoutModels(long groupId) {
		List<LayoutModel> layoutModels = new ArrayList<>();

		layoutModels.add(
			newLayoutModel(
				groupId, "welcome", LoginPortletKeys.LOGIN + ",",
				HelloWorldPortletKeys.HELLO_WORLD + ","));
		layoutModels.add(
			newLayoutModel(groupId, "blogs", "", BlogsPortletKeys.BLOGS + ","));
		layoutModels.add(
			newLayoutModel(
				groupId, "commerce_product", "",
				CPPortletKeys.CP_CONTENT_WEB + ","));
		layoutModels.add(
			newLayoutModel(
				groupId, "document_library", "",
				DLPortletKeys.DOCUMENT_LIBRARY + ","));
		layoutModels.add(
			newLayoutModel(
				groupId, "forums", "", MBPortletKeys.MESSAGE_BOARDS + ","));
		layoutModels.add(
			newLayoutModel(groupId, "wiki", "", WikiPortletKeys.WIKI + ","));

		if (BenchmarksPropsValues.SEARCH_BAR_ENABLED) {
			layoutModels.add(
				newLayoutModel(
					groupId, "search", true, "1_2_columns_i",
					new String[] {
						StringBundler.concat(
							SearchBarPortletKeys.SEARCH_BAR, StringPool.COMMA,
							SuggestionsPortletKeys.SUGGESTIONS,
							StringPool.COMMA),
						StringBundler.concat(
							SiteFacetPortletKeys.SITE_FACET, StringPool.COMMA,
							TypeFacetPortletKeys.TYPE_FACET, StringPool.COMMA,
							TagFacetPortletKeys.TAG_FACET, StringPool.COMMA,
							CategoryFacetPortletKeys.CATEGORY_FACET,
							StringPool.COMMA,
							FolderFacetPortletKeys.FOLDER_FACET,
							StringPool.COMMA, UserFacetPortletKeys.USER_FACET,
							StringPool.COMMA,
							ModifiedFacetPortletKeys.MODIFIED_FACET,
							StringPool.COMMA),
						StringBundler.concat(
							SearchResultsPortletKeys.SEARCH_RESULTS,
							StringPool.COMMA,
							SearchOptionsPortletKeys.SEARCH_OPTIONS,
							StringPool.COMMA)
					}));
		}

		return layoutModels;
	}

	public GroupModel newGroupModel(UserModel userModel) {
		return newGroupModel(
			_counter.get(), getClassNameId(User.class), userModel.getUserId(),
			userModel.getScreenName(), false);
	}

	public List<GroupModel> newGroupModels() {
		List<GroupModel> groupModels = new ArrayList<>(
			BenchmarksPropsValues.MAX_GROUP_COUNT);

		for (int i = 1; i <= BenchmarksPropsValues.MAX_GROUP_COUNT; i++) {
			groupModels.add(
				newGroupModel(
					i, getClassNameId(Group.class), i, "Site " + i, true));
		}

		return groupModels;
	}

	public GroupModel newGuestGroupModel() {
		return newGroupModel(
			_guestGroupId, getClassNameId(Group.class), _guestGroupId,
			GroupConstants.GUEST, true);
	}

	public UserModel newGuestUserModel() {
		return newUserModel(_counter.get(), "Test", "Test", "Test", false);
	}

	public JournalArticleLocalizationModel newJournalArticleLocalizationModel(
		JournalArticleModel journalArticleModel, int articleIndex,
		int versionIndex) {

		JournalArticleLocalizationModel journalArticleLocalizationModel =
			new JournalArticleLocalizationModelImpl();

		StringBundler sb = new StringBundler(4);

		sb.append("TestJournalArticle_");
		sb.append(articleIndex);
		sb.append(StringPool.UNDERLINE);
		sb.append(versionIndex);

		// PK fields

		journalArticleLocalizationModel.setArticleLocalizationId(
			_counter.get());

		// Audit fields

		journalArticleLocalizationModel.setCompanyId(
			journalArticleModel.getCompanyId());

		// Other fields

		journalArticleLocalizationModel.setArticlePK(
			journalArticleModel.getId());
		journalArticleLocalizationModel.setTitle(sb.toString());
		journalArticleLocalizationModel.setLanguageId(
			journalArticleModel.getDefaultLanguageId());

		return journalArticleLocalizationModel;
	}

	public JournalArticleModel newJournalArticleModel(
			JournalArticleResourceModel journalArticleResourceModel,
			int articleIndex, int versionIndex)
		throws PortalException {

		JournalArticleModel journalArticleModel = new JournalArticleModelImpl();

		// UUID

		journalArticleModel.setUuid(SequentialUUID.generate());

		// PK fields

		journalArticleModel.setId(_counter.get());

		// Resource

		journalArticleModel.setResourcePrimKey(
			journalArticleResourceModel.getResourcePrimKey());

		// Group instance

		journalArticleModel.setGroupId(
			journalArticleResourceModel.getGroupId());

		// Audit fields

		journalArticleModel.setCompanyId(_companyId);
		journalArticleModel.setUserId(_sampleUserId);
		journalArticleModel.setUserName(_SAMPLE_USER_NAME);
		journalArticleModel.setCreateDate(new Date());
		journalArticleModel.setModifiedDate(new Date());

		// Other fields

		journalArticleModel.setClassNameId(
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT);
		journalArticleModel.setTreePath("/");
		journalArticleModel.setArticleId(
			journalArticleResourceModel.getArticleId());
		journalArticleModel.setVersion(versionIndex);

		StringBundler sb = new StringBundler(4);

		sb.append("TestJournalArticle_");
		sb.append(articleIndex);
		sb.append(StringPool.UNDERLINE);
		sb.append(versionIndex);

		journalArticleModel.setUrlTitle(sb.toString());

		journalArticleModel.setContent(_journalArticleContent);
		journalArticleModel.setDDMStructureKey(_JOURNAL_STRUCTURE_KEY);
		journalArticleModel.setDDMTemplateKey(_JOURNAL_STRUCTURE_KEY);
		journalArticleModel.setDefaultLanguageId("en_US");
		journalArticleModel.setDisplayDate(new Date());
		journalArticleModel.setExpirationDate(nextFutureDate());
		journalArticleModel.setReviewDate(new Date());
		journalArticleModel.setIndexable(true);
		journalArticleModel.setLastPublishDate(new Date());
		journalArticleModel.setStatusDate(new Date());

		if (Validator.isNull(_defaultJournalArticleId)) {
			_defaultJournalArticleId = journalArticleModel.getArticleId();
		}

		return journalArticleModel;
	}

	public JournalArticleResourceModel newJournalArticleResourceModel(
		long groupId) {

		JournalArticleResourceModel journalArticleResourceModel =
			new JournalArticleResourceModelImpl();

		// UUID

		journalArticleResourceModel.setUuid(SequentialUUID.generate());

		// PK fields

		journalArticleResourceModel.setResourcePrimKey(_counter.get());

		// Group instance

		journalArticleResourceModel.setGroupId(groupId);

		// Audit fields

		journalArticleResourceModel.setCompanyId(_companyId);

		// Other fields

		journalArticleResourceModel.setArticleId(
			String.valueOf(_counter.get()));

		_journalArticleResourceUUIDs.put(
			journalArticleResourceModel.getPrimaryKey(),
			journalArticleResourceModel.getUuid());

		return journalArticleResourceModel;
	}

	public List<PortletPreferenceValueModel>
		newJournalArticleResourcePortletPreferenceValueModels(
			PortletPreferencesModel portletPreferencesModel,
			JournalArticleResourceModel journalArticleResourceModel) {

		return Arrays.asList(
			newPortletPreferenceValueModel(
				portletPreferencesModel, "articleId", 0,
				journalArticleResourceModel.getArticleId()),
			newPortletPreferenceValueModel(
				portletPreferencesModel, "groupId", 0,
				String.valueOf(journalArticleResourceModel.getGroupId())));
	}

	public PortletPreferencesModel newJournalContentPortletPreferencesModel(
		FragmentEntryLinkModel fragmentEntryLinkModel) {

		PortletPreferencesModel portletPreferencesModel =
			new PortletPreferencesModelImpl();

		// PK fields

		portletPreferencesModel.setPortletPreferencesId(_counter.get());

		// Other fields

		portletPreferencesModel.setOwnerId(PortletKeys.PREFS_OWNER_ID_DEFAULT);
		portletPreferencesModel.setOwnerType(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT);
		portletPreferencesModel.setPlid(fragmentEntryLinkModel.getClassPK());
		portletPreferencesModel.setPortletId(
			PortletIdCodec.encode(
				JournalContentPortletKeys.JOURNAL_CONTENT,
				fragmentEntryLinkModel.getNamespace()));

		return portletPreferencesModel;
	}

	public PortletPreferenceValueModel
		newJournalContentPortletPreferenceValueModel(
			PortletPreferencesModel portletPreferencesModel) {

		return newPortletPreferenceValueModel(
			portletPreferencesModel, "articleId", 0, _defaultJournalArticleId);
	}

	public JournalContentSearchModel newJournalContentSearchModel(
		JournalArticleModel journalArticleModel, long layoutId) {

		JournalContentSearchModel journalContentSearchModel =
			new JournalContentSearchModelImpl();

		// PK fields

		journalContentSearchModel.setContentSearchId(_counter.get());

		// Group instance

		journalContentSearchModel.setGroupId(journalArticleModel.getGroupId());

		// Audit fields

		journalContentSearchModel.setCompanyId(_companyId);

		// Other fields

		journalContentSearchModel.setLayoutId(layoutId);
		journalContentSearchModel.setPortletId(
			JournalContentPortletKeys.JOURNAL_CONTENT);
		journalContentSearchModel.setArticleId(
			journalArticleModel.getArticleId());

		return journalContentSearchModel;
	}

	public List<PortletPreferencesModel> newJournalPortletPreferencesModels(
		long plid) {

		return Collections.singletonList(
			newPortletPreferencesModel(plid, JournalPortletKeys.JOURNAL));
	}

	public LayoutFriendlyURLModel newLayoutFriendlyURLModel(
		LayoutModel layoutModel) {

		LayoutFriendlyURLModel layoutFriendlyURLEntryModel =
			new LayoutFriendlyURLModelImpl();

		// UUID

		layoutFriendlyURLEntryModel.setUuid(SequentialUUID.generate());

		// PK fields

		layoutFriendlyURLEntryModel.setLayoutFriendlyURLId(_counter.get());

		// Group instance

		layoutFriendlyURLEntryModel.setGroupId(layoutModel.getGroupId());

		// Audit fields

		layoutFriendlyURLEntryModel.setCompanyId(_companyId);
		layoutFriendlyURLEntryModel.setUserId(_sampleUserId);
		layoutFriendlyURLEntryModel.setUserName(_SAMPLE_USER_NAME);
		layoutFriendlyURLEntryModel.setCreateDate(new Date());
		layoutFriendlyURLEntryModel.setModifiedDate(new Date());

		// Other fields

		layoutFriendlyURLEntryModel.setPlid(layoutModel.getPlid());
		layoutFriendlyURLEntryModel.setFriendlyURL(
			layoutModel.getFriendlyURL());
		layoutFriendlyURLEntryModel.setLanguageId("en_US");
		layoutFriendlyURLEntryModel.setLastPublishDate(new Date());

		return layoutFriendlyURLEntryModel;
	}

	public LayoutModel newLayoutModel(
		long groupId, String name, String column1, String column2) {

		return newLayoutModel(
			groupId, name, false, "2_columns_ii", column1, column2);
	}

	public LayoutPageTemplateStructureModel newLayoutPageTemplateStructureModel(
		LayoutModel layoutModel) {

		LayoutPageTemplateStructureModel layoutPageTemplateStructureModel =
			new LayoutPageTemplateStructureModelImpl();

		// UUID

		layoutPageTemplateStructureModel.setUuid(SequentialUUID.generate());

		// PK fields

		layoutPageTemplateStructureModel.setLayoutPageTemplateStructureId(
			_counter.get());

		// Group instance

		layoutPageTemplateStructureModel.setGroupId(layoutModel.getGroupId());

		// Audit fields

		layoutPageTemplateStructureModel.setCompanyId(_companyId);
		layoutPageTemplateStructureModel.setUserId(_sampleUserId);
		layoutPageTemplateStructureModel.setUserName(_SAMPLE_USER_NAME);
		layoutPageTemplateStructureModel.setCreateDate(new Date());
		layoutPageTemplateStructureModel.setModifiedDate(new Date());

		// Other fields

		layoutPageTemplateStructureModel.setClassNameId(
			getClassNameId(Layout.class));
		layoutPageTemplateStructureModel.setClassPK(layoutModel.getPlid());

		return layoutPageTemplateStructureModel;
	}

	public LayoutPageTemplateStructureRelModel
			newLayoutPageTemplateStructureRelModel(
				LayoutModel layoutModel,
				LayoutPageTemplateStructureModel
					layoutPageTemplateStructureModel,
				FragmentEntryLinkModel fragmentEntryLinkModel)
		throws Exception {

		LayoutPageTemplateStructureRelModel
			layoutPageTemplateStructureRelModel =
				new LayoutPageTemplateStructureRelModelImpl();

		// UUID

		layoutPageTemplateStructureRelModel.setUuid(SequentialUUID.generate());

		// PK fields

		layoutPageTemplateStructureRelModel.setLayoutPageTemplateStructureRelId(
			_counter.get());

		// Group instance

		layoutPageTemplateStructureRelModel.setGroupId(
			layoutPageTemplateStructureModel.getGroupId());

		// Audit fields

		layoutPageTemplateStructureRelModel.setCompanyId(_companyId);
		layoutPageTemplateStructureRelModel.setUserId(_sampleUserId);
		layoutPageTemplateStructureRelModel.setUserName(_SAMPLE_USER_NAME);
		layoutPageTemplateStructureRelModel.setCreateDate(new Date());
		layoutPageTemplateStructureRelModel.setModifiedDate(new Date());

		// Other fields

		layoutPageTemplateStructureRelModel.setLayoutPageTemplateStructureId(
			layoutPageTemplateStructureModel.
				getLayoutPageTemplateStructureId());
		layoutPageTemplateStructureRelModel.setSegmentsExperienceId(0L);
		layoutPageTemplateStructureRelModel.setData(
			StringUtil.replace(
				_layoutPageTemplateStructureRelData, "${fragmentEntryLinkId}",
				String.valueOf(
					fragmentEntryLinkModel.getFragmentEntryLinkId())));

		return layoutPageTemplateStructureRelModel;
	}

	public List<LayoutSetModel> newLayoutSetModels(long groupId) {
		List<LayoutSetModel> layoutSetModels = new ArrayList<>(2);

		layoutSetModels.add(newLayoutSetModel(groupId, true));
		layoutSetModels.add(newLayoutSetModel(groupId, false));

		return layoutSetModels;
	}

	public List<MBCategoryModel> newMBCategoryModels(long groupId) {
		List<MBCategoryModel> mbCategoryModels = new ArrayList<>(
			BenchmarksPropsValues.MAX_MB_CATEGORY_COUNT);

		for (int i = 1; i <= BenchmarksPropsValues.MAX_MB_CATEGORY_COUNT; i++) {
			mbCategoryModels.add(newMBCategoryModel(groupId, i));
		}

		return mbCategoryModels;
	}

	public AssetEntryModel newMBDiscussionAssetEntryModel(
		BlogsEntryModel blogsEntryModel) {

		ClassNameModel classNameModel = _classNameModels.get(
			_getMBDiscussionCombinedClassName(BlogsEntry.class));

		return newAssetEntryModel(
			blogsEntryModel.getGroupId(), blogsEntryModel.getCreateDate(),
			blogsEntryModel.getModifiedDate(), classNameModel.getClassNameId(),
			blogsEntryModel.getEntryId(), "", 0, true, false, "",
			String.valueOf(blogsEntryModel.getGroupId()));
	}

	public AssetEntryModel newMBDiscussionAssetEntryModel(
		WikiPageModel wikiPageModel) {

		ClassNameModel classNameModel = _classNameModels.get(
			_getMBDiscussionCombinedClassName(WikiPage.class));

		return newAssetEntryModel(
			wikiPageModel.getGroupId(), wikiPageModel.getCreateDate(),
			wikiPageModel.getModifiedDate(), classNameModel.getClassNameId(),
			wikiPageModel.getResourcePrimKey(), "", 0, true, false, "",
			String.valueOf(wikiPageModel.getGroupId()));
	}

	public MBDiscussionModel newMBDiscussionModel(
		long groupId, long classNameId, long classPK, long threadId) {

		MBDiscussionModel mbDiscussionModel = new MBDiscussionModelImpl();

		// UUID

		mbDiscussionModel.setUuid(SequentialUUID.generate());

		// PK fields

		mbDiscussionModel.setDiscussionId(_counter.get());

		// Group instance

		mbDiscussionModel.setGroupId(groupId);

		// Audit fields

		mbDiscussionModel.setCompanyId(_companyId);
		mbDiscussionModel.setUserId(_sampleUserId);
		mbDiscussionModel.setUserName(_SAMPLE_USER_NAME);
		mbDiscussionModel.setCreateDate(new Date());
		mbDiscussionModel.setModifiedDate(new Date());

		// Other fields

		mbDiscussionModel.setClassNameId(classNameId);
		mbDiscussionModel.setClassPK(classPK);
		mbDiscussionModel.setThreadId(threadId);
		mbDiscussionModel.setLastPublishDate(new Date());

		return mbDiscussionModel;
	}

	public MBMailingListModel newMBMailingListModel(
		MBCategoryModel mbCategoryModel, UserModel sampleUserModel) {

		MBMailingListModel mbMailingListModel = new MBMailingListModelImpl();

		// UUID

		mbMailingListModel.setUuid(SequentialUUID.generate());

		// PK fields

		mbMailingListModel.setMailingListId(_counter.get());

		// Group instance

		mbMailingListModel.setGroupId(mbCategoryModel.getGroupId());

		// Audit fields

		mbMailingListModel.setCompanyId(_companyId);
		mbMailingListModel.setUserId(_sampleUserId);
		mbMailingListModel.setUserName(_SAMPLE_USER_NAME);
		mbMailingListModel.setCreateDate(new Date());
		mbMailingListModel.setModifiedDate(new Date());

		// Other fields

		mbMailingListModel.setCategoryId(mbCategoryModel.getCategoryId());
		mbMailingListModel.setInProtocol("pop3");
		mbMailingListModel.setInServerPort(110);
		mbMailingListModel.setInUserName(sampleUserModel.getEmailAddress());
		mbMailingListModel.setInPassword(sampleUserModel.getPassword());
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
		String urlSubject = null;

		if (index == 0) {
			messageId = mbThreadModel.getRootMessageId();
			parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;
			subject = String.valueOf(classPK);
			body = String.valueOf(classPK);
			urlSubject = String.valueOf(mbThreadModel.getRootMessageId());
		}
		else {
			messageId = _counter.get();
			parentMessageId = mbThreadModel.getRootMessageId();
			subject = "N/A";
			body = "This is test comment " + index + ".";
			urlSubject = "test-comment-" + index;
		}

		return newMBMessageModel(
			mbThreadModel.getGroupId(), classNameId, classPK,
			MBCategoryConstants.DISCUSSION_CATEGORY_ID,
			mbThreadModel.getThreadId(), messageId,
			mbThreadModel.getRootMessageId(), parentMessageId, subject,
			urlSubject, body);
	}

	public List<MBMessageModel> newMBMessageModels(
		MBThreadModel mbThreadModel) {

		List<MBMessageModel> mbMessageModels = new ArrayList<>(
			BenchmarksPropsValues.MAX_MB_MESSAGE_COUNT);

		mbMessageModels.add(
			newMBMessageModel(
				mbThreadModel.getGroupId(), 0, 0, mbThreadModel.getCategoryId(),
				mbThreadModel.getThreadId(), mbThreadModel.getRootMessageId(),
				mbThreadModel.getRootMessageId(),
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, "Test Message 1",
				"test-message-1", "This is test message 1."));

		for (int i = 2; i <= BenchmarksPropsValues.MAX_MB_MESSAGE_COUNT; i++) {
			mbMessageModels.add(
				newMBMessageModel(
					mbThreadModel.getGroupId(), 0, 0,
					mbThreadModel.getCategoryId(), mbThreadModel.getThreadId(),
					_counter.get(), mbThreadModel.getRootMessageId(),
					mbThreadModel.getRootMessageId(), "Test Message " + i,
					"test-message-" + i, "This is test message " + i + "."));
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

		// PK fields

		mbStatsUserModel.setStatsUserId(_counter.get());

		// Group instance

		mbStatsUserModel.setGroupId(groupId);

		// Audit fields

		mbStatsUserModel.setUserId(_sampleUserId);

		// Other fields

		mbStatsUserModel.setMessageCount(
			BenchmarksPropsValues.MAX_MB_CATEGORY_COUNT *
				BenchmarksPropsValues.MAX_MB_THREAD_COUNT *
					BenchmarksPropsValues.MAX_MB_MESSAGE_COUNT);
		mbStatsUserModel.setLastPostDate(new Date());

		return mbStatsUserModel;
	}

	public MBThreadFlagModel newMBThreadFlagModel(MBThreadModel mbThreadModel) {
		MBThreadFlagModel mbThreadFlagModel = new MBThreadFlagModelImpl();

		// UUID

		mbThreadFlagModel.setUuid(SequentialUUID.generate());

		// PK fields

		mbThreadFlagModel.setThreadFlagId(_counter.get());

		// Group instance

		mbThreadFlagModel.setGroupId(mbThreadModel.getGroupId());

		// Audit fields

		mbThreadFlagModel.setCompanyId(_companyId);
		mbThreadFlagModel.setUserId(_sampleUserId);
		mbThreadFlagModel.setUserName(_SAMPLE_USER_NAME);
		mbThreadFlagModel.setCreateDate(new Date());
		mbThreadFlagModel.setModifiedDate(new Date());

		// Other fields

		mbThreadFlagModel.setThreadId(mbThreadModel.getThreadId());
		mbThreadFlagModel.setLastPublishDate(new Date());

		return mbThreadFlagModel;
	}

	public MBThreadModel newMBThreadModel(
		long threadId, long groupId, long rootMessageId) {

		return newMBThreadModel(
			threadId, groupId, MBCategoryConstants.DISCUSSION_CATEGORY_ID,
			rootMessageId);
	}

	public List<MBThreadModel> newMBThreadModels(
		MBCategoryModel mbCategoryModel) {

		List<MBThreadModel> mbThreadModels = new ArrayList<>(
			BenchmarksPropsValues.MAX_MB_THREAD_COUNT);

		for (int i = 0; i < BenchmarksPropsValues.MAX_MB_THREAD_COUNT; i++) {
			mbThreadModels.add(
				newMBThreadModel(
					_counter.get(), mbCategoryModel.getGroupId(),
					mbCategoryModel.getCategoryId(), _counter.get()));
		}

		return mbThreadModels;
	}

	public <K, V> ObjectValuePair<K, V> newObjectValuePair(K key, V value) {
		return new ObjectValuePair<>(key, value);
	}

	public PortletPreferencesModel newPortletPreferencesModel(
		long plid, long groupId, String portletId, int currentIndex) {

		if (currentIndex == 1) {
			return newPortletPreferencesModel(plid, portletId);
		}

		String assetPublisherQueryName = "assetCategories";

		if ((currentIndex % 2) == 0) {
			assetPublisherQueryName = "assetTags";
		}

		ObjectValuePair<String[], Integer> objectValuePair = null;

		Integer startIndex = _assetPublisherQueryStartIndexes.get(groupId);

		if (startIndex == null) {
			startIndex = 0;
		}

		if (assetPublisherQueryName.equals("assetCategories")) {
			Map<Long, List<AssetCategoryModel>> assetCategoryModelsMap =
				_assetCategoryModelsMaps[(int)groupId - 1];

			List<AssetCategoryModel> assetCategoryModels =
				assetCategoryModelsMap.get(getNextAssetClassNameId(groupId));

			if ((assetCategoryModels == null) ||
				assetCategoryModels.isEmpty()) {

				return newPortletPreferencesModel(plid, portletId);
			}

			objectValuePair = getAssetPublisherAssetCategoriesQueryValues(
				assetCategoryModels, startIndex);
		}
		else {
			Map<Long, List<AssetTagModel>> assetTagModelsMap =
				_assetTagModelsMaps[(int)groupId - 1];

			List<AssetTagModel> assetTagModels = assetTagModelsMap.get(
				getNextAssetClassNameId(groupId));

			if ((assetTagModels == null) || assetTagModels.isEmpty()) {
				return newPortletPreferencesModel(plid, portletId);
			}

			objectValuePair = getAssetPublisherAssetTagsQueryValues(
				assetTagModels, startIndex);
		}

		_assetPublisherQueryStartIndexes.put(
			groupId, objectValuePair.getValue());

		return newPortletPreferencesModel(plid, portletId);
	}

	public PortletPreferencesModel newPortletPreferencesModel(
		long plid, String portletId) {

		PortletPreferencesModel portletPreferencesModel =
			new PortletPreferencesModelImpl();

		// PK fields

		portletPreferencesModel.setPortletPreferencesId(_counter.get());

		// Audit fields

		portletPreferencesModel.setCompanyId(_companyId);

		// Other fields

		portletPreferencesModel.setOwnerId(PortletKeys.PREFS_OWNER_ID_DEFAULT);
		portletPreferencesModel.setOwnerType(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT);
		portletPreferencesModel.setPlid(plid);
		portletPreferencesModel.setPortletId(portletId);

		return portletPreferencesModel;
	}

	public PortletPreferenceValueModel newPortletPreferenceValueModel(
		PortletPreferencesModel portletPreferencesModel, String name, int index,
		String value) {

		PortletPreferenceValueModel portletPreferenceValueModel =
			new PortletPreferenceValueModelImpl();

		portletPreferenceValueModel.setPortletPreferenceValueId(_counter.get());
		portletPreferenceValueModel.setPortletPreferencesId(
			portletPreferencesModel.getPortletPreferencesId());
		portletPreferenceValueModel.setName(name);
		portletPreferenceValueModel.setIndex(index);

		if (value.length() >
				PortletPreferenceValueImpl.SMALL_VALUE_MAX_LENGTH) {

			portletPreferenceValueModel.setLargeValue(value);
		}
		else {
			portletPreferenceValueModel.setSmallValue(value);
		}

		return portletPreferenceValueModel;
	}

	public List<ReleaseModel> newReleaseModels() throws IOException {
		List<ReleaseModel> releases = new ArrayList<>();

		Version latestSchemaVersion =
			PortalUpgradeProcess.getLatestSchemaVersion();

		releases.add(
			newReleaseModel(
				ReleaseConstants.DEFAULT_ID,
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME,
				latestSchemaVersion.toString(), ReleaseInfo.getBuildNumber(),
				false, ReleaseConstants.TEST_STRING));

		try (InputStream is = DataFactory.class.getResourceAsStream(
				"dependencies/releases.txt");
			Reader reader = new InputStreamReader(is);
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(reader)) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String[] parts = StringUtil.split(line, CharPool.COLON);

				if (parts.length > 0) {
					String servletContextName = parts[0];
					String schemaVersion = parts[1];

					releases.add(
						newReleaseModel(
							_counter.get(), servletContextName, schemaVersion,
							0, true, null));
				}
			}
		}

		return releases;
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		AssetCategoryModel assetCategoryModel) {

		return newResourcePermissionModels(
			AssetCategory.class.getName(),
			String.valueOf(assetCategoryModel.getCategoryId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		AssetVocabularyModel assetVocabularyModel) {

		if (assetVocabularyModel.getUserId() == _defaultUserId) {
			return Collections.singletonList(
				newResourcePermissionModel(
					AssetVocabulary.class.getName(),
					String.valueOf(assetVocabularyModel.getVocabularyId()),
					_ownerRoleModel.getRoleId(), _defaultUserId));
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

		return newResourcePermissionModels(
			DDLRecordSet.class.getName(),
			String.valueOf(ddlRecordSetModel.getRecordSetId()), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		DDMStructureModel ddmStructureModel) {

		String name = _getResourcePermissionModelName(
			DDMStructure.class.getName(),
			getClassName(ddmStructureModel.getClassNameId()));
		String primKey = String.valueOf(ddmStructureModel.getStructureId());

		return newResourcePermissionModels(name, primKey, _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		DDMTemplateModel ddmTemplateModel) {

		String name = _getResourcePermissionModelName(
			DDMTemplate.class.getName(),
			getClassName(ddmTemplateModel.getResourceClassNameId()));
		String primKey = String.valueOf(ddmTemplateModel.getTemplateId());

		return newResourcePermissionModels(name, primKey, _sampleUserId);
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
		GroupModel groupModel) {

		return Collections.singletonList(
			newResourcePermissionModel(
				Group.class.getName(), String.valueOf(groupModel.getGroupId()),
				_ownerRoleModel.getRoleId(), _sampleUserId));
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
			Layout.class.getName(), String.valueOf(layoutModel.getPlid()), 0);
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
		RoleModel roleModel) {

		return Collections.singletonList(
			newResourcePermissionModel(
				Role.class.getName(), String.valueOf(roleModel.getRoleId()),
				_ownerRoleModel.getRoleId(), _sampleUserId));
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		String name, long primKey) {

		return newResourcePermissionModels(
			name, String.valueOf(primKey), _sampleUserId);
	}

	public List<ResourcePermissionModel> newResourcePermissionModels(
		UserModel userModel) {

		return Collections.singletonList(
			newResourcePermissionModel(
				User.class.getName(), String.valueOf(userModel.getUserId()),
				_ownerRoleModel.getRoleId(), userModel.getUserId()));
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

	public UserModel newSampleUserModel() {
		return newUserModel(
			_sampleUserId, _SAMPLE_USER_NAME, _SAMPLE_USER_NAME,
			_SAMPLE_USER_NAME, false);
	}

	public List<SegmentsEntry> newSegmentsEntries(long groupId) {
		List<SegmentsEntry> segmentsEntries = new ArrayList<>(
			BenchmarksPropsValues.MAX_SEGMENTS_ENTRY_COUNT);

		for (int i = 0; i < BenchmarksPropsValues.MAX_SEGMENTS_ENTRY_COUNT;
			 i++) {

			segmentsEntries.add(newSegmentsEntry(groupId, i));
		}

		return segmentsEntries;
	}

	public SegmentsEntry newSegmentsEntry(long groupId, int index) {
		SegmentsEntry segmentsEntry = new SegmentsEntryImpl();

		segmentsEntry.setActive(true);
		segmentsEntry.setCriteria(
			JSONUtil.put(
				"criteria",
				JSONUtil.put(
					"user",
					JSONUtil.put(
						"conjunction", "and"
					).put(
						"filterString",
						"(firstName eq ''" + "Another User" + "'')"
					))
			).toString());
		segmentsEntry.setSegmentsEntryId(_counter.get());
		segmentsEntry.setGroupId(groupId);
		segmentsEntry.setCompanyId(_companyId);
		segmentsEntry.setCreateDate(new Date());
		segmentsEntry.setModifiedDate(new Date());
		segmentsEntry.setSegmentsEntryKey(_counter.getString());
		segmentsEntry.setName("SegmentName-" + index);
		segmentsEntry.setSource(SegmentsEntryConstants.SOURCE_DEFAULT);
		segmentsEntry.setType(User.class.getName());
		segmentsEntry.setUuid(SequentialUUID.generate());
		segmentsEntry.setUserId(_sampleUserId);
		segmentsEntry.setUserName(_SAMPLE_USER_NAME);

		return segmentsEntry;
	}

	public SocialActivityModel newSocialActivityModel(
		BlogsEntryModel blogsEntryModel) {

		return newSocialActivityModel(
			blogsEntryModel.getGroupId(), getClassNameId(BlogsEntry.class),
			blogsEntryModel.getEntryId(), BlogsActivityKeys.ADD_ENTRY,
			"{\"title\":\"" + blogsEntryModel.getTitle() + "\"}");
	}

	public SocialActivityModel newSocialActivityModel(
		DLFileEntryModel dlFileEntryModel) {

		return newSocialActivityModel(
			dlFileEntryModel.getGroupId(), getClassNameId(DLFileEntry.class),
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
			journalArticleModel.getGroupId(),
			getClassNameId(JournalArticle.class),
			journalArticleModel.getResourcePrimKey(), type,
			"{\"title\":\"" + journalArticleModel.getUrlTitle() + "\"}");
	}

	public SocialActivityModel newSocialActivityModel(
		MBMessageModel mbMessageModel) {

		long classNameId = mbMessageModel.getClassNameId();
		long classPK = mbMessageModel.getClassPK();

		int type = 0;
		String extraData = null;

		if (classNameId == getClassNameId(WikiPage.class)) {
			extraData = "{\"version\":1}";

			type = WikiActivityKeys.ADD_PAGE;
		}
		else if (classNameId == 0) {
			extraData = "{\"title\":\"" + mbMessageModel.getSubject() + "\"}";

			type = MBActivityKeys.ADD_MESSAGE;

			classNameId = getClassNameId(MBMessage.class);
			classPK = mbMessageModel.getMessageId();
		}
		else {
			StringBundler sb = new StringBundler(5);

			sb.append("{\"messageId\": \"");
			sb.append(mbMessageModel.getMessageId());
			sb.append("\", \"title\": ");
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
			getClassNameId(BlogsEntry.class), blogsEntryModel.getEntryId());
	}

	public SubscriptionModel newSubscriptionModel(MBThreadModel mBThreadModel) {
		return newSubscriptionModel(
			getClassNameId(MBThread.class), mBThreadModel.getThreadId());
	}

	public SubscriptionModel newSubscriptionModel(WikiPageModel wikiPageModel) {
		return newSubscriptionModel(
			getClassNameId(WikiPage.class), wikiPageModel.getResourcePrimKey());
	}

	public List<UserModel> newUserModels() {
		List<UserModel> userModels = new ArrayList<>(
			BenchmarksPropsValues.MAX_USER_COUNT);

		for (int i = 0; i < BenchmarksPropsValues.MAX_USER_COUNT; i++) {
			String[] userName = nextUserName(i);

			userModels.add(
				newUserModel(
					_counter.get(), userName[0], userName[1],
					"test" + _userScreenNameCounter.get(), false));
		}

		return userModels;
	}

	public GroupModel newUserPersonalSiteGroupModel() {
		return newGroupModel(
			_userPersonalSiteGroupId, getClassNameId(UserPersonalSite.class),
			_defaultUserId, GroupConstants.USER_PERSONAL_SITE, false);
	}

	public VirtualHostModel newVirtualHostModel() {
		VirtualHostModel virtualHostModel = new VirtualHostModelImpl();

		//  PK fields

		virtualHostModel.setVirtualHostId(_counter.get());

		// Audit fields

		virtualHostModel.setCompanyId(_companyId);

		// Other fields

		virtualHostModel.setHostname(BenchmarksPropsValues.VIRTUAL_HOST_NAME);

		return virtualHostModel;
	}

	public List<WikiNodeModel> newWikiNodeModels(long groupId) {
		List<WikiNodeModel> wikiNodeModels = new ArrayList<>(
			BenchmarksPropsValues.MAX_WIKI_NODE_COUNT);

		for (int i = 1; i <= BenchmarksPropsValues.MAX_WIKI_NODE_COUNT; i++) {
			wikiNodeModels.add(newWikiNodeModel(groupId, i));
		}

		return wikiNodeModels;
	}

	public List<WikiPageModel> newWikiPageModels(WikiNodeModel wikiNodeModel) {
		List<WikiPageModel> wikiPageModels = new ArrayList<>(
			BenchmarksPropsValues.MAX_WIKI_PAGE_COUNT);

		for (int i = 1; i <= BenchmarksPropsValues.MAX_WIKI_PAGE_COUNT; i++) {
			wikiPageModels.add(newWikiPageModel(wikiNodeModel, i));
		}

		return wikiPageModels;
	}

	public WikiPageResourceModel newWikiPageResourceModel(
		WikiPageModel wikiPageModel) {

		WikiPageResourceModel wikiPageResourceModel =
			new WikiPageResourceModelImpl();

		// UUID

		wikiPageResourceModel.setUuid(SequentialUUID.generate());

		// PK fields

		wikiPageResourceModel.setResourcePrimKey(
			wikiPageModel.getResourcePrimKey());

		// Other fields

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

	public String toInsertSQL(BaseModel<?> baseModel) {
		try {
			StringBundler sb = new StringBundler();

			toInsertSQL(sb, baseModel);

			Class<?> clazz = baseModel.getClass();

			for (Class<?> modelClass : clazz.getInterfaces()) {
				try {
					Method method = DataFactory.class.getMethod(
						"newResourcePermissionModels", modelClass);

					for (ResourcePermissionModel resourcePermissionModel :
							(List<ResourcePermissionModel>)method.invoke(
								this, baseModel)) {

						sb.append("\n");

						toInsertSQL(sb, resourcePermissionModel);
					}
				}
				catch (NoSuchMethodException noSuchMethodException) {
				}
			}

			return sb.toString();
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			return ReflectionUtil.throwException(reflectiveOperationException);
		}
	}

	public String toInsertSQL(
		String mappingTableName, long companyId, long leftPrimaryKey,
		long rightPrimaryKey) {

		StringBundler sb = new StringBundler(9);

		sb.append("insert into ");
		sb.append(mappingTableName);
		sb.append(" values (");
		sb.append(companyId);
		sb.append(", ");
		sb.append(leftPrimaryKey);
		sb.append(", ");
		sb.append(rightPrimaryKey);
		sb.append(", 0, null);");

		return sb.toString();
	}

	protected ObjectValuePair<String[], Integer>
		getAssetPublisherAssetCategoriesQueryValues(
			List<AssetCategoryModel> assetCategoryModels, int index) {

		String[] categoryIds = new String[4];

		for (int i = 0; i < 4; i++) {
			if (i > 0) {
				index +=
					BenchmarksPropsValues.
						MAX_ASSET_ENTRY_TO_ASSET_CATEGORY_COUNT;
			}

			AssetCategoryModel assetCategoryModel = assetCategoryModels.get(
				index % assetCategoryModels.size());

			categoryIds[i] = String.valueOf(assetCategoryModel.getCategoryId());
		}

		return new ObjectValuePair<>(
			categoryIds,
			index +
				BenchmarksPropsValues.MAX_ASSET_ENTRY_TO_ASSET_CATEGORY_COUNT);
	}

	protected ObjectValuePair<String[], Integer>
		getAssetPublisherAssetTagsQueryValues(
			List<AssetTagModel> assetTagModels, int index) {

		String[] assetTagNames = new String[4];

		for (int i = 0; i < 4; i++) {
			if (i > 0) {
				index +=
					BenchmarksPropsValues.MAX_ASSET_ENTRY_TO_ASSET_TAG_COUNT;
			}

			AssetTagModel assetTagModel = assetTagModels.get(
				index % assetTagModels.size());

			assetTagNames[i] = String.valueOf(assetTagModel.getName());
		}

		return new ObjectValuePair<>(
			assetTagNames,
			index + BenchmarksPropsValues.MAX_ASSET_ENTRY_TO_ASSET_TAG_COUNT);
	}

	protected String getClassName(long classNameId) {
		for (ClassNameModel classNameModel : _classNameModels.values()) {
			if (classNameModel.getClassNameId() == classNameId) {
				return classNameModel.getValue();
			}
		}

		throw new RuntimeException(
			"Unable to find class name for id " + classNameId);
	}

	protected InputStream getResourceInputStream(String resourceName) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			_DEPENDENCIES_DIR + resourceName);
	}

	protected SimpleCounter getSimpleCounter(
		Map<Long, SimpleCounter>[] simpleCountersArray, long groupId,
		long classNameId) {

		Map<Long, SimpleCounter> simpleCounters =
			simpleCountersArray[(int)groupId - 1];

		if (simpleCounters == null) {
			simpleCounters = new HashMap<>();

			simpleCountersArray[(int)groupId - 1] = simpleCounters;
		}

		SimpleCounter simpleCounter = simpleCounters.get(classNameId);

		if (simpleCounter == null) {
			simpleCounter = new SimpleCounter(0);

			simpleCounters.put(classNameId, simpleCounter);
		}

		return simpleCounter;
	}

	protected AssetCategoryModel newAssetCategoryModel(
		long groupId, String name, long vocabularyId) {

		AssetCategoryModel assetCategoryModel = new AssetCategoryModelImpl();

		// UUID

		assetCategoryModel.setUuid(SequentialUUID.generate());

		// PK fields

		assetCategoryModel.setCategoryId(_counter.get());

		// Group instance

		assetCategoryModel.setGroupId(groupId);

		// Audit fields

		assetCategoryModel.setCompanyId(_companyId);
		assetCategoryModel.setUserId(_sampleUserId);
		assetCategoryModel.setUserName(_SAMPLE_USER_NAME);
		assetCategoryModel.setCreateDate(new Date());
		assetCategoryModel.setModifiedDate(new Date());

		// Other fields

		assetCategoryModel.setParentCategoryId(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
		assetCategoryModel.setTreePath(
			"/" + assetCategoryModel.getCategoryId() + "/");
		assetCategoryModel.setName(name);

		StringBundler sb = new StringBundler(4);

		sb.append("<?xml version=\"1.0\"?><root available-locales=\"en_US\" ");
		sb.append("default-locale=\"en_US\"><Title language-id=\"en_US\">");
		sb.append(name);
		sb.append("</Title></root>");

		assetCategoryModel.setTitle(sb.toString());

		assetCategoryModel.setVocabularyId(vocabularyId);
		assetCategoryModel.setLastPublishDate(new Date());

		return assetCategoryModel;
	}

	protected AssetEntryModel newAssetEntryModel(
		long groupId, Date createDate, Date modifiedDate, long classNameId,
		long classPK, String uuid, long classTypeId, boolean listable,
		boolean visible, String mimeType, String title) {

		AssetEntryModel assetEntryModel = new AssetEntryModelImpl();

		// PK fields

		assetEntryModel.setEntryId(_counter.get());

		// Group instance

		assetEntryModel.setGroupId(groupId);

		// Audit fields

		assetEntryModel.setCompanyId(_companyId);
		assetEntryModel.setUserId(_sampleUserId);
		assetEntryModel.setUserName(_SAMPLE_USER_NAME);
		assetEntryModel.setCreateDate(createDate);
		assetEntryModel.setModifiedDate(modifiedDate);

		// Other fields

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

	protected AssetVocabularyModel newAssetVocabularyModel(
		long grouId, long userId, String userName, String name) {

		AssetVocabularyModel assetVocabularyModel =
			new AssetVocabularyModelImpl();

		// UUID

		assetVocabularyModel.setUuid(SequentialUUID.generate());

		// PK fields

		assetVocabularyModel.setVocabularyId(_counter.get());

		// Group instance

		assetVocabularyModel.setGroupId(grouId);

		// Audit fields

		assetVocabularyModel.setCompanyId(_companyId);
		assetVocabularyModel.setUserId(userId);
		assetVocabularyModel.setUserName(userName);
		assetVocabularyModel.setCreateDate(new Date());
		assetVocabularyModel.setModifiedDate(new Date());

		// Other fields

		assetVocabularyModel.setName(name);

		StringBundler sb = new StringBundler(4);

		sb.append("<?xml version=\"1.0\"?><root available-locales=\"en_US\" ");
		sb.append("default-locale=\"en_US\"><Title language-id=\"en_US\">");
		sb.append(name);
		sb.append("</Title></root>");

		assetVocabularyModel.setTitle(sb.toString());

		assetVocabularyModel.setSettings(
			"multiValued=true\\nselectedClassNameIds=0");
		assetVocabularyModel.setLastPublishDate(new Date());

		return assetVocabularyModel;
	}

	protected BlogsEntryModel newBlogsEntryModel(long groupId, int index) {
		BlogsEntryModel blogsEntryModel = new BlogsEntryModelImpl();

		// UUID

		blogsEntryModel.setUuid(SequentialUUID.generate());

		// PK fields

		blogsEntryModel.setEntryId(_counter.get());

		// Group instance

		blogsEntryModel.setGroupId(groupId);

		// Audit fields

		blogsEntryModel.setCompanyId(_companyId);
		blogsEntryModel.setUserId(_sampleUserId);
		blogsEntryModel.setUserName(_SAMPLE_USER_NAME);
		blogsEntryModel.setCreateDate(new Date());
		blogsEntryModel.setModifiedDate(new Date());

		// Other field

		blogsEntryModel.setTitle("Test Blog " + index);
		blogsEntryModel.setSubtitle("Subtitle of Test Blog " + index);
		blogsEntryModel.setUrlTitle("testblog" + index);
		blogsEntryModel.setContent("This is test blog " + index + ".");
		blogsEntryModel.setDisplayDate(new Date());
		blogsEntryModel.setLastPublishDate(new Date());
		blogsEntryModel.setStatusByUserId(_sampleUserId);
		blogsEntryModel.setStatusDate(new Date());

		return blogsEntryModel;
	}

	protected DDMStorageLinkModel newDDMStorageLinkModel(
		long ddmStorageLinkId, long classPK, long structureId, long versionId) {

		DDMStorageLinkModel ddmStorageLinkModel = new DDMStorageLinkModelImpl();

		// UUID

		ddmStorageLinkModel.setUuid(SequentialUUID.generate());

		// PK fields

		ddmStorageLinkModel.setStorageLinkId(ddmStorageLinkId);

		// Audit fields

		ddmStorageLinkModel.setCompanyId(_companyId);

		// Other fields

		ddmStorageLinkModel.setClassNameId(getClassNameId(DDMContent.class));
		ddmStorageLinkModel.setClassPK(classPK);
		ddmStorageLinkModel.setStructureId(structureId);
		ddmStorageLinkModel.setStructureVersionId(versionId);

		return ddmStorageLinkModel;
	}

	protected DDMStructureLayoutModel newDDMStructureLayoutModel(
		long groupId, long userId, long structureVersionId, String definition) {

		DDMStructureLayoutModel ddmStructureLayoutModel =
			new DDMStructureLayoutModelImpl();

		// UUID

		ddmStructureLayoutModel.setUuid(SequentialUUID.generate());

		// PK fields

		ddmStructureLayoutModel.setStructureLayoutId(_counter.get());

		// Group instance

		ddmStructureLayoutModel.setGroupId(groupId);

		// Audit fields

		ddmStructureLayoutModel.setCompanyId(_companyId);
		ddmStructureLayoutModel.setUserId(userId);
		ddmStructureLayoutModel.setUserName(_SAMPLE_USER_NAME);
		ddmStructureLayoutModel.setCreateDate(nextFutureDate());
		ddmStructureLayoutModel.setModifiedDate(nextFutureDate());

		// Other fields

		ddmStructureLayoutModel.setStructureLayoutKey(
			String.valueOf(_counter.get()));
		ddmStructureLayoutModel.setStructureVersionId(structureVersionId);
		ddmStructureLayoutModel.setDefinition(definition);

		return ddmStructureLayoutModel;
	}

	protected DDMStructureLinkModel newDDMStructureLinkModel(
		long classNameId, long classPK, long structureId) {

		DDMStructureLinkModel ddmStructureLinkModel =
			new DDMStructureLinkModelImpl();

		// PK fields

		ddmStructureLinkModel.setStructureLinkId(_counter.get());

		// Audit fields

		ddmStructureLinkModel.setCompanyId(_companyId);

		// Other fields

		ddmStructureLinkModel.setClassNameId(classNameId);
		ddmStructureLinkModel.setClassPK(classPK);
		ddmStructureLinkModel.setStructureId(structureId);

		return ddmStructureLinkModel;
	}

	protected DDMStructureModel newDDMStructureModel(
		long groupId, long userId, long classNameId, String structureKey,
		String definition, long structureId) {

		DDMStructureModel ddmStructureModel = new DDMStructureModelImpl();

		// UUID

		ddmStructureModel.setUuid(SequentialUUID.generate());

		// PK fields

		ddmStructureModel.setStructureId(structureId);

		// Group instance

		ddmStructureModel.setGroupId(groupId);

		// Audit fields

		ddmStructureModel.setCompanyId(_companyId);
		ddmStructureModel.setUserId(userId);
		ddmStructureModel.setUserName(_SAMPLE_USER_NAME);
		ddmStructureModel.setVersionUserId(userId);
		ddmStructureModel.setVersionUserName(_SAMPLE_USER_NAME);
		ddmStructureModel.setCreateDate(nextFutureDate());
		ddmStructureModel.setModifiedDate(nextFutureDate());

		// Other fields

		ddmStructureModel.setClassNameId(classNameId);
		ddmStructureModel.setStructureKey(structureKey);
		ddmStructureModel.setVersion(DDMStructureConstants.VERSION_DEFAULT);

		StringBundler sb = new StringBundler(4);

		sb.append("<?xml version=\"1.0\"?><root available-locales=\"en_US\" ");
		sb.append("default-locale=\"en_US\"><name language-id=\"en_US\">");
		sb.append(structureKey);
		sb.append("</name></root>");

		ddmStructureModel.setName(sb.toString());

		ddmStructureModel.setDefinition(definition);
		ddmStructureModel.setStorageType(StorageType.DEFAULT.toString());
		ddmStructureModel.setLastPublishDate(nextFutureDate());

		return ddmStructureModel;
	}

	protected DDMTemplateModel newDDMTemplateModel(
		long groupId, long userId, long structureId, long sourceClassNameId,
		long templateId) {

		DDMTemplateModel ddmTemplateModel = new DDMTemplateModelImpl();

		// UUID

		ddmTemplateModel.setUuid(SequentialUUID.generate());

		// PK fields

		ddmTemplateModel.setTemplateId(templateId);

		// Group instance

		ddmTemplateModel.setGroupId(groupId);

		// Audit fields

		ddmTemplateModel.setCompanyId(_companyId);
		ddmTemplateModel.setUserId(userId);
		ddmTemplateModel.setVersionUserId(userId);
		ddmTemplateModel.setVersionUserName(_SAMPLE_USER_NAME);
		ddmTemplateModel.setCreateDate(nextFutureDate());
		ddmTemplateModel.setModifiedDate(nextFutureDate());

		// Other fields

		ddmTemplateModel.setClassNameId(getClassNameId(DDMStructure.class));
		ddmTemplateModel.setClassPK(structureId);
		ddmTemplateModel.setResourceClassNameId(sourceClassNameId);
		ddmTemplateModel.setTemplateKey(_JOURNAL_STRUCTURE_KEY);
		ddmTemplateModel.setVersion(DDMTemplateConstants.VERSION_DEFAULT);

		StringBundler sb = new StringBundler(3);

		sb.append("<?xml version=\"1.0\"?><root available-locales=\"en_US\" ");
		sb.append("default-locale=\"en_US\"><name language-id=\"en_US\">");
		sb.append("Basic Web Content</name></root>");

		ddmTemplateModel.setName(sb.toString());

		ddmTemplateModel.setType(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);
		ddmTemplateModel.setMode(DDMTemplateConstants.TEMPLATE_MODE_CREATE);
		ddmTemplateModel.setLanguage(TemplateConstants.LANG_TYPE_FTL);
		ddmTemplateModel.setScript("${content.getData()}");
		ddmTemplateModel.setCacheable(true);
		ddmTemplateModel.setSmallImage(false);
		ddmTemplateModel.setLastPublishDate(nextFutureDate());

		return ddmTemplateModel;
	}

	protected DLFileEntryModel newDlFileEntryModel(
		DLFolderModel dlFolderModel, int index) {

		DLFileEntryModel dlFileEntryModel = new DLFileEntryModelImpl();

		// UUID

		dlFileEntryModel.setUuid(SequentialUUID.generate());

		// PK fields

		dlFileEntryModel.setFileEntryId(_counter.get());

		// Group instance

		dlFileEntryModel.setGroupId(dlFolderModel.getGroupId());

		// Audit fields

		dlFileEntryModel.setCompanyId(_companyId);
		dlFileEntryModel.setUserId(_sampleUserId);
		dlFileEntryModel.setUserName(_SAMPLE_USER_NAME);
		dlFileEntryModel.setCreateDate(nextFutureDate());
		dlFileEntryModel.setModifiedDate(nextFutureDate());

		// Other fields

		dlFileEntryModel.setRepositoryId(dlFolderModel.getRepositoryId());
		dlFileEntryModel.setFolderId(dlFolderModel.getFolderId());
		dlFileEntryModel.setName("TestFile" + index);
		dlFileEntryModel.setFileName("TestFile" + index + ".txt");
		dlFileEntryModel.setExtension("txt");
		dlFileEntryModel.setMimeType(ContentTypes.TEXT_PLAIN);
		dlFileEntryModel.setTitle("TestFile" + index + ".txt");
		dlFileEntryModel.setFileEntryTypeId(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		dlFileEntryModel.setVersion(DLFileEntryConstants.VERSION_DEFAULT);
		dlFileEntryModel.setSize(BenchmarksPropsValues.MAX_DL_FILE_ENTRY_SIZE);
		dlFileEntryModel.setLastPublishDate(nextFutureDate());

		return dlFileEntryModel;
	}

	protected DLFolderModel newDLFolderModel(
		long groupId, long parentFolderId, int index) {

		DLFolderModel dlFolderModel = new DLFolderModelImpl();

		// UUID

		dlFolderModel.setUuid(SequentialUUID.generate());

		// PK fields

		dlFolderModel.setFolderId(_counter.get());

		// Group instance

		dlFolderModel.setGroupId(groupId);

		// Audit fields

		dlFolderModel.setCompanyId(_companyId);
		dlFolderModel.setUserId(_sampleUserId);
		dlFolderModel.setUserName(_SAMPLE_USER_NAME);
		dlFolderModel.setCreateDate(nextFutureDate());
		dlFolderModel.setModifiedDate(nextFutureDate());

		// Other fields

		dlFolderModel.setRepositoryId(groupId);
		dlFolderModel.setParentFolderId(parentFolderId);
		dlFolderModel.setName("Test Folder " + index);
		dlFolderModel.setLastPostDate(nextFutureDate());
		dlFolderModel.setDefaultFileEntryTypeId(_defaultDLFileEntryTypeId);
		dlFolderModel.setLastPublishDate(nextFutureDate());
		dlFolderModel.setStatusDate(nextFutureDate());

		return dlFolderModel;
	}

	protected GroupModel newGroupModel(
		long groupId, long classNameId, long classPK, String name,
		boolean site) {

		GroupModel groupModel = new GroupModelImpl();

		// UUID

		groupModel.setUuid(SequentialUUID.generate());

		// PK fields

		groupModel.setGroupId(groupId);

		// Audit fields

		groupModel.setCompanyId(_companyId);
		groupModel.setCreatorUserId(_sampleUserId);

		// Other fields

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

	protected LayoutModel newLayoutModel(
		long groupId, String name, boolean privateLayout,
		String layoutTemplateId, String... columns) {

		SimpleCounter simpleCounter = _layoutCounters.get(groupId);

		if (simpleCounter == null) {
			simpleCounter = new SimpleCounter();

			_layoutCounters.put(groupId, simpleCounter);
		}

		LayoutModel layoutModel = new LayoutModelImpl();

		// UUID

		layoutModel.setUuid(SequentialUUID.generate());

		// PK fields

		layoutModel.setPlid(_counter.get());

		// Group instance

		layoutModel.setGroupId(groupId);

		// Audit fields

		layoutModel.setCompanyId(_companyId);
		layoutModel.setUserId(_sampleUserId);
		layoutModel.setUserName(_SAMPLE_USER_NAME);
		layoutModel.setCreateDate(new Date());
		layoutModel.setModifiedDate(new Date());

		// Other fields

		layoutModel.setLayoutId(simpleCounter.get());
		layoutModel.setName(
			"<?xml version=\"1.0\"?><root><name>" + name + "</name></root>");
		layoutModel.setType(LayoutConstants.TYPE_PORTLET);

		UnicodeProperties typeSettingsUnicodeProperties = new UnicodeProperties(
			true);

		typeSettingsUnicodeProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, layoutTemplateId);

		for (int i = 0; i < columns.length; i++) {
			if (!columns[i].equals("")) {
				typeSettingsUnicodeProperties.setProperty(
					"column-" + (i + 1), columns[i]);
			}
		}

		if (privateLayout) {
			typeSettingsUnicodeProperties.setProperty(
				"privateLayout", String.valueOf(privateLayout));
		}

		layoutModel.setTypeSettings(
			StringUtil.replace(
				typeSettingsUnicodeProperties.toString(), '\n', "\\n"));

		layoutModel.setFriendlyURL(StringPool.FORWARD_SLASH + name);

		layoutModel.setLastPublishDate(new Date());

		return layoutModel;
	}

	protected LayoutSetModel newLayoutSetModel(
		long groupId, boolean privateLayout) {

		LayoutSetModel layoutSetModel = new LayoutSetModelImpl();

		// PK fields

		layoutSetModel.setLayoutSetId(_counter.get());

		// Group instance

		layoutSetModel.setGroupId(groupId);

		// Audit fields

		layoutSetModel.setCompanyId(_companyId);
		layoutSetModel.setCreateDate(new Date());
		layoutSetModel.setModifiedDate(new Date());

		// Other fields

		layoutSetModel.setPrivateLayout(privateLayout);
		layoutSetModel.setThemeId("classic_WAR_classictheme");
		layoutSetModel.setColorSchemeId("01");

		return layoutSetModel;
	}

	protected MBCategoryModel newMBCategoryModel(long groupId, int index) {
		MBCategoryModel mbCategoryModel = new MBCategoryModelImpl();

		// UUID

		mbCategoryModel.setUuid(SequentialUUID.generate());

		// PK fields

		mbCategoryModel.setCategoryId(_counter.get());

		// Group instance

		mbCategoryModel.setGroupId(groupId);

		// Audit fields

		mbCategoryModel.setCompanyId(_companyId);
		mbCategoryModel.setUserId(_sampleUserId);
		mbCategoryModel.setUserName(_SAMPLE_USER_NAME);
		mbCategoryModel.setCreateDate(new Date());
		mbCategoryModel.setModifiedDate(new Date());

		// Other fields

		mbCategoryModel.setParentCategoryId(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
		mbCategoryModel.setName("Test Category " + index);
		mbCategoryModel.setDisplayStyle(
			MBCategoryConstants.DEFAULT_DISPLAY_STYLE);
		mbCategoryModel.setLastPublishDate(new Date());
		mbCategoryModel.setStatusDate(new Date());

		return mbCategoryModel;
	}

	protected MBMessageModel newMBMessageModel(
		long groupId, long classNameId, long classPK, long categoryId,
		long threadId, long messageId, long rootMessageId, long parentMessageId,
		String subject, String urlSubject, String body) {

		MBMessageModel mBMessageModel = new MBMessageModelImpl();

		// UUID

		mBMessageModel.setUuid(SequentialUUID.generate());

		// PK fields

		mBMessageModel.setMessageId(messageId);

		// Group instance

		mBMessageModel.setGroupId(groupId);

		// Audit fields

		mBMessageModel.setCompanyId(_companyId);
		mBMessageModel.setUserId(_sampleUserId);
		mBMessageModel.setUserName(_SAMPLE_USER_NAME);
		mBMessageModel.setCreateDate(new Date());
		mBMessageModel.setModifiedDate(new Date());

		// Other fields

		mBMessageModel.setClassNameId(classNameId);
		mBMessageModel.setClassPK(classPK);
		mBMessageModel.setCategoryId(categoryId);
		mBMessageModel.setThreadId(threadId);
		mBMessageModel.setRootMessageId(rootMessageId);
		mBMessageModel.setParentMessageId(parentMessageId);
		mBMessageModel.setSubject(subject);
		mBMessageModel.setUrlSubject(urlSubject + "-" + messageId);
		mBMessageModel.setBody(body);
		mBMessageModel.setFormat(MBMessageConstants.DEFAULT_FORMAT);
		mBMessageModel.setLastPublishDate(new Date());
		mBMessageModel.setStatusDate(new Date());

		return mBMessageModel;
	}

	protected MBThreadModel newMBThreadModel(
		long threadId, long groupId, long categoryId, long rootMessageId) {

		MBThreadModel mbThreadModel = new MBThreadModelImpl();

		// UUID

		mbThreadModel.setUuid(SequentialUUID.generate());

		// PK fields

		mbThreadModel.setThreadId(threadId);

		// Group instance

		mbThreadModel.setGroupId(groupId);

		// Audit fields

		mbThreadModel.setCompanyId(_companyId);
		mbThreadModel.setUserId(_sampleUserId);
		mbThreadModel.setUserName(_SAMPLE_USER_NAME);
		mbThreadModel.setCreateDate(new Date());
		mbThreadModel.setModifiedDate(new Date());

		// Other fields

		mbThreadModel.setCategoryId(categoryId);
		mbThreadModel.setRootMessageId(rootMessageId);
		mbThreadModel.setRootMessageUserId(_sampleUserId);
		mbThreadModel.setLastPostByUserId(_sampleUserId);
		mbThreadModel.setLastPostDate(new Date());
		mbThreadModel.setLastPublishDate(new Date());
		mbThreadModel.setStatusDate(new Date());

		return mbThreadModel;
	}

	protected ReleaseModelImpl newReleaseModel(
			long releaseId, String servletContextName, String schemaVersion,
			int buildNumber, boolean verified, String testString)
		throws IOException {

		ReleaseModelImpl releaseModelImpl = new ReleaseModelImpl();

		// PK fields

		releaseModelImpl.setReleaseId(releaseId);

		// Audit fields

		releaseModelImpl.setCreateDate(new Date());
		releaseModelImpl.setModifiedDate(new Date());

		// Other fields

		releaseModelImpl.setServletContextName(servletContextName);
		releaseModelImpl.setSchemaVersion(schemaVersion);
		releaseModelImpl.setBuildNumber(buildNumber);
		releaseModelImpl.setBuildDate(new Date());
		releaseModelImpl.setVerified(verified);
		releaseModelImpl.setTestString(testString);

		return releaseModelImpl;
	}

	protected ResourcePermissionModel newResourcePermissionModel(
		String name, String primKey, long roleId, long ownerId) {

		ResourcePermissionModel resourcePermissionModel =
			new ResourcePermissionModelImpl();

		// PK fields

		resourcePermissionModel.setResourcePermissionId(
			_resourcePermissionCounter.get());

		// Audit fields

		resourcePermissionModel.setCompanyId(_companyId);

		// Other fields

		resourcePermissionModel.setName(name);
		resourcePermissionModel.setScope(ResourceConstants.SCOPE_INDIVIDUAL);
		resourcePermissionModel.setPrimKey(primKey);
		resourcePermissionModel.setPrimKeyId(GetterUtil.getLong(primKey));
		resourcePermissionModel.setRoleId(roleId);
		resourcePermissionModel.setOwnerId(ownerId);
		resourcePermissionModel.setActionIds(1);
		resourcePermissionModel.setViewActionId(true);

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

		// UUID

		roleModel.setUuid(SequentialUUID.generate());

		// PK fields

		roleModel.setRoleId(_counter.get());

		// Audit fields

		roleModel.setCompanyId(_companyId);
		roleModel.setUserId(_sampleUserId);
		roleModel.setUserName(_SAMPLE_USER_NAME);
		roleModel.setCreateDate(new Date());
		roleModel.setModifiedDate(new Date());

		// Other fields

		roleModel.setClassNameId(getClassNameId(Role.class));
		roleModel.setClassPK(roleModel.getRoleId());
		roleModel.setName(name);
		roleModel.setType(type);

		return roleModel;
	}

	protected SocialActivityModel newSocialActivityModel(
		long groupId, long classNameId, long classPK, int type,
		String extraData) {

		SocialActivityModel socialActivityModel = new SocialActivityModelImpl();

		// PK fields

		socialActivityModel.setActivityId(_socialActivityCounter.get());

		// Group instance

		socialActivityModel.setGroupId(groupId);

		// Audit fields

		socialActivityModel.setCompanyId(_companyId);
		socialActivityModel.setUserId(_sampleUserId);
		socialActivityModel.setCreateDate(_CURRENT_TIME + _timeCounter.get());

		// Other fields

		socialActivityModel.setClassNameId(classNameId);
		socialActivityModel.setClassPK(classPK);
		socialActivityModel.setType(type);
		socialActivityModel.setExtraData(extraData);

		return socialActivityModel;
	}

	protected SubscriptionModel newSubscriptionModel(
		long classNameId, long classPK) {

		SubscriptionModel subscriptionModel = new SubscriptionModelImpl();

		// PK fields

		subscriptionModel.setSubscriptionId(_counter.get());

		// Audit fields

		subscriptionModel.setCompanyId(_companyId);
		subscriptionModel.setUserId(_sampleUserId);
		subscriptionModel.setUserName(_SAMPLE_USER_NAME);
		subscriptionModel.setCreateDate(new Date());
		subscriptionModel.setModifiedDate(new Date());

		// Other fields

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

		// UUID

		userModel.setUuid(SequentialUUID.generate());

		// PK fields

		userModel.setUserId(userId);

		// Audit fields

		userModel.setCompanyId(_companyId);
		userModel.setCreateDate(new Date());
		userModel.setModifiedDate(new Date());

		// Other fields

		userModel.setDefaultUser(defaultUser);
		userModel.setContactId(_counter.get());
		userModel.setPassword("test");
		userModel.setPasswordModifiedDate(new Date());
		userModel.setReminderQueryQuestion("What is your screen name?");
		userModel.setReminderQueryAnswer(screenName);
		userModel.setScreenName(screenName);
		userModel.setEmailAddress(screenName + "@liferay.com");
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

		// UUID

		wikiNodeModel.setUuid(SequentialUUID.generate());

		// PK fields

		wikiNodeModel.setNodeId(_counter.get());

		// Group instance

		wikiNodeModel.setGroupId(groupId);

		// Audit fields

		wikiNodeModel.setCompanyId(_companyId);
		wikiNodeModel.setUserId(_sampleUserId);
		wikiNodeModel.setUserName(_SAMPLE_USER_NAME);
		wikiNodeModel.setCreateDate(new Date());
		wikiNodeModel.setModifiedDate(new Date());

		// Other fields

		wikiNodeModel.setName("Test Node " + index);
		wikiNodeModel.setLastPostDate(new Date());
		wikiNodeModel.setLastPublishDate(new Date());
		wikiNodeModel.setStatusDate(new Date());

		return wikiNodeModel;
	}

	protected WikiPageModel newWikiPageModel(
		WikiNodeModel wikiNodeModel, int index) {

		WikiPageModel wikiPageModel = new WikiPageModelImpl();

		// UUID

		wikiPageModel.setUuid(SequentialUUID.generate());

		// PK fields

		wikiPageModel.setPageId(_counter.get());

		// Resource

		wikiPageModel.setResourcePrimKey(_counter.get());

		// Group instance

		wikiPageModel.setGroupId(wikiNodeModel.getGroupId());

		// Audit fields

		wikiPageModel.setCompanyId(_companyId);
		wikiPageModel.setUserId(_sampleUserId);
		wikiPageModel.setUserName(_SAMPLE_USER_NAME);
		wikiPageModel.setCreateDate(new Date());
		wikiPageModel.setModifiedDate(new Date());

		// Other fields

		wikiPageModel.setNodeId(wikiNodeModel.getNodeId());
		wikiPageModel.setTitle("Test Page " + index);
		wikiPageModel.setVersion(WikiPageConstants.VERSION_DEFAULT);
		wikiPageModel.setContent(
			StringBundler.concat(
				"This is Test Page ", index, " of ", wikiNodeModel.getName(),
				"."));
		wikiPageModel.setFormat("creole");
		wikiPageModel.setHead(true);
		wikiPageModel.setLastPublishDate(new Date());

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

	protected void toInsertSQL(StringBundler sb, BaseModel<?> baseModel) {
		try {
			sb.append("insert into ");

			Class<?> clazz = baseModel.getClass();

			Field tableNameField = clazz.getField("TABLE_NAME");

			sb.append(tableNameField.get(null));

			sb.append(" values (");

			Field tableColumnsField = clazz.getField("TABLE_COLUMNS");

			for (Object[] tableColumn :
					(Object[][])tableColumnsField.get(null)) {

				String name = TextFormatter.format(
					(String)tableColumn[0], TextFormatter.G);

				if (name.endsWith(StringPool.UNDERLINE)) {
					name = name.substring(0, name.length() - 1);
				}
				else if (name.equals("DeliverySubTypeSettings")) {
					name = "DeliverySubscriptionTypeSettings";
				}
				else if (name.equals("LPageTemplateStructureRelId")) {
					name = "LayoutPageTemplateStructureRelId";
				}

				int type = (int)tableColumn[1];

				if (type == Types.TIMESTAMP) {
					Method method = clazz.getMethod("get".concat(name));

					Date date = (Date)method.invoke(baseModel);

					if (date == null) {
						sb.append("null");
					}
					else {
						sb.append("'");
						sb.append(_simpleDateFormat.format(date));
						sb.append("'");
					}
				}
				else if ((type == Types.VARCHAR) || (type == Types.CLOB)) {
					Method method = clazz.getMethod("get".concat(name));

					sb.append("'");
					sb.append(method.invoke(baseModel));
					sb.append("'");
				}
				else if (type == Types.BOOLEAN) {
					Method method = clazz.getMethod("is".concat(name));

					sb.append(method.invoke(baseModel));
				}
				else {
					Method method = clazz.getMethod("get".concat(name));

					sb.append(method.invoke(baseModel));
				}

				sb.append(", ");
			}

			sb.setIndex(sb.index() - 1);

			sb.append(");");
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			ReflectionUtil.throwException(reflectiveOperationException);
		}
	}

	private String _getMBDiscussionCombinedClassName(Class<?> clazz) {
		return StringBundler.concat(
			MBDiscussion.class.getName(), StringPool.UNDERLINE,
			clazz.getName());
	}

	private String _getResourcePermissionModelName(String... classNames) {
		if (ArrayUtil.isEmpty(classNames)) {
			return StringPool.BLANK;
		}

		Arrays.sort(classNames);

		StringBundler sb = new StringBundler(classNames.length * 2);

		for (String className : classNames) {
			sb.append(className);
			sb.append(StringPool.DASH);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private String _readFile(String resourceName) throws Exception {
		List<String> lines = new ArrayList<>();

		StringUtil.readLines(getResourceInputStream(resourceName), lines);

		return StringUtil.merge(lines, StringPool.SPACE);
	}

	private static final long _CURRENT_TIME = System.currentTimeMillis();

	private static final String _DEPENDENCIES_DIR =
		"com/liferay/portal/tools/sample/sql/builder/dependencies/data/";

	private static final long _FUTURE_TIME =
		System.currentTimeMillis() + Time.YEAR;

	private static final String _JOURNAL_STRUCTURE_KEY = "BASIC-WEB-CONTENT";

	private static final String _SAMPLE_USER_NAME = "Sample";

	private static final PortletPreferencesFactory _portletPreferencesFactory =
		new PortletPreferencesFactoryImpl();

	private final long _accountId;
	private RoleModel _administratorRoleModel;
	private Map<Long, SimpleCounter>[] _assetCategoryCounters;
	private Map<Long, List<AssetCategoryModel>>[] _assetCategoryModelsMaps;
	private final long[] _assetClassNameIds;
	private final Map<Long, Integer> _assetClassNameIdsIndexes =
		new HashMap<>();
	private final Map<Long, Integer> _assetPublisherQueryStartIndexes =
		new HashMap<>();
	private Map<Long, SimpleCounter>[] _assetTagCounters;
	private List<AssetTagModel>[] _assetTagModelsArray;
	private Map<Long, List<AssetTagModel>>[] _assetTagModelsMaps;
	private List<AssetVocabularyModel>[] _assetVocabularyModelsArray;
	private final Map<String, ClassNameModel> _classNameModels =
		new HashMap<>();
	private final long _companyId;
	private final SimpleCounter _counter;
	private final PortletPreferencesImpl
		_defaultAssetPublisherPortletPreferencesImpl;
	private AssetVocabularyModel _defaultAssetVocabularyModel;
	private final long _defaultDLDDMStructureId;
	private final long _defaultDLDDMStructureVersionId;
	private long _defaultDLFileEntryTypeId =
		DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT;
	private String _defaultJournalArticleId;
	private final long _defaultJournalDDMStructureId;
	private final long _defaultJournalDDMStructureVersionId;
	private final long _defaultJournalDDMTemplateId;
	private final long _defaultUserId;
	private final String _dlDDMStructureContent;
	private final String _dlDDMStructureLayoutContent;
	private List<String> _firstNames;
	private final SimpleCounter _futureDateCounter;
	private final long _globalGroupId;
	private final long _guestGroupId;
	private RoleModel _guestRoleModel;
	private String _journalArticleContent;
	private final Map<Long, String> _journalArticleResourceUUIDs =
		new HashMap<>();
	private final String _journalDDMStructureContent;
	private final String _journalDDMStructureLayoutContent;
	private List<String> _lastNames;
	private final Map<Long, SimpleCounter> _layoutCounters = new HashMap<>();
	private final String _layoutPageTemplateStructureRelData;
	private RoleModel _ownerRoleModel;
	private RoleModel _powerUserRoleModel;
	private final SimpleCounter _resourcePermissionCounter;
	private List<RoleModel> _roleModels;
	private final long _sampleUserId;
	private final Format _simpleDateFormat;
	private RoleModel _siteMemberRoleModel;
	private final SimpleCounter _socialActivityCounter;
	private final SimpleCounter _timeCounter;
	private final long _userPersonalSiteGroupId;
	private RoleModel _userRoleModel;
	private final SimpleCounter _userScreenNameCounter;

}