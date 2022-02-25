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

package com.liferay.object.web.internal.deployer;

import com.liferay.application.list.PanelApp;
import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.web.internal.asset.model.ObjectEntryAssetRendererFactory;
import com.liferay.object.web.internal.info.item.provider.ObjectEntryInfoItemCapabilitiesProvider;
import com.liferay.object.web.internal.info.item.provider.ObjectEntryInfoItemDetailsProvider;
import com.liferay.object.web.internal.info.item.provider.ObjectEntryInfoItemFieldValuesProvider;
import com.liferay.object.web.internal.info.item.provider.ObjectEntryInfoItemFormProvider;
import com.liferay.object.web.internal.info.item.provider.ObjectEntryInfoItemObjectProvider;
import com.liferay.object.web.internal.info.item.renderer.ObjectEntryRowInfoItemRenderer;
import com.liferay.object.web.internal.info.list.renderer.ObjectEntryTableInfoListRenderer;
import com.liferay.object.web.internal.item.selector.ObjectEntryItemSelectorView;
import com.liferay.object.web.internal.layout.display.page.ObjectEntryLayoutDisplayPageProvider;
import com.liferay.object.web.internal.object.entries.application.list.ObjectEntriesPanelApp;
import com.liferay.object.web.internal.object.entries.display.context.ObjectEntryDisplayContextFactory;
import com.liferay.object.web.internal.object.entries.frontend.data.set.filter.ObjectEntryStatusCheckBoxFDSFilter;
import com.liferay.object.web.internal.object.entries.frontend.data.set.view.table.ObjectEntriesTableFDSView;
import com.liferay.object.web.internal.object.entries.portlet.ObjectEntriesPortlet;
import com.liferay.object.web.internal.object.entries.portlet.action.EditObjectEntryMVCActionCommand;
import com.liferay.object.web.internal.object.entries.portlet.action.EditObjectEntryMVCRenderCommand;
import com.liferay.object.web.internal.object.entries.portlet.action.EditObjectEntryRelatedModelMVCActionCommand;
import com.liferay.object.web.internal.object.entries.portlet.action.UploadAttachmentMVCActionCommand;
import com.liferay.object.web.internal.object.entries.upload.AttachmentUploadFileEntryHandler;
import com.liferay.object.web.internal.object.entries.upload.AttachmentUploadResponseHandler;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.template.info.item.capability.TemplateInfoItemCapability;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;
import com.liferay.upload.UploadHandler;

import java.util.List;

import javax.portlet.Portlet;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ObjectDefinitionDeployer.class)
public class ObjectDefinitionDeployerImpl implements ObjectDefinitionDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		List<ServiceRegistration<?>> serviceRegistrations = ListUtil.fromArray(
			_bundleContext.registerService(
				AssetRendererFactory.class,
				new ObjectEntryAssetRendererFactory(
					objectDefinition, _objectEntryDisplayContextFactory,
					_objectEntryService, _servletContext),
				HashMapDictionaryBuilder.<String, Object>put(
					"javax.portlet.name", objectDefinition.getPortletId()
				).build()),
			_bundleContext.registerService(
				FDSView.class,
				new ObjectEntriesTableFDSView(
					_fdsTableSchemaBuilderFactory, objectDefinition,
					_objectDefinitionLocalService, _objectFieldLocalService,
					_objectRelationshipLocalService, _objectViewLocalService),
				HashMapDictionaryBuilder.put(
					"frontend.data.set.name", objectDefinition.getPortletId()
				).build()),
			_bundleContext.registerService(
				FDSFilter.class, new ObjectEntryStatusCheckBoxFDSFilter(),
				HashMapDictionaryBuilder.put(
					"frontend.data.set.name", objectDefinition.getPortletId()
				).build()),
			_bundleContext.registerService(
				InfoItemCapabilitiesProvider.class,
				new ObjectEntryInfoItemCapabilitiesProvider(
					_displayPageInfoItemCapability,
					_templatePageInfoItemCapability),
				HashMapDictionaryBuilder.<String, Object>put(
					"item.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				InfoItemDetailsProvider.class,
				new ObjectEntryInfoItemDetailsProvider(objectDefinition),
				HashMapDictionaryBuilder.<String, Object>put(
					Constants.SERVICE_RANKING, 10
				).put(
					"item.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				InfoItemFieldValuesProvider.class,
				new ObjectEntryInfoItemFieldValuesProvider(
					_assetDisplayPageFriendlyURLProvider,
					_infoItemFieldReaderFieldSetProvider, _jsonFactory,
					_listTypeEntryLocalService, _objectEntryLocalService,
					_objectFieldLocalService, _templateInfoItemFieldSetProvider,
					_userLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"item.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				InfoItemFormProvider.class,
				new ObjectEntryInfoItemFormProvider(
					objectDefinition, _infoItemFieldReaderFieldSetProvider,
					_objectDefinitionLocalService, _objectFieldLocalService,
					_objectRelationshipLocalService,
					_templateInfoItemFieldSetProvider),
				HashMapDictionaryBuilder.<String, Object>put(
					Constants.SERVICE_RANKING, 10
				).put(
					"item.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				InfoItemObjectProvider.class,
				new ObjectEntryInfoItemObjectProvider(_objectEntryLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					Constants.SERVICE_RANKING, 100
				).put(
					"info.item.identifier",
					"com.liferay.info.item.ClassPKInfoItemIdentifier"
				).put(
					"item.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				InfoItemRenderer.class,
				new ObjectEntryRowInfoItemRenderer(
					_assetDisplayPageFriendlyURLProvider,
					_listTypeEntryLocalService, _objectDefinitionLocalService,
					_objectEntryLocalService, _objectFieldLocalService,
					_servletContext),
				HashMapDictionaryBuilder.<String, Object>put(
					Constants.SERVICE_RANKING, 100
				).put(
					"item.class.name", objectDefinition.getClassName()
				).put(
					"osgi.web.symbolicname", "com.liferay.object.web"
				).build()),
			_bundleContext.registerService(
				InfoListRenderer.class,
				new ObjectEntryTableInfoListRenderer(
					_infoItemRendererTracker, _objectFieldLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"item.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				ItemSelectorView.class,
				new ObjectEntryItemSelectorView(
					_itemSelectorViewDescriptorRenderer, objectDefinition,
					_objectDefinitionLocalService, _objectEntryLocalService,
					_objectScopeProviderRegistry, _portal),
				HashMapDictionaryBuilder.<String, Object>put(
					"item.selector.view.order", 500
				).build()),
			_bundleContext.registerService(
				LayoutDisplayPageProvider.class,
				new ObjectEntryLayoutDisplayPageProvider(
					objectDefinition, _objectDefinitionLocalService,
					_objectEntryLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"item.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				Portlet.class,
				new ObjectEntriesPortlet(
					objectDefinition.getObjectDefinitionId(),
					_objectDefinitionLocalService, _objectFieldLocalService,
					_objectScopeProviderRegistry, _objectViewLocalService,
					_portal,
					_getPortletResourcePermission(
						objectDefinition.getResourceName())),
				HashMapDictionaryBuilder.<String, Object>put(
					"com.liferay.portlet.company",
					objectDefinition.getCompanyId()
				).put(
					"com.liferay.portlet.display-category",
					() -> {
						if (objectDefinition.isPortlet()) {
							return "category.object";
						}

						return "category.hidden";
					}
				).put(
					"javax.portlet.display-name",
					objectDefinition.getPluralLabel(LocaleUtil.getSiteDefault())
				).put(
					"javax.portlet.init-param.view-template",
					"/object_entries/view_object_entries.jsp"
				).put(
					"javax.portlet.name", objectDefinition.getPortletId()
				).build()),
			_bundleContext.registerService(
				MVCActionCommand.class,
				new EditObjectEntryMVCActionCommand(
					_objectDefinitionLocalService, _objectEntryService,
					_objectRelatedModelsProviderRegistry,
					_objectRelationshipLocalService,
					_objectScopeProviderRegistry, _portal),
				HashMapDictionaryBuilder.<String, Object>put(
					"javax.portlet.name", objectDefinition.getPortletId()
				).put(
					"mvc.command.name", "/object_entries/edit_object_entry"
				).build()),
			_bundleContext.registerService(
				MVCActionCommand.class,
				new EditObjectEntryRelatedModelMVCActionCommand(
					_objectDefinitionLocalService,
					_objectRelationshipLocalService, _portal),
				HashMapDictionaryBuilder.<String, Object>put(
					"javax.portlet.name", objectDefinition.getPortletId()
				).put(
					"mvc.command.name",
					"/object_entries/edit_object_entry_related_model"
				).build()),
			_bundleContext.registerService(
				MVCActionCommand.class,
				new UploadAttachmentMVCActionCommand(
					_attachmentUploadFileEntryHandler,
					_attachmentUploadResponseHandler, _uploadHandler),
				HashMapDictionaryBuilder.<String, Object>put(
					"javax.portlet.name", objectDefinition.getPortletId()
				).put(
					"mvc.command.name", "/object_entries/upload_attachment"
				).build()),
			_bundleContext.registerService(
				MVCRenderCommand.class,
				new EditObjectEntryMVCRenderCommand(
					_objectEntryDisplayContextFactory, _portal),
				HashMapDictionaryBuilder.<String, Object>put(
					"javax.portlet.name", objectDefinition.getPortletId()
				).put(
					"mvc.command.name", "/object_entries/edit_object_entry"
				).build()));

		// Register ObjectEntriesPanelApp after ObjectEntriesPortlet. See
		// LPS-140379.

		serviceRegistrations.add(
			_bundleContext.registerService(
				PanelApp.class, new ObjectEntriesPanelApp(objectDefinition),
				HashMapDictionaryBuilder.<String, Object>put(
					"panel.app.order:Integer",
					objectDefinition.getPanelAppOrder()
				).put(
					"panel.category.key", objectDefinition.getPanelCategoryKey()
				).build()));

		return serviceRegistrations;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.object.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortletResourcePermission.class, "resource.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private PortletResourcePermission _getPortletResourcePermission(
		String resourceName) {

		PortletResourcePermission portletResourcePermission =
			_serviceTrackerMap.getService(resourceName);

		if (portletResourcePermission == null) {
			throw new IllegalArgumentException(
				"No portlet resource permission found with resource name " +
					resourceName);
		}

		return portletResourcePermission;
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AttachmentUploadFileEntryHandler _attachmentUploadFileEntryHandler;

	@Reference
	private AttachmentUploadResponseHandler _attachmentUploadResponseHandler;

	private BundleContext _bundleContext;

	@Reference
	private DisplayPageInfoItemCapability _displayPageInfoItemCapability;

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private InfoItemRendererTracker _infoItemRendererTracker;

	@Reference
	private ItemSelectorViewDescriptorRenderer<InfoItemItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryDisplayContextFactory _objectEntryDisplayContextFactory;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private ObjectViewLocalService _objectViewLocalService;

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, PortletResourcePermission>
		_serviceTrackerMap;
	private ServletContext _servletContext;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

	@Reference
	private TemplateInfoItemCapability _templatePageInfoItemCapability;

	@Reference
	private UploadHandler _uploadHandler;

	@Reference
	private UserLocalService _userLocalService;

}