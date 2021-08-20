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

import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.asset.list.service.AssetListEntryLocalService;

import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import java.util.Map;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import java.util.List;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;


/**
 * @author Preston Crary
 */
public class SiteInitializerRegistrar {

	public SiteInitializerRegistrar(
		AssetListEntryLocalService assetListEntryLocalService,
		Bundle bundle, BundleContext bundleContext,
		DDMStructureLocalService ddmStructureLocalService,
		DDMTemplateLocalService ddmTemplateLocalService,
		DefaultDDMStructureHelper defaultDDMStructureHelper,
		DocumentResource.Factory documentResourceFactory,
		FragmentsImporter fragmentsImporter,
		JournalArticleLocalService journalArticleLocalService,
		JSONFactory jsonFactory, LayoutCopyHelper layoutCopyHelper,
		LayoutLocalService layoutLocalService,
		LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService,
		LayoutPageTemplatesImporter layoutPageTemplatesImporter,
		LayoutPageTemplateStructureLocalService layoutPageTemplateStructureLocalService,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		Portal portal,
		SiteNavigationMenuItemLocalService siteNavigationMenuItemLocalService,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry,
		SiteNavigationMenuLocalService siteNavigationMenuLocalService,
		StyleBookEntryZipProcessor styleBookEntryZipProcessor,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		ThemeLocalService themeLocalService,
		UserLocalService userLocalService) {

		_assetListEntryLocalService = assetListEntryLocalService;
		_bundle = bundle;
		_bundleContext = bundleContext;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmTemplateLocalService = ddmTemplateLocalService;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_documentResourceFactory = documentResourceFactory;
		_fragmentsImporter = fragmentsImporter;
		_journalArticleLocalService = journalArticleLocalService;
		_jsonFactory = jsonFactory;
		_layoutCopyHelper = layoutCopyHelper;
		_layoutLocalService = layoutLocalService;
		_layoutPageTemplateEntryLocalService = layoutPageTemplateEntryLocalService;
		_layoutPageTemplatesImporter = layoutPageTemplatesImporter;
		_layoutPageTemplateStructureLocalService = layoutPageTemplateStructureLocalService;
 		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;
		_portal = portal;
		_siteNavigationMenuItemLocalService = siteNavigationMenuItemLocalService;
		_siteNavigationMenuItemTypeRegistry = siteNavigationMenuItemTypeRegistry;
		_siteNavigationMenuLocalService = siteNavigationMenuLocalService;
		_styleBookEntryZipProcessor = styleBookEntryZipProcessor;
		_taxonomyVocabularyResourceFactory = taxonomyVocabularyResourceFactory;
		_themeLocalService = themeLocalService;
		_userLocalService = userLocalService;
	}

	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected void start() {
		_serviceRegistration = _bundleContext.registerService(
			SiteInitializer.class,
			new BundleSiteInitializer(
				_assetListEntryLocalService,
				_bundle, _ddmStructureLocalService, _ddmTemplateLocalService,
				_defaultDDMStructureHelper, _documentResourceFactory,
				_fragmentsImporter, _journalArticleLocalService, _jsonFactory,
				_layoutCopyHelper, _layoutLocalService,
				_layoutPageTemplateEntryLocalService, _layoutPageTemplatesImporter,
				_layoutPageTemplateStructureLocalService,
				_objectDefinitionResourceFactory, _portal, _servletContext,
				_siteNavigationMenuItemLocalService, _siteNavigationMenuItemTypeRegistry,
				_siteNavigationMenuLocalService, _styleBookEntryZipProcessor,
				_taxonomyVocabularyResourceFactory, _themeLocalService,
				_userLocalService),
			MapUtil.singletonDictionary(
				"site.initializer.key", _bundle.getSymbolicName()));
	}

	protected void stop() {
		_serviceRegistration.unregister();
	}

	private final AssetListEntryLocalService _assetListEntryLocalService;
	private final Bundle _bundle;
	private final BundleContext _bundleContext;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMTemplateLocalService _ddmTemplateLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final DocumentResource.Factory _documentResourceFactory;
	private final FragmentsImporter _fragmentsImporter;
	private final JournalArticleLocalService _journalArticleLocalService;
	private final JSONFactory _jsonFactory;
	private final LayoutCopyHelper _layoutCopyHelper;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;
	private final LayoutPageTemplatesImporter _layoutPageTemplatesImporter;
	private final LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private final Portal _portal;
	private ServiceRegistration<?> _serviceRegistration;
	private ServletContext _servletContext;
	private final SiteNavigationMenuItemLocalService _siteNavigationMenuItemLocalService;
	private final SiteNavigationMenuItemTypeRegistry _siteNavigationMenuItemTypeRegistry;
	private final SiteNavigationMenuLocalService _siteNavigationMenuLocalService;
	private final StyleBookEntryZipProcessor _styleBookEntryZipProcessor;
	private final TaxonomyVocabularyResource.Factory
		_taxonomyVocabularyResourceFactory;
	private final ThemeLocalService _themeLocalService;
	private final UserLocalService _userLocalService;

}