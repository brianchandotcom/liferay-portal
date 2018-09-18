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

package com.liferay.bean.portlet.cdi.extension.internal.xml;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

import java.io.IOException;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Neil Griffin
 */
public class LiferayDescriptorParser {

	public static Map<String, Map<String, String>> parse(
			URL liferayDescriptorURL)
		throws DocumentException, IOException {

		Map<String, Map<String, String>> configurations = new HashMap<>();

		String xml = HttpUtil.URLtoString(liferayDescriptorURL);

		Document document = UnsecureSAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		for (Element portletElement : rootElement.elements("portlet")) {
			String portletName = null;

			Map<String, String> configuration = new HashMap<>();

			for (Element element : portletElement.elements()) {
				String elementName = element.getName();

				if (Objects.equals(elementName, "portlet-name")) {
					portletName = element.getText();
				}
				else {
					configuration.put(
						"com.liferay.portlet.".concat(elementName),
						element.getText());
				}
			}

			if (portletName != null) {
				configurations.put(portletName, configuration);
			}
		}

		return new HashMap(configurations);
	}

}