/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.extender.internal;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
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
import com.liferay.layout.helper.LayoutCopyHelper;
import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.notification.rest.resource.v1_0.NotificationTemplateResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFieldResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.settings.ArchivedSettingsFactory;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.extender.CommerceSiteInitializer;
import com.liferay.site.initializer.extender.OSBSiteInitializer;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;
import com.liferay.template.service.TemplateEntryLocalService;

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
		AccountEntryLocalService accountEntryLocalService,
		AccountEntryOrganizationRelLocalService
			accountEntryOrganizationRelLocalService,
		AccountGroupLocalService accountGroupLocalService,
		AccountGroupRelService accountGroupRelService,
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
		DependencyManager dependencyManager, DLURLHelper dlURLHelper,
		DocumentFolderResource.Factory documentFolderResourceFactory,
		DocumentResource.Factory documentResourceFactory,
		ExpandoValueLocalService expandoValueLocalService,
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
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService,
		LayoutPageTemplateStructureRelLocalService
			layoutPageTemplateStructureRelLocalService,
		LayoutSetLocalService layoutSetLocalService,
		LayoutsImporter layoutsImporter,
		LayoutUtilityPageEntryLocalService layoutUtilityPageEntryLocalService,
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
		OrganizationResource.Factory organizationResourceFactory,
		PLOEntryLocalService ploEntryLocalService, Portal portal,
		PortletPreferencesLocalService portletPreferencesLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService,
		SAPEntryLocalService sapEntryLocalService,
		SegmentsEntryLocalService segmentsEntryLocalService,
		SegmentsExperienceLocalService segmentsExperienceLocalService,
		ServletContext servletContext,
		ArchivedSettingsFactory archivedSettingsFactory,
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
		WorkflowDefinitionResource.Factory workflowDefinitionResourceFactory,
		ZipWriterFactory zipWriterFactory) {

		_dependencyManager = dependencyManager;

		_component = dependencyManager.createComponent();

		BundleSiteInitializer bundleSiteInitializer = new BundleSiteInitializer(
			accountEntryLocalService, accountEntryOrganizationRelLocalService,
			accountGroupLocalService, accountGroupRelService,
			accountResourceFactory, accountRoleLocalService,
			accountRoleResourceFactory, assetCategoryLocalService,
			assetListEntryLocalService, bundle,
			clientExtensionEntryLocalService, configurationProvider,
			ddmStructureLocalService, ddmTemplateLocalService,
			defaultDDMStructureHelper, dlURLHelper,
			documentFolderResourceFactory, documentResourceFactory,
			expandoValueLocalService, fragmentsImporter, groupLocalService,
			journalArticleLocalService, jsonFactory,
			knowledgeBaseArticleResourceFactory,
			knowledgeBaseFolderResourceFactory, layoutCopyHelper,
			layoutLocalService, layoutPageTemplateEntryLocalService,
			layoutsImporter, layoutPageTemplateStructureLocalService,
			layoutPageTemplateStructureRelLocalService, layoutSetLocalService,
			layoutUtilityPageEntryLocalService, listTypeDefinitionResource,
			listTypeDefinitionResourceFactory, listTypeEntryLocalService,
			listTypeEntryResource, listTypeEntryResourceFactory,
			notificationTemplateResourceFactory, objectActionLocalService,
			objectDefinitionLocalService, objectDefinitionResourceFactory,
			objectEntryLocalService, objectEntryManager,
			objectFieldLocalService, objectFieldResourceFactory,
			objectRelationshipLocalService, objectRelationshipResourceFactory,
			organizationLocalService, organizationResourceFactory,
			ploEntryLocalService, portal, portletPreferencesLocalService,
			resourceActionLocalService, resourcePermissionLocalService,
			roleLocalService, sapEntryLocalService, segmentsEntryLocalService,
			segmentsExperienceLocalService, archivedSettingsFactory,
			siteNavigationMenuItemLocalService,
			siteNavigationMenuItemTypeRegistry, siteNavigationMenuLocalService,
			structuredContentFolderResourceFactory, styleBookEntryZipProcessor,
			taxonomyCategoryResourceFactory, taxonomyVocabularyResourceFactory,
			templateEntryLocalService, themeLocalService,
			userAccountResourceFactory, userGroupLocalService, userLocalService,
			workflowDefinitionLinkLocalService,
			workflowDefinitionResourceFactory, zipWriterFactory);

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

		serviceDependency = _dependencyManager.createServiceDependency();

		serviceDependency.setCallbacks("setOSBSiteInitializer", null);
		serviceDependency.setRequired(false);
		serviceDependency.setService(OSBSiteInitializer.class);

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