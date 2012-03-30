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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Brian Wing Shun Chan
 */
public class WSDDMerger {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		new WSDDMerger(args[0], args[1]);
	}

	public static void merge(String source, String destination)
		throws DocumentException, IOException {

		// Source

		File sourceFile = new File(source);

		Document doc = SAXReaderUtil.read(sourceFile);

		Element root = doc.getRootElement();

		List<Element> sourceServices = root.elements("service");

		if (sourceServices.size() == 0) {
			return;
		}

		// Destination

		File destinationFile = new File(destination);

		doc = SAXReaderUtil.read(destinationFile);

		root = doc.getRootElement();

		Map<String, Element> servicesMap = new TreeMap<String, Element>();

		List<Element> serviceElements = root.elements("service");

		for (Element service : serviceElements) {
			String name = service.attributeValue("name");

			servicesMap.put(name, service);

			service.detach();
		}

		for (Element service : sourceServices) {
			String name = service.attributeValue("name");

			servicesMap.put(name, service);

			service.detach();
		}

		for (Map.Entry<String, Element> entry : servicesMap.entrySet()) {
			Element service = entry.getValue();

			root.add(service);
		}

		String content = doc.formattedString();

		content = StringUtil.replace(content, "\"/>", "\" />");

		FileUtil.write(destination, content, true);
	}

	public WSDDMerger(String source, String destination) {
		try {
			merge(source, destination);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}