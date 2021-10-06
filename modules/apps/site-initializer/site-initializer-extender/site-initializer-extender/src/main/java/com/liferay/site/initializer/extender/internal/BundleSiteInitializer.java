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

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.util.CommerceAccountRoleHelper;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.initializer.util.CPDefinitionsImporter;
import com.liferay.commerce.initializer.util.CommerceInventoryWarehousesImporter;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Catalog;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CatalogResource;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ChannelResource;
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
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.UnsafeSupplier;
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
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.template.TemplateConstants;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
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
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;

import java.io.InputStream;
import java.io.Serializable;

import java.net.URL;
import java.net.URLConnection;

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
		AssetListEntryLocalService assetListEntryLocalService, Bundle bundle,
		CatalogResource.Factory catalogResourceFactory,
		ChannelResource.Factory channelResourceFactory,
		CommerceAccountRoleHelper commerceAccountRoleHelper,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceInventoryWarehousesImporter commerceInventoryWarehousesImporter,
		CPDefinitionsImporter cpDefinitionsImporter,
		CPFileImporter cpFileImporter,
		CPMeasurementUnitLocalService cpMeasurementUnitLocalService,
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
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		ObjectEntryLocalService objectEntryLocalService, Portal portal,
		RemoteAppEntryLocalService remoteAppEntryLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService,
		SAPEntryLocalService sapEntryLocalService,
		ServletContext servletContext, SettingsFactory settingsFactory,
		SiteNavigationMenuItemLocalService siteNavigationMenuItemLocalService,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry,
		SiteNavigationMenuLocalService siteNavigationMenuLocalService,
		StructuredContentFolderResource.Factory
			structuredContentFolderResourceFactory,
		StyleBookEntryZipProcessor styleBookEntryZipProcessor,
		TaxonomyCategoryResource.Factory taxonomyCategoryResourceFactory,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		ThemeLocalService themeLocalService,
		UserLocalService userLocalService) {

		_assetListEntryLocalService = assetListEntryLocalService;
		_bundle = bundle;
		_catalogResourceFactory = catalogResourceFactory;
		_channelResourceFactory = channelResourceFactory;
		_commerceAccountRoleHelper = commerceAccountRoleHelper;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commerceInventoryWarehousesImporter =
			commerceInventoryWarehousesImporter;
		_cpDefinitionsImporter = cpDefinitionsImporter;
		_cpFileImporter = cpFileImporter;
		_cpMeasurementUnitLocalService = cpMeasurementUnitLocalService;
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
		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;
		_objectEntryLocalService = objectEntryLocalService;
		_portal = portal;
		_remoteAppEntryLocalService = remoteAppEntryLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
		_sapEntryLocalService = sapEntryLocalService;
		_servletContext = servletContext;
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
		_userLocalService = userLocalService;

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		_classLoader = bundleWiring.getClassLoader();
	}

	@Override
	public String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return _bundle.getSymbolicName();
	}

	@Override
	public String getName(Locale locale) {
		Dictionary<String, String> headers = _bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getString(headers.get("Bundle-Name"));
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/images/thumbnail.png";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		long startTime = System.currentTimeMillis();

		if (_log.isDebugEnabled()) {
			_log.debug(
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

			_invoke(() -> _addPermissions(serviceContext));

			_invoke(() -> _addDDMStructures(serviceContext));
			_invoke(() -> _addFragmentEntries(serviceContext));
			_invoke(() -> _addLayoutPageTemplates(serviceContext));
			_invoke(() -> _addObjectDefinitions(serviceContext));
			_invoke(() -> _addSAPEntries(serviceContext));
			_invoke(() -> _addStyleBookEntries(serviceContext));
			_invoke(() -> _addTaxonomyVocabularies(serviceContext));

			_invoke(() -> _addCPDefinitions(serviceContext));

			_invoke(() -> _updateLayoutSets(serviceContext));

			Map<String, String> documentsStringUtilReplaceValues = _invoke(
				() -> _addDocuments(serviceContext));

			_invoke(
				() -> _addAssetListEntries(
					_ddmStructureLocalService, serviceContext));
			_invoke(
				() -> _addDDMTemplates(
					_ddmStructureLocalService, serviceContext));
			_invoke(
				() -> _addJournalArticles(
					_ddmStructureLocalService, _ddmTemplateLocalService,
					documentsStringUtilReplaceValues, serviceContext));

			Map<String, String> remoteAppEntryIdsStringUtilReplaceValues =
				_invoke(
					() -> _addRemoteAppEntries(
						documentsStringUtilReplaceValues, serviceContext));

			_invoke(
				() -> _addLayouts(
					_assetListEntryLocalService,
					documentsStringUtilReplaceValues,
					remoteAppEntryIdsStringUtilReplaceValues, serviceContext));
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new InitializationException(exception);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Initialized ", getKey(), " for group ", groupId, " in ",
					System.currentTimeMillis() - startTime, " ms"));
		}
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	private void _addAssetListEntries(
			DDMStructureLocalService ddmStructureLocalService,
			ServiceContext serviceContext)
		throws Exception {

		String json = _read("/site-initializer/asset-list-entries.json");

		if (json == null) {
			return;
		}

		JSONArray assetListJSONArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < assetListJSONArray.length(); i++) {
			JSONObject assetListJSONObject = assetListJSONArray.getJSONObject(
				i);

			_addAssetListEntry(
				assetListJSONObject, ddmStructureLocalService, serviceContext);
		}
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
			assetListJSONObject.getJSONArray("tags"));

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
			String.valueOf(new UnicodeProperties(map, true)), serviceContext);
	}

	private void _addCommerceCatalogs(
			Channel channel,
			List<CommerceInventoryWarehouse> commerceInventoryWarehouses,
			ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/commerce-catalogs");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		CatalogResource.Builder catalogResourceBuilder =
			_catalogResourceFactory.create();

		CatalogResource catalogResource = catalogResourceBuilder.user(
			serviceContext.fetchUser()
		).build();

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith(".products.json")) {
				continue;
			}

			String json = _read(resourcePath);

			Catalog catalog = Catalog.toDTO(json);

			if (catalog == null) {
				_log.error(
					"Unable to transform commerce catalog from JSON: " + json);

				continue;
			}

			catalog = catalogResource.postCatalog(catalog);

			_addCPDefinitions(
				catalog, channel, commerceInventoryWarehouses,
				StringUtil.replaceLast(resourcePath, ".json", ".products.json"),
				serviceContext);
		}
	}

	private Channel _addCommerceChannel(ServiceContext serviceContext)
		throws Exception {

		String resourcePath = "/site-initializer/commerce-channel.json";

		String json = _read(resourcePath);

		if (json == null) {
			return null;
		}

		ChannelResource.Builder channelResourceBuilder =
			_channelResourceFactory.create();

		ChannelResource channelResource = channelResourceBuilder.user(
			serviceContext.fetchUser()
		).build();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		jsonObject.put("siteGroupId", serviceContext.getScopeGroupId());

		Channel channel = Channel.toDTO(jsonObject.toString());

		if (channel == null) {
			_log.error(
				"Unable to transform commerce channel from JSON: " + json);

			return null;
		}

		channel = channelResource.postChannel(channel);

		_addModelResourcePermissions(
			CommerceChannel.class.getName(), String.valueOf(channel.getId()),
			StringUtil.replaceLast(
				resourcePath, ".json", ".model-resource-permissions.json"),
			serviceContext);

		Group group = _groupLocalService.getGroup(
			serviceContext.getScopeGroupId());

		group.setType(GroupConstants.TYPE_SITE_PRIVATE);
		group.setManualMembership(true);
		group.setMembershipRestriction(
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION);

		_groupLocalService.updateGroup(group);

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				serviceContext.getScopeGroupId(),
				CommerceAccountConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue(
			"commerceSiteType",
			String.valueOf(CommerceAccountConstants.SITE_TYPE_B2C));

		modifiableSettings.store();

		_commerceAccountRoleHelper.checkCommerceAccountRoles(serviceContext);
		_commerceCurrencyLocalService.importDefaultValues(serviceContext);
		_cpMeasurementUnitLocalService.importDefaultValues(serviceContext);

		return channel;
	}

	private List<CommerceInventoryWarehouse> _addCommerceInventoryWarehouses(
			ServiceContext serviceContext)
		throws Exception {

		return _commerceInventoryWarehousesImporter.
			importCommerceInventoryWarehouses(
				JSONFactoryUtil.createJSONArray(
					_read(
						"/site-initializer" +
							"/commerce-inventory-warehouses.json")),
				serviceContext.getScopeGroupId(), serviceContext.getUserId());
	}

	private void _addCPDefinitions(
			Catalog catalog, Channel channel,
			List<CommerceInventoryWarehouse> commerceInventoryWarehouses,
			String resourcePath, ServiceContext serviceContext)
		throws Exception {

		String json = _read(resourcePath);

		if (json == null) {
			return;
		}

		Group commerceCatalogGroup =
			CommerceCatalogLocalServiceUtil.getCommerceCatalogGroup(
				catalog.getId());

		TaxonomyVocabularyResource.Builder taxonomyVocabularyResourceBuilder =
			_taxonomyVocabularyResourceFactory.create();

		TaxonomyVocabularyResource taxonomyVocabularyResource =
			taxonomyVocabularyResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		Group companyGroup = _groupLocalService.getCompanyGroup(
			serviceContext.getCompanyId());

		TaxonomyVocabulary taxonomyVocabulary =
			taxonomyVocabularyResource.
				getSiteTaxonomyVocabularyByExternalReferenceCode(
					companyGroup.getGroupId(),
					channel.getExternalReferenceCode());

		_cpDefinitionsImporter.importCPDefinitions(
			JSONFactoryUtil.createJSONArray(json), taxonomyVocabulary.getName(),
			commerceCatalogGroup.getGroupId(), channel.getId(),
			ListUtil.toLongArray(
				commerceInventoryWarehouses,
				CommerceInventoryWarehouse.
					COMMERCE_INVENTORY_WAREHOUSE_ID_ACCESSOR),
			BundleSiteInitializer.class.getClassLoader(),
			"/site-initializer/commerce-catalogs/" +
				StringUtil.replace(resourcePath, ".json", "/"),
			serviceContext.getScopeGroupId(), serviceContext.getUserId());
	}

	private void _addCPDefinitions(ServiceContext serviceContext)
		throws Exception {

		_addCommerceCatalogs(
			_addCommerceChannel(serviceContext),
			_addCommerceInventoryWarehouses(serviceContext), serviceContext);
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

			DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
				serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class),
				jsonObject.getString("ddmStructureKey"));

			_ddmTemplateLocalService.addTemplate(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(DDMStructure.class),
				ddmStructure.getStructureId(),
				_portal.getClassNameId(JournalArticle.class),
				jsonObject.getString("ddmTemplateKey"),
				HashMapBuilder.put(
					LocaleUtil.getSiteDefault(), jsonObject.getString("name")
				).build(),
				null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
				TemplateConstants.LANG_TYPE_FTL, _read("ddm-template.ftl", url),
				false, false, null, null, serviceContext);
		}
	}

	private Long _addDocumentFolder(
			Long documentFolderId, String resourcePath,
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

		String json = _read(resourcePath + ".metadata.json");

		if (json != null) {
			documentFolder = DocumentFolder.toDTO(json);
		}
		else if (resourcePath.contains("/site-initializer/documents/extra")) {
			documentFolder = DocumentFolder.toDTO(
				JSONUtil.put(
					"name", FileUtil.getShortFileName(resourcePath)
				).put(
					"viewableBy", "Anyone"
				).toString());
		}
		else {
			documentFolder = DocumentFolder.toDTO(
				JSONUtil.put(
					"name", FileUtil.getShortFileName(resourcePath)
				).toString());
		}

		if (documentFolderId != null) {
			documentFolder =
				documentFolderResource.postDocumentFolderDocumentFolder(
					documentFolderId, documentFolder);
		}
		else {
			documentFolder = documentFolderResource.postSiteDocumentFolder(
				serviceContext.getScopeGroupId(), documentFolder);
		}

		return documentFolder.getId();
	}

	private Map<String, String> _addDocuments(
			Long documentFolderId, String parentResourcePath,
			ServiceContext serviceContext)
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
							documentFolderId, resourcePath, serviceContext),
						resourcePath, serviceContext));

				continue;
			}

			if (resourcePath.endsWith(".metadata.json")) {
				continue;
			}

			String fileName = FileUtil.getShortFileName(resourcePath);

			URL url = _servletContext.getResource(resourcePath);

			URLConnection urlConnection = url.openConnection();

			Map<String, String> values = new HashMap<>();

			String json = _read(resourcePath + ".metadata.json");

			if (json != null) {
				values = Collections.singletonMap("document", json);
			}
			else if (resourcePath.contains(
						"/site-initializer/documents/extra")) {

				values = Collections.singletonMap(
					"document",
					JSONUtil.put(
						"viewableBy", "Anyone"
					).toString());
			}

			Document document = null;

			if (documentFolderId != null) {
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
				document = documentResource.postSiteDocument(
					serviceContext.getScopeGroupId(),
					MultipartBody.of(
						Collections.singletonMap(
							"file",
							new BinaryFile(
								MimeTypesUtil.getContentType(fileName),
								fileName, urlConnection.getInputStream(),
								urlConnection.getContentLength())),
						__ -> _objectMapper, values));
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
		}

		return documentsStringUtilReplaceValues;
	}

	private Map<String, String> _addDocuments(ServiceContext serviceContext)
		throws Exception {

		return _addDocuments(
			null, "/site-initializer/documents", serviceContext);
	}

	private void _addFragmentEntries(ServiceContext serviceContext)
		throws Exception {

		URL url = _bundle.getEntry("/fragments.zip");

		if (url == null) {
			return;
		}

		_fragmentsImporter.importFragmentEntries(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
			FileUtil.createTempFile(url.openStream()), false);
	}

	private void _addJournalArticles(
			DDMStructureLocalService ddmStructureLocalService,
			DDMTemplateLocalService ddmTemplateLocalService,
			Long documentFolderId,
			Map<String, String> documentsStringUtilReplaceValues,
			String parentResourcePath, ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			parentResourcePath = resourcePath.substring(
				0, resourcePath.length() - 1);

			if (resourcePath.endsWith("/") &&
				resourcePaths.contains(parentResourcePath + ".metadata.json")) {

				_addJournalArticles(
					ddmStructureLocalService, ddmTemplateLocalService,
					_addStructuredContentFolders(
						documentFolderId, parentResourcePath, serviceContext),
					documentsStringUtilReplaceValues, resourcePath,
					serviceContext);

				continue;
			}

			if (resourcePath.endsWith(".metadata.json") ||
				resourcePath.endsWith(".xml") || resourcePath.endsWith("/")) {

				continue;
			}

			long journalFolderId =
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			if (documentFolderId != null) {
				journalFolderId = documentFolderId;
			}

			String json = _read(resourcePath);

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

			serviceContext.setAssetTagNames(
				JSONUtil.toStringArray(jsonObject.getJSONArray("tags")));

			_journalArticleLocalService.addArticle(
				null, serviceContext.getUserId(),
				serviceContext.getScopeGroupId(), journalFolderId,
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0,
				jsonObject.getString("articleId"), false, 1, titleMap, null,
				titleMap,
				StringUtil.replace(
					_read(StringUtil.replace(resourcePath, ".json", ".xml")),
					"[$", "$]", documentsStringUtilReplaceValues),
				ddmStructureKey, ddmTemplateKey, null,
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0, 0, 0,
				0, true, true, false, null, null, null, null, serviceContext);

			serviceContext.setAssetTagNames(null);
		}
	}

	private void _addJournalArticles(
			DDMStructureLocalService ddmStructureLocalService,
			DDMTemplateLocalService ddmTemplateLocalService,
			Map<String, String> documentsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		_addJournalArticles(
			ddmStructureLocalService, ddmTemplateLocalService, null,
			documentsStringUtilReplaceValues,
			"/site-initializer/journal-articles", serviceContext);
	}

	private void _addLayout(
			Map<String, String> assetListEntryIdsStringUtilReplaceValues,
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String> remoteAppEntryIdsStringUtilReplaceValues,
			String resourcePath, ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_read(resourcePath + "page.json"));

		String type = StringUtil.toLowerCase(jsonObject.getString("type"));

		if (Objects.equals(type, "widget")) {
			type = LayoutConstants.TYPE_PORTLET;
		}

		Layout layout = _layoutLocalService.addLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			jsonObject.getBoolean("private"),
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			_toMap(jsonObject.getString("name_i18n")),
			_toMap(jsonObject.getString("title_i18n")),
			_toMap(jsonObject.getString("description_i18n")),
			_toMap(jsonObject.getString("keywords_i18n")),
			_toMap(jsonObject.getString("robots_i18n")), type, null,
			jsonObject.getBoolean("hidden"), jsonObject.getBoolean("system"),
			_toMap(jsonObject.getString("friendlyURL_i18n")), serviceContext);

		String json = _read(resourcePath + "page-definition.json");

		if (json == null) {
			return;
		}

		json = StringUtil.replace(
			json, "\"[$", "$]\"",
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

	private void _addLayoutPageTemplates(ServiceContext serviceContext)
		throws Exception {

		Enumeration<URL> enumeration = _bundle.findEntries(
			"/site-initializer/layout-page-templates", StringPool.STAR, true);

		if (enumeration == null) {
			return;
		}

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String urlPath = url.getPath();

			if (StringUtil.endsWith(urlPath, ".json")) {
				String json = StringUtil.read(url.openStream());

				Group scopeGroup = serviceContext.getScopeGroup();

				zipWriter.addEntry(
					StringUtil.removeFirst(
						urlPath, "/site-initializer/layout-page-templates/"),
					StringUtil.replace(
						json,
						new String[] {"[$GROUP_FRIENDLY_URL$]", "[$GROUP_ID$]"},
						new String[] {
							scopeGroup.getFriendlyURL(),
							String.valueOf(serviceContext.getScopeGroupId())
						}));
			}
			else {
				zipWriter.addEntry(
					StringUtil.removeFirst(
						urlPath, "/site-initializer/layout-page-templates/"),
					url.openStream());
			}
		}

		_layoutPageTemplatesImporter.importFile(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			zipWriter.getFile(), false);
	}

	private void _addLayouts(
			AssetListEntryLocalService assetListEntryLocalService,
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String> remoteAppEntryIdsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/layouts");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		Set<String> sortedResourcePaths = new TreeSet<>(
			new NaturalOrderStringComparator());

		sortedResourcePaths.addAll(resourcePaths);

		resourcePaths = sortedResourcePaths;

		Map<String, String> assetListEntryIdsStringUtilReplaceValues =
			new HashMap<>();

		List<AssetListEntry> assetListEntries =
			assetListEntryLocalService.getAssetListEntries(
				serviceContext.getScopeGroupId());

		for (AssetListEntry assetListEntry : assetListEntries) {
			String assetListEntryKeyUppercase = StringUtil.toUpperCase(
				assetListEntry.getAssetListEntryKey());

			assetListEntryIdsStringUtilReplaceValues.put(
				"ASSET_LIST_ENTRY_ID:" + assetListEntryKeyUppercase,
				String.valueOf(assetListEntry.getAssetListEntryId()));
		}

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith("/")) {
				_addLayout(
					assetListEntryIdsStringUtilReplaceValues,
					documentsStringUtilReplaceValues,
					remoteAppEntryIdsStringUtilReplaceValues, resourcePath,
					serviceContext);
			}
		}

		_addSiteNavigationMenus(serviceContext);
	}

	private void _addModelResourcePermissions(
			String className, String primKey, String resourcePath,
			ServiceContext serviceContext)
		throws Exception {

		String json = _read(resourcePath);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			_resourcePermissionLocalService.addModelResourcePermissions(
				serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
				serviceContext.getUserId(), className, primKey,
				ModelPermissionsFactory.create(
					HashMapBuilder.put(
						jsonObject.getString("roleName"),
						ArrayUtil.toStringArray(
							jsonObject.getJSONArray("actionIds"))
					).build(),
					null));
		}
	}

	private void _addObjectDefinitions(ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/object-definitions");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		ObjectDefinitionResource.Builder objectDefinitionResourceBuilder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource =
			objectDefinitionResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith(".object-entries.json")) {
				continue;
			}

			String json = _read(resourcePath);

			ObjectDefinition objectDefinition = ObjectDefinition.toDTO(json);

			if (objectDefinition == null) {
				_log.error(
					"Unable to transform object definition from JSON: " + json);

				continue;
			}

			Page<ObjectDefinition> objectDefinitionsPage =
				objectDefinitionResource.getObjectDefinitionsPage(
					objectDefinition.getName(), null);

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

			String objectEntriesJSON = _read(
				StringUtil.replaceLast(
					resourcePath, ".json", ".object-entries.json"));

			if (objectEntriesJSON == null) {
				continue;
			}

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
				objectEntriesJSON);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				_objectEntryLocalService.addObjectEntry(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), objectDefinition.getId(),
					ObjectMapperUtil.readValue(
						Serializable.class, jsonObject.toString()),
					serviceContext);
			}
		}
	}

	private void _addPermissions(ServiceContext serviceContext)
		throws Exception {

		_addRoles(serviceContext);

		_addResourcePermissions(
			"/site-initializer/resource-permissions.json", serviceContext);
	}

	private Map<String, String> _addRemoteAppEntries(
			Map<String, String> documentsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {

		String json = _read("/site-initializer/remote-app-entries.json");

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
				_remoteAppEntryLocalService.addCustomElementRemoteAppEntry(
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
					jsonObject.getBoolean("instanceable"),
					_toMap(jsonObject.getString("name_i18n")),
					jsonObject.getString("portletCategoryName"), sb.toString());

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
			String resourcePath, ServiceContext serviceContext)
		throws Exception {

		String json = _read(resourcePath);

		if (json == null) {
			return;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Role role = _roleLocalService.fetchRole(
				serviceContext.getCompanyId(),
				jsonObject.getString("roleName"));

			if (role == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No role found with name " +
							jsonObject.getString("roleName"));
				}
			}

			_resourcePermissionLocalService.addResourcePermission(
				serviceContext.getCompanyId(),
				jsonObject.getString("resourceName"),
				jsonObject.getInt("scope"), jsonObject.getString("primKey"),
				role.getRoleId(), jsonObject.getString("actionId"));
		}
	}

	private void _addRoles(ServiceContext serviceContext) throws Exception {
		String json = _read("/site-initializer/roles.json");

		if (json == null) {
			return;
		}

		_cpFileImporter.createRoles(
			_jsonFactory.createJSONArray(json), serviceContext);
	}

	private void _addSAPEntries(ServiceContext serviceContext)
		throws Exception {

		String json = _read("/site-initializer/sap-entries.json");

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
					_toMap(jsonObject.getString("title_i18n")), serviceContext);
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
					_toMap(jsonObject.getString("title_i18n")), serviceContext);
			}
		}
	}

	private void _addSiteNavigationMenuItems(
			JSONArray jsonArray, SiteNavigationMenu siteNavigationMenu,
			ServiceContext serviceContext)
		throws Exception {

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				SiteNavigationMenuItemTypeConstants.LAYOUT);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			boolean privateLayout = jsonObject.getBoolean("privateLayout");
			String friendlyURL = jsonObject.getString("friendlyURL");

			Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
				serviceContext.getScopeGroupId(), privateLayout, friendlyURL);

			if (layout == null) {
				continue;
			}

			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT,
				siteNavigationMenuItemType.getTypeSettingsFromLayout(layout),
				serviceContext);
		}
	}

	private void _addSiteNavigationMenus(ServiceContext serviceContext)
		throws Exception {

		String json = _read("/site-initializer/site-navigation-menus.json");

		if (json == null) {
			return;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String name = jsonObject.getString("name");

			SiteNavigationMenu siteNavigationMenu =
				_siteNavigationMenuLocalService.addSiteNavigationMenu(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), name, serviceContext);

			_addSiteNavigationMenuItems(
				jsonObject.getJSONArray("pages"), siteNavigationMenu,
				serviceContext);
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

		String json = _read(parentResourcePath + ".metadata.json");

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

			String json = _read(resourcePath);

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

			_addTaxonomyCategories(
				groupId, StringUtil.replaceLast(resourcePath, ".json", "/"),
				taxonomyCategory.getId(), serviceContext, taxonomyVocabularyId);
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
			ServiceContext serviceContext)
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

			String json = _read(resourcePath);

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
				null, serviceContext, taxonomyVocabulary.getId());
		}
	}

	private void _addTaxonomyVocabularies(ServiceContext serviceContext)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(
			serviceContext.getCompanyId());

		_addTaxonomyVocabularies(
			group.getGroupId(),
			"/site-initializer/taxonomy-vocabularies/company", serviceContext);

		_addTaxonomyVocabularies(
			serviceContext.getScopeGroupId(),
			"/site-initializer/taxonomy-vocabularies/group", serviceContext);
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

	private String _getThemeId(long companyId, String themeName) {
		List<Theme> themes = ListUtil.filter(
			_themeLocalService.getThemes(companyId),
			theme -> Objects.equals(theme.getName(), themeName));

		if (ListUtil.isNotEmpty(themes)) {
			Theme theme = themes.get(0);

			return theme.getThemeId();
		}

		return null;
	}

	private void _invoke(UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		long startTime = System.currentTimeMillis();

		unsafeRunnable.run();

		if (_log.isDebugEnabled()) {
			Thread thread = Thread.currentThread();

			StackTraceElement stackTraceElement = thread.getStackTrace()[2];

			_log.debug(
				StringBundler.concat(
					"Invoking line ", stackTraceElement.getLineNumber(),
					" took ", System.currentTimeMillis() - startTime, " ms"));
		}
	}

	private <T> T _invoke(UnsafeSupplier<T, Exception> unsafeSupplier)
		throws Exception {

		long startTime = System.currentTimeMillis();

		T t = unsafeSupplier.get();

		if (_log.isDebugEnabled()) {
			Thread thread = Thread.currentThread();

			StackTraceElement stackTraceElement = thread.getStackTrace()[2];

			_log.debug(
				StringBundler.concat(
					"Invoking line ", stackTraceElement.getLineNumber(), " in ",
					System.currentTimeMillis() - startTime, " ms"));
		}

		return t;
	}

	private String _read(String resourcePath) throws Exception {
		InputStream inputStream = _servletContext.getResourceAsStream(
			resourcePath);

		if (inputStream == null) {
			return null;
		}

		return StringUtil.read(inputStream);
	}

	private String _read(String fileName, URL url) throws Exception {
		String urlPath = url.getPath();

		URL entryURL = _bundle.getEntry(
			urlPath.substring(0, urlPath.lastIndexOf("/") + 1) + fileName);

		return StringUtil.read(entryURL.openStream());
	}

	private Map<Locale, String> _toMap(String values) {
		if (Validator.isBlank(values)) {
			return Collections.emptyMap();
		}

		Map<Locale, String> map = new HashMap<>();

		Map<String, String> valuesMap = ObjectMapperUtil.readValue(
			HashMap.class, values);

		for (Map.Entry<String, String> entry : valuesMap.entrySet()) {
			map.put(
				LocaleUtil.fromLanguageId(entry.getKey()), entry.getValue());
		}

		return map;
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

		String themeId = draftLayout.getThemeId();

		String themeName = settingsJSONObject.getString("themeName");

		if (Validator.isNotNull(themeName)) {
			themeId = _getThemeId(draftLayout.getCompanyId(), themeName);
		}

		draftLayout = _layoutLocalService.updateLookAndFeel(
			draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
			draftLayout.getLayoutId(), themeId,
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

		JSONObject settingsJSONObject = JSONFactoryUtil.createJSONObject(
			_read(resourcePath + "/settings.json"));

		String themeId = StringPool.BLANK;
		String themeName = settingsJSONObject.getString("themeName", "Classic");

		if (Validator.isNotNull(themeName)) {
			themeId = _getThemeId(serviceContext.getCompanyId(), themeName);
		}

		String css = _read(resourcePath + "/css.css");

		if (css != null) {
			_layoutSetLocalService.updateLookAndFeel(
				serviceContext.getScopeGroupId(), privateLayout, themeId,
				layoutSet.getColorSchemeId(), css);
		}

		URL url = _servletContext.getResource(resourcePath + "/logo.png");

		if (url != null) {
			_layoutSetLocalService.updateLogo(
				serviceContext.getScopeGroupId(), privateLayout, true,
				FileUtil.getBytes(url.openStream()));
		}

		String settingsPropertiesString = _read(
			resourcePath + "/settings.properties");

		if (settingsPropertiesString != null) {
			UnicodeProperties unicodeProperties =
				layoutSet.getSettingsProperties();

			unicodeProperties.fastLoad(settingsPropertiesString);

			_layoutSetLocalService.updateSettings(
				serviceContext.getScopeGroupId(), privateLayout,
				unicodeProperties.toString());
		}
	}

	private void _updateLayoutSets(ServiceContext serviceContext)
		throws Exception {

		_updateLayoutSet(false, serviceContext);
		_updateLayoutSet(true, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BundleSiteInitializer.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	private final AssetListEntryLocalService _assetListEntryLocalService;
	private final Bundle _bundle;
	private final CatalogResource.Factory _catalogResourceFactory;
	private final ChannelResource.Factory _channelResourceFactory;
	private final ClassLoader _classLoader;
	private final CommerceAccountRoleHelper _commerceAccountRoleHelper;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final CommerceInventoryWarehousesImporter
		_commerceInventoryWarehousesImporter;
	private final CPDefinitionsImporter _cpDefinitionsImporter;
	private final CPFileImporter _cpFileImporter;
	private final CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;
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
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final Portal _portal;
	private final RemoteAppEntryLocalService _remoteAppEntryLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;
	private final SAPEntryLocalService _sapEntryLocalService;
	private final ServletContext _servletContext;
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
	private final UserLocalService _userLocalService;

}