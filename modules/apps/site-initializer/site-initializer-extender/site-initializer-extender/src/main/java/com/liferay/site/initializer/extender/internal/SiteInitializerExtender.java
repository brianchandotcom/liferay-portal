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
import com.liferay.commerce.initializer.util.PortletSettingsImporter;
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
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
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
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = {})
public class SiteInitializerExtender
	implements BundleTrackerCustomizer<SiteInitializerExtension> {

	@Override
	public SiteInitializerExtension addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		List<BundleCapability> bundleCapabilities =
			bundleWiring.getCapabilities("liferay.site.initializer");

		if (ListUtil.isEmpty(bundleCapabilities)) {
			return null;
		}

		SiteInitializerExtension siteInitializerExtension =
			new SiteInitializerExtension(
				_accountResourceFactory, _accountRoleLocalService,
				_accountRoleResourceFactory, _assetCategoryLocalService,
				_assetListEntryLocalService, bundle, _bundleContext,
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
				_portal, _portletSettingsImporter, _remoteAppEntryLocalService,
				_resourceActionLocalService, _resourcePermissionLocalService,
				_roleLocalService, _sapEntryLocalService, _settingsFactory,
				_siteNavigationMenuItemLocalService,
				_siteNavigationMenuItemTypeRegistry,
				_siteNavigationMenuLocalService,
				_structuredContentFolderResourceFactory,
				_styleBookEntryZipProcessor, _taxonomyCategoryResourceFactory,
				_taxonomyVocabularyResourceFactory, _themeLocalService,
				_userAccountResourceFactory, _userLocalService,
				_workflowDefinitionLinkLocalService,
				_workflowDefinitionResourceFactory);

		siteInitializerExtension.start();

		return siteInitializerExtension;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		SiteInitializerExtension siteInitializerExtension) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		SiteInitializerExtension siteInitializerExtension) {

		siteInitializerExtension.destroy();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	@Reference
	private AccountResource.Factory _accountResourceFactory;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private AccountRoleResource.Factory _accountRoleResourceFactory;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	private BundleContext _bundleContext;
	private BundleTracker<?> _bundleTracker;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private DocumentFolderResource.Factory _documentFolderResourceFactory;

	@Reference
	private DocumentResource.Factory _documentResourceFactory;

	@Reference
	private FragmentsImporter _fragmentsImporter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private ListTypeDefinitionResource _listTypeDefinitionResource;

	@Reference
	private ListTypeDefinitionResource.Factory
		_listTypeDefinitionResourceFactory;

	@Reference
	private ListTypeEntryResource _listTypeEntryResource;

	@Reference
	private ListTypeEntryResource.Factory _listTypeEntryResourceFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectRelationshipResource.Factory
		_objectRelationshipResourceFactory;

	@Reference
	private Portal _portal;

	@Reference
	private PortletSettingsImporter _portletSettingsImporter;

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	@Reference
	private SettingsFactory _settingsFactory;

	@Reference
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Reference
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@Reference
	private StructuredContentFolderResource.Factory
		_structuredContentFolderResourceFactory;

	@Reference
	private StyleBookEntryZipProcessor _styleBookEntryZipProcessor;

	@Reference
	private TaxonomyCategoryResource.Factory _taxonomyCategoryResourceFactory;

	@Reference
	private TaxonomyVocabularyResource.Factory
		_taxonomyVocabularyResourceFactory;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserAccountResource.Factory _userAccountResourceFactory;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowDefinitionResource.Factory
		_workflowDefinitionResourceFactory;

}