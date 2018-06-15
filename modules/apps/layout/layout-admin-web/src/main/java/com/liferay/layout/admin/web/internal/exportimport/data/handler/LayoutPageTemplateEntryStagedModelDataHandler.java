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

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class LayoutPageTemplateEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutPageTemplateEntry> {

	public static final String[] CLASS_NAMES =
		{LayoutPageTemplateEntry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		return layoutPageTemplateEntry.getName();
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

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				layoutPageTemplateEntry.getGroupId(),
				_portal.getClassNameId(LayoutPageTemplateEntry.class),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutPageTemplateEntry, fragmentEntryLink,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		Element entryElement = portletDataContext.getExportDataElement(
			layoutPageTemplateEntry);

		portletDataContext.addClassedModel(
			entryElement,
			ExportImportPathUtil.getModelPath(layoutPageTemplateEntry),
			layoutPageTemplateEntry);
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

		Map<Long, Long> layoutPageTemplateCollectionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				LayoutPageTemplateCollection.class);

		long layoutPageTemplateCollectionId = MapUtil.getLong(
			layoutPageTemplateCollectionIds,
			layoutPageTemplateEntry.getLayoutPageTemplateCollectionId(),
			layoutPageTemplateEntry.getLayoutPageTemplateCollectionId());

		LayoutPageTemplateEntry importedLayoutPageTemplateEntry =
			(LayoutPageTemplateEntry)layoutPageTemplateEntry.clone();

		importedLayoutPageTemplateEntry.setGroupId(
			portletDataContext.getScopeGroupId());
		importedLayoutPageTemplateEntry.setLayoutPageTemplateCollectionId(
			layoutPageTemplateCollectionId);

		LayoutPageTemplateEntry existingLayoutPageTemplateEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				layoutPageTemplateEntry.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingLayoutPageTemplateEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedLayoutPageTemplateEntry =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedLayoutPageTemplateEntry);
		}
		else {
			importedLayoutPageTemplateEntry.setLayoutPageTemplateEntryId(
				existingLayoutPageTemplateEntry.getLayoutPageTemplateEntryId());

			importedLayoutPageTemplateEntry =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedLayoutPageTemplateEntry);
		}

		importFragmentEntryLinks(
			portletDataContext, layoutPageTemplateEntry,
			importedLayoutPageTemplateEntry);

		portletDataContext.importClassedModel(
			layoutPageTemplateEntry, importedLayoutPageTemplateEntry);
	}

	@Override
	protected StagedModelRepository<LayoutPageTemplateEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	protected void importFragmentEntryLinks(
			PortletDataContext portletDataContext,
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			LayoutPageTemplateEntry importedLayoutPageTemplateEntry)
		throws Exception {

		_fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(
				portletDataContext.getScopeGroupId(),
				_portal.getClassNameId(LayoutPageTemplateEntry.class),
				importedLayoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		List<Element> fragmentEntryLinkElements =
			portletDataContext.getReferenceDataElements(
				layoutPageTemplateEntry, FragmentEntryLink.class);

		for (Element fragmentEntryLinkElement : fragmentEntryLinkElements) {
			String fragmentEntryLinkPath =
				fragmentEntryLinkElement.attributeValue("path");

			FragmentEntryLink fragmentEntryLink =
				(FragmentEntryLink)portletDataContext.getZipEntryAsObject(
					fragmentEntryLinkPath);

			fragmentEntryLink.setClassNameId(
				_portal.getClassNameId(LayoutPageTemplateEntry.class));
			fragmentEntryLink.setClassPK(
				importedLayoutPageTemplateEntry.getLayoutPageTemplateEntryId());

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, fragmentEntryLink);
		}
	}

	@Override
	protected boolean isSkipImportReferenceStagedModels() {
		return true;
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateEntry)",
		unbind = "-"
	)
	private StagedModelRepository<LayoutPageTemplateEntry>
		_stagedModelRepository;

}