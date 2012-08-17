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

package com.liferay.portlet.portletdisplaytemplate.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.lar.DDMPortletDataHandlerImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Juan Fernández
 */
public class PortletDisplayTemplatePortletDataHandlerImpl
	extends BasePortletDataHandler {

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {_portletDisplayTemplates};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {_portletDisplayTemplates};
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

		Element rootElement = document.addElement("portlet-display-templates");

		exportPortletDisplayTemplates(portletDataContext, rootElement);

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		List<Element> ddmTemplateElements = rootElement.elements("template");

		for (Element ddmTemplateElement : ddmTemplateElements) {
			DDMPortletDataHandlerImpl.importTemplate(
				portletDataContext, ddmTemplateElement);
		}

		return null;
	}

	protected void exportPortletDisplayTemplates(
			PortletDataContext portletDataContext,
			Element portletDisplayTemplatesElement)
		throws Exception {

		long[] classNameIds =
			PortletDisplayTemplateHandlerRegistryUtil.getClassNameIds();

		for (long classNameId : classNameIds) {
			List<DDMTemplate> ddmTemplates =
				DDMTemplateLocalServiceUtil.getTemplates(
					portletDataContext.getScopeGroupId(), classNameId);

			for (DDMTemplate ddmTemplate : ddmTemplates) {
				DDMPortletDataHandlerImpl.exportTemplate(
					portletDataContext, portletDisplayTemplatesElement,
					getTemplatePath(portletDataContext, ddmTemplate),
					ddmTemplate);
			}
		}
	}

	protected String getTemplatePath(
		PortletDataContext portletDataContext, DDMTemplate template) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.PORTLET_DISPLAY_TEMPLATES));
		sb.append("/templates/");
		sb.append(template.getTemplateId());
		sb.append(".xml");

		return sb.toString();
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "portlet_display_templates";

	private static PortletDataHandlerBoolean _portletDisplayTemplates =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "application-display-templates");

}