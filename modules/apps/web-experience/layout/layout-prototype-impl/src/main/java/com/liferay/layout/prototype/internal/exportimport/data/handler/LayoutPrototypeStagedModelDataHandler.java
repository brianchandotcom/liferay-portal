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

package com.liferay.layout.prototype.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniela Zapata Riesco
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class LayoutPrototypeStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutPrototype> {

	public static final String[] CLASS_NAMES =
		{LayoutPrototype.class.getName()};

	@Override
	public void deleteStagedModel(LayoutPrototype layoutPrototype)
		throws PortalException {

		_layoutPrototypeLocalService.deleteLayoutPrototype(layoutPrototype);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		LayoutPrototype layoutPrototype =
			_layoutPrototypeLocalService.fetchLayoutPrototypeByUuidAndCompanyId(
				uuid, group.getCompanyId());

		if (layoutPrototype != null) {
			deleteStagedModel(layoutPrototype);
		}
	}

	@Override
	public List<LayoutPrototype> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<LayoutPrototype> layoutPrototypes = new ArrayList<>();

		layoutPrototypes.add(
			_layoutPrototypeLocalService.fetchLayoutPrototypeByUuidAndCompanyId(
				uuid, companyId));

		return layoutPrototypes;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(LayoutPrototype layoutPrototype) {
		return layoutPrototype.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPrototype layoutPrototype)
		throws Exception {

		exportLayouts(portletDataContext, layoutPrototype);

		Element layoutPrototypeElement =
			portletDataContext.getExportDataElement(layoutPrototype);

		portletDataContext.addClassedModel(
			layoutPrototypeElement,
			ExportImportPathUtil.getModelPath(layoutPrototype),
			layoutPrototype);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPrototype layoutPrototype)
		throws Exception {

		long userId = portletDataContext.getUserId(
			layoutPrototype.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutPrototype);

		serviceContext.setAttribute("addDefaultLayout", false);

		LayoutPrototype importedLayoutPrototype = null;

		if (portletDataContext.isDataStrategyMirror()) {
			LayoutPrototype existingLayoutPrototype =
				_layoutPrototypeLocalService.
					fetchLayoutPrototypeByUuidAndCompanyId(
						layoutPrototype.getUuid(),
						portletDataContext.getCompanyId());

			if (existingLayoutPrototype == null) {
				serviceContext.setUuid(layoutPrototype.getUuid());

				importedLayoutPrototype =
					_layoutPrototypeLocalService.addLayoutPrototype(
						userId, portletDataContext.getCompanyId(),
						layoutPrototype.getNameMap(),
						layoutPrototype.getDescriptionMap(),
						layoutPrototype.isActive(), serviceContext);
			}
			else {
				importedLayoutPrototype =
					_layoutPrototypeLocalService.updateLayoutPrototype(
						existingLayoutPrototype.getLayoutPrototypeId(),
						layoutPrototype.getNameMap(),
						layoutPrototype.getDescriptionMap(),
						layoutPrototype.isActive(), serviceContext);
			}
		}
		else {
			importedLayoutPrototype =
				_layoutPrototypeLocalService.addLayoutPrototype(
					userId, portletDataContext.getCompanyId(),
					layoutPrototype.getNameMap(),
					layoutPrototype.getDescriptionMap(),
					layoutPrototype.isActive(), serviceContext);
		}

		importLayouts(
			portletDataContext, layoutPrototype,
			importedLayoutPrototype.getGroupId());

		portletDataContext.importClassedModel(
			layoutPrototype, importedLayoutPrototype);
	}

	protected void exportLayouts(
			PortletDataContext portletDataContext,
			LayoutPrototype layoutPrototype)
		throws Exception {

		long groupId = portletDataContext.getGroupId();
		boolean privateLayout = portletDataContext.isPrivateLayout();
		long scopeGroupId = portletDataContext.getScopeGroupId();

		List<Layout> layouts = _layoutLocalService.getLayouts(
			layoutPrototype.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		try {
			portletDataContext.setGroupId(layoutPrototype.getGroupId());
			portletDataContext.setPrivateLayout(true);
			portletDataContext.setScopeGroupId(layoutPrototype.getGroupId());

			for (Layout layout : layouts) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, layoutPrototype, layout,
					PortletDataContext.REFERENCE_TYPE_EMBEDDED);
			}
		}
		finally {
			portletDataContext.setGroupId(groupId);
			portletDataContext.setPrivateLayout(privateLayout);
			portletDataContext.setScopeGroupId(scopeGroupId);
		}
	}

	protected void importLayouts(
			PortletDataContext portletDataContext,
			LayoutPrototype layoutPrototype, long importedGroupId)
		throws PortalException {

		long groupId = portletDataContext.getGroupId();
		boolean privateLayout = portletDataContext.isPrivateLayout();
		long scopeGroupId = portletDataContext.getScopeGroupId();

		try {
			portletDataContext.setGroupId(importedGroupId);
			portletDataContext.setPrivateLayout(true);
			portletDataContext.setScopeGroupId(importedGroupId);

			StagedModelDataHandlerUtil.importReferenceStagedModels(
				portletDataContext, layoutPrototype, Layout.class);
		}
		finally {
			portletDataContext.setGroupId(groupId);
			portletDataContext.setPrivateLayout(privateLayout);
			portletDataContext.setScopeGroupId(scopeGroupId);
		}
	}

	@Override
	protected void importReferenceStagedModels(
		PortletDataContext portletDataContext,
		LayoutPrototype layoutPrototype) {
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutPrototypeLocalService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

}