/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.exportimport.data.handler;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = StagedModelDataHandler.class)
public class LayoutSEOEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutSEOEntry> {

	public static final String[] CLASS_NAMES = {LayoutSEOEntry.class.getName()};

	@Override
	public void deleteStagedModel(LayoutSEOEntry layoutSEOEntry) {
		_layoutSEOEntryLocalService.deleteLayoutSEOEntry(layoutSEOEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_layoutSEOEntryLocalService.deleteLayoutSEOEntry(uuid, groupId);
	}

	@Override
	public List<LayoutSEOEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _layoutSEOEntryLocalService.
			getLayoutSEOEntriesByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSEOEntry layoutSEOEntry)
		throws Exception {

		FileEntry openGraphImageFileEntry = _fetchFileEntry(
			layoutSEOEntry.getOpenGraphImageFileEntryId());

		if (openGraphImageFileEntry != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutSEOEntry, openGraphImageFileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		DDMStructure ddmStructure = _getDDMStructure(
			portletDataContext.getCompanyId());

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, layoutSEOEntry, ddmStructure,
			PortletDataContext.REFERENCE_TYPE_STRONG);

		Element layoutSEOEntryElement = portletDataContext.getExportDataElement(
			layoutSEOEntry);

		Element structureFieldsElement = layoutSEOEntryElement.addElement(
			"structure-fields");

		if (layoutSEOEntry.getDDMStorageId() != 0) {
			String ddmFormValuesPath = ExportImportPathUtil.getModelPath(
				ddmStructure, String.valueOf(layoutSEOEntry.getDDMStorageId()));

			structureFieldsElement.addAttribute(
				"ddm-form-values-path", ddmFormValuesPath);

			DDMFormValuesSerializerSerializeResponse
				ddmFormValuesSerializerSerializeResponse =
					_jsonDDMFormValuesSerializer.serialize(
						DDMFormValuesSerializerSerializeRequest.Builder.
							newBuilder(
								_ddmStorageEngineManager.getDDMFormValues(
									layoutSEOEntry.getDDMStorageId())
							).build());

			portletDataContext.addZipEntry(
				ddmFormValuesPath,
				ddmFormValuesSerializerSerializeResponse.getContent());
		}

		portletDataContext.addClassedModel(
			layoutSEOEntryElement,
			ExportImportPathUtil.getModelPath(layoutSEOEntry), layoutSEOEntry);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSEOEntry layoutSEOEntry)
		throws Exception {

		Map<Long, Long> fileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		long openGraphImageFileEntryId = MapUtil.getLong(
			fileEntryIds, layoutSEOEntry.getOpenGraphImageFileEntryId(), 0);

		LayoutSEOEntry existingLayoutSEOEntry =
			fetchStagedModelByUuidAndGroupId(
				layoutSEOEntry.getUuid(), layoutSEOEntry.getGroupId());

		if (existingLayoutSEOEntry == null) {
			Map<Long, Layout> newPrimaryKeysMap =
				(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
					Layout.class + ".layout");

			Layout layout = newPrimaryKeysMap.get(layoutSEOEntry.getLayoutId());

			_layoutSEOEntryLocalService.updateLayoutSEOEntry(
				layoutSEOEntry.getUserId(), layout.getGroupId(),
				layout.isPrivateLayout(), layout.getLayoutId(),
				layoutSEOEntry.isCanonicalURLEnabled(),
				layoutSEOEntry.getCanonicalURLMap(),
				layoutSEOEntry.isOpenGraphDescriptionEnabled(),
				layoutSEOEntry.getOpenGraphDescriptionMap(),
				layoutSEOEntry.getOpenGraphImageAltMap(),
				openGraphImageFileEntryId,
				layoutSEOEntry.isOpenGraphTitleEnabled(),
				layoutSEOEntry.getOpenGraphTitleMap(),
				_createServiceContext(
					layoutSEOEntry, existingLayoutSEOEntry,
					portletDataContext));
		}
		else {
			_layoutSEOEntryLocalService.updateLayoutSEOEntry(
				existingLayoutSEOEntry.getUserId(),
				portletDataContext.getScopeGroupId(),
				layoutSEOEntry.isPrivateLayout(),
				existingLayoutSEOEntry.getLayoutId(),
				layoutSEOEntry.isCanonicalURLEnabled(),
				layoutSEOEntry.getCanonicalURLMap(),
				layoutSEOEntry.isOpenGraphDescriptionEnabled(),
				layoutSEOEntry.getOpenGraphDescriptionMap(),
				layoutSEOEntry.getOpenGraphImageAltMap(),
				openGraphImageFileEntryId,
				layoutSEOEntry.isOpenGraphTitleEnabled(),
				layoutSEOEntry.getOpenGraphTitleMap(),
				_createServiceContext(
					layoutSEOEntry, existingLayoutSEOEntry,
					portletDataContext));
		}
	}

	private ServiceContext _createServiceContext(
			LayoutSEOEntry layoutSEOEntry,
			LayoutSEOEntry existingLayoutSEOEntry,
			PortletDataContext portletDataContext)
		throws Exception {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutSEOEntry);

		DDMStructure ddmStructure = _getDDMStructure(
			portletDataContext.getCompanyId());

		Element layoutSEOEntryElement = portletDataContext.getImportDataElement(
			layoutSEOEntry);

		Element structureFieldsElement =
			(Element)layoutSEOEntryElement.selectSingleNode("structure-fields");

		String serializedDDMFormValues = portletDataContext.getZipEntryAsString(
			structureFieldsElement.attributeValue("ddm-form-values-path"));

		serviceContext.setAttribute(
			ddmStructure.getStructureId() + "ddmFormValues",
			serializedDDMFormValues);

		if (portletDataContext.isDataStrategyMirror() &&
			(existingLayoutSEOEntry == null)) {

			serviceContext.setUuid(layoutSEOEntry.getUuid());
		}

		return serviceContext;
	}

	private FileEntry _fetchFileEntry(long fileEntryId) {
		if (fileEntryId <= 0) {
			return null;
		}

		try {
			return _dlAppLocalService.getFileEntry(fileEntryId);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get file entry " + fileEntryId, portalException);
			}

			return null;
		}
	}

	private DDMStructure _getDDMStructure(long companyId) throws Exception {
		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		return _ddmStructureLocalService.getStructure(
			companyGroup.getGroupId(),
			_classNameLocalService.getClassNameId(
				LayoutSEOEntry.class.getName()),
			"custom-meta-tags");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEOEntryStagedModelDataHandler.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDMStorageEngineManager _ddmStorageEngineManager;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = "(ddm.form.values.serializer.type=json)")
	private DDMFormValuesSerializer _jsonDDMFormValuesSerializer;

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

}