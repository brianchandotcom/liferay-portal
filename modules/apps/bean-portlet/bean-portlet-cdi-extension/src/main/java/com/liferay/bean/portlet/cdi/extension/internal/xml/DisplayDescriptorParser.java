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

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

import java.io.IOException;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Neil Griffin
 */
public class DisplayDescriptorParser {

	public static Map<String, String> parse(URL displayDescriptorURL)
		throws DocumentException, IOException {

		Map<String, String> displayCategoryMap = new HashMap<>();

		Document document = UnsecureSAXReaderUtil.read(
			displayDescriptorURL, true);

		Element rootElement = document.getRootElement();

		for (Element categoryElement : rootElement.elements("category")) {
			String categoryName = categoryElement.attributeValue("name");

			for (Element portletElement : categoryElement.elements("portlet")) {
				String portletId = portletElement.attributeValue("id");

				displayCategoryMap.put(portletId, categoryName);
			}
		}

		return displayCategoryMap;
	}

}