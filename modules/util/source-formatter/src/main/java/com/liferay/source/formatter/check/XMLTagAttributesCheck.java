/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.processor.HTMLSourceProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		if (ArrayUtil.contains(_SINGLE_LINE_TAG_NAMES, tag.getFullName())) {
			tag.setMultiLine(false);
		}
		else if (ArrayUtil.contains(_MULTI_LINE_TAG_NAMES, tag.getFullName())) {
			tag.setMultiLine(true);
		}

		return tag;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (getSourceProcessor() instanceof HTMLSourceProcessor) {
			content = _formatHTMLIncorrectLineBreaks(content);
		}

		content = formatIncorrectLineBreak(fileName, content);

		content = _formatTagAttributes(absolutePath, content);

		content = formatMultiLinesTagAttributes(absolutePath, content, true);

		return content;
	}

	@Override
	protected Tag sortHTMLTagAttributes(Tag tag) {
		if (getSourceProcessor() instanceof HTMLSourceProcessor) {
			return super.sortHTMLTagAttributes(tag);
		}

		return tag;
	}

	private String _formatHTMLIncorrectLineBreaks(String content) {
		Matcher matcher = _htmlTagPattern.matcher(content);

		while (matcher.find()) {
			int x = matcher.start();

			while (true) {
				x = content.indexOf(">", x + 1);

				if (x == -1) {
					return content;
				}

				String tag = content.substring(matcher.start(), x + 1);

				if (ToolsUtil.isInsideQuotes(content, x) ||
					(getLevel(tag, "<", ">") != 0)) {

					continue;
				}

				if (tag.contains("\n")) {
					String replacement = StringUtil.replace(
						tag, new String[] {"\t", "\n>", "\n"},
						new String[] {"", ">", " "});

					return StringUtil.replaceFirst(
						content, tag, replacement, matcher.start());
				}

				break;
			}
		}

		return content.replaceAll("(</\\w+)\\s+(>)", "$1$2");
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

	private static final Pattern _htmlTagPattern = Pattern.compile("\t<\\w+");

}