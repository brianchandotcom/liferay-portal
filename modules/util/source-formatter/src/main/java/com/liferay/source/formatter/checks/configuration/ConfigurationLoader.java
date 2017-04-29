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

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class ConfigurationLoader {

	public static SourceFormatterConfiguration loadConfiguration(
			String fileName)
		throws Exception {

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

				SourceCheckConfiguration sourceCheckConfiguration =
					new SourceCheckConfiguration(
						checkElement.attributeValue("name"));

				boolean enabled = GetterUtil.getBoolean(
					checkElement.attributeValue("enabled"), true);

				sourceCheckConfiguration.setEnabled(enabled);

				for (Element propertyElement :
						(List<Element>)checkElement.elements("property")) {

					sourceCheckConfiguration.addAttribute(
						propertyElement.attributeValue("name"),
						propertyElement.attributeValue("value"));
				}

				sourceFormatterConfiguration.addSourceCheckConfiguration(
					sourceProcessorName, sourceCheckConfiguration);
			}
		}

		return sourceFormatterConfiguration;
	}

}