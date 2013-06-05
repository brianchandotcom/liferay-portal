/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.layoutsetprototypes.lar;

import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.List;

/**
 * @author Daniela Zapata Riesco
 */
public class LayoutSetPrototypeStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutSetPrototype> {

	public static final String[] CLASS_NAMES =
		{LayoutSetPrototype.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
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
			layoutSetPrototype, LayoutSetPrototypePortletDataHandler.NAMESPACE);

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

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutSetPrototype, LayoutSetPrototypePortletDataHandler.NAMESPACE);

		serviceContext.setAttribute("addDefaultLayout", false);

		UnicodeProperties settingsProperties =
			layoutSetPrototype.getSettingsProperties();

		boolean layoutsUpdateable = GetterUtil.getBoolean(
			settingsProperties.getProperty("layoutsUpdateable"), true);

		LayoutSetPrototype importedLayoutSetPrototype = null;

		if (portletDataContext.isDataStrategyMirror()) {
			LayoutSetPrototype existingLayoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					fetchLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototype.getUuid(),
						portletDataContext.getCompanyId());

			if (existingLayoutSetPrototype == null) {
				serviceContext.setUuid(layoutSetPrototype.getUuid());

				importedLayoutSetPrototype =
					LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
						userId, portletDataContext.getCompanyId(),
						layoutSetPrototype.getNameMap(),
						layoutSetPrototype.getDescription(),
						layoutSetPrototype.isActive(), layoutsUpdateable,
						serviceContext);
			}
			else {
				importedLayoutSetPrototype =
					LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(
						existingLayoutSetPrototype.getLayoutSetPrototypeId(),
						layoutSetPrototype.getNameMap(),
						layoutSetPrototype.getDescription(),
						layoutSetPrototype.isActive(), layoutsUpdateable,
						serviceContext);
			}
		}
		else {
			importedLayoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
					userId, portletDataContext.getCompanyId(),
					layoutSetPrototype.getNameMap(),
					layoutSetPrototype.getDescription(),
					layoutSetPrototype.isActive(), layoutsUpdateable,
					serviceContext);
		}

		portletDataContext.importClassedModel(
			layoutSetPrototype, importedLayoutSetPrototype,
			LayoutSetPrototypePortletDataHandler.NAMESPACE);

		importLayoutPrototypes(portletDataContext, layoutSetPrototype);

		importLayouts(
			portletDataContext, layoutSetPrototype, importedLayoutSetPrototype,
			serviceContext);
	}

	protected void exportLayoutPrototypes(
			PortletDataContext portletDataContext,
			LayoutSetPrototype layoutSetPrototype,
			Element layoutSetPrototypeElement)
		throws Exception {

		DynamicQuery dynamicQuery = LayoutLocalServiceUtil.dynamicQuery();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		Group group = layoutSetPrototype.getGroup();

		dynamicQuery.add(groupIdProperty.eq(group.getGroupId()));

		Conjunction conjunction = RestrictionsFactoryUtil.conjunction();
		dynamicQuery.add(conjunction);

		Property layoutPrototypeUuidProperty = PropertyFactoryUtil.forName(
			"layoutPrototypeUuid");

		conjunction.add(layoutPrototypeUuidProperty.isNotNull());
		conjunction.add(layoutPrototypeUuidProperty.ne(StringPool.BLANK));

		List<Layout> layouts = LayoutLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		boolean exportLayoutPrototypes = portletDataContext.getBooleanParameter(
			LayoutSetPrototypePortletDataHandler.NAMESPACE,
			LayoutSetPrototypePortletDataHandler.
				LAYOUT_PROTOTYPE_EXPORT_CONTROL);

		boolean missingReference = !exportLayoutPrototypes;

		for (Layout layout : layouts) {
			String layoutPrototypeUuid = layout.getLayoutPrototypeUuid();

			LayoutPrototype layoutPrototype =
				LayoutPrototypeLocalServiceUtil.
					getLayoutPrototypeByUuidAndCompanyId(
						layoutPrototypeUuid, portletDataContext.getCompanyId());

			portletDataContext.addReferenceElement(
				layout, layoutSetPrototypeElement, layoutPrototype,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, missingReference);

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

			String path = ExportImportPathUtil.getModelPath(
				layoutSetPrototype,
				getLayoutSetPrototypeLarFileName(layoutSetPrototype));

			portletDataContext.addZipEntry(path, inputStream);

			List<Layout> layoutSetPrototypeLayouts =
				LayoutLocalServiceUtil.getLayouts(
					layoutSetPrototype.getGroup().getGroupId(), true);

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

	protected String getLayoutSetPrototypeLarFileName(
		LayoutSetPrototype layoutSetPrototype) {

		return layoutSetPrototype.getLayoutSetPrototypeId() +
			_LAR_FILE_NAME_SUFFIX;
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
		throws PortalException, SystemException {

		InputStream inputStream = null;

		try {
			String path = ExportImportPathUtil.getModelPath(
				layoutSetPrototype,
				getLayoutSetPrototypeLarFileName(layoutSetPrototype));

			inputStream = portletDataContext.getZipEntryAsInputStream(path);

			SitesUtil.importLayoutSetPrototype(
				importedLayoutSetPrototype, inputStream, serviceContext);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	private static final String _LAR_FILE_NAME_SUFFIX = ".lar";

}