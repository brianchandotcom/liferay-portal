/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.displaytemplates.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.template.PortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.PortletDisplayTemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.lar.DDMPortletDataHandlerImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 *
 * @author Juan Fernández
 */
public class DisplayTemplatePortletDataHandlerImpl
	extends BasePortletDataHandler {

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {_displayTemplates};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {_displayTemplates};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement(
			"application-display-templates");

		exportApplicationDisplayStyles(portletDataContext, rootElement);

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		List<Element> templateElements = rootElement.elements("template");

		for (Element templateElement : templateElements) {
			DDMPortletDataHandlerImpl.importTemplate(
				portletDataContext, templateElement);
		}

		return null;
	}

	protected void exportApplicationDisplayStyles(
			PortletDataContext portletDataContext,
			Element applicationDisplayStylesElement)
		throws Exception {

		long[] classNameIds = getPortletDisplayTemplateClassNameIds();

		for (int i = 0; i < classNameIds.length; i++) {
			long classNameId = classNameIds[i];

			List<DDMTemplate> templates =
				DDMTemplateLocalServiceUtil.getTemplates(
					portletDataContext.getScopeGroupId(), classNameId);

			for (DDMTemplate template : templates) {
				DDMPortletDataHandlerImpl.exportTemplate(
					portletDataContext, applicationDisplayStylesElement,
					template);
			}
		}
	}

	protected long[] getPortletDisplayTemplateClassNameIds() {
		List<PortletDisplayTemplateHandler> portletDisplayTemplateHandlers =
			PortletDisplayTemplateHandlerRegistryUtil.
				getPortletDisplayTemplateHandlers();

		long[] classNameIds = new long[portletDisplayTemplateHandlers.size()];

		int index = 0;

		for (PortletDisplayTemplateHandler portletDisplayTemplateHandler :
			portletDisplayTemplateHandlers) {

			classNameIds[index] = PortalUtil.getClassNameId(
				portletDisplayTemplateHandler.getClassName());

			index++;
		}

		return classNameIds;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "display_templates";

	private static PortletDataHandlerBoolean _displayTemplates =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "application-display-templates");

}