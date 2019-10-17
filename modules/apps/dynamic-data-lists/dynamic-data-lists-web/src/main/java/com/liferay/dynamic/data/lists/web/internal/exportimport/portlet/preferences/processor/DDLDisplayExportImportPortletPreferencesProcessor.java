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

package com.liferay.dynamic.data.lists.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.dynamic.data.lists.constants.DDLConstants;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS_DISPLAY,
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
		return ListUtil.fromArray(_capability);
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			portletDataContext.addPortletPermissions(
				DDLConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			throw new PortletDataException(
				"Unable to export portlet permissions", pe);
		}

		String portletId = portletDataContext.getPortletId();

		long recordSetId = GetterUtil.getLong(
			portletPreferences.getValue("recordSetId", null));

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

		if (recordSet != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletId, recordSet);

			ActionableDynamicQuery recordActionableDynamicQuery =
				getRecordActionableDynamicQuery(
					portletDataContext, recordSet, portletId);

			try {
				recordActionableDynamicQuery.performActions();
			}
			catch (PortalException pe) {
				throw new PortletDataException(
					"Unable to export referenced records", pe);
			}
		}

		long displayDDMTemplateId = GetterUtil.getLong(
			portletPreferences.getValue("displayDDMTemplateId", null));

		_exportReferenceDDMTemplate(
			portletDataContext, portletId, displayDDMTemplateId);

		long formDDMTemplateId = GetterUtil.getLong(
			portletPreferences.getValue("formDDMTemplateId", null));

		_exportReferenceDDMTemplate(
			portletDataContext, portletId, formDDMTemplateId);

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			portletDataContext.importPortletPermissions(
				DDLConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			throw new PortletDataException(
				"Unable to export portlet permissions", pe);
		}

		long importedRecordSetId = GetterUtil.getLong(
			portletPreferences.getValue("recordSetId", null));

		Map<Long, Long> recordSetIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDLRecordSet.class);

		long recordSetId = MapUtil.getLong(
			recordSetIds, importedRecordSetId, importedRecordSetId);

		Map<Long, Long> templateIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class);

		long importedDisplayDDMTemplateId = GetterUtil.getLong(
			portletPreferences.getValue("displayDDMTemplateId", null));

		long displayDDMTemplateId = MapUtil.getLong(
			templateIds, importedDisplayDDMTemplateId,
			importedDisplayDDMTemplateId);

		long importedFormDDMTemplateId = GetterUtil.getLong(
			portletPreferences.getValue("formDDMTemplateId", null));

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

	protected ActionableDynamicQuery getRecordActionableDynamicQuery(
		final PortletDataContext portletDataContext,
		final DDLRecordSet recordSet, final String portletId) {

		ActionableDynamicQuery recordActionableDynamicQuery =
			_ddlRecordStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			recordActionableDynamicQuery.getAddCriteriaMethod();

		recordActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				Property property = PropertyFactoryUtil.forName("recordSetId");

				dynamicQuery.add(property.eq(recordSet.getRecordSetId()));
			});

		recordActionableDynamicQuery.setGroupId(recordSet.getGroupId());
		recordActionableDynamicQuery.setPerformActionMethod(
			(DDLRecord record) ->
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, portletId, record));

		return recordActionableDynamicQuery;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetLocalService(
		DDLRecordSetLocalService ddlRecordSetLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.lists.model.DDLRecord)",
		unbind = "-"
	)
	protected void setDDLRecordStagedModelRepository(
		StagedModelRepository<DDLRecord> ddlRecordStagedModelRepository) {

		_ddlRecordStagedModelRepository = ddlRecordStagedModelRepository;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	private void _exportReferenceDDMTemplate(
			PortletDataContext portletDataContext, String portletId,
			long ddmTemplateId)
		throws PortletDataException {

		if (ddmTemplateId == 0) {
			return;
		}

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchDDMTemplate(
			ddmTemplateId);

		if (ddmTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to export referenced template " + ddmTemplateId);
			}

			return;
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, portletId, ddmTemplate);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLDisplayExportImportPortletPreferencesProcessor.class);

	@Reference(target = "(name=ReferencedStagedModelImporter)")
	private Capability _capability;

	private DDLRecordSetLocalService _ddlRecordSetLocalService;
	private StagedModelRepository<DDLRecord> _ddlRecordStagedModelRepository;
	private DDMTemplateLocalService _ddmTemplateLocalService;

}