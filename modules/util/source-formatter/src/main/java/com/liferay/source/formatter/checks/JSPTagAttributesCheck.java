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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checks.util.TaglibUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.ParseException;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

			if (attributeName.equals("style")) {
				String tagName = tag.getName();

				if (!tagName.contains(StringPool.COLON) ||
					tagName.startsWith("aui:")) {

					tag.putAttribute(
						attributeName,
						_formatStyleAttributeValue(attributeValue));
				}
			}

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

			Set<String> primitiveTagAttributeDataType =
				_getPrimitiveTagAttributeDataTypes();

			if (primitiveTagAttributeDataType.contains(dataType)) {
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

	private String _formatStyleAttributeAttributeValue(
		String styleAttributeAttributeValue) {

		styleAttributeAttributeValue = StringUtil.trim(
			styleAttributeAttributeValue);

		if (styleAttributeAttributeValue.endsWith(_JAVA_SOURCE_REPLACEMENT) ||
			styleAttributeAttributeValue.endsWith(StringPool.SEMICOLON)) {

			return styleAttributeAttributeValue;
		}

		return styleAttributeAttributeValue + StringPool.SEMICOLON;
	}

	private String _formatStyleAttributeValue(String attributeValue) {
		List<String> javaSourceList = new ArrayList<>();

		Matcher matcher = _javaSourceInsideTagPattern.matcher(attributeValue);

		while (matcher.find()) {
			javaSourceList.add(matcher.group());
		}

		String newAttributeValue = matcher.replaceAll(_JAVA_SOURCE_REPLACEMENT);

		if (newAttributeValue.contains(StringPool.LESS_THAN)) {
			return attributeValue;
		}

		Map<String, String> styleAttributesMap = new LinkedHashMap<>();

		String styleAttributeAttributeName = null;
		int x = -1;

		matcher = _styleAttributePattern.matcher(newAttributeValue);

		while (matcher.find()) {
			if (styleAttributeAttributeName != null) {
				styleAttributesMap.put(
					styleAttributeAttributeName,
					_formatStyleAttributeAttributeValue(
						newAttributeValue.substring(x, matcher.start(2))));
			}

			x = matcher.end();

			styleAttributeAttributeName = matcher.group(2);
		}

		if (styleAttributeAttributeName != null) {
			styleAttributesMap.put(
				styleAttributeAttributeName,
				_formatStyleAttributeAttributeValue(
					newAttributeValue.substring(x)));
		}

		if (styleAttributesMap.isEmpty()) {
			return attributeValue;
		}

		StringBundler sb = new StringBundler(styleAttributesMap.size() * 4);

		for (Map.Entry<String, String> entry : styleAttributesMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append(": ");
			sb.append(entry.getValue());
			sb.append(StringPool.SPACE);
		}

		newAttributeValue = StringUtil.trim(sb.toString());

		for (String javaSource : javaSourceList) {
			newAttributeValue = StringUtil.replaceFirst(
				newAttributeValue, _JAVA_SOURCE_REPLACEMENT, javaSource);
		}

		return newAttributeValue;
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

	private synchronized Set<String> _getPrimitiveTagAttributeDataTypes() {
		if (_primitiveTagAttributeDataTypes == null) {
			_primitiveTagAttributeDataTypes = SetUtil.fromArray(
				new String[] {
					"java.lang.Boolean", "Boolean", "boolean",
					"java.lang.Double", "Double", "double", "java.lang.Integer",
					"Integer", "int", "java.lang.Long", "Long", "long"
				});
		}

		return _primitiveTagAttributeDataTypes;
	}

	private synchronized Map<String, String> _getSetMethodsMap(String tagName)
		throws Exception {

		if (_tagSetMethodsMap != null) {
			return _tagSetMethodsMap.get(tagName);
		}

		_tagSetMethodsMap = new HashMap<>();

		List<String> tldFileNames = TaglibUtil.getTLDFileNames(
			getBaseDirName(), _allFileNames, getSourceFormatterExcludes(),
			isPortalSource());

		if (tldFileNames.isEmpty()) {
			return _tagSetMethodsMap.get(tagName);
		}

		String utilTaglibSrcDirName = TaglibUtil.getUtilTaglibSrcDirName(
			getBaseDirName());

		outerLoop:
		for (String tldFileName : tldFileNames) {
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
								"/src/";
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
		throws IOException, ParseException {

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

		List<String> extendedTagFileNames = TaglibUtil.getExtendedTagFileNames(
			javaClass, tagFileName, utilTaglibSrcDirName);

		for (String extendedTagFileName : extendedTagFileNames) {
			setMethodsMap.putAll(
				_getSetMethodsMap(extendedTagFileName, utilTaglibSrcDirName));
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

	private boolean _isValidTagAttributeValue(String value, String dataType) {
		if (dataType.endsWith("Boolean") || dataType.equals("boolean")) {
			return Validator.isBoolean(value);
		}

		if (dataType.endsWith("Double") || dataType.equals("double")) {
			try {
				Double.parseDouble(value);
			}
			catch (NumberFormatException numberFormatException) {
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

	private static final String _JAVA_SOURCE_REPLACEMENT = "__JAVA_SOURCE__";

	private static final String[] _SINGLE_LINE_TAG_WHITELIST = {
		"liferay-frontend:defineObjects", "liferay-portlet:actionURL",
		"liferay-portlet:param", "liferay-portlet:renderURL",
		"liferay-portlet:renderURLParams", "liferay-portlet:resourceURL",
		"liferay-staging:defineObjects", "liferay-theme:defineObjects",
		"liferay-ui:error", "liferay-ui:icon-help", "liferay-ui:message",
		"liferay-ui:success", "liferay-util:dynamic-include",
		"liferay-util:include", "liferay-util:param"
	};

	private static final Pattern _javaSourceInsideTagPattern = Pattern.compile(
		"<%.*?%>");
	private static final Pattern _jspTaglibPattern = Pattern.compile(
		"\t*<[-\\w]+:[-\\w]+ .");
	private static final Pattern _styleAttributePattern = Pattern.compile(
		"(\\A|\\W)([a-z\\-]+)\\s*:");

	private List<String> _allFileNames;
	private final Map<String, Map<String, String>> _classSetMethodsMap =
		new HashMap<>();
	private Set<String> _primitiveTagAttributeDataTypes;
	private Map<String, Map<String, String>> _tagSetMethodsMap;

}