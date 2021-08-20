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

import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;

import javax.servlet.ServletContext;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Preston Crary
 */
public class SiteInitializerExtension {

	public SiteInitializerExtension(
		AssetListEntryLocalService assetListEntryLocalService, Bundle bundle,
		BundleContext bundleContext,
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
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		Portal portal,
		SiteNavigationMenuItemLocalService siteNavigationMenuItemLocalService,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry,
		SiteNavigationMenuLocalService siteNavigationMenuLocalService,
		StyleBookEntryZipProcessor styleBookEntryZipProcessor,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		ThemeLocalService themeLocalService,
		UserLocalService userLocalService) {

		_dependencyManager = new DependencyManager(bundle.getBundleContext());

		_component = _dependencyManager.createComponent();

		_component.setImplementation(
			new SiteInitializerRegistrar(
				assetListEntryLocalService, bundle, bundleContext,
				ddmStructureLocalService, ddmTemplateLocalService,
				defaultDDMStructureHelper, documentResourceFactory,
				fragmentsImporter, journalArticleLocalService, jsonFactory,
				layoutCopyHelper, layoutLocalService,
				layoutPageTemplateEntryLocalService,
				layoutPageTemplatesImporter,
				layoutPageTemplateStructureLocalService,
				objectDefinitionResourceFactory, portal,
				siteNavigationMenuItemLocalService,
				siteNavigationMenuItemTypeRegistry,
				siteNavigationMenuLocalService, styleBookEntryZipProcessor,
				taxonomyVocabularyResourceFactory, themeLocalService,
				userLocalService));

		ServiceDependency serviceDependency =
			_dependencyManager.createServiceDependency();

		serviceDependency.setCallbacks("setServletContext", null);
		serviceDependency.setRequired(true);
		serviceDependency.setService(
			ServletContext.class,
			"(osgi.web.symbolicname=" + bundle.getSymbolicName() + ")");

		_component.add(serviceDependency);
	}

	public void destroy() {
		_dependencyManager.remove(_component);
	}

	public void start() {
		_dependencyManager.add(_component);
	}

	private final Component _component;
	private final DependencyManager _dependencyManager;

}