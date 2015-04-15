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

package com.liferay.portlet.layoutprototypes.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniela Zapata Riesco
 */
@OSGiBeanProperties
public class LayoutPrototypeStagedModelDataHandler
	extends BaseStagedModelDataHandler <LayoutPrototype> {

	public static final String[] CLASS_NAMES =
		{LayoutPrototype.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		LayoutPrototype layoutPrototype =
			LayoutPrototypeLocalServiceUtil.
				fetchLayoutPrototypeByUuidAndCompanyId(
					uuid, group.getCompanyId());

		if (layoutPrototype != null) {
			LayoutPrototypeLocalServiceUtil.deleteLayoutPrototype(
				layoutPrototype);
		}
	}

	@Override
	public List<LayoutPrototype> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<LayoutPrototype> layoutPrototypes = new ArrayList<>();

		layoutPrototypes.add(
			LayoutPrototypeLocalServiceUtil.
				fetchLayoutPrototypeByUuidAndCompanyId(uuid, companyId));

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
				LayoutPrototypeLocalServiceUtil.
					fetchLayoutPrototypeByUuidAndCompanyId(
						layoutPrototype.getUuid(),
						portletDataContext.getCompanyId());

			if (existingLayoutPrototype == null) {
				serviceContext.setUuid(layoutPrototype.getUuid());

				importedLayoutPrototype =
					LayoutPrototypeLocalServiceUtil.addLayoutPrototype(
						userId, portletDataContext.getCompanyId(),
						layoutPrototype.getNameMap(),
						layoutPrototype.getDescriptionMap(),
						layoutPrototype.isActive(), serviceContext);
			}
			else {
				importedLayoutPrototype =
					LayoutPrototypeLocalServiceUtil.updateLayoutPrototype(
						existingLayoutPrototype.getLayoutPrototypeId(),
						layoutPrototype.getNameMap(),
						layoutPrototype.getDescriptionMap(),
						layoutPrototype.isActive(), serviceContext);
			}
		}
		else {
			importedLayoutPrototype =
				LayoutPrototypeLocalServiceUtil.addLayoutPrototype(
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
		long scopeGroupId = portletDataContext.getScopeGroupId();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			layoutPrototype.getGroupId(),
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		try {
			portletDataContext.setGroupId(layoutPrototype.getGroupId());
			portletDataContext.setScopeGroupId(layoutPrototype.getGroupId());

			for (Layout layout : layouts) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, layoutPrototype, layout,
					PortletDataContext.REFERENCE_TYPE_EMBEDDED);
			}
		}
		finally {
			portletDataContext.setGroupId(groupId);
			portletDataContext.setScopeGroupId(scopeGroupId);
		}
	}

	protected void importLayouts(
			PortletDataContext portletDataContext,
			LayoutPrototype layoutPrototype, long importedGroupId)
		throws PortalException {

		long groupId = portletDataContext.getGroupId();
		long scopeGroupId = portletDataContext.getScopeGroupId();

		try {
			portletDataContext.setGroupId(importedGroupId);
			portletDataContext.setScopeGroupId(importedGroupId);

			StagedModelDataHandlerUtil.importReferenceStagedModels(
				portletDataContext, layoutPrototype, Layout.class);
		}
		finally {
			portletDataContext.setGroupId(groupId);
			portletDataContext.setScopeGroupId(scopeGroupId);
		}
	}

	@Override
	protected void importReferenceStagedModels(
		PortletDataContext portletDataContext,
		LayoutPrototype layoutPrototype) {
	}

}