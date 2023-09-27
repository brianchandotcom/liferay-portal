/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.configuration;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.IOException;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class ConfigurationLoader {

	public static SourceFormatterConfiguration loadConfiguration(
			String fileName)
		throws DocumentException, IOException {

		ClassLoader classLoader = ConfigurationLoader.class.getClassLoader();

		String content = StringUtil.read(
			classLoader.getResourceAsStream(fileName));

		Document document = SourceUtil.readXML(content);

		if (document == null) {
			throw new DocumentException();
		}

		SourceFormatterConfiguration sourceFormatterConfiguration =
			new SourceFormatterConfiguration();

		Element rootElement = document.getRootElement();

		for (Element sourceProcessorElement :
				(List<Element>)rootElement.elements("source-processor")) {

			String sourceProcessorName = sourceProcessorElement.attributeValue(
				"name");

			for (Element checkElement :
					(List<Element>)sourceProcessorElement.elements("check")) {

				Element categoryElement = checkElement.element("category");

				SourceCheckConfiguration sourceCheckConfiguration =
					new SourceCheckConfiguration(
						categoryElement.attributeValue("name"),
						checkElement.attributeValue("name"));

				for (Element propertyElement :
						(List<Element>)checkElement.elements("property")) {

					sourceCheckConfiguration.addAttribute(
						propertyElement.attributeValue("name"),
						propertyElement.attributeValue("value"));
				}

				Element weightElement = checkElement.element("weight");

				if (weightElement != null) {
					sourceCheckConfiguration.setWeight(
						GetterUtil.getInteger(weightElement.getText()));
				}

				sourceFormatterConfiguration.addSourceCheckConfiguration(
					sourceProcessorName, sourceCheckConfiguration);
			}
		}

		return sourceFormatterConfiguration;
	}

}