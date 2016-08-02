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

package com.liferay.layout.set.prototype.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniela Zapata Riesco
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class LayoutSetPrototypeStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutSetPrototype> {

	public static final String[] CLASS_NAMES =
		{LayoutSetPrototype.class.getName()};

	@Override
	public void deleteStagedModel(LayoutSetPrototype layoutSetPrototype)
		throws PortalException {

		_layoutSetPrototypeLocalService.deleteLayoutSetPrototype(
			layoutSetPrototype);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		LayoutSetPrototype layoutSetPrototype =
			fetchStagedModelByUuidAndGroupId(uuid, group.getCompanyId());

		if (layoutSetPrototype != null) {
			deleteStagedModel(layoutSetPrototype);
		}
	}

	@Override
	public List<LayoutSetPrototype> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<LayoutSetPrototype> layoutSetPrototypes = new ArrayList<>();

		layoutSetPrototypes.add(
			_layoutSetPrototypeLocalService.
				fetchLayoutSetPrototypeByUuidAndCompanyId(uuid, companyId));

		return layoutSetPrototypes;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getLayoutSetPrototypeLARFileName(
		LayoutSetPrototype layoutSetPrototype) {

		return layoutSetPrototype.getLayoutSetPrototypeId() + ".lar";
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSetPrototype layoutSetPrototype)
		throws Exception {

		Element layoutSetPrototypeElement =
			portletDataContext.getExportDataElement(layoutSetPrototype);

		portletDataContext.addClassedModel(
			layoutSetPrototypeElement,
			ExportImportPathUtil.getModelPath(layoutSetPrototype),
			layoutSetPrototype);

		exportLayouts(layoutSetPrototype, portletDataContext);

		exportLayoutPrototypes(
			portletDataContext, layoutSetPrototype, layoutSetPrototypeElement);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSetPrototype layoutSetPrototype)
		throws Exception {

		long userId = portletDataContext.getUserId(
			layoutSetPrototype.getUserUuid());

		UnicodeProperties settingsProperties =
			layoutSetPrototype.getSettingsProperties();

		boolean layoutsUpdateable = GetterUtil.getBoolean(
			settingsProperties.getProperty("layoutsUpdateable"), true);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutSetPrototype);

		serviceContext.setAttribute("addDefaultLayout", false);

		LayoutSetPrototype importedLayoutSetPrototype = null;

		if (portletDataContext.isDataStrategyMirror()) {
			LayoutSetPrototype existingLayoutSetPrototype =
				_layoutSetPrototypeLocalService.
					fetchLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototype.getUuid(),
						portletDataContext.getCompanyId());

			if (existingLayoutSetPrototype == null) {
				serviceContext.setUuid(layoutSetPrototype.getUuid());

				importedLayoutSetPrototype =
					_layoutSetPrototypeLocalService.addLayoutSetPrototype(
						userId, portletDataContext.getCompanyId(),
						layoutSetPrototype.getNameMap(),
						layoutSetPrototype.getDescriptionMap(),
						layoutSetPrototype.isActive(), layoutsUpdateable,
						serviceContext);
			}
			else {
				importedLayoutSetPrototype =
					_layoutSetPrototypeLocalService.updateLayoutSetPrototype(
						existingLayoutSetPrototype.getLayoutSetPrototypeId(),
						layoutSetPrototype.getNameMap(),
						layoutSetPrototype.getDescriptionMap(),
						layoutSetPrototype.isActive(), layoutsUpdateable,
						serviceContext);
			}
		}
		else {
			importedLayoutSetPrototype =
				_layoutSetPrototypeLocalService.addLayoutSetPrototype(
					userId, portletDataContext.getCompanyId(),
					layoutSetPrototype.getNameMap(),
					layoutSetPrototype.getDescriptionMap(),
					layoutSetPrototype.isActive(), layoutsUpdateable,
					serviceContext);
		}

		importLayoutPrototypes(portletDataContext, layoutSetPrototype);
		importLayouts(
			portletDataContext, layoutSetPrototype, importedLayoutSetPrototype,
			serviceContext);

		portletDataContext.importClassedModel(
			layoutSetPrototype, importedLayoutSetPrototype);
	}

	protected void exportLayoutPrototypes(
			PortletDataContext portletDataContext,
			LayoutSetPrototype layoutSetPrototype,
			Element layoutSetPrototypeElement)
		throws Exception {

		DynamicQuery dynamicQuery = _layoutLocalService.dynamicQuery();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(layoutSetPrototype.getGroupId()));

		Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

		Property layoutPrototypeUuidProperty = PropertyFactoryUtil.forName(
			"layoutPrototypeUuid");

		conjunction.add(layoutPrototypeUuidProperty.isNotNull());
		conjunction.add(layoutPrototypeUuidProperty.ne(StringPool.BLANK));

		dynamicQuery.add(conjunction);

		List<Layout> layouts = _layoutLocalService.dynamicQuery(dynamicQuery);

		boolean exportLayoutPrototypes = portletDataContext.getBooleanParameter(
			"layout_set_prototypes", "page-templates");

		for (Layout layout : layouts) {
			String layoutPrototypeUuid = layout.getLayoutPrototypeUuid();

			LayoutPrototype layoutPrototype =
				_layoutPrototypeLocalService.
					getLayoutPrototypeByUuidAndCompanyId(
						layoutPrototypeUuid, portletDataContext.getCompanyId());

			portletDataContext.addReferenceElement(
				layout, layoutSetPrototypeElement, layoutPrototype,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
				!exportLayoutPrototypes);

			if (exportLayoutPrototypes) {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, layoutPrototype);
			}
		}
	}

	protected void exportLayouts(
			LayoutSetPrototype layoutSetPrototype,
			PortletDataContext portletDataContext)
		throws Exception {

		File file = null;
		InputStream inputStream = null;

		try {
			file = SitesUtil.exportLayoutSetPrototype(
				layoutSetPrototype, new ServiceContext());

			inputStream = new FileInputStream(file);

			String layoutSetPrototypeLARPath =
				ExportImportPathUtil.getModelPath(
					layoutSetPrototype,
					getLayoutSetPrototypeLARFileName(layoutSetPrototype));

			portletDataContext.addZipEntry(
				layoutSetPrototypeLARPath, inputStream);

			List<Layout> layoutSetPrototypeLayouts =
				_layoutLocalService.getLayouts(
					layoutSetPrototype.getGroupId(), true);

			Element layoutSetPrototypeElement =
				portletDataContext.getExportDataElement(layoutSetPrototype);

			for (Layout layoutSetPrototypeLayout : layoutSetPrototypeLayouts) {
				portletDataContext.addReferenceElement(
					layoutSetPrototype, layoutSetPrototypeElement,
					layoutSetPrototypeLayout,
					PortletDataContext.REFERENCE_TYPE_EMBEDDED, false);
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);

			if (file != null) {
				file.delete();
			}
		}
	}

	protected void importLayoutPrototypes(
			PortletDataContext portletDataContext,
			LayoutSetPrototype layoutSetPrototype)
		throws PortletDataException {

		List<Element> layoutPrototypeElements =
			portletDataContext.getReferenceDataElements(
				layoutSetPrototype, LayoutPrototype.class);

		for (Element layoutPrototypeElement : layoutPrototypeElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutPrototypeElement);
		}
	}

	protected void importLayouts(
			PortletDataContext portletDataContext,
			LayoutSetPrototype layoutSetPrototype,
			LayoutSetPrototype importedLayoutSetPrototype,
			ServiceContext serviceContext)
		throws PortalException {

		InputStream inputStream = null;

		try {
			String layoutSetPrototypeLARPath =
				ExportImportPathUtil.getModelPath(
					layoutSetPrototype,
					getLayoutSetPrototypeLARFileName(layoutSetPrototype));

			inputStream = portletDataContext.getZipEntryAsInputStream(
				layoutSetPrototypeLARPath);

			SitesUtil.importLayoutSetPrototype(
				importedLayoutSetPrototype, inputStream, serviceContext);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	@Override
	protected void importReferenceStagedModels(
		PortletDataContext portletDataContext,
		LayoutSetPrototype layoutSetPrototype) {
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

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		_layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

}