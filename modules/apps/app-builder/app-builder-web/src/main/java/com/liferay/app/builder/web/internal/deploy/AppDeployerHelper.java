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

package com.liferay.app.builder.web.internal.deploy;

import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.web.internal.portlet.AppPortlet;
import com.liferay.app.builder.web.internal.portlet.action.AddDataRecordMVCResourceCommand;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.Map;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = AppDeployerHelper.class)
public class AppDeployerHelper {

	public ServiceRegistration<?>[] deployPortlet(
		AppPortlet appPortlet, BundleContext bundleContext,
		Map<String, Object> customProperties) {

		Dictionary<String, Object> properties = appPortlet.getProperties(
			customProperties);

		return new ServiceRegistration<?>[] {
			bundleContext.registerService(
				Portlet.class, appPortlet, properties),
			bundleContext.registerService(
				MVCResourceCommand.class, _addDataRecordMVCResourceCommand,
				new HashMapDictionary<String, Object>() {
					{
						put(
							"javax.portlet.name",
							properties.get("javax.portlet.name"));
						put("mvc.command.name", "/app_builder/add_data_record");
					}
				})
		};
	}

	@Activate
	protected void activate() {
		_addDataRecordMVCResourceCommand = new AddDataRecordMVCResourceCommand(
			_appBuilderAppDataRecordLinkLocalService,
			_appBuilderAppLocalService, _ddlRecordLocalService);
	}

	@Deactivate
	protected void deactivate() {
		_addDataRecordMVCResourceCommand = null;
	}

	private AddDataRecordMVCResourceCommand _addDataRecordMVCResourceCommand;

	@Reference
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

}