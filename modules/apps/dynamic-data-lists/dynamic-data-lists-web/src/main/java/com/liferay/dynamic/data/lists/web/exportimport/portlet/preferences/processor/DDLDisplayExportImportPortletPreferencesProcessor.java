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

package com.liferay.dynamic.data.lists.web.exportimport.portlet.preferences.processor;

import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.capability.ReferencedStagedModelImporterCapability;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS_DISPLAY
	},
	service = ExportImportPortletPreferencesProcessor.class
)
public class DDLDisplayExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return null;
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.toList(
			new Capability[] {_referencedStagedModelImporterCapability});
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			portletDataContext.addPortletPermissions(
				DDLPermission.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			throw new PortletDataException(
				"Unable to export portlet permissions", pe);
		}

		String portletId = portletDataContext.getPortletId();

		long recordSetId = GetterUtil.getLong(
			portletPreferences.getValue("recordSetId", null), 0);

		if (recordSetId == 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Record set ID is not set for preferences of portlet " +
						portletId);
			}

			return portletPreferences;
		}

		DDLRecordSet recordSet = _ddlRecordSetLocalService.fetchRecordSet(
			recordSetId);

		if (recordSet == null) {
			return portletPreferences;
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, portletId, recordSet);

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			portletDataContext.importPortletPermissions(
				DDLPermission.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			throw new PortletDataException(
				"Unable to export portlet permissions", pe);
		}

		long importedRecordSetId = GetterUtil.getLong(
			portletPreferences.getValue("recordSetId", null));
		long importedDisplayDDMTemplateId = GetterUtil.getLong(
			portletPreferences.getValue("displayDDMTemplateId", null));
		long importedFormDDMTemplateId = GetterUtil.getLong(
			portletPreferences.getValue("formDDMTemplateId", null));

		Map<Long, Long> recordSetIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDLRecordSet.class);

		long recordSetId = MapUtil.getLong(
			recordSetIds, importedRecordSetId, importedRecordSetId);

		Map<Long, Long> templateIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class);

		long displayDDMTemplateId = MapUtil.getLong(
			templateIds, importedDisplayDDMTemplateId,
			importedDisplayDDMTemplateId);

		long formDDMTemplateId = MapUtil.getLong(
			templateIds, importedFormDDMTemplateId, importedFormDDMTemplateId);

		try {
			portletPreferences.setValue(
				"recordSetId", String.valueOf(recordSetId));
			portletPreferences.setValue(
				"displayDDMTemplateId", String.valueOf(displayDDMTemplateId));
			portletPreferences.setValue(
				"formDDMTemplateId", String.valueOf(formDDMTemplateId));
		}
		catch (ReadOnlyException roe) {
			throw new PortletDataException(
				"Unable to update portlet preferences during import", roe);
		}

		return portletPreferences;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetLocalService(
		DDLRecordSetLocalService ddlRecordSetLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setReferencedStagedModelImporterCapability(
		ReferencedStagedModelImporterCapability
			referencedStagedModelImporterCapability) {

		_referencedStagedModelImporterCapability =
			referencedStagedModelImporterCapability;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLDisplayExportImportPortletPreferencesProcessor.class);

	private volatile DDLRecordSetLocalService _ddlRecordSetLocalService;
	private volatile ReferencedStagedModelImporterCapability
		_referencedStagedModelImporterCapability;

}