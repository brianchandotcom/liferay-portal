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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class XMLTagAttributesCheck extends BaseTagAttributesCheck {

	@Override
	protected Tag doFormatLineBreaks(Tag tag, String absolutePath) {
		if (absolutePath.endsWith("/pom.xml") ||
			absolutePath.endsWith(".pom")) {

			return tag;
		}

		if (ArrayUtil.contains(_SINGLE_LINE_TAG_NAMES, tag.getName())) {
			tag.setMultiLine(false);
		}
		else if (ArrayUtil.contains(_MULTI_LINE_TAG_NAMES, tag.getName())) {
			tag.setMultiLine(true);
		}

		return tag;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		content = formatIncorrectLineBreak(fileName, content);

		content = _formatTagAttributes(absolutePath, content);

		content = formatMultiLinesTagAttributes(absolutePath, content, true);

		return content;
	}

	@Override
	protected Tag sortHTMLTagAttributes(Tag tag) {
		return tag;
	}

	private String _formatTagAttributes(String absolutePath, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			boolean sortAttributes = true;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String trimmedLine = StringUtil.trimLeading(line);

				if (sortAttributes) {
					if (trimmedLine.startsWith(StringPool.LESS_THAN) &&
						trimmedLine.endsWith(StringPool.GREATER_THAN) &&
						!trimmedLine.startsWith("<?") &&
						!trimmedLine.startsWith("<%") &&
						!trimmedLine.startsWith("<!") &&
						!(line.contains("<![CDATA[") && line.contains("]]>"))) {

						line = formatTagAttributes(
							absolutePath, line, true, false);
					}
					else if (trimmedLine.startsWith("<![CDATA[") &&
							 !trimmedLine.endsWith("]]>")) {

						sortAttributes = false;
					}
				}
				else if (trimmedLine.endsWith("]]>")) {
					sortAttributes = true;
				}

				sb.append(line);
				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	private static final String[] _MULTI_LINE_TAG_NAMES = {
		"arquillian", "beans", "chmod", "copy", "dirset", "ehcache", "fileset",
		"get", "globmapper", "instance-wrapper", "jar", "java", "javac",
		"loadfile", "mapper", "math", "mirrors-get", "move", "patternset",
		"propertyregex", "replace", "replacefilter", "replaceregexp",
		"resourcecontains", "taskdef", "unjar", "untar", "unzip", "uptodate",
		"war", "workflow-definition", "zip", "zipfileset"
	};

	private static final String[] _SINGLE_LINE_TAG_NAMES = {
		"action", "ant", "antcall", "antelope:repeat", "antelope:replace",
		"antelope:stringutil", "antelope:substring", "appender", "appender-ref",
		"archetype-descriptor", "arg", "attach", "attribute", "available",
		"band", "baseDir", "basename", "bean", "bottomPen", "category", "check",
		"class", "column", "command", "component", "condition",
		"constructor-arg", "contains", "content", "contribution-limit",
		"copyField", "definition", "delete", "description", "dirname", "div",
		"dtd", "dynamic-element", "dynamicField", "echo", "element", "entity",
		"entry", "env", "equals", "exclude", "exec", "execute", "fail", "field",
		"fieldType", "fileSet", "filter", "finder", "finder-column", "font",
		"for", "format", "forward", "generator", "gradle-execute",
		"hibernate-mapping", "hint-collection", "http", "id",
		"ignored-parameter", "ignore-error", "import", "include", "isset",
		"istrue", "jvmarg", "layout", "layout-template", "leftPen", "link",
		"list", "loadproperties", "local", "log4j:configuration", "logger",
		"lst", "macrodef", "matches", "media:thumbnail", "message", "meta-data",
		"mkdir", "model", "module", "order", "order-column", "os", "param",
		"parser", "path", "pathconvert", "pathelement", "portlet",
		"portlet-app", "poshi-execute", "present", "priority", "project",
		"property", "propertycopy", "propertyfile", "propertyref",
		"propertyresource", "put", "record", "ref", "reference", "remake-dir",
		"replaceregex", "replacestring", "reportElement", "requestHandler",
		"requiredProperty", "resourcecount", "resource-root", "return",
		"rightPen", "role", "rollingPolicy", "root", "service-builder", "sleep",
		"socket", "source-folder", "source-processor", "sql", "suppress",
		"sysproperty", "target", "task", "textElement", "textField",
		"tokenizer", "topPen", "trycatch", "util:constant", "validator", "var",
		"version", "waitfor", "web-app", "xs:attribute", "xs:choice",
		"xs:complexType", "xs:element", "xs:enumeration", "xs:extension",
		"xs:group", "xs:restriction", "xs:simpleType"
	};

}