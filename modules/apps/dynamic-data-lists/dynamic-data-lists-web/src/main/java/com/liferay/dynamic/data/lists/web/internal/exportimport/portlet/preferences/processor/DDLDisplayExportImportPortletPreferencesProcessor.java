/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.dynamic.data.lists.constants.DDLConstants;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

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
	public boolean isPublishDisplayedContent() {
		return false;
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		if (!MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.PORTLET_DATA) &&
			MergeLayoutPrototypesThreadLocal.isInProgress()) {

			return portletPreferences;
		}

		try {
			portletDataContext.addPortletPermissions(
				DDLConstants.RESOURCE_NAME);
		}
		catch (PortalException portalException) {
			throw new PortletDataException(
				"Unable to export portlet permissions", portalException);
		}

		String portletId = portletDataContext.getPortletId();

		String recordSetId = portletPreferences.getValue("recordSetId", null);

		if (recordSetId == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No recordSetId found in preferences of portlet " +
						portletId);
			}

			return portletPreferences;
		}

		long recordSetGroupId = GetterUtil.getLong(
			portletPreferences.getValue("groupId", StringPool.BLANK));

		if (recordSetGroupId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No group ID found in preferences of portlet " + portletId);
			}

			return portletPreferences;
		}

		Group group = _groupLocalService.fetchGroup(recordSetGroupId);

		if (group == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No group found with group ID " + recordSetGroupId);
			}

			return portletPreferences;
		}

		if (ExportImportThreadLocal.isStagingInProcess() &&
			!group.isStagedPortlet(DDLPortletKeys.DYNAMIC_DATA_LISTS)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Dynamic Data Lists are not staged in the site " +
						group.getName());
			}

			return portletPreferences;
		}

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		if (recordSetGroupId != previousScopeGroupId) {
			portletDataContext.setScopeGroupId(recordSetGroupId);
		}

		DDLRecordSet recordSet = _ddlRecordSetLocalService.fetchRecordSet(
			GetterUtil.getLong(recordSetId));

		if (recordSet == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Portlet ", portletId,
						" refers to an invalid recordSetId ", recordSetId));
			}

			portletDataContext.setScopeGroupId(previousScopeGroupId);

			return portletPreferences;
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, portletId, recordSet);

		ActionableDynamicQuery recordActionableDynamicQuery =
			_getRecordActionableDynamicQuery(
				portletDataContext, recordSet, portletId);

		try {
			recordActionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			throw new PortletDataException(
				"Unable to export referenced records", portalException);
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
		catch (PortalException portalException) {
			throw new PortletDataException(
				"Unable to export portlet permissions", portalException);
		}

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long importGroupId = GetterUtil.getLong(
			portletPreferences.getValue("groupId", null));

		if ((importGroupId == portletDataContext.getCompanyGroupId()) &&
			MergeLayoutPrototypesThreadLocal.isInProgress()) {

			portletDataContext.setScopeType("company");
		}

		long groupId = MapUtil.getLong(groupIds, importGroupId, importGroupId);

		Map<String, Long> recordSetGroupIds =
			(Map<String, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDLRecordSet.class + ".groupId");

		String importedRecordSetId = String.valueOf(
			GetterUtil.getLong(
				portletPreferences.getValue("recordSetId", null)));

		if (recordSetGroupIds.containsKey(importedRecordSetId)) {
			groupId = recordSetGroupIds.get(importedRecordSetId);
		}

		portletDataContext.setScopeGroupId(groupId);

		if (Validator.isNotNull(importedRecordSetId) && (groupId != 0)) {
			Group importedRecordSetGroup = _groupLocalService.fetchGroup(
				groupId);

			if (importedRecordSetGroup == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("No group found with group ID " + groupId);
				}

				return portletPreferences;
			}

			if (!ExportImportThreadLocal.isStagingInProcess() ||
				importedRecordSetGroup.isStagedPortlet(
					DDLPortletKeys.DYNAMIC_DATA_LISTS)) {

				Map<Long, Long> recordSetIds =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						DDLRecordSet.class);

				long recordSetId = GetterUtil.getLong(importedRecordSetId);

				recordSetId = MapUtil.getLong(recordSetIds, recordSetId, 0);

				try {
					portletPreferences.setValue(
						"recordSetId", String.valueOf(recordSetId));

					portletPreferences.setValue(
						"groupId", String.valueOf(groupId));
				}
				catch (ReadOnlyException readOnlyException) {
					throw new PortletDataException(
						"Unable to update portlet preferences during import",
						readOnlyException);
				}
			}
		}

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
				"displayDDMTemplateId", String.valueOf(displayDDMTemplateId));
			portletPreferences.setValue(
				"formDDMTemplateId", String.valueOf(formDDMTemplateId));
		}
		catch (ReadOnlyException readOnlyException) {
			throw new PortletDataException(
				"Unable to update portlet preferences during import",
				readOnlyException);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		return portletPreferences;
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

	private ActionableDynamicQuery _getRecordActionableDynamicQuery(
		PortletDataContext portletDataContext, DDLRecordSet recordSet,
		String portletId) {

		ActionableDynamicQuery recordActionableDynamicQuery =
			_ddlRecordStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
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

	private static final Log _log = LogFactoryUtil.getLog(
		DDLDisplayExportImportPortletPreferencesProcessor.class);

	@Reference(target = "(name=ReferencedStagedModelImporter)")
	private Capability _capability;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.lists.model.DDLRecord)"
	)
	private StagedModelRepository<DDLRecord> _ddlRecordStagedModelRepository;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}