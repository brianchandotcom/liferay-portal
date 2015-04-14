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

package com.liferay.layout.prototype.web.lar;

import com.liferay.layout.prototype.web.constants.LayoutPrototypePortletKeys;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.lar.xstream.XStreamAliasRegistryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.impl.LayoutPrototypeImpl;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniela Zapata Riesco
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutPrototypePortletKeys.LAYOUT_PROTOTYPE
	},
	service = PortletDataHandler.class
)
public class LayoutPrototypePortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "layout_prototypes";

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTAL);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(LayoutPrototype.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "page-templates", true, true, null,
				LayoutPrototype.class.getName()));

		XStreamAliasRegistryUtil.register(
			LayoutPrototypeImpl.class, "LayoutPrototype");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				LayoutPrototypePortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		LayoutPrototypeLocalServiceUtil.deleteNondefaultLayoutPrototypes(
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
			LayoutPrototypeLocalServiceUtil.getExportActionableDynamicQuery(
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

		Element layoutPrototypesElement =
			portletDataContext.getImportDataGroupElement(LayoutPrototype.class);

		List<Element> layoutPrototypeElements =
			layoutPrototypesElement.elements();

		for (Element layoutPrototypeElement : layoutPrototypeElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutPrototypeElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery layoutPrototypeExportActionableDynamicQuery =
			LayoutPrototypeLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		layoutPrototypeExportActionableDynamicQuery.performCount();
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

}