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

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeEntryResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.admin.user.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentFolderResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Preston Crary
 */
public class SiteInitializerRegistrar {

	public SiteInitializerRegistrar(
		AccountResource.Factory accountResourceFactory,
		AssetCategoryLocalService assetCategoryLocalService,
		AssetListEntryLocalService assetListEntryLocalService, Bundle bundle,
		BundleContext bundleContext,
		CommerceReferencesHolder commerceReferencesHolder,
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
		UserLocalService userLocalService) {

		_accountResourceFactory = accountResourceFactory;
		_assetCategoryLocalService = assetCategoryLocalService;
		_assetListEntryLocalService = assetListEntryLocalService;
		_bundle = bundle;
		_bundleContext = bundleContext;
		_commerceReferencesHolder = commerceReferencesHolder;
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
	}

	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected void start() {
		_serviceRegistration = _bundleContext.registerService(
			SiteInitializer.class,
			new BundleSiteInitializer(
				_accountResourceFactory, _assetCategoryLocalService,
				_assetListEntryLocalService, _bundle, _commerceReferencesHolder,
				_ddmStructureLocalService, _ddmTemplateLocalService,
				_defaultDDMStructureHelper, _dlURLHelper,
				_documentFolderResourceFactory, _documentResourceFactory,
				_fragmentsImporter, _groupLocalService,
				_journalArticleLocalService, _jsonFactory, _layoutCopyHelper,
				_layoutLocalService, _layoutPageTemplateEntryLocalService,
				_layoutPageTemplatesImporter,
				_layoutPageTemplateStructureLocalService,
				_layoutSetLocalService, _listTypeDefinitionResource,
				_listTypeDefinitionResourceFactory, _listTypeEntryResource,
				_listTypeEntryResourceFactory, _objectDefinitionLocalService,
				_objectDefinitionResourceFactory,
				_objectRelationshipResourceFactory, _objectEntryLocalService,
				_portal, _remoteAppEntryLocalService,
				_resourcePermissionLocalService, _roleLocalService,
				_sapEntryLocalService, _servletContext, _settingsFactory,
				_siteNavigationMenuItemLocalService,
				_siteNavigationMenuItemTypeRegistry,
				_siteNavigationMenuLocalService,
				_structuredContentFolderResourceFactory,
				_styleBookEntryZipProcessor, _taxonomyCategoryResourceFactory,
				_taxonomyVocabularyResourceFactory, _themeLocalService,
				_userAccountResourceFactory, _userLocalService),
			MapUtil.singletonDictionary(
				"site.initializer.key", _bundle.getSymbolicName()));
	}

	private void _stop() {
		_serviceRegistration.unregister();
	}

	private final AccountResource.Factory _accountResourceFactory;
	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetListEntryLocalService _assetListEntryLocalService;
	private final Bundle _bundle;
	private final BundleContext _bundleContext;
	private final CommerceReferencesHolder _commerceReferencesHolder;
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
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;
	private final SAPEntryLocalService _sapEntryLocalService;
	private ServiceRegistration<?> _serviceRegistration;
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

}