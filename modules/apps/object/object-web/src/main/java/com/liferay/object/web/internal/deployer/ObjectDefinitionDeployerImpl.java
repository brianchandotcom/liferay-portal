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
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.item.selector.ObjectEntryItemSelectorView;
import com.liferay.object.web.internal.object.entries.application.list.ObjectEntriesPanelApp;
import com.liferay.object.web.internal.object.entries.frontend.taglib.clay.data.set.filter.ObjectEntryStatusClayTableDataSetFilter;
import com.liferay.object.web.internal.object.entries.frontend.taglib.clay.data.set.view.table.ObjectEntriesTableClayDataSetDisplayView;
import com.liferay.object.web.internal.object.entries.portlet.ObjectEntriesPortlet;
import com.liferay.object.web.internal.object.entries.portlet.action.EditObjectEntryMVCActionCommand;
import com.liferay.object.web.internal.object.entries.portlet.action.EditObjectEntryMVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;

import java.util.Arrays;
import java.util.List;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ObjectDefinitionDeployer.class)
public class ObjectDefinitionDeployerImpl implements ObjectDefinitionDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		return Arrays.asList(
			_bundleContext.registerService(
				ClayDataSetDisplayView.class,
				new ObjectEntriesTableClayDataSetDisplayView(
					_clayTableSchemaBuilderFactory, objectDefinition,
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId())),
				HashMapDictionaryBuilder.put(
					"clay.data.set.display.name",
					objectDefinition.getPortletId()
				).build()),
			_bundleContext.registerService(
				ClayDataSetFilter.class,
				new ObjectEntryStatusClayTableDataSetFilter(),
				HashMapDictionaryBuilder.put(
					"clay.data.set.display.name",
					objectDefinition.getPortletId()
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
				PanelApp.class, new ObjectEntriesPanelApp(objectDefinition),
				HashMapDictionaryBuilder.<String, Object>put(
					"panel.app.order:Integer",
					objectDefinition.getPanelAppOrder()
				).put(
					"panel.category.key", objectDefinition.getPanelCategoryKey()
				).build()),
			_bundleContext.registerService(
				Portlet.class,
				new ObjectEntriesPortlet(
					objectDefinition.getObjectDefinitionId(),
					_objectDefinitionLocalService, _portal,
					objectDefinition.getRESTContextPath()),
				HashMapDictionaryBuilder.<String, Object>put(
					"com.liferay.portlet.display-category", "category.hidden"
				).put(
					"javax.portlet.display-name",
					objectDefinition.getShortName()
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
					_objectScopeProviderRegistry, _portal),
				HashMapDictionaryBuilder.<String, Object>put(
					"javax.portlet.name", objectDefinition.getPortletId()
				).put(
					"mvc.command.name", "/object_entries/edit_object_entry"
				).build()),
			_bundleContext.registerService(
				MVCRenderCommand.class,
				new EditObjectEntryMVCRenderCommand(
					_ddmFormRenderer, _listTypeEntryLocalService,
					_objectEntryService, _objectFieldLocalService,
					_objectLayoutLocalService, _objectRelationshipLocalService,
					_portal),
				HashMapDictionaryBuilder.<String, Object>put(
					"javax.portlet.name", objectDefinition.getPortletId()
				).put(
					"mvc.command.name", "/object_entries/edit_object_entry"
				).build()));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private ItemSelectorViewDescriptorRenderer<InfoItemItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectLayoutLocalService _objectLayoutLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private Portal _portal;

}