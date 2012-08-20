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

package com.liferay.portal.kernel.portletdisplaytemplate;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eduardo Garcia
 */
public abstract class BasePortletDisplayTemplateHandler
	implements PortletDisplayTemplateHandler {

	public List<Element> getDefaultTemplates() throws Exception {
		List<Element> templateElements = new ArrayList<Element>();

		String filePath = getDefaultTemplatesConfigPath();

		if (Validator.isNotNull(filePath)) {
			String xml = StringUtil.read(
				getClass().getClassLoader(), filePath, false);

			Document document = SAXReaderUtil.read(xml);

			Element rootElement = document.getRootElement();

			templateElements = rootElement.elements("template");
		}

		return templateElements;
	}

	public String getHelpTemplatePath() {
		return "com/liferay/portlet/portletdisplaytemplate/dependencies/" +
			"portlet_display_template.vm";
	}

	protected String getDefaultTemplatesConfigPath() {
		return null;
	}

}