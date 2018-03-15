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

package com.liferay.user.associated.data.web.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.exporter.UADEntityExporter;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.Map;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA},
	service = PortletDataHandler.class
)
public class UserAssociatedDataPortletDataHandler
	extends BasePortletDataHandler {

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		if (!parameterMap.containsKey("uadRegistryKey") ||
			!parameterMap.containsKey("userId")) {

			return null;
		}

		String userIdString = parameterMap.get("userId")[0];

		long userId = GetterUtil.getLong(userIdString);

		String[] uadRegistryKeys = parameterMap.get("uadRegistryKey");

		for (String uadRegistryKey : uadRegistryKeys) {
			UADEntityExporter uadEntityExporter =
				_uadRegistry.getUADEntityExporter(uadRegistryKey);

			uadEntityExporter.exportAll(userId, portletDataContext);
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		if (!parameterMap.containsKey("uadRegistryKey") ||
			!parameterMap.containsKey("userId")) {

			return;
		}

		String userIdString = parameterMap.get("userId")[0];

		long userId = GetterUtil.getLong(userIdString);

		String[] uadRegistryKeys = parameterMap.get("uadRegistryKey");

		for (String uadRegistryKey : uadRegistryKeys) {
			UADEntityExporter uadEntityExporter =
				_uadRegistry.getUADEntityExporter(uadRegistryKey);

			uadEntityExporter.prepareManifestSummary(
				userId, portletDataContext);
		}
	}

	@Reference
	private UADRegistry _uadRegistry;

}