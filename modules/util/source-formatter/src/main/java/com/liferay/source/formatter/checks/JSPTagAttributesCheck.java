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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class JSPTagAttributesCheck extends TagAttributesCheck {

	@Override
	public void init() throws Exception {
		_primitiveTagAttributeDataTypes = _getPrimitiveTagAttributeDataTypes();
	}

	@Override
	public void setAllFileNames(List<String> allFileNames) {
		_allFileNames = allFileNames;
	}

	@Override
	protected Tag doFormatLineBreaks(Tag tag, String absolutePath) {
		String tagName = tag.getName();

		if (!tagName.contains(StringPool.COLON) || tagName.startsWith("aui:") ||
			tagName.startsWith("c:") || tagName.startsWith("portlet:") ||
			ArrayUtil.contains(_SINGLE_LINE_TAG_WHITELIST, tagName)) {

			tag.setMultiLine(false);
		}
		else {
			tag.setMultiLine(true);
		}

		return tag;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (isSubrepository() || isReadOnly(absolutePath)) {
			return content;
		}

		content = formatIncorrectLineBreak(fileName, content);

		content = _formatSingleLineTagAttributes(absolutePath, content);

		content = formatMultiLinesTagAttributes(absolutePath, content, false);

		return content;
	}

	@Override
	protected Tag formatTagAttributeType(Tag tag) throws Exception {
		Map<String, String> setMethodsMap = _getSetMethodsMap(tag.getName());

		Map<String, String> attributesMap = tag.getAttributesMap();

		for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
			String attributeValue = entry.getValue();

			String attributeName = entry.getKey();

			if (attributeValue.matches("<%= Boolean\\.(FALSE|TRUE) %>")) {
				attributeValue = StringUtil.replace(
					attributeValue,
					new String[] {"Boolean.FALSE", "Boolean.TRUE"},
					new String[] {"false", "true"});

				tag.putAttribute(attributeName, attributeValue);
			}

			if (attributeValue.matches("<%=.*%>")) {
				continue;
			}

			if ((setMethodsMap == null) ||
				(!isPortalSource() && !isSubrepository())) {

				continue;
			}

			String setAttributeMethodName =
				"set" + TextFormatter.format(attributeName, TextFormatter.G);

			String dataType = setMethodsMap.get(setAttributeMethodName);

			if (dataType == null) {
				continue;
			}

			if (_primitiveTagAttributeDataTypes.contains(dataType)) {
				if (!_isValidTagAttributeValue(attributeValue, dataType)) {
					continue;
				}

				tag.putAttribute(
					attributeName, "<%= " + attributeValue + " %>");
			}

			if (dataType.equals("java.lang.String") ||
				dataType.equals("String")) {

				attributeValue = StringUtil.replace(
					attributeValue, new String[] {"=\"false\"", "=\"true\""},
					new String[] {
						"=\"<%= Boolean.FALSE.toString() %>\"",
						"=\"<%= Boolean.TRUE.toString() %>\""
					});

				tag.putAttribute(attributeName, attributeValue);
			}
		}

		return tag;
	}

	@Override
	protected Tag sortHTMLTagAttributes(Tag tag) {
		String tagName = tag.getName();

		if (tagName.equals("liferay-ui:tabs")) {
			return tag;
		}

		Map<String, String> attributesMap = tag.getAttributesMap();

		for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
			String attributeValue = entry.getValue();

			if (!attributeValue.matches("([-a-z0-9]+ )+[-a-z0-9]+")) {
				continue;
			}

			List<String> htmlAttributes = ListUtil.fromArray(
				StringUtil.split(attributeValue, StringPool.SPACE));

			Collections.sort(htmlAttributes);

			tag.putAttribute(
				entry.getKey(),
				StringUtil.merge(htmlAttributes, StringPool.SPACE));
		}

		return tag;
	}

	private String _formatSingleLineTagAttributes(
			String absolutePath, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.matches("<\\w+ .*>.*")) {
					String htmlTag = _getTag(trimmedLine, 0);

					if (htmlTag != null) {
						String newHTMLTag = formatTagAttributes(
							absolutePath, htmlTag, false, true);

						line = StringUtil.replace(line, htmlTag, newHTMLTag);
					}
				}

				for (String jspTag : _getJSPTag(line)) {
					boolean forceSingleLine = false;

					if (!line.equals(jspTag)) {
						forceSingleLine = true;
					}

					String newJSPTag = formatTagAttributes(
						absolutePath, jspTag, false, forceSingleLine);

					line = StringUtil.replace(line, jspTag, newJSPTag);
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

	private String _getExtendedFileName(
		String content, String fileName, List<String> imports,
		String utilTaglibSrcDirName) {

		Matcher matcher = _extendedClassPattern.matcher(content);

		if (!matcher.find()) {
			return null;
		}

		String extendedClassName = matcher.group(1);

		if (!extendedClassName.contains(StringPool.PERIOD)) {
			for (String importName : imports) {
				if (importName.endsWith(
						StringPool.PERIOD + extendedClassName)) {

					extendedClassName = importName;

					break;
				}
			}
		}

		StringBundler sb = new StringBundler(3);

		if (extendedClassName.startsWith("com.liferay.taglib")) {
			sb.append(utilTaglibSrcDirName);
			sb.append(
				StringUtil.replace(
					extendedClassName, CharPool.PERIOD, CharPool.SLASH));
		}
		else if (!extendedClassName.contains(StringPool.PERIOD)) {
			int pos = fileName.lastIndexOf(CharPool.SLASH);

			sb.append(fileName.substring(0, pos + 1));

			sb.append(extendedClassName);
		}
		else {
			return null;
		}

		sb.append(".java");

		return sb.toString();
	}

	private List<String> _getJSPTag(String line) {
		List<String> jspTags = new ArrayList<>();

		Matcher matcher = _jspTaglibPattern.matcher(line);

		while (matcher.find()) {
			String tag = _getTag(line, matcher.start());

			if (tag == null) {
				return jspTags;
			}

			jspTags.add(tag);
		}

		return jspTags;
	}

	private Set<String> _getPrimitiveTagAttributeDataTypes() {
		return SetUtil.fromArray(
			new String[] {
				"java.lang.Boolean", "Boolean", "boolean", "java.lang.Double",
				"Double", "double", "java.lang.Integer", "Integer", "int",
				"java.lang.Long", "Long", "long"
			});
	}

	private synchronized Map<String, String> _getSetMethodsMap(String tagName)
		throws Exception {

		if (_tagSetMethodsMap != null) {
			return _tagSetMethodsMap.get(tagName);
		}

		_tagSetMethodsMap = new HashMap<>();

		List<String> tldFileNames = _getTLDFileNames();

		if (tldFileNames.isEmpty()) {
			return _tagSetMethodsMap.get(tagName);
		}

		String utilTaglibSrcDirName = _getUtilTaglibSrcDirName();

		outerLoop:
		for (String tldFileName : _getTLDFileNames()) {
			tldFileName = StringUtil.replace(
				tldFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			File tldFile = new File(tldFileName);

			String content = FileUtil.read(tldFile);

			Document document = SourceUtil.readXML(content);

			Element rootElement = document.getRootElement();

			Element shortNameElement = rootElement.element("short-name");

			String shortName = shortNameElement.getStringValue();

			List<Element> tagElements = rootElement.elements("tag");

			String srcDir = null;

			for (Element tagElement : tagElements) {
				Element tagClassElement = tagElement.element("tag-class");

				String tagClassName = tagClassElement.getStringValue();

				if (!tagClassName.startsWith("com.liferay")) {
					continue;
				}

				Element tagNameElement = tagElement.element("name");

				String curTagName = tagNameElement.getStringValue();

				if (_tagSetMethodsMap.containsKey(
						shortName + StringPool.COLON + curTagName)) {

					continue;
				}

				if (srcDir == null) {
					if (tldFileName.contains("/src/")) {
						srcDir = SourceUtil.getAbsolutePath(tldFile);

						srcDir =
							srcDir.substring(0, srcDir.lastIndexOf("/src/")) +
								"/src/main/java/";
					}
					else {
						srcDir = utilTaglibSrcDirName;

						if (Validator.isNull(srcDir)) {
							continue outerLoop;
						}
					}
				}

				StringBundler sb = new StringBundler(3);

				sb.append(srcDir);
				sb.append(
					StringUtil.replace(
						tagClassName, CharPool.PERIOD, CharPool.SLASH));
				sb.append(".java");

				Map<String, String> setMethodsMap = _getSetMethodsMap(
					sb.toString(), utilTaglibSrcDirName);

				if (setMethodsMap.isEmpty()) {
					continue;
				}

				_tagSetMethodsMap.put(
					shortName + StringPool.COLON + curTagName, setMethodsMap);
			}
		}

		return _tagSetMethodsMap.get(tagName);
	}

	private Map<String, String> _getSetMethodsMap(
			String tagFileName, String utilTaglibSrcDirName)
		throws Exception {

		if (_classSetMethodsMap.containsKey(tagFileName)) {
			return _classSetMethodsMap.get(tagFileName);
		}

		Map<String, String> setMethodsMap = new HashMap<>();

		File tagFile = new File(tagFileName);

		if (!tagFile.exists()) {
			return setMethodsMap;
		}

		String tagFileContent = FileUtil.read(tagFile);

		JavaClass javaClass = JavaClassParser.parseJavaClass(
			tagFileName, tagFileContent);

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (!javaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)javaTerm;

			String methodName = javaMethod.getName();

			if (!methodName.startsWith("set")) {
				continue;
			}

			JavaSignature javaSignature = javaMethod.getSignature();

			List<JavaParameter> javaParameters = javaSignature.getParameters();

			if (javaParameters.size() != 1) {
				continue;
			}

			JavaParameter javaParameter = javaParameters.get(0);

			setMethodsMap.put(methodName, javaParameter.getParameterType());
		}

		String extendedFileName = _getExtendedFileName(
			tagFileContent, tagFileName, javaClass.getImports(),
			utilTaglibSrcDirName);

		if (extendedFileName != null) {
			setMethodsMap.putAll(
				_getSetMethodsMap(extendedFileName, utilTaglibSrcDirName));
		}

		_classSetMethodsMap.put(tagFileName, setMethodsMap);

		return setMethodsMap;
	}

	private String _getTag(String s, int fromIndex) {
		int x = fromIndex;

		while (true) {
			x = s.indexOf(">", x + 1);

			if (x == -1) {
				return null;
			}

			String part = s.substring(fromIndex, x + 1);

			if (getLevel(part, "<", ">") == 0) {
				return part;
			}
		}
	}

	private List<String> _getTLDFileNames() throws Exception {
		String[] excludes =
			{"**/dependencies/**", "**/util-taglib/**", "**/portal-web/**"};

		List<String> tldFileNames = SourceFormatterUtil.filterFileNames(
			_allFileNames, excludes, new String[] {"**/*.tld"},
			getSourceFormatterExcludes(), true);

		if (!isPortalSource()) {
			return tldFileNames;
		}

		String tldDirLocation = "portal-web/docroot/WEB-INF/tld/";

		for (int i = 0; i < ToolsUtil.PORTAL_MAX_DIR_LEVEL - 1; i++) {
			File file = new File(getBaseDirName() + tldDirLocation);

			if (file.exists()) {
				tldFileNames.addAll(
					getFileNames(
						getBaseDirName() + tldDirLocation, new String[0],
						new String[] {"**/*.tld"}));

				break;
			}

			tldDirLocation = "../" + tldDirLocation;
		}

		return tldFileNames;
	}

	private String _getUtilTaglibSrcDirName() {
		File utilTaglibDir = getFile(
			"util-taglib/src", ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (utilTaglibDir == null) {
			return StringPool.BLANK;
		}

		return SourceUtil.getAbsolutePath(utilTaglibDir) + StringPool.SLASH;
	}

	private boolean _isValidTagAttributeValue(String value, String dataType) {
		if (dataType.endsWith("Boolean") || dataType.equals("boolean")) {
			return Validator.isBoolean(value);
		}

		if (dataType.endsWith("Double") || dataType.equals("double")) {
			try {
				Double.parseDouble(value);
			}
			catch (NumberFormatException nfe) {
				return false;
			}

			return true;
		}

		if (dataType.endsWith("Integer") || dataType.equals("int") ||
			dataType.endsWith("Long") || dataType.equals("long")) {

			return Validator.isNumber(value);
		}

		return false;
	}

	private static final String[] _SINGLE_LINE_TAG_WHITELIST = {
		"liferay-frontend:defineObjects", "liferay-portlet:actionURL",
		"liferay-portlet:param", "liferay-portlet:renderURL",
		"liferay-portlet:renderURLParams", "liferay-portlet:resourceURL",
		"liferay-staging:defineObjects", "liferay-theme:defineObjects",
		"liferay-ui:error", "liferay-ui:icon-help", "liferay-ui:message",
		"liferay-ui:success", "liferay-util:dynamic-include",
		"liferay-util:include", "liferay-util:param"
	};

	private List<String> _allFileNames;
	private final Map<String, Map<String, String>> _classSetMethodsMap =
		new HashMap<>();
	private final Pattern _extendedClassPattern = Pattern.compile(
		"\\sextends\\s+(\\w+)\\W");
	private final Pattern _jspTaglibPattern = Pattern.compile(
		"\t*<[-\\w]+:[-\\w]+ .");
	private Set<String> _primitiveTagAttributeDataTypes;
	private Map<String, Map<String, String>> _tagSetMethodsMap;

}