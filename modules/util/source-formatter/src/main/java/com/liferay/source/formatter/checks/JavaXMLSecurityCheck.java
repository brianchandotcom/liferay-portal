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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.SourceFormatterMessage;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JavaXMLSecurityCheck extends BaseFileCheck {

	public JavaXMLSecurityCheck(
		List<String> runOutsidePortalExcludes, List<String> secureXMLExcludes) {

		_runOutsidePortalExcludes = runOutsidePortalExcludes;
		_secureXMLExcludes = secureXMLExcludes;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (hasGeneratedTag(content)) {
			return new Tuple(content, Collections.emptySet());
		}

		if (isExcludedPath(_secureXMLExcludes, absolutePath) ||
			fileName.contains("/test/") ||
			fileName.contains("/testIntegration/")) {

			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		_checkXMLSecurity(
			sourceFormatterMessages, fileName, absolutePath, content);

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkXMLSecurity(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String absolutePath, String content) {

		String[] xmlVulnerabitilies = new String[] {
			"DocumentBuilderFactory.newInstance",
			"new javax.xml.parsers.SAXParser",
			"new org.apache.xerces.parsers.SAXParser",
			"new org.dom4j.io.SAXReader", "new SAXParser", "new SAXReader",
			"SAXParserFactory.newInstance", "saxParserFactory.newInstance",
			"SAXParserFactory.newSAXParser", "saxParserFactory.newSAXParser",
			"XMLInputFactory.newFactory", "xmlInputFactory.newFactory",
			"XMLInputFactory.newInstance", "xmlInputFactory.newInstance"
		};

		boolean runOutsidePortalExclusion = isExcludedPath(
			_runOutsidePortalExcludes, absolutePath);

		for (String xmlVulnerabitily : xmlVulnerabitilies) {
			if (!content.contains(xmlVulnerabitily)) {
				continue;
			}

			StringBundler sb = new StringBundler(3);

			if (runOutsidePortalExclusion) {
				sb.append("Possible XXE or Quadratic Blowup security ");
				sb.append("vulnerability using ");
			}
			else {
				sb.append("Use SecureXMLFactoryProviderUtil.");
				sb.append("newDocumentBuilderFactory instead of ");
			}

			sb.append(xmlVulnerabitily);

			addMessage(sourceFormatterMessages, fileName, sb.toString());
		}
	}

	private final List<String> _runOutsidePortalExcludes;
	private final List<String> _secureXMLExcludes;

}