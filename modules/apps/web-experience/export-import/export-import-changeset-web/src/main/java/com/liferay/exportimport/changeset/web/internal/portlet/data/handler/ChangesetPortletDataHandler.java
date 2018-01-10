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

package com.liferay.exportimport.changeset.web.internal.portlet.data.handler;

import com.liferay.exportimport.changeset.constants.ChangesetPortletKeys;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + ChangesetPortletKeys.CHANGESET},
	service = PortletDataHandler.class
)
public class ChangesetPortletDataHandler extends BasePortletDataHandler {

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTAL);
	}

	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		String[] exportingEntities = parameterMap.get("exportingEntities");

		if (exportingEntities != null) {
			for (String exportingEntity : exportingEntities) {
				String className = _getClassName(exportingEntity);
				long groupId = _getGroupId(exportingEntity);
				String uuid = _getUuid(exportingEntity);

				_exportEntity(portletDataContext, uuid, groupId, className);
			}
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Element importDataRootElement =
			portletDataContext.getImportDataRootElement();

		List<Element> entityTypeElements = importDataRootElement.elements();

		for (Element entityTypeElement : entityTypeElements) {
			List<Element> entityElements = entityTypeElement.elements();

			for (Element entityElement : entityElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, entityElement);
			}
		}

		return portletPreferences;
	}

	private void _exportEntity(
			PortletDataContext portletDataContext, String uuid, long groupId,
			String className)
		throws PortalException {

		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				className);

		StagedModel stagedModel =
			stagedModelDataHandler.fetchStagedModelByUuidAndGroupId(
				uuid, groupId);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModel);
	}

	private String _getClassName(String exportingEntity) {
		return exportingEntity.substring(
			0, exportingEntity.indexOf(StringPool.POUND));
	}

	private long _getGroupId(String exportingEntity) {
		return Long.valueOf(
			exportingEntity.substring(
				exportingEntity.indexOf(StringPool.POUND),
				exportingEntity.lastIndexOf(StringPool.POUND)));
	}

	private String _getUuid(String exportingEntity) {
		return exportingEntity.substring(
			exportingEntity.lastIndexOf(StringPool.POUND) + 1);
	}

}