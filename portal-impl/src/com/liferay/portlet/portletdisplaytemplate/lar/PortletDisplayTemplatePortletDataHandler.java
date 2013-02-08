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
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Juan Fernández
 */
public class PortletDisplayTemplatePortletDataHandler
	extends BasePortletDataHandler {

	public static final String NAMESPACE = "portlet_display_templates";

	public PortletDisplayTemplatePortletDataHandler() {
		setAlwaysExportable(true);
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "application-display-templates"));
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportRootElement();

		exportPortletDisplayTemplates(portletDataContext, rootElement);

		return rootElement.formattedString();
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
			StagedModelDataHandlerUtil.importStagedModel(
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
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, portletDisplayTemplatesElement,
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

}