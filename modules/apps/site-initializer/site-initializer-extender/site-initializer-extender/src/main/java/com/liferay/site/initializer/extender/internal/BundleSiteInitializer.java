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
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
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
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.resource.v1_0.AccountRoleResource;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentFolderResource;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;
import com.liferay.segments.constants.SegmentsExperienceConstants;
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

import java.io.Serializable;

import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
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
		DDMStructureLocalService ddmStructureLocalService,
		DDMTemplateLocalService ddmTemplateLocalService,
		DefaultDDMStructureHelper defaultDDMStructureHelper,
		DLURLHelper dlURLHelper,
		DocumentFolderResource.Factory documentFolderResourceFactory,
		DocumentResource.Factory documentResourceFactory,
		FragmentsImporter fragmentsImporter,
		GroupLocalService groupLocalService,
		JournalArticleLocalService journalArticleLocalService,
		JSONFactory jsonFactory, LayoutCopyHelper layoutCopyHelper,
		LayoutLocalService layoutLocalService,
		LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService,
		LayoutPageTemplatesImporter layoutPageTemplatesImporter,
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService,
		LayoutSetLocalService layoutSetLocalService,
		ListTypeDefinitionResource listTypeDefinitionResource,
		ListTypeDefinitionResource.Factory listTypeDefinitionResourceFactory,
		ListTypeEntryResource listTypeEntryResource,
		ListTypeEntryResource.Factory listTypeEntryResourceFactory,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		ObjectRelationshipResource.Factory objectRelationshipResourceFactory,
		ObjectEntryLocalService objectEntryLocalService, Portal portal,
		RemoteAppEntryLocalService remoteAppEntryLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService,
		SAPEntryLocalService sapEntryLocalService,
		SettingsFactory settingsFactory,
		SiteNavigationMenuItemLocalService siteNavigationMenuItemLocalService,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry,
		SiteNavigationMenuLocalService siteNavigationMenuLocalService,
		StructuredContentFolderResource.Factory
			structuredContentFolderResourceFactory,
		StyleBookEntryZipProcessor styleBookEntryZipProcessor,
		TaxonomyCategoryResource.Factory taxonomyCategoryResourceFactory,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		ThemeLocalService themeLocalService,
		UserAccountResource.Factory userAccountResourceFactory,
		UserLocalService userLocalService,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService,
		WorkflowDefinitionResource.Factory workflowDefinitionResourceFactory) {

		_accountResourceFactory = accountResourceFactory;
		_accountRoleLocalService = accountRoleLocalService;
		_accountRoleResourceFactory = accountRoleResourceFactory;
		_assetCategoryLocalService = assetCategoryLocalService;
		_assetListEntryLocalService = assetListEntryLocalService;
		_bundle = bundle;
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
		_layoutCopyHelper = layoutCopyHelper;
		_layoutLocalService = layoutLocalService;
		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
		_layoutPageTemplatesImporter = layoutPageTemplatesImporter;
		_layoutPageTemplateStructureLocalService =
			layoutPageTemplateStructureLocalService;
		_layoutSetLocalService = layoutSetLocalService;
		_listTypeDefinitionResource = listTypeDefinitionResource;
		_listTypeDefinitionResourceFactory = listTypeDefinitionResourceFactory;
		_listTypeEntryResource = listTypeEntryResource;
		_listTypeEntryResourceFactory = listTypeEntryResourceFactory;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;
		_objectRelationshipResourceFactory = objectRelationshipResourceFactory;
		_objectEntryLocalService = objectEntryLocalService;
		_portal = portal;
		_remoteAppEntryLocalService = remoteAppEntryLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
		_sapEntryLocalService = sapEntryLocalService;
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
		_themeLocalService = themeLocalService;
		_userAccountResourceFactory = userAccountResourceFactory;
		_userLocalService = userLocalService;
		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
		_workflowDefinitionResourceFactory = workflowDefinitionResourceFactory;

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		_classLoader = bundleWiring.getClassLoader();
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
		return _servletContext.getContextPath() + "/images/thumbnail.png";
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

			ServiceContext serviceContext = new ServiceContext() {
				{
					setAddGroupPermissions(true);
					setAddGuestPermissions(true);
					setCompanyId(user.getCompanyId());
					setScopeGroupId(groupId);
					setTimeZone(user.getTimeZone());
					setUserId(user.getUserId());
				}
			};

			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder =
					new SiteNavigationMenuItemSettingsBuilder();

			_invoke(() -> _addAccounts(serviceContext));
			_invoke(() -> _addDDMStructures(serviceContext));

			Map<String, String> assetListEntryIdsStringUtilReplaceValues =
				_invoke(
					() -> _addAssetListEntries(
						_ddmStructureLocalService, serviceContext));
			Map<String, String> documentsStringUtilReplaceValues = _invoke(
				() -> _addDocuments(
					serviceContext, siteNavigationMenuItemSettingsBuilder));

			_invoke(
				() -> _addFragmentEntries(
					assetListEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues, serviceContext));

			_invoke(() -> _addSAPEntries(serviceContext));
			_invoke(() -> _addSiteConfiguration(serviceContext));
			_invoke(() -> _addStyleBookEntries(serviceContext));
			_invoke(
				() -> _addTaxonomyVocabularies(
					serviceContext, siteNavigationMenuItemSettingsBuilder));
			_invoke(() -> _updateLayoutSets(serviceContext));

			_invoke(
				() -> _addDDMTemplates(
					_ddmStructureLocalService, serviceContext));
			_invoke(
				() -> _addJournalArticles(
					_ddmStructureLocalService, _ddmTemplateLocalService,
					documentsStringUtilReplaceValues, serviceContext,
					siteNavigationMenuItemSettingsBuilder));

			Map<String, Layout> layouts = _invoke(
				() -> _addLayouts(serviceContext));

			_invoke(
				() -> _addLayoutPageTemplates(
					assetListEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues, serviceContext));

			Map<String, String> listTypeDefinitionIdsStringUtilReplaceValues =
				_invoke(() -> _addListTypeDefinitions(serviceContext));

			ObjectDefinitionResource.Builder objectDefinitionResourceBuilder =
				_objectDefinitionResourceFactory.create();

			ObjectDefinitionResource objectDefinitionResource =
				objectDefinitionResourceBuilder.user(
					serviceContext.fetchUser()
				).build();

			Map<String, String> objectDefinitionIdsStringUtilReplaceValues =
				_invoke(
					() -> _addObjectDefinitions(
						listTypeDefinitionIdsStringUtilReplaceValues,
						objectDefinitionResource, serviceContext,
						siteNavigationMenuItemSettingsBuilder));

			_invoke(
				() -> _addCPDefinitions(
					documentsStringUtilReplaceValues,
					objectDefinitionIdsStringUtilReplaceValues,
					serviceContext));
			_invoke(
				() -> _addObjectRelationships(
					objectDefinitionIdsStringUtilReplaceValues,
					serviceContext));
			_invoke(
				() -> _addPermissions(
					objectDefinitionIdsStringUtilReplaceValues,
					serviceContext));

			Map<String, String> remoteAppEntryIdsStringUtilReplaceValues =
				_invoke(
					() -> _addRemoteAppEntries(
						documentsStringUtilReplaceValues, serviceContext));

			_invoke(
				() -> _addLayoutsContent(
					assetListEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues, layouts,
					remoteAppEntryIdsStringUtilReplaceValues, serviceContext,
					siteNavigationMenuItemSettingsBuilder.build()));

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					_invoke(
						() -> _addWorkflowDefinitions(
							objectDefinitionResource, serviceContext));

					return null;
				});
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

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

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

		JSONArray assetListJSONArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < assetListJSONArray.length(); i++) {
			JSONObject assetListJSONObject = assetListJSONArray.getJSONObject(
				i);

			_addAssetListEntry(
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

	private void _addAssetListEntry(
			JSONObject assetListJSONObject,
			DDMStructureLocalService ddmStructureLocalService,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject unicodePropertiesJSONObject =
			assetListJSONObject.getJSONObject("unicodeProperties");

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			serviceContext.getScopeGroupId(),
			_portal.getClassNameId(
				unicodePropertiesJSONObject.getString("classNameIds")),
			assetListJSONObject.getString("ddmStructureKey"));

		Map<String, String> map = HashMapBuilder.put(
			"anyAssetType",
			String.valueOf(
				_portal.getClassNameId(
					unicodePropertiesJSONObject.getString("classNameIds")))
		).put(
			unicodePropertiesJSONObject.getString("anyClassType"),
			String.valueOf(ddmStructure.getStructureId())
		).put(
			"classNameIds",
			unicodePropertiesJSONObject.getString("classNameIds")
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

		_assetListEntryLocalService.addDynamicAssetListEntry(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			assetListJSONObject.getString("title"),
			UnicodePropertiesBuilder.create(
				map, true
			).buildString(),
			serviceContext);
	}

	private void _addCPDefinitions(
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String> objectDefinitionIdsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		if ((_commerceSiteInitializer == null) ||
			!GetterUtil.getBoolean(
				PropsUtil.get("enterprise.product.commerce.enabled"))) {

			return;
		}

		_commerceSiteInitializer.addCPDefinitions(
			_bundle, documentsStringUtilReplaceValues,
			objectDefinitionIdsStringUtilReplaceValues, serviceContext,
			_servletContext);
	}

	private void _addDDMStructures(ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/ddm-structures");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			_defaultDDMStructureHelper.addDDMStructures(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class), _classLoader,
				resourcePath, serviceContext);
		}
	}

	private void _addDDMTemplates(
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

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
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

			_ddmTemplateLocalService.addTemplate(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(
					jsonObject.getString(
						"className", DDMStructure.class.getName())),
				ddmStructureId, resourceClassNameId,
				jsonObject.getString("ddmTemplateKey"),
				HashMapBuilder.put(
					LocaleUtil.getSiteDefault(), jsonObject.getString("name")
				).build(),
				null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
				TemplateConstants.LANG_TYPE_FTL,
				SiteInitializerUtil.read(_bundle, "ddm-template.ftl", url),
				false, false, null, null, serviceContext);
		}
	}

	private Long _addDocumentFolder(
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

	private Map<String, String> _addDocuments(
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
					_addDocuments(
						_addDocumentFolder(
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

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(fileEntry));

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

	private Map<String, String> _addDocuments(
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(
			serviceContext.getCompanyId());

		return HashMapBuilder.putAll(
			_addDocuments(
				null, group.getGroupId(), "/site-initializer/documents/company",
				serviceContext, siteNavigationMenuItemSettingsBuilder)
		).putAll(
			_addDocuments(
				null, serviceContext.getScopeGroupId(),
				"/site-initializer/documents/group", serviceContext,
				siteNavigationMenuItemSettingsBuilder)
		).build();
	}

	private void _addFragmentEntries(
			Map<String, String> assetListEntryIdsStringUtilReplaceValues,
			Map<String, String> documentsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		Enumeration<URL> enumeration = _bundle.findEntries(
			"/site-initializer/fragments", StringPool.STAR, true);

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

				json = StringUtil.replace(
					json, "\"[$", "$]\"",
					HashMapBuilder.putAll(
						assetListEntryIdsStringUtilReplaceValues
					).putAll(
						documentsStringUtilReplaceValues
					).build());

				Group scopeGroup = serviceContext.getScopeGroup();

				json = StringUtil.replace(
					json,
					new String[] {"[$GROUP_FRIENDLY_URL$]", "[$GROUP_ID$]"},
					new String[] {
						scopeGroup.getFriendlyURL(),
						String.valueOf(serviceContext.getScopeGroupId())
					});

				zipWriter.addEntry(
					StringUtil.removeFirst(
						fileName, "/site-initializer/fragments/"),
					json);
			}
			else {
				zipWriter.addEntry(
					StringUtil.removeFirst(
						fileName, "/site-initializer/fragments/"),
					url.openStream());
			}
		}

		_fragmentsImporter.importFragmentEntries(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
			zipWriter.getFile(), false);
	}

	private void _addJournalArticles(
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
				_addJournalArticles(
					ddmStructureLocalService, ddmTemplateLocalService,
					_addStructuredContentFolders(
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

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

			Map<Locale, String> titleMap = Collections.singletonMap(
				LocaleUtil.getSiteDefault(), jsonObject.getString("name"));

			String ddmStructureKey = jsonObject.getString("ddmStructureKey");

			ddmStructureLocalService.getStructure(
				serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class), ddmStructureKey);

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
				_journalArticleLocalService.addArticle(
					null, serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), journalFolderId,
					JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0,
					jsonObject.getString("articleId"), false, 1, titleMap, null,
					titleMap,
					StringUtil.replace(
						SiteInitializerUtil.read(
							StringUtil.replace(resourcePath, ".json", ".xml"),
							_servletContext),
						"[$", "$]", documentsStringUtilReplaceValues),
					ddmStructureKey, ddmTemplateKey, null,
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH),
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0, 0,
					0, 0, true, true, false, null, null, null, null,
					serviceContext);

			serviceContext.setAssetCategoryIds(null);
			serviceContext.setAssetTagNames(null);

			DDMStructure ddmStructure = journalArticle.getDDMStructure();

			siteNavigationMenuItemSettingsBuilder.put(
				resourcePath,
				new SiteNavigationMenuItemSetting() {
					{
						className = JournalArticle.class.getName();
						classPK = String.valueOf(
							journalArticle.getResourcePrimKey());
						classTypeId = String.valueOf(
							ddmStructure.getStructureId());
						title = journalArticle.getTitle(
							serviceContext.getLocale());
						type = ResourceActionsUtil.getModelResource(
							serviceContext.getLocale(),
							JournalArticle.class.getName());
					}
				});
		}
	}

	private void _addJournalArticles(
			DDMStructureLocalService ddmStructureLocalService,
			DDMTemplateLocalService ddmTemplateLocalService,
			Map<String, String> documentsStringUtilReplaceValues,
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		_addJournalArticles(
			ddmStructureLocalService, ddmTemplateLocalService, null,
			documentsStringUtilReplaceValues,
			"/site-initializer/journal-articles", serviceContext,
			siteNavigationMenuItemSettingsBuilder);
	}

	private Layout _addLayout(
			String resourcePath, ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			SiteInitializerUtil.read(
				resourcePath + "page.json", _servletContext));

		String type = StringUtil.toLowerCase(jsonObject.getString("type"));

		if (Objects.equals(type, "widget")) {
			type = LayoutConstants.TYPE_PORTLET;
		}

		return _layoutLocalService.addLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			jsonObject.getBoolean("private"),
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			SiteInitializerUtil.toMap(jsonObject.getString("name_i18n")),
			SiteInitializerUtil.toMap(jsonObject.getString("title_i18n")),
			SiteInitializerUtil.toMap(jsonObject.getString("description_i18n")),
			SiteInitializerUtil.toMap(jsonObject.getString("keywords_i18n")),
			SiteInitializerUtil.toMap(jsonObject.getString("robots_i18n")),
			type, null, jsonObject.getBoolean("hidden"),
			jsonObject.getBoolean("system"),
			SiteInitializerUtil.toMap(jsonObject.getString("friendlyURL_i18n")),
			serviceContext);
	}

	private void _addLayoutContent(
			Map<String, String> assetListEntryIdsStringUtilReplaceValues,
			Map<String, String> documentsStringUtilReplaceValues, Layout layout,
			Map<String, String> remoteAppEntryIdsStringUtilReplaceValues,
			String resourcePath, ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			SiteInitializerUtil.read(
				resourcePath + "page.json", _servletContext));

		String json = SiteInitializerUtil.read(
			resourcePath + "page-definition.json", _servletContext);

		if (json == null) {
			return;
		}

		json = StringUtil.replace(
			json, "[$", "$]",
			HashMapBuilder.putAll(
				assetListEntryIdsStringUtilReplaceValues
			).putAll(
				documentsStringUtilReplaceValues
			).putAll(
				remoteAppEntryIdsStringUtilReplaceValues
			).build());

		JSONObject pageDefinitionJSONObject = JSONFactoryUtil.createJSONObject(
			json);

		Layout draftLayout = layout.fetchDraftLayout();

		String type = StringUtil.toLowerCase(jsonObject.getString("type"));

		if (Objects.equals(type, "widget")) {
			type = LayoutConstants.TYPE_PORTLET;
		}

		if (Objects.equals(type, LayoutConstants.TYPE_COLLECTION) ||
			Objects.equals(type, LayoutConstants.TYPE_CONTENT)) {

			JSONObject pageElementJSONObject =
				pageDefinitionJSONObject.getJSONObject("pageElement");

			if ((pageElementJSONObject != null) &&
				Objects.equals(
					pageElementJSONObject.getString("type"), "Root")) {

				LayoutPageTemplateStructure layoutPageTemplateStructure =
					_layoutPageTemplateStructureLocalService.
						fetchLayoutPageTemplateStructure(
							draftLayout.getGroupId(), draftLayout.getPlid(),
							true);

				LayoutStructure layoutStructure = LayoutStructure.of(
					layoutPageTemplateStructure.getData(
						SegmentsExperienceConstants.ID_DEFAULT));

				JSONArray jsonArray = pageElementJSONObject.getJSONArray(
					"pageElements");

				for (int i = 0; i < jsonArray.length(); i++) {
					_layoutPageTemplatesImporter.importPageElement(
						draftLayout, layoutStructure,
						layoutStructure.getMainItemId(), jsonArray.getString(i),
						i);
				}
			}
		}

		if (Objects.equals(type, LayoutConstants.TYPE_COLLECTION)) {
			UnicodeProperties unicodeProperties =
				draftLayout.getTypeSettingsProperties();

			Object[] typeSettings = JSONUtil.toObjectArray(
				jsonObject.getJSONArray("typeSettings"));

			for (Object typeSetting : typeSettings) {
				JSONObject typeSettingJSONObject = (JSONObject)typeSetting;

				String key = typeSettingJSONObject.getString("key");
				String value = typeSettingJSONObject.getString("value");

				unicodeProperties.put(
					key,
					StringUtil.replace(
						value, "[$", "$]",
						assetListEntryIdsStringUtilReplaceValues));
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

			layout = _layoutCopyHelper.copyLayout(draftLayout, layout);

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
			ServiceContext serviceContext)
		throws Exception {

		Enumeration<URL> enumeration = _bundle.findEntries(
			"/site-initializer/layout-page-templates", StringPool.STAR, true);

		if (enumeration == null) {
			return;
		}

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			// Begin LPS-146172

			String fileName = url.getFile();

			int index = fileName.lastIndexOf(CharPool.FORWARD_SLASH);

			if ((index == -1) || (index >= (fileName.length() - 1))) {
				continue;
			}

			fileName = fileName.substring(index + 1);

			if (Validator.isNull(fileName)) {
				continue;
			}

			// End LPS-146172

			String urlPath = url.getPath();

			if (StringUtil.endsWith(urlPath, "page-definition.json")) {
				String json = StringUtil.read(url.openStream());

				json = StringUtil.replace(
					json, "\"[$", "$]\"",
					HashMapBuilder.putAll(
						assetListEntryIdsStringUtilReplaceValues
					).putAll(
						documentsStringUtilReplaceValues
					).build());

				Group scopeGroup = serviceContext.getScopeGroup();

				json = StringUtil.replace(
					json,
					new String[] {"[$GROUP_FRIENDLY_URL$]", "[$GROUP_ID$]"},
					new String[] {
						scopeGroup.getFriendlyURL(),
						String.valueOf(serviceContext.getScopeGroupId())
					});

				String css = SiteInitializerUtil.read(
					FileUtil.getPath(urlPath) + "/css.css", _servletContext);

				if (Validator.isNotNull(css)) {
					JSONObject jsonObject = _jsonFactory.createJSONObject(json);

					JSONObject settingsJSONObject = jsonObject.getJSONObject(
						"settings");

					settingsJSONObject.put("css", css);

					jsonObject.put("settings", settingsJSONObject);

					json = jsonObject.toString();
				}

				zipWriter.addEntry(
					StringUtil.removeFirst(
						urlPath, "/site-initializer/layout-page-templates"),
					json);
			}
			else {
				zipWriter.addEntry(
					StringUtil.removeFirst(
						urlPath, "/site-initializer/layout-page-templates"),
					url.openStream());
			}
		}

		_layoutPageTemplatesImporter.importFile(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			zipWriter.getFile(), false);
	}

	private Map<String, Layout> _addLayouts(ServiceContext serviceContext)
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
				Layout layout = _addLayout(resourcePath, serviceContext);

				layouts.put(resourcePath, layout);
			}
		}

		return layouts;
	}

	private void _addLayoutsContent(
			Map<String, String> assetListEntryIdsStringUtilReplaceValues,
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, Layout> layouts,
			Map<String, String> remoteAppEntryIdsStringUtilReplaceValues,
			ServiceContext serviceContext,
			Map<String, SiteNavigationMenuItemSetting>
				siteNavigationMenuItemSettings)
		throws Exception {

		for (Map.Entry<String, Layout> entry : layouts.entrySet()) {
			_addLayoutContent(
				assetListEntryIdsStringUtilReplaceValues,
				documentsStringUtilReplaceValues, entry.getValue(),
				remoteAppEntryIdsStringUtilReplaceValues, entry.getKey(),
				serviceContext);
		}

		_addSiteNavigationMenus(serviceContext, siteNavigationMenuItemSettings);
	}

	private Map<String, String> _addListTypeDefinitions(
			ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/list-type-definitions");

		Map<String, String> listTypeDefinitionIdsStringUtilReplaceValues =
			new HashMap<>();

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

			Page<ListTypeDefinition> listTypeDefinitionsPage =
				listTypeDefinitionResource.getListTypeDefinitionsPage(
					null, null,
					listTypeDefinitionResource.toFilter(
						StringBundler.concat(
							"name eq '", listTypeDefinition.getName(), "'")),
					null, null);

			ListTypeDefinition existingListTypeDefinition =
				listTypeDefinitionsPage.fetchFirstItem();

			if (existingListTypeDefinition == null) {
				listTypeDefinition =
					listTypeDefinitionResource.postListTypeDefinition(
						listTypeDefinition);
			}
			else {
				listTypeDefinition =
					listTypeDefinitionResource.putListTypeDefinition(
						existingListTypeDefinition.getId(), listTypeDefinition);
			}

			listTypeDefinitionIdsStringUtilReplaceValues.put(
				"LIST_TYPE_DEFINITION_ID:" + listTypeDefinition.getName(),
				String.valueOf(listTypeDefinition.getId()));

			String listTypeEntriesJSON = SiteInitializerUtil.read(
				StringUtil.replace(
					resourcePath, ".json", ".list-type-entries.json"),
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

				Page<ListTypeEntry> listTypeEntriesPage =
					listTypeEntryResource.
						getListTypeDefinitionListTypeEntriesPage(
							listTypeDefinition.getId(), null, null,
							listTypeEntryResource.toFilter(
								StringBundler.concat(
									"key eq '", listTypeEntry.getKey(), "'")),
							null, null);

				ListTypeEntry existingListTypeEntry =
					listTypeEntriesPage.fetchFirstItem();

				if (existingListTypeEntry == null) {
					listTypeEntryResource.postListTypeDefinitionListTypeEntry(
						listTypeDefinition.getId(), listTypeEntry);
				}
				else {
					listTypeEntryResource.putListTypeEntry(
						existingListTypeEntry.getId(), listTypeEntry);
				}
			}
		}

		return listTypeDefinitionIdsStringUtilReplaceValues;
	}

	private Map<String, String> _addObjectDefinitions(
			Map<String, String> listTypeDefinitionIdsStringUtilReplaceValues,
			ObjectDefinitionResource objectDefinitionResource,
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		Map<String, String> objectDefinitionIdsStringUtilReplaceValues =
			new HashMap<>();

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/object-definitions");

		if (SetUtil.isEmpty(resourcePaths)) {
			return objectDefinitionIdsStringUtilReplaceValues;
		}

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith(".object-entries.json")) {
				continue;
			}

			String json = SiteInitializerUtil.read(
				resourcePath, _servletContext);

			json = StringUtil.replace(
				json, "[$", "$]", listTypeDefinitionIdsStringUtilReplaceValues);

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

			long groupId = serviceContext.getScopeGroupId();

			if (Objects.equals(
					objectDefinition.getScope(),
					ObjectDefinitionConstants.SCOPE_COMPANY)) {

				groupId = 0;

				if (existingObjectDefinition != null) {
					continue;
				}
			}

			String objectEntriesJSON = SiteInitializerUtil.read(
				StringUtil.replaceLast(
					resourcePath, ".json", ".object-entries.json"),
				_servletContext);

			if (objectEntriesJSON == null) {
				continue;
			}

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
				objectEntriesJSON);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				ObjectEntry objectEntry =
					_objectEntryLocalService.addObjectEntry(
						serviceContext.getUserId(), groupId,
						objectDefinition.getId(),
						ObjectMapperUtil.readValue(
							Serializable.class, String.valueOf(jsonObject)),
						serviceContext);

				String objectEntrySiteInitializerKey = jsonObject.getString(
					"objectEntrySiteInitializerKey");

				if (objectEntrySiteInitializerKey == null) {
					continue;
				}

				String objectDefinitionName = objectDefinition.getName();

				siteNavigationMenuItemSettingsBuilder.put(
					objectEntrySiteInitializerKey,
					new SiteNavigationMenuItemSetting() {
						{
							className = objectEntry.getModelClassName();
							classPK = String.valueOf(
								objectEntry.getObjectEntryId());
							title =
								objectDefinitionName + StringPool.SPACE +
									objectEntry.getObjectEntryId();
						}
					});
			}
		}

		return objectDefinitionIdsStringUtilReplaceValues;
	}

	private void _addObjectRelationships(
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

			json = StringUtil.replace(
				json, "[$", "$]", objectDefinitionIdsStringUtilReplaceValues);

			ObjectRelationship objectRelationship = ObjectRelationship.toDTO(
				json);

			if (objectRelationship == null) {
				_log.error(
					"Unable to transform object relationship from JSON: " +
						json);

				continue;
			}

			Page<ObjectRelationship> objectRelationshipsPage =
				objectRelationshipResource.
					getObjectDefinitionObjectRelationshipsPage(
						objectRelationship.getObjectDefinitionId1(), null,
						objectRelationshipResource.toFilter(
							StringBundler.concat(
								"name eq '", objectRelationship.getName(),
								"'")),
						null);

			ObjectRelationship existingObjectRelationship =
				objectRelationshipsPage.fetchFirstItem();

			if (existingObjectRelationship == null) {
				objectRelationshipResource.
					postObjectDefinitionObjectRelationship(
						objectRelationship.getObjectDefinitionId1(),
						objectRelationship);
			}
			else {
				objectRelationshipResource.putObjectRelationship(
					existingObjectRelationship.getId(), objectRelationship);
			}
		}
	}

	private void _addPermissions(
			Map<String, String> objectDefinitionIdsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		_addRoles(objectDefinitionIdsStringUtilReplaceValues, serviceContext);

		_addUserAccounts(serviceContext);

		_addUserRoles(serviceContext);
	}

	private Map<String, String> _addRemoteAppEntries(
			Map<String, String> documentsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/remote-app-entries.json", _servletContext);

		if (json == null) {
			return Collections.emptyMap();
		}

		Map<String, String> remoteAppEntryIdsStringUtilReplaceValues =
			new HashMap<>();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

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

			RemoteAppEntry remoteAppEntry =
				_remoteAppEntryLocalService.
					addOrUpdateCustomElementRemoteAppEntry(
						jsonObject.getString("externalReferenceCode"),
						serviceContext.getUserId(),
						StringUtil.replace(
							StringUtil.merge(
								JSONUtil.toStringArray(
									jsonObject.getJSONArray("cssURLs")),
								StringPool.NEW_LINE),
							"[$", "$]", documentsStringUtilReplaceValues),
						jsonObject.getString("htmlElementName"),
						StringUtil.replace(
							StringUtil.merge(
								JSONUtil.toStringArray(
									jsonObject.getJSONArray("elementURLs")),
								StringPool.NEW_LINE),
							"[$", "$]", documentsStringUtilReplaceValues),
						false, StringPool.BLANK, StringPool.BLANK,
						jsonObject.getBoolean("instanceable"),
						SiteInitializerUtil.toMap(
							jsonObject.getString("name_i18n")),
						jsonObject.getString("portletCategoryName"),
						sb.toString(), StringPool.BLANK);

			remoteAppEntryIdsStringUtilReplaceValues.put(
				"REMOTE_APP_ENTRY_ID:" +
					jsonObject.getString("remoteAppEntryKey"),
				StringUtil.replace(
					jsonObject.getString("widgetName"),
					StringBundler.concat(
						"[$REMOTE_APP_ENTRY_ID:",
						jsonObject.getString("remoteAppEntryKey"), "$]"),
					String.valueOf(remoteAppEntry.getRemoteAppEntryId())));
		}

		return remoteAppEntryIdsStringUtilReplaceValues;
	}

	private void _addResourcePermissions(
			Map<String, String> objectDefinitionIdsStringUtilReplaceValues,
			String resourcePath, ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(resourcePath, _servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			StringUtil.replace(
				json, "[$", "$]", objectDefinitionIdsStringUtilReplaceValues));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			ResourceAction resourceAction =
				_resourceActionLocalService.fetchResourceAction(
					jsonObject.getString("resourceName"),
					jsonObject.getString("actionId"));

			if (resourceAction == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"No resource action found with resourceName ",
							jsonObject.getString("resourceName"),
							" with the actionId: ",
							jsonObject.getString("actionId")));
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

			_resourcePermissionLocalService.addResourcePermission(
				serviceContext.getCompanyId(),
				jsonObject.getString("resourceName"), scope,
				jsonObject.getString("primKey"), role.getRoleId(),
				jsonObject.getString("actionId"));
		}
	}

	private void _addRole(JSONObject jsonObject, ServiceContext serviceContext)
		throws Exception {

		String name = jsonObject.getString("name");

		Role role = _roleLocalService.fetchRole(
			serviceContext.getCompanyId(), name);

		if (role == null) {
			if (jsonObject.getInt("type") == RoleConstants.TYPE_ACCOUNT) {
				_accountRoleLocalService.addAccountRole(
					serviceContext.getUserId(),
					AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, name,
					Collections.singletonMap(serviceContext.getLocale(), name),
					SiteInitializerUtil.toMap(
						jsonObject.getString("description")));
			}
			else {
				role = _roleLocalService.addRole(
					serviceContext.getUserId(), null, 0, name,
					Collections.singletonMap(serviceContext.getLocale(), name),
					SiteInitializerUtil.toMap(
						jsonObject.getString("description")),
					jsonObject.getInt("type"), jsonObject.getString("subtype"),
					serviceContext);
			}
		}

		JSONArray jsonArray = jsonObject.getJSONArray("actions");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject actionsJSONObject = jsonArray.getJSONObject(i);

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
					ResourceConstants.SCOPE_GROUP, role.getRoleId(), actionId);

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

	private void _addRoles(
			Map<String, String> objectDefinitionIdsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/roles.json", _servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			_addRole(jsonArray.getJSONObject(i), serviceContext);
		}

		_addResourcePermissions(
			objectDefinitionIdsStringUtilReplaceValues,
			"/site-initializer/resource-permissions.json", serviceContext);
	}

	private void _addSAPEntries(ServiceContext serviceContext)
		throws Exception {

		String json = SiteInitializerUtil.read(
			"/site-initializer/sap-entries.json", _servletContext);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

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

	private void _addSiteConfiguration(ServiceContext serviceContext)
		throws Exception {

		String resourcePath = "site-initializer/site-configuration.json";

		String json = SiteInitializerUtil.read(resourcePath, _servletContext);

		if (json == null) {
			return;
		}

		Group group = _groupLocalService.getGroup(
			serviceContext.getScopeGroupId());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

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
				jsonObject.getString("name"), serviceContext);

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

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			_addSiteNavigationMenu(
				jsonArray.getJSONObject(i), serviceContext,
				siteNavigationMenuItemSettings);
		}
	}

	private Long _addStructuredContentFolders(
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

		if (documentFolderId != null) {
			structuredContentFolder =
				structuredContentFolderResource.
					postStructuredContentFolderStructuredContentFolder(
						documentFolderId, structuredContentFolder);
		}
		else {
			structuredContentFolder =
				structuredContentFolderResource.postSiteStructuredContentFolder(
					serviceContext.getScopeGroupId(), structuredContentFolder);
		}

		return structuredContentFolder.getId();
	}

	private void _addStyleBookEntries(ServiceContext serviceContext)
		throws Exception {

		URL url = _bundle.getEntry("/style-books.zip");

		if (url == null) {
			return;
		}

		_styleBookEntryZipProcessor.importStyleBookEntries(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			FileUtil.createTempFile(url.openStream()), false);
	}

	private void _addTaxonomyCategories(
			long groupId, String parentResourcePath,
			String parentTaxonomyCategoryId, ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder,
			long taxonomyVocabularyId)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
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
				taxonomyCategory = _addTaxonomyVocabularyTaxonomyCategory(
					serviceContext, taxonomyCategory, taxonomyVocabularyId);
			}
			else {
				taxonomyCategory = _addTaxonomyCategoryTaxonomyCategory(
					parentTaxonomyCategoryId, serviceContext, taxonomyCategory);
			}

			TaxonomyCategory finalTaxonomyCategory = taxonomyCategory;

			siteNavigationMenuItemSettingsBuilder.put(
				resourcePath,
				new SiteNavigationMenuItemSetting() {
					{
						className = AssetCategory.class.getName();
						classPK = finalTaxonomyCategory.getId();
						title = finalTaxonomyCategory.getName();
					}
				});

			_addTaxonomyCategories(
				groupId, StringUtil.replaceLast(resourcePath, ".json", "/"),
				taxonomyCategory.getId(), serviceContext,
				siteNavigationMenuItemSettingsBuilder, taxonomyVocabularyId);
		}
	}

	private TaxonomyCategory _addTaxonomyCategoryTaxonomyCategory(
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
				parentTaxonomyCategoryId, "",
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

	private void _addTaxonomyVocabularies(
			long groupId, String parentResourcePath,
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
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
					groupId, "",
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

			_addTaxonomyCategories(
				groupId, StringUtil.replaceLast(resourcePath, ".json", "/"),
				null, serviceContext, siteNavigationMenuItemSettingsBuilder,
				taxonomyVocabulary.getId());
		}
	}

	private void _addTaxonomyVocabularies(
			ServiceContext serviceContext,
			SiteNavigationMenuItemSettingsBuilder
				siteNavigationMenuItemSettingsBuilder)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(
			serviceContext.getCompanyId());

		_addTaxonomyVocabularies(
			group.getGroupId(),
			"/site-initializer/taxonomy-vocabularies/company", serviceContext,
			siteNavigationMenuItemSettingsBuilder);

		_addTaxonomyVocabularies(
			serviceContext.getScopeGroupId(),
			"/site-initializer/taxonomy-vocabularies/group", serviceContext,
			siteNavigationMenuItemSettingsBuilder);
	}

	private TaxonomyCategory _addTaxonomyVocabularyTaxonomyCategory(
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
					vocabularyId, "",
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
			).build();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray accountBriefsJSONArray = jsonObject.getJSONArray(
				"accountBriefs");

			if (accountBriefsJSONArray.length() == 0) {
				continue;
			}

			int j = 0;

			UserAccount userAccount = UserAccount.toDTO(
				String.valueOf(jsonObject));

			User existingUserAccount =
				_userLocalService.fetchUserByEmailAddress(
					serviceContext.getCompanyId(),
					userAccount.getEmailAddress());

			if (existingUserAccount == null) {
				JSONObject accountBriefsJSONObject =
					accountBriefsJSONArray.getJSONObject(j);

				userAccountResource.
					postAccountUserAccountByExternalReferenceCode(
						accountBriefsJSONObject.getString(
							"externalReferenceCode"),
						userAccount);

				j++;

				_associateUserAccounts(
					accountBriefsJSONObject,
					jsonObject.getString("emailAddress"), serviceContext);
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
			List<Role> roles = new ArrayList<>();

			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray rolesJSONArray = jsonObject.getJSONArray("roles");

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
			ObjectDefinitionResource objectDefinitionResource,
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
				JSONFactoryUtil.createJSONObject(
					SiteInitializerUtil.read(
						resourcePath + "workflow-definition.json",
						_servletContext));

			workflowDefinitionJSONObject.put(
				"content",
				SiteInitializerUtil.read(
					resourcePath + "workflow-definition.xml", _servletContext));

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

			JSONArray propertiesJSONArray = JSONFactoryUtil.createJSONArray(
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

					Page<ObjectDefinition> objectDefinitionsPage =
						objectDefinitionResource.getObjectDefinitionsPage(
							null, null,
							objectDefinitionResource.toFilter(
								StringBundler.concat(
									"name eq '",
									propertiesJSONObject.getString("assetName"),
									"'")),
							null, null);

					ObjectDefinition objectDefinition =
						objectDefinitionsPage.fetchFirstItem();

					if (objectDefinition == null) {
						continue;
					}

					className = StringBundler.concat(
						className, "#", objectDefinition.getId());
				}

				long typePK = 0;

				if (StringUtil.equals(
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

		AccountRoleResource.Builder builder =
			_accountRoleResourceFactory.create();

		AccountRoleResource accountRoleResource = builder.user(
			serviceContext.fetchUser()
		).build();

		JSONArray jsonArray = accountBriefsJSONObject.getJSONArray(
			"roleBriefs");

		for (int i = 0; i < jsonArray.length(); i++) {
			Role role = _roleLocalService.fetchRole(
				serviceContext.getCompanyId(), jsonArray.getString(i));

			if (role == null) {
				continue;
			}

			AccountRole accountRole =
				_accountRoleLocalService.fetchAccountRoleByRoleId(
					role.getRoleId());

			if (accountRole == null) {
				continue;
			}

			accountRoleResource.
				postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
					accountBriefsJSONObject.getString("externalReferenceCode"),
					accountRole.getAccountRoleId(), emailAddress);
		}
	}

	private long[] _getAssetCategoryIds(
		long groupId, String[] externalReferenceCodes) {

		List<Long> assetCategoryIds = new ArrayList<>();

		for (String externalReferenceCode : externalReferenceCodes) {
			AssetCategory assetCategory =
				_assetCategoryLocalService.
					fetchAssetCategoryByExternalReferenceCode(
						groupId, externalReferenceCode);

			if (assetCategory != null) {
				assetCategoryIds.add(assetCategory.getCategoryId());
			}
		}

		return ArrayUtil.toLongArray(assetCategoryIds);
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

	private void _updateLayoutSet(
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

		JSONObject metadataJSONObject = JSONFactoryUtil.createJSONObject(
			(metadataJSON == null) ? "{}" : metadataJSON);

		String css = GetterUtil.getString(
			SiteInitializerUtil.read(
				resourcePath + "/css.css", _servletContext));

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

	private void _updateLayoutSets(ServiceContext serviceContext)
		throws Exception {

		_updateLayoutSet(false, serviceContext);
		_updateLayoutSet(true, serviceContext);
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
	private CommerceSiteInitializer _commerceSiteInitializer;
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
	private final LayoutCopyHelper _layoutCopyHelper;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;
	private final LayoutPageTemplatesImporter _layoutPageTemplatesImporter;
	private final LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;
	private final LayoutSetLocalService _layoutSetLocalService;
	private final ListTypeDefinitionResource _listTypeDefinitionResource;
	private final ListTypeDefinitionResource.Factory
		_listTypeDefinitionResourceFactory;
	private final ListTypeEntryResource _listTypeEntryResource;
	private final ListTypeEntryResource.Factory _listTypeEntryResourceFactory;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectRelationshipResource.Factory
		_objectRelationshipResourceFactory;
	private final Portal _portal;
	private final RemoteAppEntryLocalService _remoteAppEntryLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;
	private final SAPEntryLocalService _sapEntryLocalService;
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
	private final ThemeLocalService _themeLocalService;
	private final UserAccountResource.Factory _userAccountResourceFactory;
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