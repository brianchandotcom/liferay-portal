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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class SuppressionsLoader {

	public static SourceChecksSuppressions loadSuppressions(List<File> files)
		throws Exception {

		SourceChecksSuppressions sourceChecksSuppressions =
			new SourceChecksSuppressions();

		for (File file : files) {
			String suppressionsFileLocation = _getFileLocation(file);

			String content = FileUtil.read(file);

			Document document = SourceUtil.readXML(content);

			Element rootElement = document.getRootElement();

			for (Element suppressElement :
					(List<Element>)rootElement.elements("suppress")) {

				String sourceCheckName = suppressElement.attributeValue(
					"checks");
				String fileName = suppressElement.attributeValue("files");

				sourceChecksSuppressions.addSuppression(
					suppressionsFileLocation, sourceCheckName, fileName);
			}
		}

		return sourceChecksSuppressions;
	}

	private static String _getFileLocation(File file) {
		String absolutePath = SourceUtil.getAbsolutePath(file);

		int pos = absolutePath.lastIndexOf(CharPool.SLASH);

		return absolutePath.substring(0, pos + 1);
	}

}