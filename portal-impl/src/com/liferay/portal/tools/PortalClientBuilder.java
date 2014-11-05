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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.util.ant.Wsdl2JavaTask;

import java.io.File;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalClientBuilder {

	public static void main(String[] args) {
		ToolDependencies.wireBasic();

		if (args.length == 4) {
			new PortalClientBuilder(args[0], args[1], args[2], args[3]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public PortalClientBuilder(
		String fileName, String outputDir, String mappingFile, String url) {

		try {
			Document document = SAXReaderUtil.read(new File(fileName));

			Element rootElement = document.getRootElement();

			List<Element> serviceElements = rootElement.elements("service");

			for (Element serviceElement : serviceElements) {
				String serviceName = serviceElement.attributeValue("name");

				if (serviceName.startsWith("Plugin_") &&
					!FileUtil.exists(mappingFile)) {

					_writePluginMappingFile(mappingFile, serviceElement);
				}

				if (serviceName.startsWith("Plugin_") ||
					serviceName.startsWith("Portal_") ||
					serviceName.startsWith("Portlet_")) {

					Wsdl2JavaTask.generateJava(
						url + "/" + serviceName + "?wsdl", outputDir,
						mappingFile);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		File testNamespace = new File(outputDir + "/com/liferay/portal");

		if (testNamespace.exists()) {
			throw new RuntimeException(
				"Please update " + mappingFile + " to namespace " +
					"com.liferay.portal to com.liferay.client.soap.portal");
		}
	}

	private void _writePluginMappingFile(
			String mappingFile, Element serviceElement)
		throws Exception {

		String wsdlTargetNamespace = null;

		List<Element> parameterElements = serviceElement.elements("parameter");

		for (Element parameterElement : parameterElements) {
			String parameterName = parameterElement.attributeValue("name");

			if (parameterName.equals("wsdlTargetNamespace")) {
				wsdlTargetNamespace = parameterElement.attributeValue("value");

				break;
			}
		}

		int pos = wsdlTargetNamespace.indexOf(".service.");

		String soapNamespace = wsdlTargetNamespace.substring(pos + 9);

		String[] soapNamespaceArray = StringUtil.split(
			soapNamespace, CharPool.PERIOD);

		ArrayUtil.reverse(soapNamespaceArray);

		soapNamespace = StringUtil.merge(soapNamespaceArray, StringPool.PERIOD);

		pos = soapNamespace.lastIndexOf(StringPool.PERIOD);

		soapNamespace =
			soapNamespace.substring(0, pos) + ".client.soap" +
				soapNamespace.substring(pos);

		StringBundler sb = new StringBundler(12);

		sb.append("com.liferay.client.soap.portal.kernel.util=");
		sb.append("http://util.kernel.portal.liferay.com\n");

		sb.append("com.liferay.client.soap.portal.model=");
		sb.append("http://model.portal.liferay.com\n");

		sb.append("com.liferay.client.soap.portal.service=");
		sb.append("http://service.portal.liferay.com\n");

		sb.append(soapNamespace);
		sb.append(".model=");
		sb.append("http://model.knowledgebase.liferay.com\n");

		sb.append(soapNamespace);
		sb.append(".service.http=");
		sb.append("urn:http.service.knowledgebase.liferay.com\n");

		FileUtil.write(mappingFile, sb.toString());
	}

}