/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.servlet.filters.absoluteredirects.AbsoluteRedirectsFilter;
import com.liferay.portal.xml.DocumentImpl;
import com.liferay.util.xml.XMLMerger;
import com.liferay.util.xml.descriptor.WebXML23Descriptor;
import com.liferay.util.xml.descriptor.WebXML24Descriptor;
import com.liferay.util.xml.descriptor.WebXML30Descriptor;
import com.liferay.util.xml.descriptor.XMLDescriptor;

import java.io.IOException;

/**
 * @author Brian Wing Shun Chan
 * @author Tang Ying Jian
 * @author Brian Myunghun Kim
 * @author Minhchau Dang
 */
public class WebXMLBuilder {

	public static void main(String[] args) {
		ToolDependencies.wireBasic();

		if (args.length == 3) {
			mergeWebXML(args[0], args[1], args[2]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public static void mergeWebXML(
		String originalWebXML, String customWebXML, String mergedWebXML) {

		try {
			String customContent = getCustomContent(customWebXML);

			String originalContent = FileUtil.read(originalWebXML);

			String mergedContent = originalContent;

			int x = customContent.indexOf("<filter-mapping>");

			if (x != -1) {
				int y = customContent.lastIndexOf("</filter-mapping>") + 17;

				String filterMappings = customContent.substring(x, y);

				int z = getOriginalContentIndex(originalContent);

				mergedContent =
					mergedContent.substring(0, z) + filterMappings +
						mergedContent.substring(z);

				customContent =
					customContent.substring(0, x) +
						customContent.substring(y + 1);
			}

			int z = getMergedContentIndex(mergedContent);

			mergedContent =
				mergedContent.substring(0, z) + customContent +
					mergedContent.substring(z);

			mergedContent = organizeWebXML(mergedContent);

			FileUtil.write(mergedWebXML, mergedContent, true);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	public static String organizeWebXML(String webXML)
		throws DocumentException, IOException {

		webXML = HtmlUtil.stripComments(webXML);

		Document document = UnsecureSAXReaderUtil.read(webXML);

		Element rootElement = document.getRootElement();

		double version = 2.3;

		version = GetterUtil.getDouble(
			rootElement.attributeValue("version"), version);

		XMLDescriptor xmlDescriptor = null;

		if (version == 2.3) {
			xmlDescriptor = new WebXML23Descriptor();
		}
		else if (version == 2.4) {
			xmlDescriptor = new WebXML24Descriptor();
		}
		else {
			xmlDescriptor = new WebXML30Descriptor();
		}

		XMLMerger xmlMerger = new XMLMerger(xmlDescriptor);

		DocumentImpl documentImpl = (DocumentImpl)document;

		xmlMerger.organizeXML(documentImpl.getWrappedDocument());

		return document.formattedString();
	}

	protected static String getCustomContent(String customWebXML)
		throws IOException {

		String customContent = FileUtil.read(customWebXML);

		int x = customContent.indexOf("<web-app");

		x = customContent.indexOf(">", x) + 1;

		int y = customContent.indexOf("</web-app>");

		return customContent.substring(x, y);
	}

	protected static int getMergedContentIndex(String content) {
		int x = content.indexOf("<web-app");

		return content.indexOf(">", x) + 1;
	}

	protected static int getOriginalContentIndex(String content) {
		int x = content.indexOf(AbsoluteRedirectsFilter.class.getName());

		if (x == -1) {
			x = content.indexOf("<web-app");
			x = content.indexOf(">", x) + 1;

			return x;
		}

		x = content.lastIndexOf("<filter-name", x);
		x = content.indexOf(">", x) + 1;

		int y = content.indexOf("</filter-name>", x);

		String filterName = content.substring(x, y);

		x = content.lastIndexOf(filterName);

		y = content.indexOf("</filter-mapping>", x);

		y = content.indexOf(">", y) + 1;

		return y;
	}

	private static final Log _log = LogFactoryUtil.getLog(WebXMLBuilder.class);

}