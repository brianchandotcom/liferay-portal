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

package com.liferay.source.formatter.checks.configuration;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

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

		SourceFormatterConfiguration sourceFormatterConfiguration =
			new SourceFormatterConfiguration();

		ClassLoader classLoader = ConfigurationLoader.class.getClassLoader();

		String content = StringUtil.read(
			classLoader.getResourceAsStream(fileName));

		Document document = SourceUtil.readXML(content);

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