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
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exporter.UADEntityExporter;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.List;
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

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		if (!parameterMap.containsKey("uadRegistryKey") ||
			!parameterMap.containsKey("userId")) {

			return null;
		}

		String[] uadRegistryKeys = parameterMap.get("uadRegistryKey");
		String userIdString = parameterMap.get("userId")[0];

		long userId = Long.valueOf(userIdString);

		for (String uadRegistryKey : uadRegistryKeys) {
			_exportUADEntities(portletDataContext, uadRegistryKey, userId);
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		if (!parameterMap.containsKey("uadRegistryKey") ||
			!parameterMap.containsKey("userId")) {

			return;
		}

		String uadRegistryKey = parameterMap.get("uadRegistryKey")[0];
		String userIdString = parameterMap.get("userId")[0];

		long userId = Long.valueOf(userIdString);

		UADEntityAggregator uadEntityAggregator =
			_uadRegistry.getUADEntityAggregator(uadRegistryKey);

		List<UADEntity> uadEntities = uadEntityAggregator.getUADEntities(
			userId, 0, 1);

		UADEntity uadEntity = uadEntities.get(0);

		long modelAdditionCount = uadEntityAggregator.count(userId);

		manifestSummary.addModelAdditionCount(
			uadEntity.getStagedModelType(), modelAdditionCount);
	}

	private void _exportUADEntities(
			PortletDataContext portletDataContext, String uadRegistryKey,
			long userId)
		throws Exception {

		UADEntityAggregator uadEntityAggregator =
			_uadRegistry.getUADEntityAggregator(uadRegistryKey);

		List<UADEntity> uadEntities = uadEntityAggregator.getUADEntities(
			userId);

		UADEntityExporter uadEntityExporter = _uadRegistry.getUADEntityExporter(
			uadRegistryKey);

		StagedModelDataHandler stagedModelDataHandler =
			uadEntityExporter.getStagedModelDataHandler();

		for (UADEntity uadEntity : uadEntities) {
			stagedModelDataHandler.exportStagedModel(
				portletDataContext, uadEntity);
		}
	}

	@Reference
	private UADRegistry _uadRegistry;

}