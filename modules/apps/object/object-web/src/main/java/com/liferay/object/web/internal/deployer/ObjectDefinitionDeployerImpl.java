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
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.application.list.ObjectDefinitionPanelApp;
import com.liferay.object.web.internal.frontend.taglib.clay.data.set.view.table.ObjectDefinitionTableClayDataSetDisplayView;
import com.liferay.object.web.internal.portlet.ObjectDefinitionPortlet;
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

	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		return Arrays.asList(
			_bundleContext.registerService(
				ClayDataSetDisplayView.class,
				new ObjectDefinitionTableClayDataSetDisplayView(
					_clayTableSchemaBuilderFactory, objectDefinition,
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId())),
				HashMapDictionaryBuilder.put(
					"clay.data.set.display.name",
					objectDefinition.getPortletId()
				).build()),
			_bundleContext.registerService(
				PanelApp.class, new ObjectDefinitionPanelApp(objectDefinition),
				HashMapDictionaryBuilder.<String, Object>put(
					"panel.app.order:Integer", "300"
				).put(
					"panel.category.key", PanelCategoryKeys.CONTROL_PANEL_USERS
				).build()),
			_bundleContext.registerService(
				Portlet.class,
				new ObjectDefinitionPortlet(
					_portal, objectDefinition.getRESTContextPath()),
				HashMapDictionaryBuilder.<String, Object>put(
					"com.liferay.portlet.display-category", "category.hidden"
				).put(
					"javax.portlet.display-name", objectDefinition.getName()
				).put(
					"javax.portlet.name", objectDefinition.getPortletId()
				).put(
					"javax.portlet.init-param.view-template", "/view.jsp"
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
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private Portal _portal;

}