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
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CatalogResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ChannelResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentFolderResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
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
		CatalogResource.Factory catalogResourceFactory,
		ChannelResource.Factory channelResourceFactory,
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
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		Portal portal,
		SiteNavigationMenuLocalService siteNavigationMenuLocalService,
		StructuredContentFolderResource.Factory
			structuredContentFolderResourceFactory,
		StyleBookEntryZipProcessor styleBookEntryZipProcessor,
		TaxonomyCategoryResource.Factory taxonomyCategoryResourceFactory,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		UserLocalService userLocalService) {

		_dependencyManager = new DependencyManager(bundle.getBundleContext());

		_component = _dependencyManager.createComponent();

		_component.setImplementation(
			new SiteInitializerRegistrar(
				assetListEntryLocalService, bundle, bundleContext,
				catalogResourceFactory, channelResourceFactory,
				ddmStructureLocalService, ddmTemplateLocalService,
				defaultDDMStructureHelper, dlURLHelper,
				documentFolderResourceFactory, documentResourceFactory,
				fragmentsImporter, groupLocalService,
				journalArticleLocalService, jsonFactory,
				objectDefinitionResourceFactory, portal,
				siteNavigationMenuLocalService,
				structuredContentFolderResourceFactory,
				styleBookEntryZipProcessor, taxonomyCategoryResourceFactory,
				taxonomyVocabularyResourceFactory, userLocalService));

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