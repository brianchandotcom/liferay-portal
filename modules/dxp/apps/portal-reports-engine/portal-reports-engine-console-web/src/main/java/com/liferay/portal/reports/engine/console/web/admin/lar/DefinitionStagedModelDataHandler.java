/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.web.admin.lar;

import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.DefinitionLocalService;
import com.liferay.portal.reports.engine.console.service.SourceLocalService;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class DefinitionStagedModelDataHandler
	extends BaseStagedModelDataHandler<Definition> {

	public static final String[] CLASS_NAMES = {Definition.class.getName()};

	@Override
	public void deleteStagedModel(Definition definition)
		throws PortalException {

		_definitionLocalService.deleteDefinition(definition);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Definition definition = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (definition != null) {
			deleteStagedModel(definition);
		}
	}

	@Override
	public Definition fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _definitionLocalService.fetchDefinitionByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<Definition> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _definitionLocalService.getDefinitionsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<Definition>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(Definition definition) {
		return definition.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Definition definition)
		throws Exception {

		Source source = _sourceLocalService.fetchSource(
			definition.getSourceId());

		if (source != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, definition, source,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element definitionElement = portletDataContext.getExportDataElement(
			definition);

		for (String fullFileName : definition.getAttachmentsFiles()) {
			Element attachmentElement = definitionElement.addElement(
				"attachment");

			String binPath = ExportImportPathUtil.getModelPath(
				definition, fullFileName);

			attachmentElement.addAttribute("bin-path", binPath);

			attachmentElement.addAttribute("full-file-name", fullFileName);

			byte[] bytes = DLStoreUtil.getFileAsBytes(
				definition.getCompanyId(), CompanyConstants.SYSTEM,
				fullFileName);

			portletDataContext.addZipEntry(binPath, bytes);
		}

		portletDataContext.addClassedModel(
			definitionElement, ExportImportPathUtil.getModelPath(definition),
			definition);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Definition definition)
		throws Exception {

		long userId = portletDataContext.getUserId(definition.getUserUuid());

		Map<Long, Long> sourceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Source.class);

		long sourceId = MapUtil.getLong(
			sourceIds, definition.getSourceId(), definition.getSourceId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			definition);

		Element definitionElement =
			portletDataContext.getImportDataStagedModelElement(definition);

		Element attachmentElement = definitionElement.element("attachment");

		String fileName = null;
		String binPath = null;

		if (attachmentElement != null) {
			binPath = attachmentElement.attributeValue("bin-path");
			String fullFileName = attachmentElement.attributeValue(
				"full-file-name");

			fileName = FileUtil.getShortFileName(fullFileName);
		}

		try (InputStream inputStream =
				portletDataContext.getZipEntryAsInputStream(binPath)) {

			Definition importedDefinition = null;

			if (portletDataContext.isDataStrategyMirror()) {
				Definition existingDefinition =
					fetchStagedModelByUuidAndGroupId(
						definition.getUuid(),
						portletDataContext.getScopeGroupId());

				if (existingDefinition == null) {
					serviceContext.setUuid(definition.getUuid());

					importedDefinition = _definitionLocalService.addDefinition(
						userId, portletDataContext.getScopeGroupId(),
						definition.getNameMap(), definition.getDescriptionMap(),
						sourceId, definition.getReportParameters(), fileName,
						inputStream, serviceContext);
				}
				else {
					importedDefinition =
						_definitionLocalService.updateDefinition(
							existingDefinition.getDefinitionId(),
							definition.getNameMap(),
							definition.getDescriptionMap(), sourceId,
							definition.getReportParameters(), fileName,
							inputStream, serviceContext);
				}
			}
			else {
				importedDefinition = _definitionLocalService.addDefinition(
					userId, portletDataContext.getScopeGroupId(),
					definition.getNameMap(), definition.getDescriptionMap(),
					sourceId, definition.getReportParameters(), fileName,
					inputStream, serviceContext);
			}

			portletDataContext.importClassedModel(
				definition, importedDefinition);
		}
	}

	@Reference
	private DefinitionLocalService _definitionLocalService;

	@Reference
	private SourceLocalService _sourceLocalService;

}