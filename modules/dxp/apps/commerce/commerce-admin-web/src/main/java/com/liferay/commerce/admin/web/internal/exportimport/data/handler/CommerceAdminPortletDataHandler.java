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

package com.liferay.commerce.admin.web.internal.exportimport.data.handler;

import com.liferay.commerce.admin.CommerceAdminModule;
import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.admin.web.internal.util.CommerceAdminModuleRegistry;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
	service = PortletDataHandler.class
)
public class CommerceAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "commerce_admin";

	@Activate
	protected void activate() {
		List<StagedModelType> deletionSystemEventStagedModelTypes =
			new ArrayList<>();
		List<PortletDataHandlerControl> exportControls = new ArrayList<>();

		Map<String, CommerceAdminModule> commerceAdminModules =
			_commerceAdminModuleRegistry.getCommerceAdminModules();

		for (CommerceAdminModule commerceAdminModule :
				commerceAdminModules.values()) {

			deletionSystemEventStagedModelTypes.addAll(
				commerceAdminModule.getDeletionSystemEventStagedModelTypes());
			exportControls.addAll(
				commerceAdminModule.getExportControls(NAMESPACE));
		}

		setDeletionSystemEventStagedModelTypes(
			deletionSystemEventStagedModelTypes.toArray(
				new StagedModelType
					[deletionSystemEventStagedModelTypes.size()]));
		setExportControls(
			exportControls.toArray(
				new PortletDataHandlerControl[exportControls.size()]));

		setImportControls(getExportControls());
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				CommerceAdminPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		Map<String, CommerceAdminModule> commerceAdminModules =
			_commerceAdminModuleRegistry.getCommerceAdminModules();

		for (CommerceAdminModule commerceAdminModule :
				commerceAdminModules.values()) {

			commerceAdminModule.deleteData(portletDataContext);
		}

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		Map<String, CommerceAdminModule> commerceAdminModules =
			_commerceAdminModuleRegistry.getCommerceAdminModules();

		for (CommerceAdminModule commerceAdminModule :
				commerceAdminModules.values()) {

			commerceAdminModule.exportData(NAMESPACE, portletDataContext);
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Map<String, CommerceAdminModule> commerceAdminModules =
			_commerceAdminModuleRegistry.getCommerceAdminModules();

		for (CommerceAdminModule commerceAdminModule :
				commerceAdminModules.values()) {

			commerceAdminModule.importData(NAMESPACE, portletDataContext);
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		Map<String, CommerceAdminModule> commerceAdminModules =
			_commerceAdminModuleRegistry.getCommerceAdminModules();

		for (CommerceAdminModule commerceAdminModule :
				commerceAdminModules.values()) {

			commerceAdminModule.prepareManifestSummary(portletDataContext);
		}
	}

	@Reference
	private CommerceAdminModuleRegistry _commerceAdminModuleRegistry;

}