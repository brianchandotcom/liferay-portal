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

package com.liferay.site.initializer.extender.internal;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.util.comparator.ClassNameModelResourceComparator;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeEntry;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeEntryResource;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.dto.v1_0.AccountRole;
import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.resource.v1_0.AccountRoleResource;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentFolderResource;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.utility.page.converter.LayoutUtilityPageEntryTypeConverter;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.notification.rest.dto.v1_0.NotificationTemplate;
import com.liferay.notification.rest.resource.v1_0.NotificationTemplateResource;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship;
import com.liferay.object.admin.rest.dto.v1_0.util.ObjectActionUtil;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFieldResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.GroupModel;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.extender.internal.util.SiteInitializerUtil;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;

import java.io.Serializable;

import java.net.URL;
import java.net.URLConnection;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Brian Wing Shun Chan
 */
public class BundleSiteInitializer implements SiteInitializer {

	public BundleSiteInitializer(
		AccountResource.Factory accountResourceFactory,
		AccountRoleLocalService accountRoleLocalService,
		AccountRoleResource.Factory accountRoleResourceFactory,
		AssetCategoryLocalService assetCategoryLocalService,
		AssetListEntryLocalService assetListEntryLocalService, Bundle bundle,
		ClientExtensionEntryLocalService clientExtensionEntryLocalService,
		ConfigurationProvider configurationProvider,
		DDMStructureLocalService ddmStructureLocalService,
		DDMTemplateLocalService ddmTemplateLocalService,
		DefaultDDMStructureHelper defaultDDMStructureHelper,
		DLURLHelper dlURLHelper,
		DocumentFolderResource.Factory documentFolderResourceFactory,
		DocumentResource.Factory documentResourceFactory,
		FragmentsImporter fragmentsImporter,
		GroupLocalService groupLocalService,
		JournalArticleLocalService journalArticleLocalService,
		JSONFactory jsonFactory,
		KnowledgeBaseArticleResource.Factory
			knowledgeBaseArticleResourceFactory,
		KnowledgeBaseFolderResource.Factory knowledgeBaseFolderResourceFactory,
		LayoutCopyHelper layoutCopyHelper,
		LayoutLocalService layoutLocalService,
		LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService,
		LayoutsImporter layoutsImporter,
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService,
		LayoutPageTemplateStructureRelLocalService
			layoutPageTemplateStructureRelLocalService,
		LayoutSetLocalService layoutSetLocalService,
		LayoutUtilityPageEntryLocalService layoutUtilityPageEntryLocalService,
		ListTypeDefinitionLocalService listTypeDefinitionLocalService,
		ListTypeDefinitionResource listTypeDefinitionResource,
		ListTypeDefinitionResource.Factory listTypeDefinitionResourceFactory,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ListTypeEntryResource listTypeEntryResource,
		ListTypeEntryResource.Factory listTypeEntryResourceFactory,
		NotificationTemplateResource.Factory
			notificationTemplateResourceFactory,
		ObjectActionLocalService objectActionLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryManager objectEntryManager,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectFieldResource.Factory objectFieldResourceFactory,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		ObjectRelationshipResource.Factory objectRelationshipResourceFactory,
		OrganizationLocalService organizationLocalService,
		OrganizationResource.Factory organizationResourceFactory, Portal portal,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService,
		SAPEntryLocalService sapEntryLocalService,
		SegmentsEntryLocalService segmentsEntryLocalService,
		SegmentsExperienceLocalService segmentsExperienceLocalService,
		SettingsFactory settingsFactory,
		SiteNavigationMenuItemLocalService siteNavigationMenuItemLocalService,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry,
		SiteNavigationMenuLocalService siteNavigationMenuLocalService,
		StructuredContentFolderResource.Factory
			structuredContentFolderResourceFactory,
		StyleBookEntryZipProcessor styleBookEntryZipProcessor,
		TaxonomyCategoryResource.Factory taxonomyCategoryResourceFactory,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		TemplateEntryLocalService templateEntryLocalService,
		ThemeLocalService themeLocalService,
		UserAccountResource.Factory userAccountResourceFactory,
		UserGroupLocalService userGroupLocalService,
		UserLocalService userLocalService,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService,
		WorkflowDefinitionResource.Factory workflowDefinitionResourceFactory) {

		_accountResourceFactory = accountResourceFactory;
		_accountRoleLocalService = accountRoleLocalService;
		_accountRoleResourceFactory = accountRoleResourceFactory;
		_assetCategoryLocalService = assetCategoryLocalService;
		_assetListEntryLocalService = assetListEntryLocalService;
		_bundle = bundle;
		_clientExtensionEntryLocalService = clientExtensionEntryLocalService;
		_configurationProvider = configurationProvider;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmTemplateLocalService = ddmTemplateLocalService;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_dlURLHelper = dlURLHelper;
		_documentFolderResourceFactory = documentFolderResourceFactory;
		_documentResourceFactory = documentResourceFactory;
		_fragmentsImporter = fragmentsImporter;
		_groupLocalService = groupLocalService;
		_journalArticleLocalService = journalArticleLocalService;
		_jsonFactory = jsonFactory;
		_knowledgeBaseArticleResourceFactory =
			knowledgeBaseArticleResourceFactory;
		_knowledgeBaseFolderResourceFactory =
			knowledgeBaseFolderResourceFactory;
		_layoutCopyHelper = layoutCopyHelper;
		_layoutLocalService = layoutLocalService;
		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
		_layoutsImporter = layoutsImporter;
		_layoutPageTemplateStructureLocalService =
			layoutPageTemplateStructureLocalService;
		_layoutPageTemplateStructureRelLocalService =
			layoutPageTemplateStructureRelLocalService;
		_layoutSetLocalService = layoutSetLocalService;
		_layoutUtilityPageEntryLocalService =
			layoutUtilityPageEntryLocalService;
		_listTypeDefinitionLocalService = listTypeDefinitionLocalService;
		_listTypeDefinitionResource = listTypeDefinitionResource;
		_listTypeDefinitionResourceFactory = listTypeDefinitionResourceFactory;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_listTypeEntryResource = listTypeEntryResource;
		_listTypeEntryResourceFactory = listTypeEntryResourceFactory;
		_notificationTemplateResourceFactory =
			notificationTemplateResourceFactory;
		_objectActionLocalService = objectActionLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryManager = objectEntryManager;
		_objectFieldLocalService = objectFieldLocalService;
		_objectFieldResourceFactory = objectFieldResourceFactory;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_objectRelationshipResourceFactory = objectRelationshipResourceFactory;
		_organizationLocalService = organizationLocalService;
		_organizationResourceFactory = organizationResourceFactory;
		_portal = portal;
		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
		_sapEntryLocalService = sapEntryLocalService;
		_segmentsEntryLocalService = segmentsEntryLocalService;
		_segmentsExperienceLocalService = segmentsExperienceLocalService;
		_settingsFactory = settingsFactory;
		_siteNavigationMenuItemLocalService =
			siteNavigationMenuItemLocalService;
		_siteNavigationMenuItemTypeRegistry =
			siteNavigationMenuItemTypeRegistry;
		_siteNavigationMenuLocalService = siteNavigationMenuLocalService;
		_structuredContentFolderResourceFactory =
			structuredContentFolderResourceFactory;
		_styleBookEntryZipProcessor = styleBookEntryZipProcessor;
		_taxonomyCategoryResourceFactory = taxonomyCategoryResourceFactory;
		_taxonomyVocabularyResourceFactory = taxonomyVocabularyResourceFactory;
		_templateEntryLocalService = templateEntryLocalService;
		_themeLocalService = themeLocalService;
		_userAccountResourceFactory = userAccountResourceFactory;
		_userGroupLocalService = userGroupLocalService;
		_userLocalService = userLocalService;
		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
		_workflowDefinitionResourceFactory = workflowDefinitionResourceFactory;

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		_classLoader = bundleWiring.getClassLoader();

		_classNameIdStringUtilReplaceValues =
			_getClassNameIdStringUtilReplaceValues();
		_releaseInfoStringUtilReplaceValues =
			_getReleaseInfoStringUtilReplaceValues();
	}

	@Override
	public String getDescription(Locale locale) {
		Dictionary<String, String> headers = _bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getString(
			headers.get("Liferay-Site-Initializer-Description"));
	}

	@Override
	public String getKey() {
		return _bundle.getSymbolicName();
	}

	@Override
	public String getName(Locale locale) {
		Dictionary<String, String> headers = _bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getString(
			headers.get("Liferay-Site-Initializer-Name"),
			headers.get("Bundle-Name"));
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/thumbnail.png";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		if (_log.isDebugEnabled()) {
			_log.debug("Commerce site initializer " + _commerceSiteInitializer);
		}

		long startTime = System.currentTimeMillis();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Initializing ", getKey(), " for group ", groupId));
		}

		try {
			User user = _userLocalService.getUser(
				PrincipalThreadLocal.getUserId());

			ServiceContext serviceContextThreadLocal =
				ServiceContextThreadLocal.getServiceContext();

			ServiceContext serviceContext =
				(ServiceContext)serviceContextThreadLocal.clone();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setCompanyId(user.getCompanyId());
			serviceContext.setScopeGroupId(groupId);
			serviceContext.setTimeZone(user.getTimeZone());
			serviceContext.setUserId(user.getUserId());

			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder =
					new SiteNavigationMenuItemSettingsBuilder();

			_invoke(() -> _addAccounts(serviceContext));

			Map<String, String> ddmStructureEntryIdsStringUtilReplaceValues =
				_invoke(() -> _addOrUpdateDDMStructures(serviceContext));

			Map<String, String> assetListEntryIdsStringUtilReplaceValues =
				_invoke(
					() -> _addAssetListEntries(
						_ddmStructureLocalService, serviceContext));
			Map<String, String> documentsStringUtilReplaceValues = _invoke(
				() -> _addOrUpdateDocuments(
					serviceContext, siteNavigationMenuItemSettingsBuilder));

			_invoke(
				() -> _addFragmentEntries(
					assetListEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues, serviceContext));

			_invoke(() -> _addOrUpdateExpandoColumns(serviceContext));
			_invoke(() -> _addOrUpdateKnowledgeBaseArticles(serviceContext));
			_invoke(() -> _addOrUpdateOrganizations(serviceContext));

			Map<String, String> roleIdsStringUtilReplaceValues = _invoke(
				() -> _addOrUpdateRoles(serviceContext));

			_invoke(() -> _addOrUpdateSAPEntries(serviceContext));

			Map<String, String> segmentsEntriesIdsStringUtilReplaceValues =
				_invoke(() -> _addOrUpdateSegmentsEntries(serviceContext));

			_invoke(() -> _addSiteConfiguration(serviceContext));
			_invoke(() -> _addSiteSettings(serviceContext));
			_invoke(() -> _addStyleBookEntries(serviceContext));
			_invoke(() -> _addOrUpdateUserGroups(serviceContext));

			Map<String, String>
				taxonomyCategoryIdsAndTaxonomyVocabularyIdsStringUtilReplaceValues =
					_invoke(
						() -> _addOrUpdateTaxonomyVocabularies(
							serviceContext,
							siteNavigationMenuItemSettingsBuilder));

			_invoke(() -> _addPortletSettings(serviceContext));
			_invoke(
				() -> _updateLayoutSets(
					documentsStringUtilReplaceValues, serviceContext));

			_invoke(
				() -> _addOrUpdateDDMTemplates(
					_ddmStructureLocalService, serviceContext));
			_invoke(
				() -> _addOrUpdateJournalArticles(
					_ddmStructureLocalService, _ddmTemplateLocalService,
					documentsStringUtilReplaceValues, serviceContext,
					siteNavigationMenuItemSettingsBuilder));

			Map<String, String> listTypeDefinitionIdsStringUtilReplaceValues =
				_invoke(() -> _addOrUpdateListTypeDefinitions(serviceContext));

			_invoke(() -> _addUserAccounts(serviceContext));

			Map<String, String>
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues =
					_invoke(
						() -> _addObjectDefinitions(
							documentsStringUtilReplaceValues,
							listTypeDefinitionIdsStringUtilReplaceValues,
							serviceContext,
							siteNavigationMenuItemSettingsBuilder));

			_invoke(
				() -> _addOrUpdateNotificationTemplates(
					documentsStringUtilReplaceValues,
					objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
					serviceContext));

			Map<String, Layout> layouts = _invoke(
				() -> _addOrUpdateLayouts(serviceContext));

			_invoke(
				() -> _addCPDefinitions(
					documentsStringUtilReplaceValues,
					objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
					serviceContext));

			// LPS-172108 Layouts have to be created first so that links in
			// layout page templates work

			_invoke(
				() -> _addLayoutPageTemplates(
					assetListEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues,
					objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
					serviceContext,
					taxonomyCategoryIdsAndTaxonomyVocabularyIdsStringUtilReplaceValues));

			_invoke(
				() -> _addLayoutUtilityPageEntries(
					assetListEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues,
					objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
					serviceContext,
					taxonomyCategoryIdsAndTaxonomyVocabularyIdsStringUtilReplaceValues));

			// TODO Review order/dependency

			Map<String, String> clientExtensionEntryIdsStringUtilReplaceValues =
				_invoke(
					() -> _addOrUpdateClientExtensionEntries(
						documentsStringUtilReplaceValues, serviceContext));

			_invoke(
				() -> _addLayoutsContent(
					assetListEntryIdsStringUtilReplaceValues,
					clientExtensionEntryIdsStringUtilReplaceValues,
					ddmStructureEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues, layouts,
					objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
					serviceContext,
					siteNavigationMenuItemSettingsBuilder.build(),
					taxonomyCategoryIdsAndTaxonomyVocabularyIdsStringUtilReplaceValues));

			_invoke(() -> _addRolesAssignments(serviceContext));

			_invoke(
				() -> _addSegmentsExperiences(
					assetListEntryIdsStringUtilReplaceValues,
					clientExtensionEntryIdsStringUtilReplaceValues,
					ddmStructureEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues,
					objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
					segmentsEntriesIdsStringUtilReplaceValues, serviceContext,
					taxonomyCategoryIdsAndTaxonomyVocabularyIdsStringUtilReplaceValues));
			_invoke(() -> _addUserRoles(serviceContext));

			_invoke(
				() -> _addWorkflowDefinitions(
					roleIdsStringUtilReplaceValues, serviceContext));

			_invoke(
				() -> _addOrUpdateResourcePermissions(
					objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
					serviceContext));

			_invoke(() -> _updateGroupSiteInitializerKey(groupId));
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new InitializationException(exception);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Initialized ", getKey(), " for group ", groupId, " in ",
					System.currentTimeMillis() - startTime, " ms"));
		}
	}

	@Override
	public boolean isActive(long companyId) {
		Dictionary<String, String> headers = _bundle.getHeaders(
			StringPool.BLANK);

		String featureFlag = headers.get(
			"Liferay-Site-Initializer-Feature-Flag");

		if (Validator.isNotNull(featureFlag) &&
			!FeatureFlagManagerUtil.isEnabled(featureFlag)) {

			return false;
		}

		return true;
	}

	protected void setCommerceSiteInitializer(
		CommerceSiteInitializer commerceSiteInitializer) {

		_commerceSiteInitializer = commerceSiteInitializer;
	}

	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private void _addAccounts(ServiceContext serviceContext) throws Exception {
		String json = SiteInitializerUtil.read(
			"/site-initializer/accounts.json", _servletContext);

		if (json == null) {
			return;
		}

		AccountResource.Builder builder = _accountResourceFactory.create();

		AccountResource accountResource = builder.user(
			serviceContext.fetchUser()
		).build();

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			Account account = Account.toDTO(
				String.valueOf(jsonArray.getJSONObject(i)));

			accountResource.putAccountByExternalReferenceCode(
				account.getExternalReferenceCode(), account);
		}
	}

	private Map<String, String> _addAssetListEntries(
			DDMStructureLocalService ddmStructureLocalService,
			ServiceContext serviceContext)
		throws Exception {

		Map<String, String> assetListEntryIdsStringUtilReplaceValues =
			new HashMap<>();

		String json = SiteInitializerUtil.read(
			"/site-initializer/asset-list-entries.json", _servletContext);

		if (json == null) {
			return assetListEntryIdsStringUtilReplaceValues;
		}

		JSONArray assetListJSONArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < assetListJSONArray.length(); i++) {
			JSONObject assetListJSONObject = assetListJSONArray.getJSONObject(
				i);

			_addOrUpdateAssetListEntry(
				assetListJSONObject, ddmStructureLocalService, serviceContext);
		}

		List<AssetListEntry> assetListEntries =
			_assetListEntryLocalService.getAssetListEntries(
				serviceContext.getScopeGroupId());

		for (AssetListEntry assetListEntry : assetListEntries) {
			String assetListEntryKeyUppercase = StringUtil.toUpperCase(
				assetListEntry.getAssetListEntryKey());

			assetListEntryIdsStringUtilReplaceValues.put(
				"ASSET_LIST_ENTRY_ID:" + assetListEntryKeyUppercase,
				String.valueOf(assetListEntry.getAssetListEntryId()));
		}

		return assetListEntryIdsStringUtilReplaceValues;
	}

	private void _addCPDefinitions(
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String>
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		if (_commerceSiteInitializer == null) {
			return;
		}

		_commerceSiteInitializer.addCPDefinitions(
			_bundle, documentsStringUtilReplaceValues,
			objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
			serviceContext, _servletContext);
	}

	private void _addFragmentEntries(
			Map<String, String> assetListEntryIdsStringUtilReplaceValues,
			Map<String, String> documentsStringUtilReplaceValues, long groupId,
			String parentResourcePath, ServiceContext serviceContext)
		throws Exception {

		Enumeration<URL> enumeration = _bundle.findEntries(
			parentResourcePath, StringPool.STAR, true);

		if (enumeration == null) {
			return;
		}

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String fileName = url.getFile();

			if (fileName.endsWith("/")) {
				continue;
			}

			if (StringUtil.endsWith(
					fileName, "fragment-composition-definition.json")) {

				String json = StringUtil.read(url.openStream());

				json = _replace(
					_replace(json, serviceContext),
					assetListEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues);

				zipWriter.addEntry(
					_removeFirst(fileName, parentResourcePath), json);
			}
			else {
				zipWriter.addEntry(
					_removeFirst(fileName, parentResourcePath),
					url.openStream());
			}
		}

		_fragmentsImporter.importFragmentEntries(
			serviceContext.getUserId(), groupId, 0, zipWriter.getFile(), true);
	}

	private void _addFragmentEntries(
			Map<String, String> assetListEntryIdsStringUtilReplaceValues,
			Map<String, String> documentsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(
			serviceContext.getCompanyId());

		_addFragmentEntries(
			assetListEntryIdsStringUtilReplaceValues,
			documentsStringUtilReplaceValues, group.getGroupId(),
			"/site-initializer/fragments/company", serviceContext);

		_addFragmentEntries(
			assetListEntryIdsStringUtilReplaceValues,
			documentsStringUtilReplaceValues, serviceContext.getScopeGroupId(),
			"/site-initializer/fragments/group", serviceContext);
	}

	private void _addLayoutContent(
			Map<String, String> assetListEntryIdsStringUtilReplaceValues,
			Map<String, String> clientExtensionEntryIdsStringUtilReplaceValues,
			Map<String, String> ddmStructureEntryIdsStringUtilReplaceValues,
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String>
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
			Layout layout, String resourcePath, long segmentsExperienceId,
			ServiceContext serviceContext,
			Map<String, String> taxonomyCategoryIdsStringUtilReplaceValues)
		throws Exception {

		JSONObject pageJSONObject = _jsonFactory.createJSONObject(
			SiteInitializerUtil.read(
				resourcePath + "page.json", _servletContext));

		String type = StringUtil.toLowerCase(pageJSONObject.getString("type"));

		if (Objects.equals(type, "url")) {
			return;
		}
		else if (Objects.equals(type, "widget")) {
			type = LayoutConstants.TYPE_PORTLET;
		}

		String json = SiteInitializerUtil.read(
			resourcePath + "page-definition.json", _servletContext);

		if (json == null) {
			return;
		}

		json = _replace(
			_replace(json, serviceContext),
			assetListEntryIdsStringUtilReplaceValues,
			clientExtensionEntryIdsStringUtilReplaceValues,
			ddmStructureEntryIdsStringUtilReplaceValues,
			documentsStringUtilReplaceValues,
			objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
			taxonomyCategoryIdsStringUtilReplaceValues);

		JSONObject pageDefinitionJSONObject = _jsonFactory.createJSONObject(
			json);

		Layout draftLayout = layout.fetchDraftLayout();

		if (Objects.equals(type, LayoutConstants.TYPE_COLLECTION) ||
			Objects.equals(type, LayoutConstants.TYPE_CONTENT)) {

			JSONObject pageElementJSONObject =
				pageDefinitionJSONObject.getJSONObject("pageElement");

			if ((pageElementJSONObject != null) &&
				Objects.equals(
					pageElementJSONObject.getString("type"), "Root")) {

				JSONArray jsonArray = pageElementJSONObject.getJSONArray(
					"pageElements");

				if (!JSONUtil.isEmpty(jsonArray)) {
					LayoutPageTemplateStructure layoutPageTemplateStructure =
						_layoutPageTemplateStructureLocalService.
							fetchLayoutPageTemplateStructure(
								draftLayout.getGroupId(), draftLayout.getPlid(),
								true);

					LayoutStructure layoutStructure = null;

					if (segmentsExperienceId == 0) {
						layoutStructure = LayoutStructure.of(
							layoutPageTemplateStructure.
								getDefaultSegmentsExperienceData());

						segmentsExperienceId =
							_segmentsExperienceLocalService.
								fetchDefaultSegmentsExperienceId(
									draftLayout.getPlid());
					}
					else {
						layoutStructure = LayoutStructure.of(
							layoutPageTemplateStructure.getData(
								segmentsExperienceId));
					}

					for (int i = 0; i < jsonArray.length(); i++) {
						_layoutsImporter.importPageElement(
							draftLayout, layoutStructure,
							layoutStructure.getMainItemId(),
							jsonArray.getString(i), i, segmentsExperienceId);
					}
				}
			}
		}

		if (Objects.equals(type, LayoutConstants.TYPE_COLLECTION)) {
			UnicodeProperties unicodeProperties =
				draftLayout.getTypeSettingsProperties();

			Object[] typeSettings = JSONUtil.toObjectArray(
				pageJSONObject.getJSONArray("typeSettings"));

			for (Object typeSetting : typeSettings) {
				JSONObject typeSettingJSONObject = (JSONObject)typeSetting;

				String key = typeSettingJSONObject.getString("key");
				String value = typeSettingJSONObject.getString("value");

				unicodeProperties.put(
					key,
					_replace(value, assetListEntryIdsStringUtilReplaceValues));
			}

			draftLayout = _layoutLocalService.updateLayout(
				serviceContext.getScopeGroupId(), draftLayout.isPrivateLayout(),
				draftLayout.getLayoutId(), unicodeProperties.toString());
		}

		if (Objects.equals(type, LayoutConstants.TYPE_COLLECTION) ||
			Objects.equals(type, LayoutConstants.TYPE_CONTENT)) {

			JSONObject settingsJSONObject =
				pageDefinitionJSONObject.getJSONObject("settings");

			if (settingsJSONObject != null) {
				draftLayout = _updateDraftLayout(
					draftLayout, settingsJSONObject);
			}

			layout = _layoutCopyHelper.copyLayoutContent(draftLayout, layout);

			_layoutLocalService.updateStatus(
				layout.getUserId(), draftLayout.getPlid(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);
			_layoutLocalService.updateStatus(
				layout.getUserId(), layout.getPlid(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);
		}
	}

	private void _addLayoutPageTemplates(
			Map<String, String> assetListEntryIdsStringUtilReplaceValues,
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String>
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
			ServiceContext serviceContext,
			Map<String, String> taxonomyCategoryIdsStringUtilReplaceValues)
		throws Exception {

		Enumeration<URL> enumeration = _bundle.findEntries(
			"/site-initializer/layout-page-templates", StringPool.STAR, true);

		if (enumeration == null) {
			return;
		}

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String fileName = url.getFile();

			if (fileName.endsWith("/")) {
				continue;
			}

			String urlPath = url.getPath();

			if (StringUtil.endsWith(urlPath, "display-page-template.json") ||
				StringUtil.endsWith(urlPath, "page-definition.json")) {

				String json = StringUtil.read(url.openStream());

				json = _replace(
					_replace(json, serviceContext),
					assetListEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues,
					objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
					taxonomyCategoryIdsStringUtilReplaceValues);

				String css = _replace(
					SiteInitializerUtil.read(
						FileUtil.getPath(urlPath) + "/css.css",
						_servletContext),
					documentsStringUtilReplaceValues);

				if (Validator.isNotNull(css)) {
					JSONObject jsonObject = _jsonFactory.createJSONObject(json);

					JSONObject settingsJSONObject = jsonObject.getJSONObject(
						"settings");

					settingsJSONObject.put("css", css);

					jsonObject.put("settings", settingsJSONObject);

					json = jsonObject.toString();
				}

				zipWriter.addEntry(
					_removeFirst(
						urlPath, "/site-initializer/layout-page-templates"),
					json);
			}
			else {
				zipWriter.addEntry(
					_removeFirst(
						urlPath, "/site-initializer/layout-page-templates"),
					url.openStream());
			}
		}

		_layoutsImporter.importFile(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			zipWriter.getFile(), true);
	}

	private void _addLayoutsContent(
			Map<String, String> assetListEntryIdsStringUtilReplaceValues,
			Map<String, String> clientExtensionEntryIdsStringUtilReplaceValues,
			Map<String, String> ddmStructureEntryIdsStringUtilReplaceValues,
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, Layout> layouts,
			Map<String, String>
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
			ServiceContext serviceContext,
			Map<String, SiteNavigationMenuItemSetting>
				siteNavigationMenuItemSettings,
			Map<String, String> taxonomyCategoryIdsStringUtilReplaceValues)
		throws Exception {

		for (Map.Entry<String, Layout> entry : layouts.entrySet()) {
			_addLayoutContent(
				assetListEntryIdsStringUtilReplaceValues,
				clientExtensionEntryIdsStringUtilReplaceValues,
				ddmStructureEntryIdsStringUtilReplaceValues,
				documentsStringUtilReplaceValues,
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
				entry.getValue(), entry.getKey(), 0, serviceContext,
				taxonomyCategoryIdsStringUtilReplaceValues);
		}

		_siteNavigationMenuLocalService.deleteSiteNavigationMenus(
			serviceContext.getScopeGroupId());

		_addSiteNavigationMenus(serviceContext, siteNavigationMenuItemSettings);
	}

	private void _addLayoutUtilityPageEntries(
			Map<String, String> assetListEntryIdsStringUtilReplaceValues,
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String>
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
			ServiceContext serviceContext,
			Map<String, String> taxonomyCategoryIdsStringUtilReplaceValues)
		throws Exception {

		Enumeration<URL> enumeration = _bundle.findEntries(
			"/site-initializer/layout-utility-page-entries", StringPool.STAR,
			true);

		if (enumeration == null) {
			return;
		}

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String fileName = url.getFile();

			if (fileName.endsWith("/")) {
				continue;
			}

			String urlPath = url.getPath();

			if (StringUtil.endsWith(urlPath, "page-definition.json")) {
				String json = StringUtil.read(url.openStream());

				json = _replace(
					_replace(json, serviceContext),
					assetListEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues,
					objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
					taxonomyCategoryIdsStringUtilReplaceValues);

				String css = _replace(
					SiteInitializerUtil.read(
						FileUtil.getPath(urlPath) + "/css.css",
						_servletContext),
					documentsStringUtilReplaceValues);

				if (Validator.isNotNull(css)) {
					JSONObject jsonObject = _jsonFactory.createJSONObject(json);

					JSONObject settingsJSONObject = jsonObject.getJSONObject(
						"settings");

					settingsJSONObject.put("css", css);

					jsonObject.put("settings", settingsJSONObject);

					json = jsonObject.toString();
				}

				zipWriter.addEntry(
					_removeFirst(
						urlPath,
						"/site-initializer/layout-utility-page-entries"),
					json);
			}
			else {
				zipWriter.addEntry(
					_removeFirst(
						urlPath,
						"/site-initializer/layout-utility-page-entries"),
					url.openStream());
			}
		}

		_layoutsImporter.importFile(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			zipWriter.getFile(), true);

		_setDefaultLayoutUtilityPageEntries(serviceContext);
	}

	private Map<String, String> _addObjectDefinitions(
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String> listTypeDefinitionIdsStringUtilReplaceValues,
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		Map<String, String> objectDefinitionIdsStringUtilReplaceValues =
			new HashMap<>();

		List<com.liferay.object.model.ObjectDefinition>
			serviceBuilderObjectDefinitions =
				_objectDefinitionLocalService.getObjectDefinitions(
					serviceContext.getCompanyId(), true,
					WorkflowConstants.STATUS_APPROVED);

		for (com.liferay.object.model.ObjectDefinition
				serviceBuilderObjectDefinition :
					serviceBuilderObjectDefinitions) {

			objectDefinitionIdsStringUtilReplaceValues.put(
				"OBJECT_DEFINITION_ID:" +
					serviceBuilderObjectDefinition.getShortName(),
				String.valueOf(
					serviceBuilderObjectDefinition.getObjectDefinitionId()));
		}

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/object-definitions");

		if (SetUtil.isEmpty(resourcePaths)) {
			return objectDefinitionIdsStringUtilReplaceValues;
		}

		Map<String, ObjectDefinition> accountEntryRestrictedObjectDefinitions =
			new HashMap<>();

		ObjectDefinitionResource.Builder objectDefinitionResourceBuilder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource =
			objectDefinitionResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith(".object-actions.json")) {
				continue;
			}

			String json = SiteInitializerUtil.read(
				resourcePath, _servletContext);

			json = _replace(json, listTypeDefinitionIdsStringUtilReplaceValues);

			ObjectDefinition objectDefinition = ObjectDefinition.toDTO(json);

			if (objectDefinition == null) {
				_log.error(
					"Unable to transform object definition from JSON: " + json);

				continue;
			}

			Page<ObjectDefinition> objectDefinitionsPage =
				objectDefinitionResource.getObjectDefinitionsPage(
					null, null,
					objectDefinitionResource.toFilter(
						StringBundler.concat(
							"name eq '", objectDefinition.getName(), "'")),
					null, null);

			ObjectDefinition existingObjectDefinition =
				objectDefinitionsPage.fetchFirstItem();

			if (existingObjectDefinition == null) {
				if (GetterUtil.getBoolean(
						objectDefinition.getAccountEntryRestricted())) {

					accountEntryRestrictedObjectDefinitions.put(
						objectDefinition.getName(), objectDefinition);
				}

				objectDefinition =
					objectDefinitionResource.postObjectDefinition(
						objectDefinition);

				objectDefinitionResource.postObjectDefinitionPublish(
					objectDefinition.getId());
			}
			else {
				objectDefinition =
					objectDefinitionResource.patchObjectDefinition(
						existingObjectDefinition.getId(), objectDefinition);
			}

			objectDefinitionIdsStringUtilReplaceValues.put(
				"OBJECT_DEFINITION_ID:" + objectDefinition.getName(),
				String.valueOf(objectDefinition.getId()));

			if (Objects.equals(
					objectDefinition.getScope(),
					ObjectDefinitionConstants.SCOPE_COMPANY) &&
				(existingObjectDefinition != null)) {

				continue;
			}

			String objectActionsJSON = SiteInitializerUtil.read(
				StringUtil.replaceLast(
					resourcePath, ".json", ".object-actions.json"),
				_servletContext);

			if (objectActionsJSON == null) {
				continue;
			}

			JSONArray jsonArray = _jsonFactory.createJSONArray(
				objectActionsJSON);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				JSONObject parametersJSONObject = jsonObject.getJSONObject(
					"parameters");

				_objectActionLocalService.addOrUpdateObjectAction(
					jsonObject.getString("externalReferenceCode"), 0,
					serviceContext.getUserId(), objectDefinition.getId(),
					jsonObject.getBoolean("active"),
					jsonObject.getString("conditionExpression"),
					jsonObject.getString("description"),
					SiteInitializerUtil.toMap(
						jsonObject.getString("errorMessage")),
					SiteInitializerUtil.toMap(jsonObject.getString("label")),
					jsonObject.getString("name"),
					jsonObject.getString("objectActionExecutorKey"),
					jsonObject.getString("objectActionTriggerKey"),
					ObjectActionUtil.toParametersUnicodeProperties(
						parametersJSONObject.toMap()));
			}
		}

		_invoke(
			() -> _addOrUpdateObjectRelationships(
				objectDefinitionIdsStringUtilReplaceValues, serviceContext));

		for (Map.Entry<String, ObjectDefinition> entry :
				accountEntryRestrictedObjectDefinitions.entrySet()) {

			com.liferay.object.model.ObjectDefinition
				serviceBuilderObjectDefinition =
					_objectDefinitionLocalService.fetchObjectDefinition(
						serviceContext.getCompanyId(), "C_" + entry.getKey());

			_objectDefinitionLocalService.enableAccountEntryRestricted(
				_objectRelationshipLocalService.
					getObjectRelationshipByObjectDefinitionId(
						serviceBuilderObjectDefinition.getObjectDefinitionId(),
						"accountEntryTo" +
							serviceBuilderObjectDefinition.getShortName()));
		}

		_invoke(
			() -> _addOrUpdateObjectFields(
				listTypeDefinitionIdsStringUtilReplaceValues,
				objectDefinitionIdsStringUtilReplaceValues, serviceContext));

		Map<String, String> objectEntryIdsStringUtilReplaceValues = _invoke(
			() -> _addOrUpdateObjectEntries(
				documentsStringUtilReplaceValues, serviceContext,
				siteNavigationMenuItemSettingsBuilder));

		return HashMapBuilder.putAll(
			objectDefinitionIdsStringUtilReplaceValues
		).putAll(
			objectEntryIdsStringUtilReplaceValues
		).build();
	}

	private void _addOrganizationUser(
			JSONArray jsonArray, ServiceContext serviceContext, long userId)
		throws Exception {

		if (JSONUtil.isEmpty(jsonArray)) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long organizationId = _organizationLocalService.getOrganizationId(
				serviceContext.getCompanyId(), jsonObject.getString("name"));

			if (organizationId <= 0) {
				continue;
			}

			_userLocalService.addOrganizationUser(organizationId, userId);
		}
	}

	private void _addOrKnowledgeBaseObjects(
			boolean folder, long parentKnowledgeBaseObjectId,
			String parentResourcePath, ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			if (!resourcePath.endsWith(".metadata.json")) {
				continue;
			}

			String json = SiteInitializerUtil.read(
				resourcePath, _servletContext);

			if (json == null) {
				continue;
			}

			JSONObject jsonObject = _jsonFactory.createJSONObject(json);

			if (jsonObject.has("articleBody")) {
				_addOrUpdateKnowledgeBaseArticle(
					folder, jsonObject, parentKnowledgeBaseObjectId,
					resourcePath.substring(
						0, resourcePath.indexOf(".metadata.json")),
					serviceContext);
			}
			else {
				_addOrUpdateKnowledgeBaseFolder(
					jsonObject, parentKnowledgeBaseObjectId,
					resourcePath.substring(
						0, resourcePath.indexOf(".metadata.json")),
					serviceContext);
			}
		}
	}

	private void _addOrUpdateAssetListEntry(
			JSONObject assetListJSONObject,
			DDMStructureLocalService ddmStructureLocalService,
			ServiceContext serviceContext)
		throws Exception {

		AssetListEntry assetListEntry = null;

		String assetListEntryKey = StringUtil.toLowerCase(
			_replace(assetListJSONObject.getString("title"), " ", "-"));

		for (AssetListEntry curAssetListEntry :
				_assetListEntryLocalService.getAssetListEntries(
					serviceContext.getScopeGroupId())) {

			if (Objects.equals(
					curAssetListEntry.getAssetListEntryKey(),
					assetListEntryKey)) {

				assetListEntry = curAssetListEntry;

				break;
			}
		}

		JSONObject unicodePropertiesJSONObject =
			assetListJSONObject.getJSONObject("unicodeProperties");

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			serviceContext.getScopeGroupId(),
			_portal.getClassNameId(
				unicodePropertiesJSONObject.getString("classNameIds")),
			assetListJSONObject.getString("ddmStructureKey"));

		List<String> classNameIdStrings = new ArrayList<>();

		List<Long> classNameIds = ListUtil.fromArray(
			AssetRendererFactoryRegistryUtil.getIndexableClassNameIds(
				serviceContext.getCompanyId(), true));

		classNameIds = ListUtil.sort(
			classNameIds,
			new ClassNameModelResourceComparator(
				true, serviceContext.getLocale()));

		classNameIds.forEach(
			classNameId -> classNameIdStrings.add(classNameId.toString()));

		Map<String, String> map = HashMapBuilder.put(
			"anyAssetType",
			String.valueOf(
				_portal.getClassNameId(
					unicodePropertiesJSONObject.getString("classNameIds")))
		).put(
			unicodePropertiesJSONObject.getString("anyClassType"),
			String.valueOf(ddmStructure.getStructureId())
		).put(
			"classNameIds", StringUtil.merge(classNameIdStrings, ",")
		).put(
			unicodePropertiesJSONObject.getString("classTypeIds"),
			String.valueOf(ddmStructure.getStructureId())
		).put(
			"groupIds", String.valueOf(serviceContext.getScopeGroupId())
		).build();

		Object[] orderByObjects = JSONUtil.toObjectArray(
			unicodePropertiesJSONObject.getJSONArray("orderBy"));

		for (Object orderByObject : orderByObjects) {
			JSONObject orderByJSONObject = (JSONObject)orderByObject;

			map.put(
				orderByJSONObject.getString("key"),
				orderByJSONObject.getString("value"));
		}

		String[] assetTagNames = JSONUtil.toStringArray(
			assetListJSONObject.getJSONArray("assetTagNames"));

		for (int i = 0; i < assetTagNames.length; i++) {
			map.put("queryValues" + i, assetTagNames[i]);

			Object[] queryObjects = JSONUtil.toObjectArray(
				unicodePropertiesJSONObject.getJSONArray("query"));

			for (Object queryObject : queryObjects) {
				JSONObject queryJSONObject = (JSONObject)queryObject;

				map.put(
					queryJSONObject.getString("key"),
					queryJSONObject.getString("value"));
			}
		}

		if (assetListEntry == null) {
			_assetListEntryLocalService.addDynamicAssetListEntry(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				assetListJSONObject.getString("title"),
				UnicodePropertiesBuilder.create(
					map, true
				).buildString(),
				serviceContext);
		}
		else {
			_assetListEntryLocalService.updateAssetListEntry(
				assetListEntry.getAssetListEntryId(),
				assetListJSONObject.getString("title"));
		}
	}

	private Map<String, String> _addOrUpdateClientExtensionEntries(
			Map<String, String> documentsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		Map<String, String> clientExtensionEntryIdsStringUtilReplaceValues =
			new HashMap<>();

		String json = SiteInitializerUtil.read(
			"/site-initializer/client-extension-entries.json", _servletContext);

		if (json == null) {
			return clientExtensionEntryIdsStringUtilReplaceValues;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			StringBundler sb = new StringBundler();

			JSONObject propertiesJSONObject = jsonObject.getJSONObject(
				"properties");

			if (propertiesJSONObject != null) {
				for (String key : propertiesJSONObject.keySet()) {
					sb.append(key);
					sb.append(StringPool.EQUAL);
					sb.append(propertiesJSONObject.getString(key));
					sb.append(StringPool.NEW_LINE);
				}
			}

			_clientExtensionEntryLocalService.addOrUpdateClientExtensionEntry(
				jsonObject.getString("externalReferenceCode"),
				serviceContext.getUserId(), StringPool.BLANK,
				SiteInitializerUtil.toMap(jsonObject.getString("name_i18n")),
				sb.toString(), StringPool.BLANK,
				ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"cssURLs",
					_replace(
						StringUtil.merge(
							JSONUtil.toStringArray(
								jsonObject.getJSONArray("cssURLs")),
							StringPool.NEW_LINE),
						documentsStringUtilReplaceValues)
				).put(
					"friendlyURLMapping", StringPool.BLANK
				).put(
					"htmlElementName", jsonObject.getString("htmlElementName")
				).put(
					"instanceable", jsonObject.getBoolean("instanceable")
				).put(
					"portletCategoryName",
					jsonObject.getString("portletCategoryName")
				).put(
					"urls",
					_replace(
						StringUtil.merge(
							JSONUtil.toStringArray(
								jsonObject.getJSONArray("elementURLs")),
							StringPool.NEW_LINE),
						documentsStringUtilReplaceValues)
				).put(
					"useESM", jsonObject.getBoolean("useESM", false)
				).buildString());

			clientExtensionEntryIdsStringUtilReplaceValues.put(
				"CLIENT_EXTENSION_ENTRY_ID:" +
					jsonObject.getString("clientExtensionEntryKey"),
				_replace(
					jsonObject.getString("widgetName"),
					StringBundler.concat(
						"[$CLIENT_EXTENSION_ENTRY_ID:",
						jsonObject.getString("clientExtensionEntryKey"), "$]"),
					serviceContext.getCompanyId() + StringPool.UNDERLINE +
						jsonObject.getString("externalReferenceCode")));
		}

		return clientExtensionEntryIdsStringUtilReplaceValues;
	}

	private Map<String, String> _addOrUpdateDDMStructures(
			ServiceContext serviceContext)
		throws Exception {

		Map<String, String> ddmStructuresIdsStringUtilReplaceValues =
			new HashMap<>();

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/ddm-structures");

		if (SetUtil.isEmpty(resourcePaths)) {
			return ddmStructuresIdsStringUtilReplaceValues;
		}

		for (String resourcePath : resourcePaths) {
			_defaultDDMStructureHelper.addOrUpdateDDMStructures(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class), _classLoader,
				resourcePath, serviceContext);
		}

		List<DDMStructure> ddmStructures =
			_ddmStructureLocalService.getStructures(
				serviceContext.getScopeGroupId());

		for (DDMStructure ddmStructure : ddmStructures) {
			ddmStructuresIdsStringUtilReplaceValues.put(
				"DDM_STRUCTURE_ID:" + ddmStructure.getStructureKey(),
				String.valueOf(ddmStructure.getStructureId()));
		}

		return ddmStructuresIdsStringUtilReplaceValues;
	}

	private void _addOrUpdateDDMTemplates(
			DDMStructureLocalService ddmStructureLocalService,
			ServiceContext serviceContext)
		throws Exception {

		Enumeration<URL> enumeration = _bundle.findEntries(
			"/site-initializer/ddm-templates", "ddm-template.json", true);

		if (enumeration == null) {
			return;
		}

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				StringUtil.read(url.openStream()));

			long resourceClassNameId = _portal.getClassNameId(
				jsonObject.getString(
					"resourceClassName", JournalArticle.class.getName()));

			long ddmStructureId = 0;

			String ddmStructureKey = jsonObject.getString("ddmStructureKey");

			if (Validator.isNotNull(ddmStructureKey)) {
				DDMStructure ddmStructure =
					ddmStructureLocalService.fetchStructure(
						serviceContext.getScopeGroupId(), resourceClassNameId,
						ddmStructureKey);

				ddmStructureId = ddmStructure.getStructureId();
			}

			DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
				serviceContext.getScopeGroupId(),
				_portal.getClassNameId(
					jsonObject.getString(
						"className", DDMStructure.class.getName())),
				jsonObject.getString("ddmTemplateKey"));

			if (ddmTemplate == null) {
				ddmTemplate = _ddmTemplateLocalService.addTemplate(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(),
					_portal.getClassNameId(
						jsonObject.getString(
							"className", DDMStructure.class.getName())),
					ddmStructureId, resourceClassNameId,
					jsonObject.getString("ddmTemplateKey"),
					HashMapBuilder.put(
						LocaleUtil.getSiteDefault(),
						jsonObject.getString("name")
					).build(),
					null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
					TemplateConstants.LANG_TYPE_FTL,
					SiteInitializerUtil.read(_bundle, "ddm-template.ftl", url),
					false, false, null, null, serviceContext);

				if (!Objects.equals(
						jsonObject.getString("className"),
						TemplateEntry.class.getName())) {

					continue;
				}

				_templateEntryLocalService.addTemplateEntry(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(),
					ddmTemplate.getTemplateId(),
					jsonObject.getString("infoItemClassName"),
					jsonObject.getString("infoItemKey"), serviceContext);
			}
			else {
				_ddmTemplateLocalService.updateTemplate(
					serviceContext.getUserId(), ddmTemplate.getTemplateId(),
					ddmStructureId,
					HashMapBuilder.put(
						LocaleUtil.getSiteDefault(),
						jsonObject.getString("name")
					).build(),
					null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
					TemplateConstants.LANG_TYPE_FTL,
					SiteInitializerUtil.read(_bundle, "ddm-template.ftl", url),
					false, false, null, null, serviceContext);
			}
		}
	}

	private Long _addOrUpdateDocumentFolder(
			Long documentFolderId, long groupId, String resourcePath,
			ServiceContext serviceContext)
		throws Exception {

		DocumentFolderResource.Builder documentFolderResourceBuilder =
			_documentFolderResourceFactory.create();

		DocumentFolderResource documentFolderResource =
			documentFolderResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		DocumentFolder documentFolder = null;

		resourcePath = resourcePath.substring(0, resourcePath.length() - 1);

		String json = SiteInitializerUtil.read(
			resourcePath + ".metadata.json", _servletContext);

		if (json != null) {
			documentFolder = DocumentFolder.toDTO(json);
		}
		else {
			documentFolder = DocumentFolder.toDTO(
				JSONUtil.put(
					"name", FileUtil.getShortFileName(resourcePath)
				).put(
					"viewableBy", "Anyone"
				).toString());
		}

		Page<DocumentFolder> documentFoldersPage =
			documentFolderResource.getSiteDocumentFoldersPage(
				groupId, true, null, null,
				documentFolderResource.toFilter(
					StringBundler.concat(
						"name eq '", documentFolder.getName(), "'")),
				null, null);

		DocumentFolder existingDocumentFolder =
			documentFoldersPage.fetchFirstItem();

		if (existingDocumentFolder == null) {
			if (documentFolderId != null) {
				documentFolder =
					documentFolderResource.postDocumentFolderDocumentFolder(
						documentFolderId, documentFolder);
			}
			else {
				documentFolder = documentFolderResource.postSiteDocumentFolder(
					groupId, documentFolder);
			}
		}
		else {
			documentFolder = documentFolderResource.putDocumentFolder(
				existingDocumentFolder.getId(), documentFolder);
		}

		return documentFolder.getId();
	}

	private Map<String, String> _addOrUpdateDocuments(
			Long documentFolderId, long groupId, String parentResourcePath,
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		Map<String, String> documentsStringUtilReplaceValues = new HashMap<>();

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

		if (SetUtil.isEmpty(resourcePaths)) {
			return documentsStringUtilReplaceValues;
		}

		DocumentResource.Builder documentResourceBuilder =
			_documentResourceFactory.create();

		DocumentResource documentResource = documentResourceBuilder.user(
			serviceContext.fetchUser()
		).build();

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith("/")) {
				documentsStringUtilReplaceValues.putAll(
					_addOrUpdateDocuments(
						_addOrUpdateDocumentFolder(
							documentFolderId, groupId, resourcePath,
							serviceContext),
						groupId, resourcePath, serviceContext,
						siteNavigationMenuItemSettingsBuilder));

				continue;
			}

			if (resourcePath.endsWith(".gitkeep") ||
				resourcePath.endsWith(".metadata.json")) {

				continue;
			}

			String fileName = FileUtil.getShortFileName(resourcePath);

			URL url = _servletContext.getResource(resourcePath);

			URLConnection urlConnection = url.openConnection();

			Map<String, String> values = new HashMap<>();

			String json = SiteInitializerUtil.read(
				resourcePath + ".metadata.json", _servletContext);

			if (json != null) {
				values = Collections.singletonMap("document", json);
			}
			else {
				values = Collections.singletonMap(
					"document",
					JSONUtil.put(
						"viewableBy", "Anyone"
					).toString());
			}

			Document document = null;

			if (documentFolderId != null) {
				Page<Document> documentsPage =
					documentResource.getDocumentFolderDocumentsPage(
						documentFolderId, false, null, null,
						documentResource.toFilter(
							StringBundler.concat("title eq '", fileName, "'")),
						null, null);

				Document existingDocument = documentsPage.fetchFirstItem();

				if (existingDocument == null) {
					document = documentResource.postDocumentFolderDocument(
						documentFolderId,
						MultipartBody.of(
							Collections.singletonMap(
								"file",
								new BinaryFile(
									MimeTypesUtil.getContentType(fileName),
									fileName, urlConnection.getInputStream(),
									urlConnection.getContentLength())),
							__ -> _objectMapper, values));
				}
				else {
					document = documentResource.putDocument(
						existingDocument.getId(),
						MultipartBody.of(
							Collections.singletonMap(
								"file",
								new BinaryFile(
									MimeTypesUtil.getContentType(fileName),
									fileName, urlConnection.getInputStream(),
									urlConnection.getContentLength())),
							__ -> _objectMapper, values));
				}
			}
			else {
				Page<Document> documentsPage =
					documentResource.getSiteDocumentsPage(
						groupId, false, null, null,
						documentResource.toFilter(
							StringBundler.concat("title eq '", fileName, "'")),
						null, null);

				Document existingDocument = documentsPage.fetchFirstItem();

				if (existingDocument == null) {
					document = documentResource.postSiteDocument(
						groupId,
						MultipartBody.of(
							Collections.singletonMap(
								"file",
								new BinaryFile(
									MimeTypesUtil.getContentType(fileName),
									fileName, urlConnection.getInputStream(),
									urlConnection.getContentLength())),
							__ -> _objectMapper, values));
				}
				else {
					document = documentResource.putDocument(
						existingDocument.getId(),
						MultipartBody.of(
							Collections.singletonMap(
								"file",
								new BinaryFile(
									MimeTypesUtil.getContentType(fileName),
									fileName, urlConnection.getInputStream(),
									urlConnection.getContentLength())),
							__ -> _objectMapper, values));
				}
			}

			String key = resourcePath;

			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				document.getId());

			documentsStringUtilReplaceValues.put(
				"DOCUMENT_FILE_ENTRY_ID:" + key,
				String.valueOf(fileEntry.getFileEntryId()));

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				_jsonFactory.looseSerialize(fileEntry));

			jsonObject.put("alt", StringPool.BLANK);

			documentsStringUtilReplaceValues.put(
				"DOCUMENT_JSON:" + key, jsonObject.toString());

			documentsStringUtilReplaceValues.put(
				"DOCUMENT_URL:" + key,
				_dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false));

			long fileEntryTypeId = 0;

			if (fileEntry.getModel() instanceof DLFileEntry) {
				DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

				DLFileEntryType dlFileEntryType =
					dlFileEntry.getDLFileEntryType();

				fileEntryTypeId = dlFileEntryType.getFileEntryTypeId();
			}

			String fileEntryTypeIdString = String.valueOf(fileEntryTypeId);

			siteNavigationMenuItemSettingsBuilder.put(
				key,
				new SiteNavigationMenuItemSetting() {
					{
						className = FileEntry.class.getName();
						classPK = String.valueOf(fileEntry.getFileEntryId());
						classTypeId = fileEntryTypeIdString;
						title = fileEntry.getTitle();
						type = ResourceActionsUtil.getModelResource(
							serviceContext.getLocale(),
							FileEntry.class.getName());
					}
				});
		}

		return documentsStringUtilReplaceValues;
	}

	private Map<String, String> _addOrUpdateDocuments(
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(
			serviceContext.getCompanyId());

		return HashMapBuilder.putAll(
			_addOrUpdateDocuments(
				null, group.getGroupId(), "/site-initializer/documents/company",
				serviceContext, siteNavigationMenuItemSettingsBuilder)
		).putAll(
			_addOrUpdateDocuments(
				null, serviceContext.getScopeGroupId(),
				"/site-initializer/documents/group", serviceContext,
				siteNavigationMenuItemSettingsBuilder)
		).build();
	}

	private void _addOrUpdateExpandoColumns(ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/expando-columns.json", _servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			ExpandoBridge expandoBridge =
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					serviceContext.getCompanyId(),
					jsonObject.getString("modelResource"));

			if (expandoBridge == null) {
				continue;
			}

			if (expandoBridge.getAttribute(jsonObject.getString("name")) !=
					null) {

				expandoBridge.setAttributeDefault(
					jsonObject.getString("name"),
					_getExpandoAttributeValue(jsonObject));
			}
			else {
				expandoBridge.addAttribute(
					jsonObject.getString("name"), jsonObject.getInt("dataType"),
					_getExpandoAttributeValue(jsonObject));
			}

			if (jsonObject.has("properties")) {
				UnicodeProperties unicodeProperties = new UnicodeProperties(
					true);

				JSONObject propertiesJSONObject = jsonObject.getJSONObject(
					"properties");

				Map<String, Object> map = propertiesJSONObject.toMap();

				for (Map.Entry<String, Object> entry : map.entrySet()) {
					unicodeProperties.setProperty(
						TextFormatter.format(entry.getKey(), TextFormatter.K),
						String.valueOf(entry.getValue()));
				}

				expandoBridge.setAttributeProperties(
					jsonObject.getString("name"), unicodeProperties);
			}
		}
	}

	private void _addOrUpdateJournalArticles(
			DDMStructureLocalService ddmStructureLocalService,
			DDMTemplateLocalService ddmTemplateLocalService,
			Long documentFolderId,
			Map<String, String> documentsStringUtilReplaceValues,
			String parentResourcePath, ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			parentResourcePath = resourcePath.substring(
				0, resourcePath.length() - 1);

			if (resourcePath.endsWith("/")) {
				_addOrUpdateJournalArticles(
					ddmStructureLocalService, ddmTemplateLocalService,
					_addOrUpdateStructuredContentFolders(
						documentFolderId, parentResourcePath, serviceContext),
					documentsStringUtilReplaceValues, resourcePath,
					serviceContext, siteNavigationMenuItemSettingsBuilder);

				continue;
			}

			if (resourcePath.endsWith(".gitkeep") ||
				resourcePath.endsWith(".metadata.json") ||
				resourcePath.endsWith(".xml")) {

				continue;
			}

			long journalFolderId =
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			if (documentFolderId != null) {
				journalFolderId = documentFolderId;
			}

			String json = SiteInitializerUtil.read(
				resourcePath, _servletContext);

			JSONObject jsonObject = _jsonFactory.createJSONObject(json);

			Map<Locale, String> titleMap = Collections.singletonMap(
				LocaleUtil.getSiteDefault(), jsonObject.getString("name"));

			String ddmStructureKey = jsonObject.getString("ddmStructureKey");

			DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
				serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class), ddmStructureKey,
				true);

			String ddmTemplateKey = jsonObject.getString("ddmTemplateKey");

			ddmTemplateLocalService.getTemplate(
				serviceContext.getScopeGroupId(),
				_portal.getClassNameId(DDMStructure.class), ddmTemplateKey);

			Calendar calendar = CalendarFactoryUtil.getCalendar(
				serviceContext.getTimeZone());

			serviceContext.setAssetCategoryIds(
				_getAssetCategoryIds(
					serviceContext.getScopeGroupId(),
					JSONUtil.toStringArray(
						jsonObject.getJSONArray("assetCategoryERCs"))));
			serviceContext.setAssetTagNames(
				JSONUtil.toStringArray(
					jsonObject.getJSONArray("assetTagNames")));

			JournalArticle journalArticle =
				_journalArticleLocalService.fetchArticle(
					serviceContext.getScopeGroupId(),
					jsonObject.getString("articleId"));

			if (journalArticle == null) {
				journalArticle = _journalArticleLocalService.addArticle(
					null, serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), journalFolderId,
					JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0,
					jsonObject.getString("articleId"), false, 1, titleMap, null,
					titleMap,
					_replace(
						SiteInitializerUtil.read(
							_replace(resourcePath, ".json", ".xml"),
							_servletContext),
						documentsStringUtilReplaceValues),
					ddmStructure.getStructureId(), ddmTemplateKey, null,
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH),
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0, 0,
					0, 0, true, true, false, null, null, null, null,
					serviceContext);
			}
			else {
				journalArticle = _journalArticleLocalService.updateArticle(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), journalFolderId,
					jsonObject.getString("articleId"),
					journalArticle.getVersion(), titleMap, null, titleMap,
					_replace(
						SiteInitializerUtil.read(
							_replace(resourcePath, ".json", ".xml"),
							_servletContext),
						documentsStringUtilReplaceValues),
					ddmTemplateKey, null, calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH),
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0, 0,
					0, 0, true, true, false, null, null, null, null,
					serviceContext);
			}

			JournalArticle finalJournalArticle = journalArticle;

			serviceContext.setAssetCategoryIds(null);
			serviceContext.setAssetTagNames(null);

			siteNavigationMenuItemSettingsBuilder.put(
				resourcePath,
				new SiteNavigationMenuItemSetting() {
					{
						className = JournalArticle.class.getName();
						classPK = String.valueOf(
							finalJournalArticle.getResourcePrimKey());
						classTypeId = String.valueOf(
							ddmStructure.getStructureId());
						title = finalJournalArticle.getTitle(
							serviceContext.getLocale());
						type = ResourceActionsUtil.getModelResource(
							serviceContext.getLocale(),
							JournalArticle.class.getName());
					}
				});
		}
	}

	private void _addOrUpdateJournalArticles(
			DDMStructureLocalService ddmStructureLocalService,
			DDMTemplateLocalService ddmTemplateLocalService,
			Map<String, String> documentsStringUtilReplaceValues,
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		_addOrUpdateJournalArticles(
			ddmStructureLocalService, ddmTemplateLocalService, null,
			documentsStringUtilReplaceValues,
			"/site-initializer/journal-articles", serviceContext,
			siteNavigationMenuItemSettingsBuilder);
	}

	private KnowledgeBaseArticle _addOrUpdateKnowledgeBaseArticle(
			boolean folder, JSONObject jsonObject,
			long parentKnowledgeBaseObjectId, ServiceContext serviceContext)
		throws Exception {

		KnowledgeBaseArticleResource.Builder
			knowledgeBaseArticleResourceBuilder =
				_knowledgeBaseArticleResourceFactory.create();

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			knowledgeBaseArticleResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		KnowledgeBaseArticle knowledgeBaseArticle = KnowledgeBaseArticle.toDTO(
			jsonObject.toString());

		if (!folder) {
			knowledgeBaseArticle.setParentKnowledgeBaseArticleId(
				parentKnowledgeBaseObjectId);
		}
		else {
			knowledgeBaseArticle.setParentKnowledgeBaseFolderId(
				parentKnowledgeBaseObjectId);
		}

		return knowledgeBaseArticleResource.
			putSiteKnowledgeBaseArticleByExternalReferenceCode(
				serviceContext.getScopeGroupId(),
				knowledgeBaseArticle.getExternalReferenceCode(),
				knowledgeBaseArticle);
	}

	private void _addOrUpdateKnowledgeBaseArticle(
			boolean folder, JSONObject jsonObject,
			long parentKnowledgeBaseObjectId, String resourcePath,
			ServiceContext serviceContext)
		throws Exception {

		KnowledgeBaseArticle knowledgeBaseArticle =
			_addOrUpdateKnowledgeBaseArticle(
				folder, jsonObject, parentKnowledgeBaseObjectId,
				serviceContext);

		_addOrKnowledgeBaseObjects(
			false, knowledgeBaseArticle.getId(), resourcePath, serviceContext);
	}

	private void _addOrUpdateKnowledgeBaseArticles(
			ServiceContext serviceContext)
		throws Exception {

		_addOrKnowledgeBaseObjects(
			true, 0, "/site-initializer/knowledge-base-articles",
			serviceContext);
	}

	private KnowledgeBaseFolder _addOrUpdateKnowledgeBaseFolder(
			JSONObject jsonObject, long parentKnowledgeBaseObjectId,
			ServiceContext serviceContext)
		throws Exception {

		KnowledgeBaseFolderResource.Builder knowledgeBaseFolderResourceBuilder =
			_knowledgeBaseFolderResourceFactory.create();

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			knowledgeBaseFolderResourceBuilder.httpServletRequest(
				serviceContext.getRequest()
			).user(
				serviceContext.fetchUser()
			).build();

		KnowledgeBaseFolder knowledgeBaseFolder = KnowledgeBaseFolder.toDTO(
			jsonObject.toString());

		knowledgeBaseFolder.setParentKnowledgeBaseFolderId(
			parentKnowledgeBaseObjectId);

		return knowledgeBaseFolderResource.
			putSiteKnowledgeBaseFolderByExternalReferenceCode(
				serviceContext.getScopeGroupId(),
				knowledgeBaseFolder.getExternalReferenceCode(),
				knowledgeBaseFolder);
	}

	private void _addOrUpdateKnowledgeBaseFolder(
			JSONObject jsonObject, long parentKnowledgeBaseObjectId,
			String resourcePath, ServiceContext serviceContext)
		throws Exception {

		KnowledgeBaseFolder knowledgeBaseFolder =
			_addOrUpdateKnowledgeBaseFolder(
				jsonObject, parentKnowledgeBaseObjectId, serviceContext);

		_addOrKnowledgeBaseObjects(
			true, knowledgeBaseFolder.getId(), resourcePath, serviceContext);
	}

	private Map<String, Layout> _addOrUpdateLayout(
			long parentLayoutId, String parentResourcePath,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject pageJSONObject = _jsonFactory.createJSONObject(
			SiteInitializerUtil.read(
				parentResourcePath + "page.json", _servletContext));

		Map<Locale, String> nameMap = new HashMap<>(
			SiteInitializerUtil.toMap(pageJSONObject.getString("name_i18n")));

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(
			serviceContext.getScopeGroupId());

		if (!nameMap.containsKey(siteDefaultLocale)) {
			nameMap.put(siteDefaultLocale, pageJSONObject.getString("name"));
		}

		String type = StringUtil.toLowerCase(pageJSONObject.getString("type"));

		if (Objects.equals(type, "url")) {
			type = LayoutConstants.TYPE_URL;
		}
		else if (Objects.equals(type, "widget")) {
			type = LayoutConstants.TYPE_PORTLET;
		}

		Map<Locale, String> friendlyURLMap = new HashMap<>(
			SiteInitializerUtil.toMap(
				pageJSONObject.getString("friendlyURL_i18n")));

		if (!friendlyURLMap.containsKey(siteDefaultLocale)) {
			friendlyURLMap.put(
				siteDefaultLocale, pageJSONObject.getString("friendlyURL"));
		}

		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		JSONArray typeSettingsJSONArray = pageJSONObject.getJSONArray(
			"typeSettings");

		if (typeSettingsJSONArray != null) {
			for (int i = 0; i < typeSettingsJSONArray.length(); i++) {
				JSONObject propertyJSONObject =
					typeSettingsJSONArray.getJSONObject(i);

				unicodeProperties.put(
					propertyJSONObject.getString("key"),
					propertyJSONObject.getString("value"));
			}
		}

		Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
			serviceContext.getScopeGroupId(),
			pageJSONObject.getBoolean("private"),
			pageJSONObject.getString("friendlyURL"));

		if (layout != null) {
			_layoutLocalService.deleteLayout(layout);
		}

		layout = _layoutLocalService.addLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			pageJSONObject.getBoolean("private"), parentLayoutId, nameMap,
			SiteInitializerUtil.toMap(pageJSONObject.getString("title_i18n")),
			SiteInitializerUtil.toMap(
				pageJSONObject.getString("description_i18n")),
			SiteInitializerUtil.toMap(
				pageJSONObject.getString("keywords_i18n")),
			SiteInitializerUtil.toMap(pageJSONObject.getString("robots_i18n")),
			type, unicodeProperties.toString(),
			pageJSONObject.getBoolean("hidden"),
			pageJSONObject.getBoolean("system"), friendlyURLMap,
			serviceContext);

		_setResourcePermissions(
			layout.getCompanyId(), layout.getModelClassName(),
			pageJSONObject.getJSONArray("permissions"),
			String.valueOf(layout.getPlid()));

		if (pageJSONObject.has("priority")) {
			layout = _layoutLocalService.updatePriority(
				layout.getPlid(), pageJSONObject.getInt("priority"));
		}

		Map<String, Layout> layouts = HashMapBuilder.<String, Layout>put(
			parentResourcePath, layout
		).build();

		String layoutTemplateId = StringUtil.toLowerCase(
			pageJSONObject.getString("layoutTemplateId"));

		if (Validator.isNotNull(layoutTemplateId)) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlet.setLayoutTemplateId(0, layoutTemplateId, false);
		}

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

		if (SetUtil.isEmpty(resourcePaths)) {
			return layouts;
		}

		Set<String> sortedResourcePaths = new TreeSet<>(
			new NaturalOrderStringComparator());

		sortedResourcePaths.addAll(resourcePaths);

		resourcePaths = sortedResourcePaths;

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith("/")) {
				layouts.putAll(
					_addOrUpdateLayout(
						layout.getLayoutId(), resourcePath, serviceContext));
			}
		}

		return layouts;
	}

	private Map<String, Layout> _addOrUpdateLayouts(
			ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/layouts");

		if (SetUtil.isEmpty(resourcePaths)) {
			return new HashMap<>();
		}

		Map<String, Layout> layouts = new HashMap<>();

		Set<String> sortedResourcePaths = new TreeSet<>(
			new NaturalOrderStringComparator());

		sortedResourcePaths.addAll(resourcePaths);

		resourcePaths = sortedResourcePaths;

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith("/")) {
				layouts.putAll(
					_addOrUpdateLayout(
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, resourcePath,
						serviceContext));
			}
		}

		return layouts;
	}

	private Map<String, String> _addOrUpdateListTypeDefinitions(
			ServiceContext serviceContext)
		throws Exception {

		Map<String, String> listTypeDefinitionIdsStringUtilReplaceValues =
			new HashMap<>();

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/list-type-definitions");

		if (SetUtil.isEmpty(resourcePaths)) {
			return listTypeDefinitionIdsStringUtilReplaceValues;
		}

		ListTypeDefinitionResource.Builder listTypeDefinitionResourceBuilder =
			_listTypeDefinitionResourceFactory.create();

		ListTypeDefinitionResource listTypeDefinitionResource =
			listTypeDefinitionResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith(".list-type-entries.json")) {
				continue;
			}

			String json = SiteInitializerUtil.read(
				resourcePath, _servletContext);

			ListTypeDefinition listTypeDefinition = ListTypeDefinition.toDTO(
				json);

			if (listTypeDefinition == null) {
				_log.error(
					"Unable to transform list type definition from JSON: " +
						json);

				continue;
			}

			com.liferay.list.type.model.ListTypeDefinition
				serviceBuilderListTypeDefinition =
					_listTypeDefinitionLocalService.
						fetchListTypeDefinitionByExternalReferenceCode(
							listTypeDefinition.getExternalReferenceCode(),
							serviceContext.getCompanyId());

			if (serviceBuilderListTypeDefinition == null) {
				listTypeDefinition =
					listTypeDefinitionResource.postListTypeDefinition(
						listTypeDefinition);
			}
			else {
				listTypeDefinition =
					listTypeDefinitionResource.patchListTypeDefinition(
						serviceBuilderListTypeDefinition.
							getListTypeDefinitionId(),
						listTypeDefinition);
			}

			listTypeDefinitionIdsStringUtilReplaceValues.put(
				"LIST_TYPE_DEFINITION_ID:" + listTypeDefinition.getName(),
				String.valueOf(listTypeDefinition.getId()));

			String listTypeEntriesJSON = SiteInitializerUtil.read(
				_replace(resourcePath, ".json", ".list-type-entries.json"),
				_servletContext);

			if (listTypeEntriesJSON == null) {
				continue;
			}

			JSONArray jsonArray = _jsonFactory.createJSONArray(
				listTypeEntriesJSON);

			ListTypeEntryResource.Builder listTypeEntryResourceBuilder =
				_listTypeEntryResourceFactory.create();

			ListTypeEntryResource listTypeEntryResource =
				listTypeEntryResourceBuilder.user(
					serviceContext.fetchUser()
				).build();

			for (int i = 0; i < jsonArray.length(); i++) {
				ListTypeEntry listTypeEntry = ListTypeEntry.toDTO(
					String.valueOf(jsonArray.getJSONObject(i)));

				com.liferay.list.type.model.ListTypeEntry
					serviceBuilderListTypeEntry =
						_listTypeEntryLocalService.fetchListTypeEntry(
							listTypeDefinition.getId(), listTypeEntry.getKey());

				if (serviceBuilderListTypeEntry == null) {
					listTypeEntryResource.postListTypeDefinitionListTypeEntry(
						listTypeDefinition.getId(), listTypeEntry);
				}
				else {
					listTypeEntryResource.putListTypeEntry(
						serviceBuilderListTypeEntry.getListTypeEntryId(),
						listTypeEntry);
				}
			}
		}

		return listTypeDefinitionIdsStringUtilReplaceValues;
	}

	private void _addOrUpdateNotificationTemplate(
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String>
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
			String resourcePath, ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			resourcePath + "notification-template.json", _servletContext);

		if (json == null) {
			return;
		}

		JSONObject bodyJSONObject = _jsonFactory.createJSONObject();

		Enumeration<URL> enumeration = _bundle.findEntries(
			resourcePath, "*.html", false);

		if (enumeration == null) {
			return;
		}

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			bodyJSONObject.put(
				FileUtil.getShortFileName(
					FileUtil.stripExtension(url.getPath())),
				_replace(
					_replace(StringUtil.read(url.openStream()), serviceContext),
					documentsStringUtilReplaceValues));
		}

		JSONObject notificationTemplateJSONObject =
			_jsonFactory.createJSONObject(json);

		notificationTemplateJSONObject.put("body", bodyJSONObject);

		NotificationTemplate notificationTemplate = NotificationTemplate.toDTO(
			notificationTemplateJSONObject.toString());

		NotificationTemplateResource.Builder
			notificationTemplateResourceBuilder =
				_notificationTemplateResourceFactory.create();

		NotificationTemplateResource notificationTemplateResource =
			notificationTemplateResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		Page<NotificationTemplate> notificationTemplatesPage =
			notificationTemplateResource.getNotificationTemplatesPage(
				null, null,
				notificationTemplateResource.toFilter(
					StringBundler.concat(
						"name eq '", notificationTemplate.getName(), "'")),
				null, null);

		NotificationTemplate existingNotificationTemplate =
			notificationTemplatesPage.fetchFirstItem();

		if (existingNotificationTemplate == null) {
			notificationTemplate =
				notificationTemplateResource.postNotificationTemplate(
					notificationTemplate);
		}
		else {
			notificationTemplate =
				notificationTemplateResource.putNotificationTemplate(
					existingNotificationTemplate.getId(), notificationTemplate);
		}

		json = SiteInitializerUtil.read(
			resourcePath + "notification-template.object-actions.json",
			_servletContext);

		if (json == null) {
			return;
		}

		json = _replace(
			json, objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues);

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		Map<String, Long> parametersMap = HashMapBuilder.put(
			"notificationTemplateId", notificationTemplate.getId()
		).build();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			_objectActionLocalService.addOrUpdateObjectAction(
				jsonObject.getString("externalReferenceCode"), 0,
				serviceContext.getUserId(),
				jsonObject.getLong("objectDefinitionId"),
				jsonObject.getBoolean("active"),
				jsonObject.getString("conditionExpression"),
				jsonObject.getString("description"),
				SiteInitializerUtil.toMap(jsonObject.getString("errorMessage")),
				SiteInitializerUtil.toMap(jsonObject.getString("label")),
				jsonObject.getString("name"),
				jsonObject.getString("objectActionExecutorKey"),
				jsonObject.getString("objectActionTriggerKey"),
				ObjectActionUtil.toParametersUnicodeProperties(parametersMap));
		}
	}

	private void _addOrUpdateNotificationTemplates(
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String>
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/notification-templates");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			_addOrUpdateNotificationTemplate(
				documentsStringUtilReplaceValues,
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
				resourcePath, serviceContext);
		}
	}

	private Map<String, String> _addOrUpdateObjectEntries(
			Map<String, String> documentsStringUtilReplaceValues,
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/object-entries");

		if (SetUtil.isEmpty(resourcePaths)) {
			return null;
		}

		Set<String> sortedResourcePaths = new TreeSet<>(
			new NaturalOrderStringComparator());

		sortedResourcePaths.addAll(resourcePaths);

		resourcePaths = sortedResourcePaths;

		Map<String, String> objectEntryIdsStringUtilReplaceValues =
			new HashMap<>();

		for (String resourcePath : resourcePaths) {
			String json = SiteInitializerUtil.read(
				resourcePath, _servletContext);

			if (json == null) {
				continue;
			}

			json = _replace(
				json, documentsStringUtilReplaceValues,
				objectEntryIdsStringUtilReplaceValues);

			JSONObject jsonObject = _jsonFactory.createJSONObject(json);

			com.liferay.object.model.ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					serviceContext.getCompanyId(),
					"C_" + jsonObject.getString("objectDefinitionName"));

			if (objectDefinition == null) {
				continue;
			}

			JSONArray jsonArray = jsonObject.getJSONArray("object-entries");

			if (JSONUtil.isEmpty(jsonArray)) {
				continue;
			}

			DefaultDTOConverterContext defaultDTOConverterContext =
				new DefaultDTOConverterContext(
					false, null, null, null, null, LocaleUtil.getSiteDefault(),
					null, serviceContext.fetchUser());

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject objectEntryJSONObject = jsonArray.getJSONObject(i);

				String externalReferenceCode = objectEntryJSONObject.getString(
					"externalReferenceCode");

				ObjectEntry objectEntry = ObjectEntry.toDTO(
					JSONUtil.toString(objectEntryJSONObject));

				objectEntry = _objectEntryManager.addOrUpdateObjectEntry(
					serviceContext.getCompanyId(), defaultDTOConverterContext,
					externalReferenceCode, objectDefinition, objectEntry,
					String.valueOf(serviceContext.getScopeGroupId()));

				if (Validator.isNotNull(externalReferenceCode)) {
					objectEntryIdsStringUtilReplaceValues.put(
						StringBundler.concat(
							objectDefinition.getShortName(), "#",
							externalReferenceCode),
						String.valueOf(objectEntry.getId()));
				}

				String objectEntrySiteInitializerKey =
					objectEntryJSONObject.getString(
						"objectEntrySiteInitializerKey");

				if (Validator.isNull(objectEntrySiteInitializerKey)) {
					continue;
				}

				com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
					_objectEntryLocalService.getObjectEntry(
						objectEntry.getId());

				siteNavigationMenuItemSettingsBuilder.put(
					objectEntrySiteInitializerKey,
					new SiteNavigationMenuItemSetting() {
						{
							className =
								serviceBuilderObjectEntry.getModelClassName();
							classPK = String.valueOf(
								serviceBuilderObjectEntry.getObjectEntryId());
							title = StringBundler.concat(
								objectDefinition.getName(), StringPool.SPACE,
								serviceBuilderObjectEntry.getObjectEntryId());
						}
					});
			}
		}

		return objectEntryIdsStringUtilReplaceValues;
	}

	private void _addOrUpdateObjectFields(
			Map<String, String> listTypeDefinitionIdsStringUtilReplaceValues,
			Map<String, String> objectDefinitionIdsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/object-fields");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		ObjectFieldResource.Builder objectFieldResourceBuilder =
			_objectFieldResourceFactory.create();

		ObjectFieldResource objectFieldResource =
			objectFieldResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		for (String resourcePath : resourcePaths) {
			String json = SiteInitializerUtil.read(
				resourcePath, _servletContext);

			json = _replace(
				json, listTypeDefinitionIdsStringUtilReplaceValues,
				objectDefinitionIdsStringUtilReplaceValues);

			JSONObject jsonObject = _jsonFactory.createJSONObject(json);

			JSONArray jsonArray = jsonObject.getJSONArray("object-fields");

			if (JSONUtil.isEmpty(jsonArray)) {
				continue;
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject objectFieldJSONObject = jsonArray.getJSONObject(i);

				ObjectField objectField = ObjectField.toDTO(
					JSONUtil.toString(objectFieldJSONObject));

				if (objectField == null) {
					_log.error(
						"Unable to transform object field from JSON: " + json);

					continue;
				}

				com.liferay.object.model.ObjectField existingObjectField =
					_objectFieldLocalService.fetchObjectField(
						jsonObject.getLong("objectDefinitionId"),
						objectField.getName());

				if (existingObjectField == null) {
					objectFieldResource.postObjectDefinitionObjectField(
						jsonObject.getLong("objectDefinitionId"), objectField);
				}
				else {
					objectFieldResource.putObjectField(
						existingObjectField.getObjectFieldId(), objectField);
				}
			}
		}
	}

	private void _addOrUpdateObjectRelationships(
			Map<String, String> objectDefinitionIdsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/object-relationships");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		ObjectRelationshipResource.Builder objectRelationshipResourceBuilder =
			_objectRelationshipResourceFactory.create();

		ObjectRelationshipResource objectRelationshipResource =
			objectRelationshipResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		for (String resourcePath : resourcePaths) {
			String json = SiteInitializerUtil.read(
				resourcePath, _servletContext);

			json = _replace(json, objectDefinitionIdsStringUtilReplaceValues);

			ObjectRelationship objectRelationship = ObjectRelationship.toDTO(
				json);

			if (objectRelationship == null) {
				_log.error(
					"Unable to transform object relationship from JSON: " +
						json);

				continue;
			}

			com.liferay.object.model.ObjectRelationship
				existingObjectRelationship =
					_objectRelationshipLocalService.
						fetchObjectRelationshipByObjectDefinitionId1(
							objectRelationship.getObjectDefinitionId1(),
							objectRelationship.getName());

			if (existingObjectRelationship == null) {
				objectRelationshipResource.
					postObjectDefinitionObjectRelationship(
						objectRelationship.getObjectDefinitionId1(),
						objectRelationship);
			}
			else {
				objectRelationshipResource.putObjectRelationship(
					existingObjectRelationship.getObjectRelationshipId(),
					objectRelationship);
			}
		}
	}

	private void _addOrUpdateOrganization(
			JSONObject jsonObject, Organization parentOrganization,
			ServiceContext serviceContext)
		throws Exception {

		Organization organization = Organization.toDTO(jsonObject.toString());

		if (organization == null) {
			_log.error(
				"Unable to transform organization from JSON: " + jsonObject);

			return;
		}

		organization.setParentOrganization(parentOrganization);

		OrganizationResource.Builder organizationResourceBuilder =
			_organizationResourceFactory.create();

		OrganizationResource organizationResource =
			organizationResourceBuilder.user(
				serviceContext.fetchUser()
			).httpServletRequest(
				serviceContext.getRequest()
			).build();

		organization =
			organizationResource.putOrganizationByExternalReferenceCode(
				organization.getExternalReferenceCode(), organization);

		JSONArray jsonArray = jsonObject.getJSONArray("childOrganizations");

		if (JSONUtil.isEmpty(jsonArray)) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			_addOrUpdateOrganization(
				jsonArray.getJSONObject(i), organization, serviceContext);
		}
	}

	private void _addOrUpdateOrganizations(ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/organizations.json", _servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			_addOrUpdateOrganization(
				jsonArray.getJSONObject(i), null, serviceContext);
		}
	}

	private void _addOrUpdateResourcePermissions(
			Map<String, String>
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/resource-permissions.json", _servletContext);

		if (json == null) {
			return;
		}

		Map<String, String> layoutPageTemplateEntryReplaceValues =
			new HashMap<>();

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntries(
				serviceContext.getScopeGroupId());

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			layoutPageTemplateEntryReplaceValues.put(
				"LAYOUT_PAGE_TEMPLATE_ENTRY_ID:" +
					layoutPageTemplateEntry.getName(),
				String.valueOf(
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			_replace(
				json, layoutPageTemplateEntryReplaceValues,
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String[] actionIds = ArrayUtil.toStringArray(
				jsonObject.getJSONArray("actionIds"));
			String[] resourceActionIds = TransformUtil.transformToArray(
				_resourceActionLocalService.getResourceActions(
					jsonObject.getString("resourceName")),
				ResourceAction -> ResourceAction.getActionId(), String.class);

			if (!ArrayUtil.containsAll(resourceActionIds, actionIds)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"No resource action found with resourceName ",
							jsonObject.getString("resourceName"),
							" with the actionIds: ",
							ArrayUtil.toString(actionIds, "")));
				}

				continue;
			}

			Role role = _roleLocalService.fetchRole(
				serviceContext.getCompanyId(),
				jsonObject.getString("roleName"));

			if (role == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No role found with name " +
							jsonObject.getString("roleName"));
				}

				continue;
			}

			int scope = jsonObject.getInt("scope");

			if (scope == ResourceConstants.SCOPE_COMPANY) {
				jsonObject.put(
					"primKey", String.valueOf(serviceContext.getCompanyId()));
			}
			else if (scope == ResourceConstants.SCOPE_GROUP) {
				jsonObject.put(
					"primKey",
					String.valueOf(serviceContext.getScopeGroupId()));
			}

			_resourcePermissionLocalService.setResourcePermissions(
				serviceContext.getCompanyId(),
				jsonObject.getString("resourceName"), scope,
				jsonObject.getString("primKey"), role.getRoleId(), actionIds);
		}
	}

	private Map<String, String> _addOrUpdateRoles(ServiceContext serviceContext)
		throws Exception {

		Map<String, String> roleIdsStringUtilReplaceValues = new HashMap<>();

		List<Role> roles = _roleLocalService.getRoles(
			serviceContext.getCompanyId());

		for (Role role : roles) {
			roleIdsStringUtilReplaceValues.put(
				"ROLE_ID:" + role.getName(), String.valueOf(role.getRoleId()));
		}

		String json = SiteInitializerUtil.read(
			"/site-initializer/roles.json", _servletContext);

		if (json == null) {
			return roleIdsStringUtilReplaceValues;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Role role = _roleLocalService.fetchRole(
				serviceContext.getCompanyId(), jsonObject.getString("name"));

			if (role == null) {
				if (jsonObject.getInt("type") == RoleConstants.TYPE_ACCOUNT) {
					com.liferay.account.model.AccountRole accountRole =
						_accountRoleLocalService.addAccountRole(
							serviceContext.getUserId(),
							AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
							jsonObject.getString("name"),
							SiteInitializerUtil.toMap(
								jsonObject.getString("name_i18n")),
							SiteInitializerUtil.toMap(
								jsonObject.getString("description")));

					role = accountRole.getRole();
				}
				else {
					role = _roleLocalService.addRole(
						serviceContext.getUserId(), null, 0,
						jsonObject.getString("name"),
						SiteInitializerUtil.toMap(
							jsonObject.getString("name_i18n")),
						SiteInitializerUtil.toMap(
							jsonObject.getString("description")),
						jsonObject.getInt("type"),
						jsonObject.getString("subtype"), serviceContext);
				}
			}
			else {
				role = _roleLocalService.updateRole(
					role.getRoleId(), jsonObject.getString("name"),
					SiteInitializerUtil.toMap(
						jsonObject.getString("name_i18n")),
					SiteInitializerUtil.toMap(
						jsonObject.getString("description")),
					jsonObject.getString("subtype"), serviceContext);
			}

			roleIdsStringUtilReplaceValues.put(
				"ROLE_ID:" + role.getName(), String.valueOf(role.getRoleId()));

			JSONArray actionsJSONArray = jsonObject.getJSONArray("actions");

			if (JSONUtil.isEmpty(actionsJSONArray) || (role == null)) {
				continue;
			}

			for (int j = 0; j < actionsJSONArray.length(); j++) {
				JSONObject actionsJSONObject = actionsJSONArray.getJSONObject(
					j);

				String resource = actionsJSONObject.getString("resource");
				int scope = actionsJSONObject.getInt("scope");
				String actionId = actionsJSONObject.getString("actionId");

				if (scope == ResourceConstants.SCOPE_COMPANY) {
					_resourcePermissionLocalService.addResourcePermission(
						serviceContext.getCompanyId(), resource, scope,
						String.valueOf(role.getCompanyId()), role.getRoleId(),
						actionId);
				}
				else if (scope == ResourceConstants.SCOPE_GROUP) {
					_resourcePermissionLocalService.removeResourcePermissions(
						serviceContext.getCompanyId(), resource,
						ResourceConstants.SCOPE_GROUP, role.getRoleId(),
						actionId);

					_resourcePermissionLocalService.addResourcePermission(
						serviceContext.getCompanyId(), resource,
						ResourceConstants.SCOPE_GROUP,
						String.valueOf(serviceContext.getScopeGroupId()),
						role.getRoleId(), actionId);
				}
				else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
					_resourcePermissionLocalService.addResourcePermission(
						serviceContext.getCompanyId(), resource,
						ResourceConstants.SCOPE_GROUP_TEMPLATE,
						String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
						role.getRoleId(), actionId);
				}
			}
		}

		return roleIdsStringUtilReplaceValues;
	}

	private void _addOrUpdateSAPEntries(ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/sap-entries.json", _servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
				serviceContext.getCompanyId(), jsonObject.getString("name"));

			if (sapEntry == null) {
				_sapEntryLocalService.addSAPEntry(
					serviceContext.getUserId(),
					StringUtil.merge(
						JSONUtil.toStringArray(
							jsonObject.getJSONArray(
								"allowedServiceSignatures")),
						StringPool.NEW_LINE),
					jsonObject.getBoolean("defaultSAPEntry", true),
					jsonObject.getBoolean("enabled", true),
					jsonObject.getString("name"),
					SiteInitializerUtil.toMap(
						jsonObject.getString("title_i18n")),
					serviceContext);
			}
			else {
				_sapEntryLocalService.updateSAPEntry(
					sapEntry.getSapEntryId(),
					StringUtil.merge(
						JSONUtil.toStringArray(
							jsonObject.getJSONArray(
								"allowedServiceSignatures")),
						StringPool.NEW_LINE),
					jsonObject.getBoolean("defaultSAPEntry", true),
					jsonObject.getBoolean("enabled", true),
					jsonObject.getString("name"),
					SiteInitializerUtil.toMap(
						jsonObject.getString("title_i18n")),
					serviceContext);
			}
		}
	}

	private Map<String, String> _addOrUpdateSegmentsEntries(
			ServiceContext serviceContext)
		throws Exception {

		Map<String, String> segmentsEntriesIdsStringUtilReplaceValues =
			new HashMap<>();

		String json = SiteInitializerUtil.read(
			"/site-initializer/segments-entries.json", _servletContext);

		if (json == null) {
			return segmentsEntriesIdsStringUtilReplaceValues;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			SegmentsEntry segmentsEntry =
				_segmentsEntryLocalService.fetchSegmentsEntry(
					serviceContext.getScopeGroupId(),
					jsonObject.getString("segmentsEntryKey"), true);

			if (segmentsEntry == null) {
				segmentsEntry = _segmentsEntryLocalService.addSegmentsEntry(
					jsonObject.getString("segmentsEntryKey"),
					SiteInitializerUtil.toMap(
						jsonObject.getString("name_i18n")),
					null, jsonObject.getBoolean("active", true),
					jsonObject.getString("criteria"),
					jsonObject.getString("type"), serviceContext);
			}
			else {
				segmentsEntry = _segmentsEntryLocalService.updateSegmentsEntry(
					segmentsEntry.getSegmentsEntryId(),
					jsonObject.getString("segmentsEntryKey"),
					SiteInitializerUtil.toMap(
						jsonObject.getString("name_i18n")),
					null, jsonObject.getBoolean("active", true),
					jsonObject.getString("criteria"), serviceContext);
			}

			segmentsEntriesIdsStringUtilReplaceValues.put(
				"SEGMENTS_ENTRY_ID:" + segmentsEntry.getSegmentsEntryKey(),
				String.valueOf(segmentsEntry.getSegmentsEntryId()));
		}

		return segmentsEntriesIdsStringUtilReplaceValues;
	}

	private Long _addOrUpdateStructuredContentFolders(
			Long documentFolderId, String parentResourcePath,
			ServiceContext serviceContext)
		throws Exception {

		StructuredContentFolderResource.Builder
			structuredContentFolderResourceBuilder =
				_structuredContentFolderResourceFactory.create();

		StructuredContentFolderResource structuredContentFolderResource =
			structuredContentFolderResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		String json = SiteInitializerUtil.read(
			parentResourcePath + ".metadata.json", _servletContext);

		if (json == null) {
			json = JSONUtil.put(
				"name", FileUtil.getShortFileName(parentResourcePath)
			).toString();
		}

		StructuredContentFolder structuredContentFolder =
			StructuredContentFolder.toDTO(json);

		structuredContentFolder.setParentStructuredContentFolderId(
			documentFolderId);

		structuredContentFolder =
			structuredContentFolderResource.
				putSiteStructuredContentFolderByExternalReferenceCode(
					serviceContext.getScopeGroupId(),
					structuredContentFolder.getExternalReferenceCode(),
					structuredContentFolder);

		return structuredContentFolder.getId();
	}

	private TaxonomyCategory _addOrUpdateTaxonomyCategoryTaxonomyCategory(
			String parentTaxonomyCategoryId, ServiceContext serviceContext,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		TaxonomyCategoryResource.Builder taxonomyCategoryResourceBuilder =
			_taxonomyCategoryResourceFactory.create();

		TaxonomyCategoryResource taxonomyCategoryResource =
			taxonomyCategoryResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		Page<TaxonomyCategory> taxonomyCategoryPage =
			taxonomyCategoryResource.getTaxonomyCategoryTaxonomyCategoriesPage(
				parentTaxonomyCategoryId, "", null,
				taxonomyCategoryResource.toFilter(
					StringBundler.concat(
						"name eq '", taxonomyCategory.getName(), "'")),
				null, null);

		TaxonomyCategory existingTaxonomyCategory =
			taxonomyCategoryPage.fetchFirstItem();

		if (existingTaxonomyCategory == null) {
			taxonomyCategory =
				taxonomyCategoryResource.postTaxonomyCategoryTaxonomyCategory(
					parentTaxonomyCategoryId, taxonomyCategory);
		}
		else {
			taxonomyCategory = taxonomyCategoryResource.patchTaxonomyCategory(
				existingTaxonomyCategory.getId(), taxonomyCategory);
		}

		return taxonomyCategory;
	}

	private Map<String, String> _addOrUpdateTaxonomyVocabularies(
			long groupId, String parentResourcePath,
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		Map<String, String>
			taxonomyCategoryIdsAndTaxonomyVocabularyIdsStringUtilReplaceValues =
				new HashMap<>();

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

		if (SetUtil.isEmpty(resourcePaths)) {
			return taxonomyCategoryIdsAndTaxonomyVocabularyIdsStringUtilReplaceValues;
		}

		TaxonomyVocabularyResource.Builder taxonomyVocabularyResourceBuilder =
			_taxonomyVocabularyResourceFactory.create();

		TaxonomyVocabularyResource taxonomyVocabularyResource =
			taxonomyVocabularyResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith("/")) {
				continue;
			}

			String json = SiteInitializerUtil.read(
				resourcePath, _servletContext);

			TaxonomyVocabulary taxonomyVocabulary = TaxonomyVocabulary.toDTO(
				json);

			if (taxonomyVocabulary == null) {
				_log.error(
					"Unable to transform taxonomy vocabulary from JSON: " +
						json);

				continue;
			}

			Page<TaxonomyVocabulary> taxonomyVocabularyPage =
				taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
					groupId, "", null,
					taxonomyVocabularyResource.toFilter(
						StringBundler.concat(
							"name eq '", taxonomyVocabulary.getName(), "'")),
					null, null);

			TaxonomyVocabulary existingTaxonomyVocabulary =
				taxonomyVocabularyPage.fetchFirstItem();

			if (existingTaxonomyVocabulary == null) {
				taxonomyVocabulary =
					taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
						groupId, taxonomyVocabulary);
			}
			else {
				taxonomyVocabulary =
					taxonomyVocabularyResource.patchTaxonomyVocabulary(
						existingTaxonomyVocabulary.getId(), taxonomyVocabulary);
			}

			taxonomyCategoryIdsAndTaxonomyVocabularyIdsStringUtilReplaceValues.
				put(
					"TAXONOMY_VOCABULARY_ID:" + taxonomyVocabulary.getName(),
					String.valueOf(taxonomyVocabulary.getId()));

			taxonomyCategoryIdsAndTaxonomyVocabularyIdsStringUtilReplaceValues.
				putAll(
					_addTaxonomyCategories(
						StringUtil.replaceLast(resourcePath, ".json", "/"),
						null, serviceContext,
						siteNavigationMenuItemSettingsBuilder,
						taxonomyVocabulary.getId()));
		}

		return taxonomyCategoryIdsAndTaxonomyVocabularyIdsStringUtilReplaceValues;
	}

	private Map<String, String> _addOrUpdateTaxonomyVocabularies(
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(
			serviceContext.getCompanyId());

		return HashMapBuilder.putAll(
			_addOrUpdateTaxonomyVocabularies(
				group.getGroupId(),
				"/site-initializer/taxonomy-vocabularies/company",
				serviceContext, siteNavigationMenuItemSettingsBuilder)
		).putAll(
			_addOrUpdateTaxonomyVocabularies(
				serviceContext.getScopeGroupId(),
				"/site-initializer/taxonomy-vocabularies/group", serviceContext,
				siteNavigationMenuItemSettingsBuilder)
		).build();
	}

	private TaxonomyCategory _addOrUpdateTaxonomyVocabularyTaxonomyCategory(
			ServiceContext serviceContext, TaxonomyCategory taxonomyCategory,
			long vocabularyId)
		throws Exception {

		TaxonomyCategoryResource.Builder taxonomyCategoryResourceBuilder =
			_taxonomyCategoryResourceFactory.create();

		TaxonomyCategoryResource taxonomyCategoryResource =
			taxonomyCategoryResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		Page<TaxonomyCategory> taxonomyCategoryPage =
			taxonomyCategoryResource.
				getTaxonomyVocabularyTaxonomyCategoriesPage(
					vocabularyId, null, "", null,
					taxonomyCategoryResource.toFilter(
						StringBundler.concat(
							"name eq '", taxonomyCategory.getName(), "'")),
					null, null);

		TaxonomyCategory existingTaxonomyCategory =
			taxonomyCategoryPage.fetchFirstItem();

		if (existingTaxonomyCategory == null) {
			taxonomyCategory =
				taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
					vocabularyId, taxonomyCategory);
		}
		else {
			taxonomyCategory = taxonomyCategoryResource.patchTaxonomyCategory(
				existingTaxonomyCategory.getId(), taxonomyCategory);
		}

		return taxonomyCategory;
	}

	private void _addOrUpdateUserGroups(ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/user-groups.json", _servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			UserGroup userGroup = _userGroupLocalService.addOrUpdateUserGroup(
				jsonObject.getString("externalReferenceCode"),
				serviceContext.getUserId(), serviceContext.getCompanyId(),
				jsonObject.getString("name"),
				jsonObject.getString("description"), serviceContext);

			_userGroupLocalService.addGroupUserGroup(
				serviceContext.getScopeGroupId(), userGroup);
		}
	}

	private void _addPortletSettings(ServiceContext serviceContext)
		throws Exception {

		if (_commerceSiteInitializer == null) {
			return;
		}

		_commerceSiteInitializer.addPortletSettings(
			_classLoader, serviceContext, _servletContext);
	}

	private void _addRolesAssignments(ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/roles-assignments.json", _servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray groupsJSONArray = jsonObject.getJSONArray("groups");

			if (JSONUtil.isEmpty(groupsJSONArray)) {
				continue;
			}

			List<Long> groupIds = new ArrayList<>();

			for (int j = 0; j < groupsJSONArray.length(); j++) {
				JSONObject groupJSONObject = groupsJSONArray.getJSONObject(j);

				String groupType = groupJSONObject.getString("groupType");

				if (StringUtil.equals(groupType, "Organization")) {
					com.liferay.portal.kernel.model.Organization organization =
						_organizationLocalService.fetchOrganization(
							serviceContext.getCompanyId(),
							groupJSONObject.getString("groupName"));

					if (organization == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"No organization found with name " +
									groupJSONObject.getString("groupName"));
						}

						continue;
					}

					Group group = _groupLocalService.getOrganizationGroup(
						serviceContext.getCompanyId(),
						organization.getOrganizationId());

					groupIds.add(group.getGroupId());
				}
				else if (StringUtil.equals(groupType, "Site")) {
					groupIds.add(serviceContext.getScopeGroupId());
				}
				else if (StringUtil.equals(groupType, "User")) {
					User user = _userLocalService.fetchUserByScreenName(
						serviceContext.getCompanyId(),
						groupJSONObject.getString("groupName"));

					if (user == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"No user found with screen name " +
									groupJSONObject.getString("groupName"));
						}

						continue;
					}

					Group group = _groupLocalService.getUserGroup(
						serviceContext.getCompanyId(), user.getUserId());

					groupIds.add(group.getGroupId());
				}
				else if (StringUtil.equals(groupType, "UserGroups")) {
					UserGroup userGroup = _userGroupLocalService.fetchUserGroup(
						serviceContext.getCompanyId(),
						groupJSONObject.getString("groupName"));

					if (userGroup == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"No user group found with name " +
									groupJSONObject.getString("groupName"));
						}

						continue;
					}

					Group group = _groupLocalService.getUserGroupGroup(
						serviceContext.getCompanyId(),
						userGroup.getUserGroupId());

					groupIds.add(group.getGroupId());
				}
			}

			if (ListUtil.isEmpty(groupIds)) {
				continue;
			}

			Role role = _roleLocalService.fetchRole(
				serviceContext.getCompanyId(),
				jsonObject.getString("roleName"));

			if (role == null) {
				if (_log.isWarnEnabled()) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"No role found with name " +
								jsonObject.getString("roleName"));
					}
				}

				continue;
			}

			_groupLocalService.setRoleGroups(
				role.getRoleId(), ArrayUtil.toLongArray(groupIds));
		}
	}

	private void _addSegmentsExperiences(
			Map<String, String> assetListEntryIdsStringUtilReplaceValues,
			Map<String, String> clientExtensionEntryIdsStringUtilReplaceValues,
			Map<String, String> ddmStructureEntryIdsStringUtilReplaceValues,
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String>
				objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
			Map<String, String> segmentsEntriesIdsStringUtilReplaceValues,
			ServiceContext serviceContext,
			Map<String, String> taxonomyCategoryIdsStringUtilReplaceValues)
		throws Exception {

		Set<String> parentResourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/segments-experiences");

		if (SetUtil.isEmpty(parentResourcePaths)) {
			return;
		}

		for (String parentResourcePath : parentResourcePaths) {
			String json = SiteInitializerUtil.read(
				parentResourcePath + "segments-experiences.json",
				_servletContext);

			if (json == null) {
				return;
			}

			json = _replace(json, segmentsEntriesIdsStringUtilReplaceValues);

			JSONObject jsonObject = _jsonFactory.createJSONObject(json);

			Layout layout = _layoutLocalService.getLayoutByFriendlyURL(
				serviceContext.getScopeGroupId(), false,
				jsonObject.getString("friendlyURL"));

			Layout draftLayout = layout.fetchDraftLayout();

			UnicodeProperties unicodeProperties = new UnicodeProperties(true);

			JSONObject propertiesJSONObject = jsonObject.getJSONObject(
				"typeSettings");

			if (propertiesJSONObject != null) {
				Map<String, String> map = JSONUtil.toStringMap(
					propertiesJSONObject);

				unicodeProperties.putAll(map);
			}

			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.appendSegmentsExperience(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(),
					jsonObject.getLong("segmentsEntryId"),
					draftLayout.getClassPK(),
					SiteInitializerUtil.toMap(
						jsonObject.getString("name_i18n")),
					jsonObject.getBoolean("active", true), unicodeProperties,
					serviceContext);

			LayoutStructure layoutStructure = new LayoutStructure();

			layoutStructure.addRootLayoutStructureItem();

			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						draftLayout.getGroupId(), draftLayout.getPlid(), true);

			_layoutPageTemplateStructureRelLocalService.
				addLayoutPageTemplateStructureRel(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(),
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId(),
					segmentsExperience.getSegmentsExperienceId(),
					layoutStructure.toString(), serviceContext);

			Set<String> resourcePaths = _servletContext.getResourcePaths(
				parentResourcePath);

			for (String resourcePath : resourcePaths) {
				if (resourcePath.endsWith("/")) {
					_addLayoutContent(
						assetListEntryIdsStringUtilReplaceValues,
						clientExtensionEntryIdsStringUtilReplaceValues,
						ddmStructureEntryIdsStringUtilReplaceValues,
						documentsStringUtilReplaceValues,
						objectDefinitionIdsAndObjectEntryIdsStringUtilReplaceValues,
						layout, resourcePath,
						segmentsExperience.getSegmentsExperienceId(),
						serviceContext,
						taxonomyCategoryIdsStringUtilReplaceValues);
				}
			}
		}
	}

	private void _addSiteConfiguration(ServiceContext serviceContext)
		throws Exception {

		String resourcePath = "site-initializer/site-configuration.json";

		String json = SiteInitializerUtil.read(resourcePath, _servletContext);

		if (json == null) {
			return;
		}

		Group group = _groupLocalService.getGroup(
			serviceContext.getScopeGroupId());

		JSONObject jsonObject = _jsonFactory.createJSONObject(json);

		group.setType(jsonObject.getInt("typeSite"));
		group.setManualMembership(jsonObject.getBoolean("manualMembership"));
		group.setMembershipRestriction(
			jsonObject.getInt("membershipRestriction"));

		_groupLocalService.updateGroup(group);
	}

	private void _addSiteNavigationMenu(
			JSONObject jsonObject, ServiceContext serviceContext,
			Map<String, SiteNavigationMenuItemSetting>
				siteNavigationMenuItemSettings)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				jsonObject.getString("name"), jsonObject.getInt("typeSite"),
				serviceContext);

		_addSiteNavigationMenuItems(
			jsonObject, siteNavigationMenu, 0, serviceContext,
			siteNavigationMenuItemSettings);
	}

	private void _addSiteNavigationMenuItems(
			JSONObject jsonObject, SiteNavigationMenu siteNavigationMenu,
			long parentSiteNavigationMenuItemId, ServiceContext serviceContext,
			Map<String, SiteNavigationMenuItemSetting>
				siteNavigationMenuItemSettings)
		throws Exception {

		for (Object object :
				JSONUtil.toObjectArray(jsonObject.getJSONArray("menuItems"))) {

			JSONObject menuItemJSONObject = (JSONObject)object;

			String type = menuItemJSONObject.getString("type");

			String typeSettings = null;

			if (type.equals(SiteNavigationMenuItemTypeConstants.LAYOUT)) {
				boolean privateLayout = menuItemJSONObject.getBoolean(
					"privateLayout");
				String friendlyURL = menuItemJSONObject.getString(
					"friendlyURL");

				Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
					serviceContext.getScopeGroupId(), privateLayout,
					friendlyURL);

				if (layout == null) {
					return;
				}

				SiteNavigationMenuItemType siteNavigationMenuItemType =
					_siteNavigationMenuItemTypeRegistry.
						getSiteNavigationMenuItemType(
							SiteNavigationMenuItemTypeConstants.LAYOUT);

				typeSettings =
					siteNavigationMenuItemType.getTypeSettingsFromLayout(
						layout);
			}
			else if (type.equals(SiteNavigationMenuItemTypeConstants.NODE)) {
				typeSettings = UnicodePropertiesBuilder.put(
					"name", menuItemJSONObject.getString("name")
				).buildString();
			}
			else if (type.equals(SiteNavigationMenuItemTypeConstants.URL)) {
				typeSettings = UnicodePropertiesBuilder.put(
					"name", menuItemJSONObject.getString("name")
				).put(
					"url", menuItemJSONObject.getString("url")
				).put(
					"useNewTab", menuItemJSONObject.getString("useNewTab")
				).buildString();
			}
			else if (type.equals("display-page")) {
				String key = menuItemJSONObject.getString("key");

				if (Validator.isNull(key)) {
					continue;
				}

				SiteNavigationMenuItemSetting siteNavigationMenuItemSetting =
					siteNavigationMenuItemSettings.get(key);

				if (siteNavigationMenuItemSetting == null) {
					continue;
				}

				type = siteNavigationMenuItemSetting.className;

				typeSettings = UnicodePropertiesBuilder.create(
					true
				).put(
					"className", siteNavigationMenuItemSetting.className
				).put(
					"classNameId",
					String.valueOf(
						_portal.getClassNameId(
							siteNavigationMenuItemSetting.className))
				).put(
					"classPK",
					String.valueOf(siteNavigationMenuItemSetting.classPK)
				).put(
					"classTypeId", siteNavigationMenuItemSetting.classTypeId
				).put(
					"title", siteNavigationMenuItemSetting.title
				).put(
					"type", siteNavigationMenuItemSetting.type
				).buildString();
			}

			SiteNavigationMenuItem siteNavigationMenuItem =
				_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(),
					siteNavigationMenu.getSiteNavigationMenuId(),
					parentSiteNavigationMenuItemId, type, typeSettings,
					serviceContext);

			_addSiteNavigationMenuItems(
				menuItemJSONObject, siteNavigationMenu,
				siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				serviceContext, siteNavigationMenuItemSettings);
		}
	}

	private void _addSiteNavigationMenus(
			ServiceContext serviceContext,
			Map<String, SiteNavigationMenuItemSetting>
				siteNavigationMenuItemSettings)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/site-navigation-menus.json", _servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			_addSiteNavigationMenu(
				jsonArray.getJSONObject(i), serviceContext,
				siteNavigationMenuItemSettings);
		}
	}

	private void _addSiteSettings(ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/site-settings.json", _servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			JSONObject propertiesJSONObject = jsonObject.getJSONObject(
				"properties");

			Iterator<String> iterator = propertiesJSONObject.keys();

			while (iterator.hasNext()) {
				String key = iterator.next();

				properties.put(key, propertiesJSONObject.getString(key));
			}

			_configurationProvider.saveGroupConfiguration(
				serviceContext.getScopeGroupId(), jsonObject.getString("pid"),
				properties);
		}
	}

	private void _addStyleBookEntries(ServiceContext serviceContext)
		throws Exception {

		Enumeration<URL> enumeration = _bundle.findEntries(
			"/site-initializer/style-books", StringPool.STAR, true);

		if (enumeration == null) {
			return;
		}

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String fileName = url.getFile();

			if (fileName.endsWith("/")) {
				continue;
			}

			zipWriter.addEntry(
				_removeFirst(fileName, "/site-initializer/style-books/"),
				url.openStream());
		}

		_styleBookEntryZipProcessor.importStyleBookEntries(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			zipWriter.getFile(), true);
	}

	private Map<String, String> _addTaxonomyCategories(
			String parentResourcePath, String parentTaxonomyCategoryId,
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder,
			long taxonomyVocabularyId)
		throws Exception {

		Map<String, String> taxonomyCategoryIdsStringUtilReplaceValues =
			new HashMap<>();

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

		if (SetUtil.isEmpty(resourcePaths)) {
			return taxonomyCategoryIdsStringUtilReplaceValues;
		}

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith("/")) {
				continue;
			}

			String json = SiteInitializerUtil.read(
				resourcePath, _servletContext);

			TaxonomyCategory taxonomyCategory = TaxonomyCategory.toDTO(json);

			if (taxonomyCategory == null) {
				_log.error(
					"Unable to transform taxonomy category from JSON: " + json);

				continue;
			}

			if (parentTaxonomyCategoryId == null) {
				taxonomyCategory =
					_addOrUpdateTaxonomyVocabularyTaxonomyCategory(
						serviceContext, taxonomyCategory, taxonomyVocabularyId);
			}
			else {
				taxonomyCategory = _addOrUpdateTaxonomyCategoryTaxonomyCategory(
					parentTaxonomyCategoryId, serviceContext, taxonomyCategory);
			}

			TaxonomyCategory finalTaxonomyCategory = taxonomyCategory;

			String key = resourcePath;

			taxonomyCategoryIdsStringUtilReplaceValues.put(
				"TAXONOMY_CATEGORY_ID:" + key,
				String.valueOf(finalTaxonomyCategory.getId()));

			siteNavigationMenuItemSettingsBuilder.put(
				resourcePath,
				new SiteNavigationMenuItemSetting() {
					{
						className = AssetCategory.class.getName();
						classPK = finalTaxonomyCategory.getId();
						title = finalTaxonomyCategory.getName();
					}
				});

			taxonomyCategoryIdsStringUtilReplaceValues.putAll(
				_addTaxonomyCategories(
					StringUtil.replaceLast(resourcePath, ".json", "/"),
					taxonomyCategory.getId(), serviceContext,
					siteNavigationMenuItemSettingsBuilder,
					taxonomyVocabularyId));
		}

		return taxonomyCategoryIdsStringUtilReplaceValues;
	}

	private void _addUserAccounts(ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/user-accounts.json", _servletContext);

		if (json == null) {
			return;
		}

		UserAccountResource.Builder userAccountResourceBuilder =
			_userAccountResourceFactory.create();

		UserAccountResource userAccountResource =
			userAccountResourceBuilder.user(
				serviceContext.fetchUser()
			).httpServletRequest(
				serviceContext.getRequest()
			).build();

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray accountBriefsJSONArray = jsonObject.getJSONArray(
				"accountBriefs");

			if (JSONUtil.isEmpty(accountBriefsJSONArray)) {
				continue;
			}

			List<Group> oldGroups = new ArrayList<>();

			int j = 0;
			long userId = 0;

			UserAccount userAccount = UserAccount.toDTO(
				String.valueOf(jsonObject));

			User user = _userLocalService.fetchUserByEmailAddress(
				serviceContext.getCompanyId(), userAccount.getEmailAddress());

			if (user == null) {
				JSONObject accountBriefsJSONObject =
					accountBriefsJSONArray.getJSONObject(j);

				userAccount =
					userAccountResource.putUserAccountByExternalReferenceCode(
						jsonObject.getString("externalReferenceCode"),
						userAccount);

				userAccountResource.
					postAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(
						accountBriefsJSONObject.getString(
							"externalReferenceCode"),
						userAccount.getExternalReferenceCode());

				j++;

				_associateUserAccounts(
					accountBriefsJSONObject,
					jsonObject.getString("emailAddress"), serviceContext);

				userId = userAccount.getId();
			}
			else {
				userId = user.getUserId();

				oldGroups = user.getSiteGroups();
			}

			oldGroups.add(serviceContext.getScopeGroup());

			_userLocalService.updateGroups(
				userId, ListUtil.toLongArray(oldGroups, GroupModel::getGroupId),
				serviceContext);

			if (jsonObject.has("organizationBriefs")) {
				_addOrganizationUser(
					jsonObject.getJSONArray("organizationBriefs"),
					serviceContext, userId);
			}

			for (; j < accountBriefsJSONArray.length(); j++) {
				JSONObject accountBriefsJSONObject =
					accountBriefsJSONArray.getJSONObject(j);

				userAccountResource.
					postAccountUserAccountByExternalReferenceCodeByEmailAddress(
						accountBriefsJSONObject.getString(
							"externalReferenceCode"),
						userAccount.getEmailAddress());

				_associateUserAccounts(
					accountBriefsJSONObject,
					jsonObject.getString("emailAddress"), serviceContext);
			}
		}
	}

	private void _addUserRoles(ServiceContext serviceContext) throws Exception {
		String json = SiteInitializerUtil.read(
			"/site-initializer/user-roles.json", _servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray rolesJSONArray = jsonObject.getJSONArray("roles");

			if (JSONUtil.isEmpty(rolesJSONArray)) {
				continue;
			}

			List<Role> roles = new ArrayList<>();

			for (int j = 0; j < rolesJSONArray.length(); j++) {
				roles.add(
					_roleLocalService.getRole(
						serviceContext.getCompanyId(),
						rolesJSONArray.getString(j)));
			}

			if (ListUtil.isNotEmpty(roles)) {
				User user = _userLocalService.fetchUserByEmailAddress(
					serviceContext.getCompanyId(),
					jsonObject.getString("emailAddress"));

				_roleLocalService.addUserRoles(user.getUserId(), roles);
			}
		}
	}

	private void _addWorkflowDefinitions(
			Map<String, String> roleIdsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/workflow-definitions");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		WorkflowDefinitionResource.Builder workflowDefinitionResourceBuilder =
			_workflowDefinitionResourceFactory.create();

		WorkflowDefinitionResource workflowDefinitionResource =
			workflowDefinitionResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		for (String resourcePath : resourcePaths) {
			JSONObject workflowDefinitionJSONObject =
				_jsonFactory.createJSONObject(
					SiteInitializerUtil.read(
						resourcePath + "workflow-definition.json",
						_servletContext));

			workflowDefinitionJSONObject.put(
				"content",
				_replace(
					SiteInitializerUtil.read(
						resourcePath + "workflow-definition.xml",
						_servletContext),
					roleIdsStringUtilReplaceValues));

			WorkflowDefinition workflowDefinition =
				workflowDefinitionResource.postWorkflowDefinitionDeploy(
					WorkflowDefinition.toDTO(
						workflowDefinitionJSONObject.toString()));

			String propertiesJSON = SiteInitializerUtil.read(
				resourcePath + "workflow-definition.properties.json",
				_servletContext);

			if (propertiesJSON == null) {
				continue;
			}

			JSONArray propertiesJSONArray = _jsonFactory.createJSONArray(
				propertiesJSON);

			for (int i = 0; i < propertiesJSONArray.length(); i++) {
				JSONObject propertiesJSONObject =
					propertiesJSONArray.getJSONObject(i);

				long groupId = 0;

				if (StringUtil.equals(
						propertiesJSONObject.getString("scope"), "site")) {

					groupId = serviceContext.getScopeGroupId();
				}

				String className = propertiesJSONObject.getString("className");

				if (StringUtil.equals(
						className,
						com.liferay.object.model.ObjectDefinition.class.
							getName())) {

					com.liferay.object.model.ObjectDefinition objectDefinition =
						_objectDefinitionLocalService.fetchObjectDefinition(
							serviceContext.getCompanyId(),
							propertiesJSONObject.getString("assetName"));

					if (objectDefinition == null) {
						continue;
					}

					className = StringBundler.concat(
						className, "#",
						objectDefinition.getObjectDefinitionId());
				}

				long typePK = 0;

				if ((_commerceSiteInitializer != null) &&
					StringUtil.equals(
						className,
						_commerceSiteInitializer.getCommerceOrderClassName())) {

					groupId =
						_commerceSiteInitializer.getCommerceChannelGroupId(
							groupId);

					typePK = propertiesJSONObject.getLong("typePK");
				}

				_workflowDefinitionLinkLocalService.
					updateWorkflowDefinitionLink(
						serviceContext.getUserId(),
						serviceContext.getCompanyId(), groupId, className, 0,
						typePK,
						StringBundler.concat(
							workflowDefinition.getName(), "@",
							workflowDefinition.getVersion()));
			}
		}
	}

	private void _associateUserAccounts(
			JSONObject accountBriefsJSONObject, String emailAddress,
			ServiceContext serviceContext)
		throws Exception {

		if (!accountBriefsJSONObject.has("roleBriefs")) {
			return;
		}

		JSONArray jsonArray = accountBriefsJSONObject.getJSONArray(
			"roleBriefs");

		if (JSONUtil.isEmpty(jsonArray)) {
			return;
		}

		AccountRoleResource.Builder builder =
			_accountRoleResourceFactory.create();

		AccountRoleResource accountRoleResource = builder.user(
			serviceContext.fetchUser()
		).build();

		for (int i = 0; i < jsonArray.length(); i++) {
			Page<AccountRole> accountRolePage =
				accountRoleResource.
					getAccountAccountRolesByExternalReferenceCodePage(
						accountBriefsJSONObject.getString(
							"externalReferenceCode"),
						null,
						accountRoleResource.toFilter(
							StringBundler.concat(
								"name eq '", jsonArray.getString(i), "'")),
						null, null);

			AccountRole accountRole = accountRolePage.fetchFirstItem();

			if (accountRole == null) {
				continue;
			}

			accountRoleResource.
				postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
					accountBriefsJSONObject.getString("externalReferenceCode"),
					accountRole.getId(), emailAddress);
		}
	}

	private long[] _getAssetCategoryIds(
		long groupId, String[] externalReferenceCodes) {

		List<Long> assetCategoryIds = new ArrayList<>();

		for (String externalReferenceCode : externalReferenceCodes) {
			AssetCategory assetCategory =
				_assetCategoryLocalService.
					fetchAssetCategoryByExternalReferenceCode(
						externalReferenceCode, groupId);

			if (assetCategory != null) {
				assetCategoryIds.add(assetCategory.getCategoryId());
			}
		}

		return ArrayUtil.toLongArray(assetCategoryIds);
	}

	private Map<String, String> _getClassNameIdStringUtilReplaceValues() {
		Map<String, String> map = new HashMap<>();

		Class<?>[] classes = {DDMStructure.class, JournalArticle.class};

		for (Class<?> clazz : classes) {
			map.put(
				"CLASS_NAME_ID:" + clazz.getName(),
				String.valueOf(_portal.getClassNameId(clazz)));
		}

		return map;
	}

	private Serializable _getExpandoAttributeValue(JSONObject jsonObject)
		throws Exception {

		if (jsonObject.getInt("dataType") == ExpandoColumnConstants.BOOLEAN) {
			return jsonObject.getBoolean("defaultValue");
		}
		else if (jsonObject.getInt("dataType") == ExpandoColumnConstants.DATE) {
			if (Validator.isNull(jsonObject.getString("defaultValue"))) {
				return null;
			}

			DateFormat dateFormat = DateUtil.getISOFormat(
				jsonObject.getString("defaultValue"));

			return dateFormat.parse(jsonObject.getString("defaultValue"));
		}
		else if (jsonObject.getInt("dataType") ==
					ExpandoColumnConstants.DOUBLE) {

			return jsonObject.getDouble("defaultValue");
		}
		else if (jsonObject.getInt("dataType") ==
					ExpandoColumnConstants.DOUBLE_ARRAY) {

			return JSONUtil.toDoubleArray(
				jsonObject.getJSONArray("defaultValue"));
		}
		else if (jsonObject.getInt("dataType") ==
					ExpandoColumnConstants.FLOAT) {

			return jsonObject.getDouble("defaultValue");
		}
		else if (jsonObject.getInt("dataType") ==
					ExpandoColumnConstants.FLOAT_ARRAY) {

			return JSONUtil.toFloatArray(
				jsonObject.getJSONArray("defaultValue"));
		}
		else if (jsonObject.getInt("dataType") ==
					ExpandoColumnConstants.INTEGER) {

			return jsonObject.getInt("defaultValue");
		}
		else if (jsonObject.getInt("dataType") ==
					ExpandoColumnConstants.INTEGER_ARRAY) {

			return JSONUtil.toIntegerArray(
				jsonObject.getJSONArray("defaultValue"));
		}
		else if (jsonObject.getInt("dataType") == ExpandoColumnConstants.LONG) {
			return jsonObject.getLong("defaultValue");
		}
		else if (jsonObject.getInt("dataType") ==
					ExpandoColumnConstants.LONG_ARRAY) {

			return JSONUtil.toLongArray(
				jsonObject.getJSONArray("defaultValue"));
		}
		else if (jsonObject.getInt("dataType") ==
					ExpandoColumnConstants.NUMBER) {

			return jsonObject.getDouble("defaultValue");
		}
		else if (jsonObject.getInt("dataType") ==
					ExpandoColumnConstants.NUMBER_ARRAY) {

			return JSONUtil.toIntegerArray(
				jsonObject.getJSONArray("defaultValue"));
		}
		else if (jsonObject.getInt("dataType") ==
					ExpandoColumnConstants.STRING) {

			return jsonObject.getString("defaultValue");
		}
		else if (jsonObject.getInt("dataType") ==
					ExpandoColumnConstants.STRING_ARRAY) {

			return JSONUtil.toStringArray(
				jsonObject.getJSONArray("defaultValue"));
		}

		return (Serializable)jsonObject.get("defaultValue");
	}

	private Map<String, String> _getReleaseInfoStringUtilReplaceValues() {
		Map<String, String> map = new HashMap<>();

		Object[] entries = {
			"BUILD_DATE", ReleaseInfo.getBuildDate(), "BUILD_NUMBER",
			ReleaseInfo.getBuildNumber(), "CODE_NAME",
			ReleaseInfo.getCodeName(), "NAME", ReleaseInfo.getName(),
			"PARENT_BUILD_NUMBER", ReleaseInfo.getParentBuildNumber(),
			"RELEASE_INFO",
			_replace(
				ReleaseInfo.getReleaseInfo(), StringPool.OPEN_PARENTHESIS,
				"<br>("),
			"SERVER_INFO", ReleaseInfo.getServerInfo(), "VENDOR",
			ReleaseInfo.getVendor(), "VERSION", ReleaseInfo.getVersion(),
			"VERSION_DISPLAY_NAME", ReleaseInfo.getVersionDisplayName()
		};

		for (int i = 0; i < entries.length; i += 2) {
			String entryKey = String.valueOf(entries[i]);
			String entryValue = String.valueOf(entries[i + 1]);

			map.put("RELEASE_INFO:" + entryKey, entryValue);
		}

		return map;
	}

	private String _getThemeId(
		long companyId, String defaultThemeId, String themeName) {

		List<Theme> themes = ListUtil.filter(
			_themeLocalService.getThemes(companyId),
			theme -> Objects.equals(theme.getName(), themeName));

		if (ListUtil.isNotEmpty(themes)) {
			Theme theme = themes.get(0);

			return theme.getThemeId();
		}

		return defaultThemeId;
	}

	private void _invoke(UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		long startTime = System.currentTimeMillis();

		unsafeRunnable.run();

		if (_log.isInfoEnabled()) {
			Thread thread = Thread.currentThread();

			StackTraceElement stackTraceElement = thread.getStackTrace()[2];

			_log.info(
				StringBundler.concat(
					"Invoking line ", stackTraceElement.getLineNumber(),
					" took ", System.currentTimeMillis() - startTime, " ms"));
		}
	}

	private <T> T _invoke(UnsafeSupplier<T, Exception> unsafeSupplier)
		throws Exception {

		long startTime = System.currentTimeMillis();

		T t = unsafeSupplier.get();

		if (_log.isInfoEnabled()) {
			Thread thread = Thread.currentThread();

			StackTraceElement stackTraceElement = thread.getStackTrace()[2];

			_log.info(
				StringBundler.concat(
					"Invoking line ", stackTraceElement.getLineNumber(), " in ",
					System.currentTimeMillis() - startTime, " ms"));
		}

		return t;
	}

	private String _removeFirst(String s, String oldSub) {
		int index = s.indexOf(oldSub);

		return s.substring(index + oldSub.length());
	}

	private String _replace(
		String s, Map<String, String>... stringUtilReplaceValuesArray) {

		Map<String, String> aggregatedStringUtilReplaceValues = new HashMap<>();

		for (Map<String, String> stringUtilReplaceValues :
				stringUtilReplaceValuesArray) {

			aggregatedStringUtilReplaceValues.putAll(stringUtilReplaceValues);
		}

		aggregatedStringUtilReplaceValues.putAll(
			_classNameIdStringUtilReplaceValues);
		aggregatedStringUtilReplaceValues.putAll(
			_releaseInfoStringUtilReplaceValues);

		s = StringUtil.replace(
			s, "\"[#", "#]\"", aggregatedStringUtilReplaceValues);

		return StringUtil.replace(
			s, "[$", "$]", aggregatedStringUtilReplaceValues);
	}

	private String _replace(String s, ServiceContext serviceContext)
		throws Exception {

		Group group = serviceContext.getScopeGroup();

		return StringUtil.replace(
			s,
			new String[] {
				"[$COMPANY_ID$]", "[$GROUP_FRIENDLY_URL$]", "[$GROUP_ID$]",
				"[$GROUP_KEY$]", "[$PORTAL_URL$]"
			},
			new String[] {
				String.valueOf(group.getCompanyId()), group.getFriendlyURL(),
				String.valueOf(serviceContext.getScopeGroupId()),
				group.getGroupKey(), serviceContext.getPortalURL()
			});
	}

	private String _replace(String s, String oldSub, String newSub) {
		return StringUtil.replace(s, oldSub, newSub);
	}

	private void _setDefaultLayoutUtilityPageEntries(
			ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/layout-utility-page-entries" +
				"/default-utility-page-entries.json",
			_servletContext);

		if (json == null) {
			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String type = iterator.next();

			String name = jsonObject.getString(type);

			LayoutUtilityPageEntry layoutUtilityPageEntry =
				_layoutUtilityPageEntryLocalService.fetchLayoutUtilityPageEntry(
					serviceContext.getScopeGroupId(), name,
					LayoutUtilityPageEntryTypeConverter.convertToInternalValue(
						type));

			if (layoutUtilityPageEntry != null) {
				_layoutUtilityPageEntryLocalService.
					setDefaultLayoutUtilityPageEntry(
						layoutUtilityPageEntry.getLayoutUtilityPageEntryId());
			}
		}
	}

	private void _setResourcePermissions(
			long companyId, String name, JSONArray permissionsJSONArray,
			String primKey)
		throws Exception {

		if (permissionsJSONArray == null) {
			return;
		}

		for (int i = 0; i < permissionsJSONArray.length(); i++) {
			JSONObject permissionsJSONObject =
				permissionsJSONArray.getJSONObject(i);

			int scope = permissionsJSONObject.getInt("scope");

			String roleName = permissionsJSONObject.getString("roleName");

			Role role = _roleLocalService.getRole(companyId, roleName);

			String[] actionIds = new String[0];

			JSONArray actionIdsJSONArray = permissionsJSONObject.getJSONArray(
				"actionIds");

			if (actionIdsJSONArray != null) {
				for (int j = 0; j < actionIdsJSONArray.length(); j++) {
					actionIds = ArrayUtil.append(
						actionIds, actionIdsJSONArray.getString(j));
				}
			}

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, name, scope, primKey, role.getRoleId(), actionIds);
		}
	}

	private Layout _updateDraftLayout(
			Layout draftLayout, JSONObject settingsJSONObject)
		throws Exception {

		UnicodeProperties unicodeProperties =
			draftLayout.getTypeSettingsProperties();

		Set<Map.Entry<String, String>> set = unicodeProperties.entrySet();

		set.removeIf(
			entry -> StringUtil.startsWith(entry.getKey(), "lfr-theme:"));

		JSONObject themeSettingsJSONObject = settingsJSONObject.getJSONObject(
			"themeSettings");

		if (themeSettingsJSONObject != null) {
			for (String key : themeSettingsJSONObject.keySet()) {
				unicodeProperties.put(
					key, themeSettingsJSONObject.getString(key));
			}

			draftLayout = _layoutLocalService.updateLayout(
				draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
				draftLayout.getLayoutId(), unicodeProperties.toString());

			draftLayout.setTypeSettingsProperties(unicodeProperties);
		}

		draftLayout = _layoutLocalService.updateLookAndFeel(
			draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
			draftLayout.getLayoutId(),
			_getThemeId(
				draftLayout.getCompanyId(), draftLayout.getThemeId(),
				settingsJSONObject.getString("themeName")),
			settingsJSONObject.getString(
				"colorSchemeName", draftLayout.getColorSchemeId()),
			settingsJSONObject.getString("css", draftLayout.getCss()));

		JSONObject masterPageJSONObject = settingsJSONObject.getJSONObject(
			"masterPage");

		if (masterPageJSONObject != null) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(
						draftLayout.getGroupId(),
						masterPageJSONObject.getString("key"));

			if (layoutPageTemplateEntry != null) {
				draftLayout = _layoutLocalService.updateMasterLayoutPlid(
					draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
					draftLayout.getLayoutId(),
					layoutPageTemplateEntry.getPlid());
			}
		}

		return draftLayout;
	}

	private void _updateGroupSiteInitializerKey(long groupId) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-165482")) {
			return;
		}

		Group group = _groupLocalService.getGroup(groupId);

		UnicodeProperties typeSettingsUnicodeProperties =
			group.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.setProperty(
			"siteInitializerKey", getKey());

		_groupLocalService.updateGroup(
			group.getGroupId(), typeSettingsUnicodeProperties.toString());
	}

	private void _updateLayoutSet(
			Map<String, String> documentsStringUtilReplaceValues,
			boolean privateLayout, ServiceContext serviceContext)
		throws Exception {

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			serviceContext.getScopeGroupId(), privateLayout);

		String resourcePath = "/site-initializer/layout-set";

		if (privateLayout) {
			resourcePath += "/private";
		}
		else {
			resourcePath += "/public";
		}

		String metadataJSON = SiteInitializerUtil.read(
			resourcePath + "/metadata.json", _servletContext);

		JSONObject metadataJSONObject = _jsonFactory.createJSONObject(
			(metadataJSON == null) ? "{}" : metadataJSON);

		String css = _replace(
			SiteInitializerUtil.read(
				resourcePath + "/css.css", _servletContext),
			documentsStringUtilReplaceValues);

		_layoutSetLocalService.updateLookAndFeel(
			serviceContext.getScopeGroupId(), privateLayout,
			_getThemeId(
				serviceContext.getCompanyId(), StringPool.BLANK,
				metadataJSONObject.getString("themeName")),
			layoutSet.getColorSchemeId(), css);

		URL url = _servletContext.getResource(resourcePath + "/logo.png");

		if (url != null) {
			_layoutSetLocalService.updateLogo(
				serviceContext.getScopeGroupId(), privateLayout, true,
				FileUtil.getBytes(url.openStream()));
		}

		JSONObject settingsJSONObject = metadataJSONObject.getJSONObject(
			"settings");

		if (settingsJSONObject == null) {
			return;
		}

		String js = SiteInitializerUtil.read(
			resourcePath + "/js.js", _servletContext);

		if (Validator.isNotNull(js)) {
			settingsJSONObject.put("javascript", js);
		}

		UnicodeProperties unicodeProperties = layoutSet.getSettingsProperties();

		for (String key : settingsJSONObject.keySet()) {
			unicodeProperties.put(key, settingsJSONObject.getString(key));
		}

		_layoutSetLocalService.updateSettings(
			serviceContext.getScopeGroupId(), privateLayout,
			unicodeProperties.toString());
	}

	private void _updateLayoutSets(
			Map<String, String> documentsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		_updateLayoutSet(
			documentsStringUtilReplaceValues, false, serviceContext);
		_updateLayoutSet(
			documentsStringUtilReplaceValues, true, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BundleSiteInitializer.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	private final AccountResource.Factory _accountResourceFactory;
	private final AccountRoleLocalService _accountRoleLocalService;
	private final AccountRoleResource.Factory _accountRoleResourceFactory;
	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetListEntryLocalService _assetListEntryLocalService;
	private final Bundle _bundle;
	private final ClassLoader _classLoader;
	private final Map<String, String> _classNameIdStringUtilReplaceValues;
	private final ClientExtensionEntryLocalService
		_clientExtensionEntryLocalService;
	private CommerceSiteInitializer _commerceSiteInitializer;
	private final ConfigurationProvider _configurationProvider;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMTemplateLocalService _ddmTemplateLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final DLURLHelper _dlURLHelper;
	private final DocumentFolderResource.Factory _documentFolderResourceFactory;
	private final DocumentResource.Factory _documentResourceFactory;
	private final FragmentsImporter _fragmentsImporter;
	private final GroupLocalService _groupLocalService;
	private final JournalArticleLocalService _journalArticleLocalService;
	private final JSONFactory _jsonFactory;
	private final KnowledgeBaseArticleResource.Factory
		_knowledgeBaseArticleResourceFactory;
	private final KnowledgeBaseFolderResource.Factory
		_knowledgeBaseFolderResourceFactory;
	private final LayoutCopyHelper _layoutCopyHelper;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;
	private final LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;
	private final LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;
	private final LayoutSetLocalService _layoutSetLocalService;
	private final LayoutsImporter _layoutsImporter;
	private final LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;
	private final ListTypeDefinitionLocalService
		_listTypeDefinitionLocalService;
	private final ListTypeDefinitionResource _listTypeDefinitionResource;
	private final ListTypeDefinitionResource.Factory
		_listTypeDefinitionResourceFactory;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ListTypeEntryResource _listTypeEntryResource;
	private final ListTypeEntryResource.Factory _listTypeEntryResourceFactory;
	private final NotificationTemplateResource.Factory
		_notificationTemplateResourceFactory;
	private final ObjectActionLocalService _objectActionLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManager _objectEntryManager;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectFieldResource.Factory _objectFieldResourceFactory;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final ObjectRelationshipResource.Factory
		_objectRelationshipResourceFactory;
	private final OrganizationLocalService _organizationLocalService;
	private final OrganizationResource.Factory _organizationResourceFactory;
	private final Portal _portal;
	private final Map<String, String> _releaseInfoStringUtilReplaceValues;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;
	private final SAPEntryLocalService _sapEntryLocalService;
	private final SegmentsEntryLocalService _segmentsEntryLocalService;
	private final SegmentsExperienceLocalService
		_segmentsExperienceLocalService;
	private ServletContext _servletContext;
	private final SettingsFactory _settingsFactory;
	private final SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;
	private final SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;
	private final SiteNavigationMenuLocalService
		_siteNavigationMenuLocalService;
	private final StructuredContentFolderResource.Factory
		_structuredContentFolderResourceFactory;
	private final StyleBookEntryZipProcessor _styleBookEntryZipProcessor;
	private final TaxonomyCategoryResource.Factory
		_taxonomyCategoryResourceFactory;
	private final TaxonomyVocabularyResource.Factory
		_taxonomyVocabularyResourceFactory;
	private final TemplateEntryLocalService _templateEntryLocalService;
	private final ThemeLocalService _themeLocalService;
	private final UserAccountResource.Factory _userAccountResourceFactory;
	private final UserGroupLocalService _userGroupLocalService;
	private final UserLocalService _userLocalService;
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;
	private final WorkflowDefinitionResource.Factory
		_workflowDefinitionResourceFactory;

	private class SiteNavigationMenuItemSetting {

		public String className;
		public String classPK;
		public String classTypeId = StringPool.BLANK;
		public String title;
		public String type = StringPool.BLANK;

	}

	private class SiteNavigationMenuItemSettingsBuilder {

		public Map<String, SiteNavigationMenuItemSetting> build() {
			return _siteNavigationMenuItemSettings;
		}

		public void put(
			String key,
			SiteNavigationMenuItemSetting siteNavigationMenuItemSetting) {

			_siteNavigationMenuItemSettings.put(
				key, siteNavigationMenuItemSetting);
		}

		private Map<String, SiteNavigationMenuItemSetting>
			_siteNavigationMenuItemSettings = new HashMap<>();

	}

}