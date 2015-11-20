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

package com.liferay.layout.set.prototype.web.lar;

import com.liferay.layout.set.prototype.web.constants.LayoutSetPrototypePortletKeys;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.impl.LayoutSetPrototypeImpl;
import com.liferay.portal.service.LayoutSetPrototypeLocalService;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.DataLevel;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerControl;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;
import com.liferay.portlet.exportimport.xstream.XStreamAliasRegistryUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniela Zapata Riesco
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE
	},
	service = PortletDataHandler.class
)
public class LayoutSetPrototypePortletDataHandler
	extends BasePortletDataHandler {

	public static final String NAMESPACE = "layout_set_prototypes";

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTAL);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(LayoutSetPrototype.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "site-templates", true, true,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "page-templates", true, false)
				},
				LayoutSetPrototype.class.getName()
			));

		XStreamAliasRegistryUtil.register(
			LayoutSetPrototypeImpl.class, "LayoutSetPrototype");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				LayoutSetPrototypePortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_layoutSetPrototypeLocalService.deleteNondefaultLayoutSetPrototypes(
			portletDataContext.getCompanyId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortalPermissions();

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery actionableDynamicQuery =
			_layoutSetPrototypeLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortalPermissions();

		Element layoutSetPrototypesElement =
			portletDataContext.getImportDataGroupElement(
				LayoutSetPrototype.class);

		List<Element> layoutSetPrototypeElements =
			layoutSetPrototypesElement.elements();

		for (Element layoutSetPrototypeElement : layoutSetPrototypeElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutSetPrototypeElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery layoutSetPrototypeExportActionableDynamicQuery =
			_layoutSetPrototypeLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		layoutSetPrototypeExportActionableDynamicQuery.performCount();
	}

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		_layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private volatile LayoutSetPrototypeLocalService
		_layoutSetPrototypeLocalService;

}