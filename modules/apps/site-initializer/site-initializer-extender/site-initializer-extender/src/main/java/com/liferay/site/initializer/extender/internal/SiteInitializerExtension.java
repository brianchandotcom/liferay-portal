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

import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
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
import com.liferay.headless.admin.user.resource.v1_0.AccountRoleResource;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentFolderResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.notification.rest.resource.v1_0.NotificationTemplateResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;

import javax.servlet.ServletContext;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;

import org.osgi.framework.Bundle;

/**
 * @author Preston Crary
 */
public class SiteInitializerExtension {

	public SiteInitializerExtension(
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
		LayoutPageTemplatesImporter layoutPageTemplatesImporter,
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService,
		LayoutSetLocalService layoutSetLocalService,
		ListTypeDefinitionResource listTypeDefinitionResource,
		ListTypeDefinitionResource.Factory listTypeDefinitionResourceFactory,
		ListTypeEntryResource listTypeEntryResource,
		ListTypeEntryResource.Factory listTypeEntryResourceFactory,
		NotificationTemplateResource.Factory
			notificationTemplateResourceFactory,
		ObjectActionLocalService objectActionLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		ObjectRelationshipResource.Factory objectRelationshipResourceFactory,
		ObjectEntryLocalService objectEntryLocalService,
		OrganizationLocalService organizationLocalService,
		OrganizationResource.Factory organizationResourceFactory, Portal portal,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService,
		SAPEntryLocalService sapEntryLocalService,
		SegmentsEntryLocalService segmentsEntryLocalService,
		SegmentsExperienceLocalService segmentsExperienceLocalService,
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
		UserAccountResource.Factory userAccountResourceFactory,
		UserGroupLocalService userGroupLocalService,
		UserLocalService userLocalService,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService,
		WorkflowDefinitionResource.Factory workflowDefinitionResourceFactory) {

		_dependencyManager = new DependencyManager(bundle.getBundleContext());

		_component = _dependencyManager.createComponent();

		BundleSiteInitializer bundleSiteInitializer = new BundleSiteInitializer(
			accountResourceFactory, accountRoleLocalService,
			accountRoleResourceFactory, assetCategoryLocalService,
			assetListEntryLocalService, bundle,
			clientExtensionEntryLocalService, configurationProvider,
			ddmStructureLocalService, ddmTemplateLocalService,
			defaultDDMStructureHelper, dlURLHelper,
			documentFolderResourceFactory, documentResourceFactory,
			fragmentsImporter, groupLocalService, journalArticleLocalService,
			jsonFactory, knowledgeBaseArticleResourceFactory,
			knowledgeBaseFolderResourceFactory, layoutCopyHelper,
			layoutLocalService, layoutPageTemplateEntryLocalService,
			layoutPageTemplatesImporter,
			layoutPageTemplateStructureLocalService, layoutSetLocalService,
			listTypeDefinitionResource, listTypeDefinitionResourceFactory,
			listTypeEntryResource, listTypeEntryResourceFactory,
			notificationTemplateResourceFactory, objectActionLocalService,
			objectDefinitionLocalService, objectDefinitionResourceFactory,
			objectRelationshipLocalService, objectRelationshipResourceFactory,
			objectEntryLocalService, organizationLocalService,
			organizationResourceFactory, portal, resourceActionLocalService,
			resourcePermissionLocalService, roleLocalService,
			sapEntryLocalService, segmentsEntryLocalService,
			segmentsExperienceLocalService, settingsFactory,
			siteNavigationMenuItemLocalService,
			siteNavigationMenuItemTypeRegistry, siteNavigationMenuLocalService,
			structuredContentFolderResourceFactory, styleBookEntryZipProcessor,
			taxonomyCategoryResourceFactory, taxonomyVocabularyResourceFactory,
			themeLocalService, userAccountResourceFactory,
			userGroupLocalService, userLocalService,
			workflowDefinitionLinkLocalService,
			workflowDefinitionResourceFactory);

		_component.setImplementation(bundleSiteInitializer);

		_component.setInterface(
			SiteInitializer.class,
			MapUtil.singletonDictionary(
				"site.initializer.key", bundle.getSymbolicName()));

		ServiceDependency serviceDependency =
			_dependencyManager.createServiceDependency();

		serviceDependency.setCallbacks("setCommerceSiteInitializer", null);
		serviceDependency.setRequired(false);
		serviceDependency.setService(CommerceSiteInitializer.class);

		_component.add(serviceDependency);

		if (servletContext == null) {
			serviceDependency = _dependencyManager.createServiceDependency();

			serviceDependency.setCallbacks("setServletContext", null);
			serviceDependency.setRequired(true);
			serviceDependency.setService(
				ServletContext.class,
				"(osgi.web.symbolicname=" + bundle.getSymbolicName() + ")");

			_component.add(serviceDependency);
		}
		else {
			bundleSiteInitializer.setServletContext(servletContext);
		}
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