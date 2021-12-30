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

package com.liferay.layout.admin.web.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.configuration.LayoutExportImportConfiguration;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	configurationPid = "com.liferay.layout.configuration.LayoutExportImportConfiguration",
	immediate = true, service = StagedModelDataHandler.class
)
public class LayoutPageTemplateEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutPageTemplateEntry> {

	public static final String[] CLASS_NAMES = {
		LayoutPageTemplateEntry.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		return layoutPageTemplateEntry.getName();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_layoutExportImportConfiguration = ConfigurableUtil.createConfigurable(
			LayoutExportImportConfiguration.class, properties);
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				fetchLayoutPageTemplateCollection(
					layoutPageTemplateEntry.
						getLayoutPageTemplateCollectionId());

		if (layoutPageTemplateCollection != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutPageTemplateEntry,
				layoutPageTemplateCollection,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			layoutPageTemplateEntry.getClassTypeId());

		if (ddmStructure != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutPageTemplateEntry, ddmStructure,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		if (layoutPageTemplateEntry.getLayoutPrototypeId() > 0) {
			LayoutPrototype layoutPrototype =
				_layoutPrototypeLocalService.getLayoutPrototype(
					layoutPageTemplateEntry.getLayoutPrototypeId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutPageTemplateEntry, layoutPrototype,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		if (layoutPageTemplateEntry.getPreviewFileEntryId() > 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				layoutPageTemplateEntry.getPreviewFileEntryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutPageTemplateEntry, fileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		_exportReferenceLayout(layoutPageTemplateEntry, portletDataContext);

		Element entryElement = portletDataContext.getExportDataElement(
			layoutPageTemplateEntry);

		long defaultUserId = _userLocalService.getDefaultUserId(
			layoutPageTemplateEntry.getCompanyId());

		if (defaultUserId == layoutPageTemplateEntry.getUserId()) {
			entryElement.addAttribute("preloaded", "true");
		}

		entryElement.addAttribute(
			"type", String.valueOf(layoutPageTemplateEntry.getType()));

		portletDataContext.addClassedModel(
			entryElement,
			ExportImportPathUtil.getModelPath(layoutPageTemplateEntry),
			layoutPageTemplateEntry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException {

		importMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		long layoutPageTemplateEntryId = GetterUtil.getLong(
			referenceElement.attributeValue("class-pk"));
		String name = GetterUtil.getString(
			referenceElement.attributeValue("name"));
		int type = GetterUtil.getInteger(
			referenceElement.attributeValue("type"));
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		LayoutPageTemplateEntry existingLayoutPageTemplateEntry = null;

		if (!preloaded) {
			existingLayoutPageTemplateEntry = fetchMissingReference(
				uuid, groupId);
		}
		else {
			existingLayoutPageTemplateEntry = _fetchExistingTemplate(
				uuid, groupId, name, type, 0L, preloaded);
		}

		if (existingLayoutPageTemplateEntry == null) {
			return;
		}

		Map<Long, Long> layoutPageTemplateEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				LayoutPageTemplateEntry.class);

		layoutPageTemplateEntryIds.put(
			layoutPageTemplateEntryId,
			existingLayoutPageTemplateEntry.getLayoutPageTemplateEntryId());
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long layoutPageTemplateEntryId)
		throws Exception {

		LayoutPageTemplateEntry existingLayoutPageTemplateEntry =
			fetchMissingReference(uuid, groupId);

		if (existingLayoutPageTemplateEntry == null) {
			return;
		}

		Map<Long, Long> layoutPageTemplateEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				LayoutPageTemplateEntry.class);

		layoutPageTemplateEntryIds.put(
			layoutPageTemplateEntryId,
			existingLayoutPageTemplateEntry.getLayoutPageTemplateEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		StagedModelDataHandlerUtil.importReferenceStagedModel(
			portletDataContext, layoutPageTemplateEntry,
			LayoutPageTemplateCollection.class,
			layoutPageTemplateEntry.getLayoutPageTemplateCollectionId());

		Map<Long, Long> layoutPageTemplateCollectionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				LayoutPageTemplateCollection.class);

		long layoutPageTemplateCollectionId = MapUtil.getLong(
			layoutPageTemplateCollectionIds,
			layoutPageTemplateEntry.getLayoutPageTemplateCollectionId(),
			layoutPageTemplateEntry.getLayoutPageTemplateCollectionId());

		long classTypeId = layoutPageTemplateEntry.getClassTypeId();

		if (classTypeId > 0) {
			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, layoutPageTemplateEntry, DDMStructure.class,
				Long.valueOf(classTypeId));

			Map<Long, Long> structureIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DDMStructure.class);

			classTypeId = MapUtil.getLong(
				structureIds, classTypeId, classTypeId);
		}

		Map<Long, Long> plids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		long plid = MapUtil.getLong(
			plids, layoutPageTemplateEntry.getPlid(),
			layoutPageTemplateEntry.getPlid());

		LayoutPageTemplateEntry importedLayoutPageTemplateEntry =
			(LayoutPageTemplateEntry)layoutPageTemplateEntry.clone();

		importedLayoutPageTemplateEntry.setGroupId(
			portletDataContext.getScopeGroupId());
		importedLayoutPageTemplateEntry.setLayoutPageTemplateCollectionId(
			layoutPageTemplateCollectionId);
		importedLayoutPageTemplateEntry.setClassTypeId(classTypeId);
		importedLayoutPageTemplateEntry.setPlid(plid);

		LayoutPrototype layoutPrototype = null;

		Element layoutPrototypeElement =
			portletDataContext.getReferenceDataElement(
				layoutPageTemplateEntry, LayoutPrototype.class,
				layoutPageTemplateEntry.getLayoutPrototypeId());

		if (layoutPrototypeElement != null) {
			String layoutPrototypePath = layoutPrototypeElement.attributeValue(
				"path");

			layoutPrototype =
				(LayoutPrototype)portletDataContext.getZipEntryAsObject(
					layoutPrototypePath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutPrototype);

			Map<Long, Long> layoutPrototypeIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					LayoutPrototype.class);

			long layoutPrototypeId = MapUtil.getLong(
				layoutPrototypeIds,
				layoutPageTemplateEntry.getLayoutPrototypeId(),
				layoutPageTemplateEntry.getLayoutPrototypeId());

			importedLayoutPageTemplateEntry.setLayoutPrototypeId(
				layoutPrototypeId);
		}

		if (portletDataContext.isDataStrategyMirror()) {
			Element element =
				portletDataContext.getImportDataStagedModelElement(
					layoutPageTemplateEntry);

			boolean preloaded = GetterUtil.getBoolean(
				element.attributeValue("preloaded"));

			LayoutPageTemplateEntry existingLayoutPageTemplateEntry =
				_fetchExistingTemplate(
					layoutPageTemplateEntry.getUuid(),
					portletDataContext.getScopeGroupId(),
					layoutPageTemplateEntry.getName(),
					layoutPageTemplateEntry.getType(), plid, preloaded);

			if (existingLayoutPageTemplateEntry == null) {
				importedLayoutPageTemplateEntry = _addStagedModel(
					portletDataContext, importedLayoutPageTemplateEntry);

				_validateLayoutPrototype(
					portletDataContext, layoutPageTemplateEntry,
					importedLayoutPageTemplateEntry, layoutPrototype);
			}
			else {
				importedLayoutPageTemplateEntry.setMvccVersion(
					existingLayoutPageTemplateEntry.getMvccVersion());
				importedLayoutPageTemplateEntry.setLayoutPageTemplateEntryId(
					existingLayoutPageTemplateEntry.
						getLayoutPageTemplateEntryId());

				importedLayoutPageTemplateEntry =
					_stagedModelRepository.updateStagedModel(
						portletDataContext, importedLayoutPageTemplateEntry);
			}
		}
		else {
			importedLayoutPageTemplateEntry = _addStagedModel(
				portletDataContext, importedLayoutPageTemplateEntry);
		}

		if (layoutPageTemplateEntry.getPreviewFileEntryId() > 0) {
			Map<Long, Long> fileEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					FileEntry.class);

			long previewFileEntryId = MapUtil.getLong(
				fileEntryIds, layoutPageTemplateEntry.getPreviewFileEntryId(),
				0);

			importedLayoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					updateLayoutPageTemplateEntry(
						importedLayoutPageTemplateEntry.
							getLayoutPageTemplateEntryId(),
						previewFileEntryId);
		}

		portletDataContext.importClassedModel(
			layoutPageTemplateEntry, importedLayoutPageTemplateEntry);
	}

	@Override
	protected StagedModelRepository<LayoutPageTemplateEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private LayoutPageTemplateEntry _addStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		if (layoutPageTemplateEntry.isDefaultTemplate()) {
			LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchDefaultLayoutPageTemplateEntry(
						layoutPageTemplateEntry.getGroupId(),
						layoutPageTemplateEntry.getClassNameId(),
						layoutPageTemplateEntry.getClassTypeId());

			if (defaultLayoutPageTemplateEntry != null) {
				layoutPageTemplateEntry.setDefaultTemplate(false);
			}
		}

		return _stagedModelRepository.addStagedModel(
			portletDataContext, layoutPageTemplateEntry);
	}

	private void _exportReferenceLayout(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			PortletDataContext portletDataContext)
		throws Exception {

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		if (layout == null) {
			return;
		}

		Element layoutElement = portletDataContext.getReferenceElement(
			Layout.class.getName(), Long.valueOf(layout.getPlid()));

		if ((layoutElement != null) &&
			Validator.isNotNull(
				layoutElement.attributeValue("master-layout-uuid"))) {

			return;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (_layoutExportImportConfiguration.exportDraftLayout() &&
			(draftLayout != null)) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutPageTemplateEntry, draftLayout,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, layoutPageTemplateEntry, layout,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
	}

	private LayoutPageTemplateEntry _fetchExistingTemplate(
		String uuid, long groupId, String name, int type, long plid,
		boolean preloaded) {

		LayoutPageTemplateEntry existingTemplate = null;

		if (!preloaded) {
			existingTemplate =
				_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
					uuid, groupId);
		}
		else if (plid > 0) {
			existingTemplate =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntryByPlid(plid);
		}

		if ((existingTemplate == null) && preloaded) {
			existingTemplate =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(groupId, name, type);
		}

		return existingTemplate;
	}

	private void _validateLayoutPrototype(
			PortletDataContext portletDataContext,
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			LayoutPageTemplateEntry importedLayoutPageTemplateEntry,
			LayoutPrototype layoutPrototype)
		throws Exception {

		if (ExportImportThreadLocal.isStagingInProcess() ||
			(layoutPrototype == null)) {

			return;
		}

		LayoutPrototype existingLayoutPrototype =
			_layoutPrototypeLocalService.getLayoutPrototypeByUuidAndCompanyId(
				layoutPrototype.getUuid(), portletDataContext.getCompanyId());

		if (existingLayoutPrototype == null) {
			return;
		}

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_layoutPageTemplateEntryLocalService.
				getLayoutPageTemplateEntriesByLayoutPrototypeId(
					existingLayoutPrototype.getLayoutPrototypeId());

		for (LayoutPageTemplateEntry existingLayoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			long existingLayoutPageTemplateEntryId =
				existingLayoutPageTemplateEntry.getLayoutPageTemplateEntryId();
			long importedLayoutPageTemplateEntryId =
				importedLayoutPageTemplateEntry.getLayoutPageTemplateEntryId();

			if ((existingLayoutPageTemplateEntryId !=
					importedLayoutPageTemplateEntryId) &&
				(existingLayoutPageTemplateEntry.getCompanyId() ==
					importedLayoutPageTemplateEntry.getCompanyId())) {

				throw new UnsupportedOperationException(
					StringBundler.concat(
						"Layout page template ",
						layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
						" cannot be imported because a layout prototype with ",
						"UUID ", layoutPrototype.getUuid(), " and company ID ",
						portletDataContext.getCompanyId(), " already exists"));
			}
		}
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	private volatile LayoutExportImportConfiguration
		_layoutExportImportConfiguration;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateEntry)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutPageTemplateEntry>
		_stagedModelRepository;

	private UserLocalService _userLocalService;

}