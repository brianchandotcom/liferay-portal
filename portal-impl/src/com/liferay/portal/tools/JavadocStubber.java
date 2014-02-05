/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.xml.SAXReaderImpl;
import com.liferay.util.xml.DocUtil;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.AbstractBaseJavaEntity;
import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaPackage;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import jargs.gnu.CmdLineParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.Reader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * Modifies a file adding stubs for missing Javadoc, formatting all Javadoc
 * (including pre-existing), and adding the Override annotation where applicable.
 *
 * @author James Hinkey
 */
public class JavadocStubber {

	/**
	 * Invokes the JavadocStubber on a single Java file.
	 *
	 * @param args Should be -Dfile=SomeJavaFileName (omit file path and
	 * suffix)
	 */
	public static void main(String[] args) {
		try {
			new JavadocStubber(args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stubs a single Java file.
	 *
	 * @param args Should be -Dfile=SomeJavaFileName (omit file path and
	 * suffix)
	 * @throws Exception if an exception occurred
	 */
	public JavadocStubber(String[] args) throws Exception {
		CmdLineParser cmdLineParser = new CmdLineParser();

		CmdLineParser.Option fileOption = cmdLineParser.addStringOption(
			"file");

		cmdLineParser.parse(args);

		String file = (String)cmdLineParser.getOptionValue(fileOption);

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(_basedir);
		ds.setExcludes(
			new String[] {"**\\classes\\**", "**\\portal-client\\**"});

		List<String> includes = new ArrayList<String>();

		if (Validator.isNotNull(file) && !file.startsWith("$")) {
			String[] fileArray = StringUtil.split(file, '/');

			for (String curFile : fileArray) {
				includes.add(
					"**\\" + StringUtil.replace(curFile, ".", "\\") +
					"\\**\\*.java");

				includes.add("**\\" + curFile + ".java");
			}
		}
		else {
			System.out.println("Must specify a file");
			return;
		}

		ds.setIncludes(includes.toArray(new String[includes.size()]));

		ds.scan();

		String[] fileNames = ds.getIncludedFiles();

		if ((fileNames.length == 0) &&
			Validator.isNotNull(file) &&
			!file.startsWith("$")) {

			StringBuilder sb = new StringBuilder("File not found: ");

			sb.append(file);

			if (file.contains(".")) {
				sb.append(" Specify filename without package path or ");
				sb.append("file type suffix.");
			}

			System.out.println(sb.toString());
		}

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(fileName, "\\", "/");

			_process(fileName);
		}
	}

	static private String splitCamelCase(String s) {
		String text =
		 s.replaceAll(
			String.format("%s|%s|%s",
				"(?<=[A-Z])(?=[A-Z][a-z])",
				"(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"
			),
			" "
		);

		String[] splitText = StringUtil.split(text, StringPool.BLANK);
		if (splitText.length > 1) {
			StringBuffer sb = new StringBuffer();
			sb.append(splitText[0]);
			for (int ii = 1; ii < splitText.length; ii++) {
				sb.append(StringPool.BLANK);
				if (Pattern.matches("^.*[a-z]+.*$", splitText[ii])) {
					sb.append(splitText[ii].toLowerCase());
				} else {
					sb.append(splitText[ii]);
				}
			}
			return sb.toString();
		} else {
			return text;
		}
	}

	private void _addClassCommentElement(
			Element rootElement, JavaClass javaClass) {

		Element commentElement = rootElement.addElement("comment");

		String comment = _getCDATA(javaClass);

		if (comment.startsWith("Copyright")) {
			comment = StringPool.BLANK;
		}

		commentElement.addCDATA(comment);
	}

	private void _addDocletElements(
			Element parentElement, AbstractJavaEntity abstractJavaEntity,
			String name)
		throws Exception {

		DocletTag[] docletTags = abstractJavaEntity.getTagsByName(name);

		for (DocletTag docletTag : docletTags) {
			String value = docletTag.getValue();

			value = _trimMultilineText(value);

			value = StringUtil.replace(value, " </", "</");

			Element element = parentElement.addElement(name);

			element.addCDATA(value);
		}

		if ((docletTags.length == 0) && name.equals("author")) {
			Element element = parentElement.addElement(name);

			element.addCDATA(ServiceBuilder.AUTHOR);
		}
	}

	private void _addDocletElements(
			Element parentElement, AbstractJavaEntity abstractJavaEntity,
			String name, String defaultComment)
		throws Exception {

		if (name.equals("deprecated") && !_isDeprecated(abstractJavaEntity)) {
			return;
		}

		DocletTag[] docletTags = abstractJavaEntity.getTagsByName(name);

		for (DocletTag docletTag : docletTags) {
			String value = docletTag.getValue();

			if (Validator.isNull(value)) {
				value = defaultComment;
			}

			value = _trimMultilineText(value);

			value = StringUtil.replace(value, " </", "</");

			Element element = parentElement.addElement(name);

			element.addCDATA(value);
		}

		if (docletTags.length == 0) {
			Element element = parentElement.addElement(name);

			if (name.equals("author")) {
				element.addCDATA(ServiceBuilder.AUTHOR);
			}
			else {
				if (Validator.isNotNull(defaultComment)) {
					String value = _trimMultilineText(defaultComment);

					element.addCDATA(value);
				}
			}
		}
	}

	private void _addDocletMethodReturnElement(
			Element methodElement, JavaMethod javaMethod) {

		DocletTag[] returnDocletTags = javaMethod.getTagsByName("return");

		String value = null;
		if (returnDocletTags.length == 0) {
			value = _getReturnComment(javaMethod);
		} else {

			// initial population of value from tag

			value = returnDocletTags[0].getValue();

			if (Validator.isNull(value)) {
				value = _getReturnComment(javaMethod);
			}
		}

		value = _trimMultilineText(value);

		Element returnElement = methodElement.addElement("return");

		Element commentElement = returnElement.addElement("comment");
		commentElement.addCDATA(value);
	}

	private String _addDocletTags(
			Element parentElement, String[] names, String indent,
			boolean isPublic) {

		StringBuilder sb = new StringBuilder();

		int maxNameLength = 0;

		for (String name : names) {
			if (name.length() < maxNameLength) {
				continue;
			}

			List<Element> elements = parentElement.elements(name);

			for (Element element : elements) {
				Element commentElement = element.element("comment");

				String comment = null;

				if (commentElement != null) {
					comment = commentElement.getText();
				}
				else {
					comment = element.getText();
				}

				if (!name.equals("deprecated") && Validator.isNull(comment)) {
					continue;
				}

				if (!isPublic && Validator.isNull(comment)) {
					continue;
				}

				maxNameLength = name.length();

				break;
			}
		}

		// There should be one space after the name and an @ before it

		maxNameLength += 2;

		String nameIndent = _getSpacesIndent(maxNameLength);

		for (String name : names) {
			List<Element> elements = parentElement.elements(name);

			for (Element element : elements) {
				Element commentElement = element.element("comment");

				String comment = null;

				if (commentElement != null) {
					comment = commentElement.getText();
				}
				else {
					comment = element.getText();
				}

				if (!name.equals("deprecated") && Validator.isNull(comment)) {
					continue;
				}

				if (!isPublic && Validator.isNull(comment)) {
					continue;
				}

				if (commentElement != null) {
					String elementName = element.elementText("name");

					if (Validator.isNotNull(elementName)) {
						comment = elementName + " " + comment;
					}
				}

				if (Validator.isNull(comment)) {
					sb.append(indent);
					sb.append(StringPool.AT);
					sb.append(name);
					sb.append(StringPool.NEW_LINE);
				}
				else {
					comment = _wrapText(comment, indent + nameIndent);

					String firstLine = indent + "@" + name;

					comment = firstLine +
							comment.substring(firstLine.length());

					sb.append(comment);
				}
			}
		}

		return sb.toString();
	}

	private String _addDocletTagsToComment(
		String comment, Element fieldElement, String[] tagNames, String indent,
		boolean isPublic) {

		StringBuilder sb = new StringBuilder();

		sb.append(indent);
		sb.append("/**\n");

		String docletTags = _addDocletTags(
			fieldElement,
			tagNames,
			indent + " * ",
			isPublic);

		if (Validator.isNotNull(comment) && !comment.isEmpty()) {
			sb.append(_wrapText(comment, indent + " * "));
		}

		if (docletTags.length() > 0) {
			if (comment != null) {
				sb.append(indent);
				sb.append(" *\n");
			}

			sb.append(docletTags);
		} else {
			if (Validator.isNull(comment)) {
				return null;
			}
		}

		sb.append(indent);
		sb.append(" */\n");

		comment = sb.toString();
		return comment;
	}

	private void _addFieldElement(Element rootElement, JavaField javaField)
		throws Exception {

		Element fieldElement = rootElement.addElement("field");

		DocUtil.add(fieldElement, "name", javaField.getName());

		Element commentElement = fieldElement.addElement("comment");

		commentElement.addCDATA(_getCDATA(javaField));

		_addDocletElements(fieldElement, javaField, "see");
		_addDocletElements(fieldElement, javaField, "since");
		_addDocletElements(fieldElement, javaField, "deprecated");
	}

	private void _addMethodElement(Element rootElement, JavaMethod javaMethod)
		throws Exception {

		Element methodElement = rootElement.addElement("method");

		DocUtil.add(methodElement, "name", javaMethod.getName());

		Element commentElement = methodElement.addElement("comment");

		commentElement.addCDATA(_getCDATA(javaMethod));

		_addParamElements(methodElement, javaMethod);
		_addReturnElement(methodElement, javaMethod);
		_addThrowsElements(methodElement, javaMethod);

		_addDocletElements(methodElement, javaMethod, "see");
		_addDocletElements(methodElement, javaMethod, "since");
		_addDocletElements(methodElement, javaMethod, "deprecated");
	}

	private void _addNamedElement(
			Element parentElement, DocletTag[] docletTags, String elemName,
			String elemType, String tagName, String defaultValue) {

		String value = null;

		for (DocletTag docletTag : docletTags) {
			String curValue = docletTag.getValue();

			if (!curValue.startsWith(elemName)) {
				continue;
			}
			else {
				value = curValue;

				break;
			}
		}

		Element element = parentElement.addElement(tagName);

		DocUtil.add(element, "name", elemName);
		DocUtil.add(element, "type", elemType);

		if (Validator.isNotNull(defaultValue)) {
			if (Validator.isNull(value)) {
				value = defaultValue;
			} else {
				value = value.substring(elemName.length());
				if (value.trim().isEmpty()) {
					value = defaultValue;
				}
			}
		}

		value = _trimMultilineText(value);

		Element commentElement = element.addElement("comment");

		commentElement.addCDATA(_getCDATA(value));
	}

	private String _addParagraphToMethodDetailComment(String detailedComment) {

		StringBuffer details_sb = new StringBuffer();
		details_sb.append(detailedComment);
		details_sb.append("\n\n<p>\n");
		details_sb.append(METHOD_DESC_DETAIL_PARAGRAGH_RANGE);
		details_sb.append("\n</p>");

		return details_sb.toString();
	}

	private void _addParamElement(
			Element methodElement, JavaParameter javaParameter,
			DocletTag[] paramDocletTags) {

		String name = javaParameter.getName();
		String type = javaParameter.getType().getValue();
		String value = null;

		for (DocletTag paramDocletTag : paramDocletTags) {
			String curValue = paramDocletTag.getValue();

			if (!curValue.startsWith(name)) {
				continue;
			}
			else {
				value = curValue;

				break;
			}
		}

		Element paramElement = methodElement.addElement("param");

		DocUtil.add(paramElement, "name", name);
		DocUtil.add(paramElement, "type", type);

		if (value != null) {
			value = value.substring(name.length());
		}

		value = _trimMultilineText(value);

		Element commentElement = paramElement.addElement("comment");

		commentElement.addCDATA(value);
	}

	private void _addParamElements(
			Element methodElement, JavaMethod javaMethod) {

		JavaParameter[] javaParameters = javaMethod.getParameters();

		DocletTag[] paramDocletTags = javaMethod.getTagsByName("param");

		for (JavaParameter javaParameter : javaParameters) {
			if (_isPublicEntity(javaMethod)) {

				String paramName = javaParameter.getName();

				StringBuffer sb = new StringBuffer();
				if (javaParameter.getType().getJavaClass().isA("boolean")) {
					sb.append(PARAM_BOOLEAN);
				}
				else if (paramName.endsWith("Id") ||
						 paramName.endsWith("Pk") ||
						 paramName.endsWith("PK")) {

					sb.append(PARAM_ID);

					int index = -1;
					if (paramName.endsWith("Id")) {
						index = paramName.indexOf("Id");
					} else if (paramName.endsWith("Pk")) {
						index = paramName.indexOf("Pk");
					} else if (paramName.endsWith("PK")) {
						index = paramName.indexOf("PK");
					}

					if (index > 0) {
						String text =
							splitCamelCase(paramName.substring(0, index));
						String[] splitText =
							StringUtil.split(text, StringPool.SPACE);
						if (splitText.length >= 0) {
							_appendSplitText(splitText, 0, sb);
						}
						else {
							sb.append("something");
						}
					}
					else {
						sb.append("something");
					}
				}
				else if (paramName.endsWith("Ids") ||
						 paramName.endsWith("PKs")) {
					sb.append(PARAM_IDS);

					int index = -1;
					if (paramName.endsWith("Ids")) {
						index = paramName.indexOf("Ids");
					} else if (paramName.endsWith("PKs")) {
						index = paramName.indexOf("PKs");
					}

					if (index > 0) {
						String text =
							splitCamelCase(paramName.substring(0, index));
						String[] splitText =
							StringUtil.split(text, StringPool.SPACE);
						if (splitText.length >= 0) {
							_appendSplitText(splitText, 0, sb);
							sb.append('s');
						}
						else {
							sb.append("somethings");
						}
					}
					else {
						sb.append("somethings");
					}
				}
				else if (paramName.endsWith("start")) {
					sb.append(PARAM_START);
				}
				else if (paramName.endsWith("end")) {
					sb.append(PARAM_END);
				}
				else if (paramName.endsWith("Context")) {
					sb.append(PARAM_GENERAL_1);
					String text = splitCamelCase(paramName);
					String[] splitText =
						StringUtil.split(text, StringPool.SPACE);
					if (splitText.length >= 0) {
						_appendSplitText(splitText, 0, sb);
					} else {
						sb.append("something");
					}

					sb.append(" Must specify what? Can specify what? " +
						"(optionally <code>null</code>)");
				}
				else if (javaParameter.getType().getJavaClass().isA(
					"java.util.Comparator")) {

					sb.append(PARAM_COMPARATOR);
				}
				else if (javaParameter.getType().getJavaClass().isA(
					"com.liferay.portal.kernel.search.Sort")) {

					sb.append(PARAM_SORTER);
				}
				else {
					sb.append(PARAM_GENERAL_1);
					String text = splitCamelCase(paramName);
					String[] splitText =
						StringUtil.split(text, StringPool.SPACE);
					if (splitText.length >= 0) {
						_appendSplitText(splitText, 0, sb);
					} else {
						sb.append("something");
					}

					// append appropriate option
					Type paramType = javaParameter.getType();
					if (!paramType.isPrimitive()) {
						sb.append(PARAM_GENERAL_2);
						sb.append(" (optionally <code>null</code>)");
					}
					else if (paramType.getJavaClass().isA("int") ||
							 paramType.getJavaClass().isA("long") ||
							 paramType.getJavaClass().isA("double")) {
						sb.append(PARAM_GENERAL_2);
						sb.append(" (optionally <code>0</code>)");
					}
					else {
						sb.append(PARAM_GENERAL_2);
					}
				}

				_addNamedElement(methodElement, paramDocletTags,
						paramName,
						javaParameter.getType().getValue(), "param",
						sb.toString());
			}
			else {
				_addParamElement(
					methodElement, javaParameter, paramDocletTags);
			}
		}
	}

	private void _addReturnElement(
			Element methodElement, JavaMethod javaMethod)
		throws Exception {

		Type returns = javaMethod.getReturns();

		if ((returns == null) || returns.getValue().equals("void")) {
			return;
		}

		if (_isPublicEntity(javaMethod)) {
			_addDocletMethodReturnElement(methodElement, javaMethod);
		}
		else {
			_addDocletElements(methodElement, javaMethod, "return");
		}
	}

	private void _addThrowsElements(
			Element methodElement, JavaMethod javaMethod) throws Exception {

		Type[] exceptions = javaMethod.getExceptions();

		DocletTag[] throwsDocletTags = javaMethod.getTagsByName("throws");

		for (Type exception : exceptions) {
			if (_isPublicEntity(javaMethod)) {
				String comment = null;

				String className = exception.getJavaClass().
					getFullyQualifiedName();

				if (SYSTEM_CLASS_NAME.equals(className)) {
					comment = THROWS_DESC_SYSTEM_EXCEPTION;
				} else {
					comment = THROWS_DESC;
				}

				_addNamedElement(
					methodElement, throwsDocletTags,
					exception.getJavaClass().getName(),
					exception.getValue(), "throws", comment);
			}
			else {
				_addThrowsElement(methodElement, exception, throwsDocletTags);
			}
		}
	}

	private void _addThrowsElement(
			Element methodElement, Type exception,
			DocletTag[] throwsDocletTags) {

		String name = exception.getJavaClass().getName();
		String value = null;

		for (DocletTag paramDocletTag : throwsDocletTags) {
			String curValue = paramDocletTag.getValue();

			if (!curValue.startsWith(name)) {
				continue;
			}
			else {
				value = curValue;

				break;
			}
		}

		Element paramElement = methodElement.addElement("throws");

		DocUtil.add(paramElement, "name", name);

		if (value != null) {
			value = value.substring(name.length());
		}

		value = _trimMultilineText(value);

		Element commentElement = paramElement.addElement("comment");

		commentElement.addCDATA(value);
	}

	private void _appendSplitText(
		String[] splitText, int startIndex, StringBuffer sb) {

		for (int ii = startIndex; ii < splitText.length; ii++) {
			sb.append(StringPool.SPACE);
			if (Pattern.matches("^.*[a-z]+.*$", splitText[ii])) {
				sb.append(splitText[ii].toLowerCase());
			} else {
				sb.append(splitText[ii]);
			}
		}
	}

	private String _formatInlines(String text) {

		// Capitalize ID

		text = text.replaceAll("(?i)\\bid(s)?\\b", "ID$1");

		// Wrap special constants in code tags

		text = text.replaceAll(
			"(?i)(?<!<code>|\\w)(null|false|true)(?!\\w)",
			"<code>$1</code>");

		return text;
	}

	private List<JavaClass> _getAncestorJavaClasses(JavaClass javaClass) {
		List<JavaClass> ancestorJavaClasses = new ArrayList<JavaClass>();

		while ((javaClass = javaClass.getSuperJavaClass()) != null) {
			ancestorJavaClasses.add(javaClass);
		}

		return ancestorJavaClasses;
	}

	private String _getCDATA(AbstractJavaEntity abstractJavaEntity) {
		return _getCDATA(abstractJavaEntity.getComment());
	}

	private String _getCDATA(String cdata) {
		if (cdata == null) {
			return StringPool.BLANK;
		}

		cdata = cdata.replaceAll(
			"(?s)\\s*<(p|pre|[ou]l)>\\s*(.*?)\\s*</\\1>\\s*",
			"\n\n<$1>\n$2\n</$1>\n\n");
		cdata = cdata.replaceAll(
			"(?s)\\s*<li>\\s*(.*?)\\s*</li>\\s*", "\n<li>\n$1\n</li>\n");

		cdata = StringUtil.replace(cdata, "</li>\n\n<li>", "</li>\n<li>");
		cdata = cdata.replaceAll("\n\\s+\n", "\n\n");
		cdata = cdata.replaceAll(" +", " ");

		// Trim whitespace inside paragraph tags or in the first paragraph

		Pattern pattern = Pattern.compile(
			"(^.*?(?=\n\n|$)+|(?<=<p>\n).*?(?=\n</p>))", Pattern.DOTALL);

		Matcher matcher = pattern.matcher(cdata);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			String trimmed = _trimMultilineText(matcher.group());

			// Escape dollar signs so they are not treated as replacement
			// groups

			trimmed = trimmed.replaceAll("\\$", "\\\\\\$");

			matcher.appendReplacement(sb, trimmed);
		}

		matcher.appendTail(sb);

		cdata = sb.toString();

		return cdata.trim();
	}

	private String _getClassName(String fileName) {
		int pos = fileName.indexOf("src/");

		if (pos == -1) {
			pos = fileName.indexOf("test/");
		}

		if (pos == -1) {
			pos = fileName.indexOf("service/");
		}

		if (pos == -1) {
			throw new RuntimeException(fileName);
		}

		pos = fileName.indexOf("/", pos);

		String srcFile = fileName.substring(pos + 1, fileName.length());

		return StringUtil.replace(
			srcFile.substring(0, srcFile.length() - 5), "/", ".");
	}

	private String _getFieldKey(Element fieldElement) {
		return fieldElement.elementText("name");
	}

	private String _getFieldKey(JavaField javaField) {
		return javaField.getName();
	}

	private String _getIndent(
			String[] lines, AbstractBaseJavaEntity abstractBaseJavaEntity) {

		String line = lines[abstractBaseJavaEntity.getLineNumber() - 1];

		String indent = StringPool.BLANK;

		for (char c : line.toCharArray()) {
			if (Character.isWhitespace(c)) {
				indent += c;
			}
			else {
				break;
			}
		}

		return indent;
	}

	private int _getIndentLength(String indent) {
		int indentLength = 0;

		for (char c : indent.toCharArray()) {
			if (c == '\t') {
				indentLength = indentLength + 4;
			}
			else {
				indentLength++;
			}
		}

		return indentLength;
	}

	private JavaClass _getJavaClass(String fileName, Reader reader)
		throws Exception {

		String className = _getClassName(fileName);

		JavaDocBuilder builder = new JavaDocBuilder();

		if (reader == null) {
			File file = new File(fileName);

			if (!file.exists()) {
				return null;
			}

			builder.addSource(file);
		}
		else {
			builder.addSource(reader);
		}

		return builder.getClassByName(className);
	}

	private String _getJavaClassComment(
			Element rootElement, JavaClass javaClass) {

		String indent = StringPool.BLANK;

		String comment = rootElement.elementText("comment");

		if (Validator.isNull(comment) ||
			comment.trim().startsWith("Copyright"))
		{
			StringBuffer desc_sb = new StringBuffer(CLASS_DESC);
			desc_sb.append("\n");
			desc_sb.append("\n");
			desc_sb.append(CLASS_DESC_DETAIL);
			desc_sb.append("\n");
			desc_sb.append("\n");
			desc_sb.append(CLASS_USAGE_EXAMPLE);
			desc_sb.append("\n");

			comment = desc_sb.toString();
		}

		String[] tagNames = new String[] {
				"author", "version", "see", "since", "serial", "deprecated"
		};

		comment = _addDocletTagsToComment(
			comment, rootElement, tagNames,	indent,
			_isPublicEntity(javaClass));
		return comment;
	}

	private int _getJavaClassLineNumber(JavaClass javaClass) {
		int lineNumber = javaClass.getLineNumber();

		Annotation[] annotations = javaClass.getAnnotations();

		if (annotations.length == 0) {
			return lineNumber;
		}

		for (Annotation annotation : annotations) {
			int annotationLineNumber = annotation.getLineNumber();

			if (annotation.getPropertyMap().isEmpty()) {
				annotationLineNumber--;
			}

			if (annotationLineNumber < lineNumber) {
				lineNumber = annotationLineNumber;
			}
		}

		return lineNumber;
	}

	private Document _getJavadocDocument(JavaClass javaClass)
		throws Exception {

		Element rootElement = _saxReaderUtil.createElement("javadoc");

		Document document = _saxReaderUtil.createDocument(rootElement);

		DocUtil.add(rootElement, "name", javaClass.getName());
		DocUtil.add(rootElement, "type", javaClass.getFullyQualifiedName());

		_addClassCommentElement(rootElement, javaClass);

		if (this._isPublicEntity(javaClass)) {
			_addDocletElements(rootElement, javaClass, "author", CLASS_AUTHOR);
		} else {
			_addDocletElements(rootElement, javaClass, "author");
		}

		_addDocletElements(rootElement, javaClass, "version");
		_addDocletElements(rootElement, javaClass, "see");
		_addDocletElements(rootElement, javaClass, "since");
		_addDocletElements(rootElement, javaClass, "serial");
		_addDocletElements(rootElement, javaClass, "deprecated");

		JavaMethod[] javaMethods = javaClass.getMethods();

		for (JavaMethod javaMethod : javaMethods) {
			_addMethodElement(rootElement, javaMethod);
		}

		JavaField[] javaFields = javaClass.getFields();

		for (JavaField javaField : javaFields) {
			_addFieldElement(rootElement, javaField);
		}

		return document;
	}

	private String _getJavaFieldComment(
			String[] lines, Map<String, Element> fieldElementsMap,
			JavaField javaField) {

		String fieldKey = _getFieldKey(javaField);

		Element fieldElement = fieldElementsMap.get(fieldKey);

		if (fieldElement == null) {
			return null;
		}

		String comment = fieldElement.elementText("comment");

		if (_isPublicEntity(javaField) && Validator.isNull(comment)) {
			comment = FIELD_DESC;
		}

		String[] tagNames = new String[] {"see", "since", "deprecated"};

		String indent = _getIndent(lines, javaField);

		comment = _addDocletTagsToComment(
			comment, fieldElement, tagNames, indent,
			_isPublicEntity(javaField));

		return comment;
	}

	private String _getJavaMethodComment(
			String[] lines, Map<String, Element> methodElementsMap,
			JavaMethod javaMethod) {

		String methodKey = _getMethodKey(javaMethod);

		Element methodElement = methodElementsMap.get(methodKey);

		if (methodElement == null) {
			return null;
		}

		String indent = _getIndent(lines, javaMethod);

		String comment = methodElement.elementText("comment");

		String[] tagNames = new String[] {
				"param", "return", "throws", "see", "since",
				"deprecated"
		};

		if (_isPublicEntity(javaMethod) && Validator.isNull(comment)) {
			String initialComment = METHOD_DESC_GENERAL;
			String detailedComment = METHOD_DESC_DETAIL;

			if (_isGetterFetcherSearcher(javaMethod)) {
				boolean hasCollectionReturn = _returnsCollection(javaMethod);

				if (hasCollectionReturn) {
					boolean hasRange = _hasRange(javaMethod);
					boolean isOrdered = _isOrdered(javaMethod);

					if (hasRange && isOrdered) {
						initialComment = METHOD_DESC_RETURNS_ORDERED_RANGE;

						detailedComment = _addParagraphToMethodDetailComment(
							detailedComment);
					}
					else if (hasRange) {
						initialComment = METHOD_DESC_RETURNS_RANGE;

						detailedComment = _addParagraphToMethodDetailComment(
							detailedComment);
					}
					else if (isOrdered) {
						initialComment =
							METHOD_DESC_RETURNS_ORDERED_COLLECTION;
					}
					else {
						initialComment = METHOD_DESC_RETURNS_COLLECTION;
					}
				} else {
					if (javaMethod.getName().endsWith("Count")) {
						initialComment = METHOD_DESC_RETURNS_COUNT;
					}
					else {
						initialComment = METHOD_DESC_GETTER;
					}
				}
			} else if (_returnsBoolean(javaMethod)) {
					initialComment = METHOD_DESC_RETURNS_BOOLEAN;
			} else {
				initialComment = METHOD_DESC_GENERAL;
			}

			StringBuffer desc_sb = new StringBuffer(initialComment);
			desc_sb.append("\n");
			desc_sb.append("\n");
			desc_sb.append(detailedComment);
			desc_sb.append("\n");
			comment = desc_sb.toString();
		}

		comment = _addDocletTagsToComment(
			comment, methodElement, tagNames, indent,
			_isPublicEntity(javaMethod));

		return comment;
	}

	private String _getMethodKey(Element methodElement) {
		StringBuilder sb = new StringBuilder();

		sb.append(methodElement.elementText("name"));
		sb.append("(");

		List<Element> paramElements = methodElement.elements("param");

		for (Element paramElement : paramElements) {
			sb.append(paramElement.elementText("name"));
			sb.append("|");
			sb.append(paramElement.elementText("type"));
			sb.append(",");
		}

		sb.append(")");

		return sb.toString();
	}

	private String _getMethodKey(JavaMethod javaMethod) {
		StringBuilder sb = new StringBuilder();

		sb.append(javaMethod.getName());
		sb.append("(");

		JavaParameter[] javaParameters = javaMethod.getParameters();

		for (JavaParameter javaParameter : javaParameters) {
			sb.append(javaParameter.getName());
			sb.append("|");
			sb.append(javaParameter.getType().getValue());
			sb.append(",");
		}

		sb.append(")");

		return sb.toString();
	}

	private String _getReturnComment(JavaMethod javaMethod) {
		String comment = RETURN_GENERAL;

		if (_isGetterFetcherSearcher(javaMethod)) {
			boolean hasCollectionReturn = _returnsCollection(javaMethod);

			if (hasCollectionReturn) {
				boolean hasRange = _hasRange(javaMethod);
				boolean isOrdered = _isOrdered(javaMethod);

				if (hasRange && isOrdered) {
					comment = RETURN_ORDERED_RANGE;
				}
				else if (hasRange) {
					comment = RETURN_RANGE;
				}
				else if (isOrdered) {
					comment = RETURN_ORDERED_COLLECTION;
				}
				else {
					comment = RETURN_COLLECTION;
				}
			} else {
				if (javaMethod.getName().endsWith("Count")) {
					comment = RETURN_COUNT;
				}
				else {
					comment = METHOD_DESC_GETTER;
				}
			}
		} else if (_returnsBoolean(javaMethod)) {
			comment = RETURN_BOOLEAN;
		} else {
			comment = RETURN_GENERAL;
		}

		return comment;
	}

	private String _getSpacesIndent(int length) {
		String indent = StringPool.BLANK;

		for (int i = 0; i < length; i++) {
			indent += StringPool.SPACE;
		}

		return indent;
	}

	private boolean _hasAnnotation(
			AbstractBaseJavaEntity abstractBaseJavaEntity,
			String annotationName) {

		Annotation[] annotations = abstractBaseJavaEntity.getAnnotations();

		if (annotations == null) {
			return false;
		}

		for (int i = 0; i < annotations.length; i++) {
			Type type = annotations[i].getType();

			JavaClass javaClass = type.getJavaClass();

			if (annotationName.equals(javaClass.getName())) {
				return true;
			}
		}

		return false;
	}

	private boolean _hasRange(JavaMethod javaMethod) {
		JavaParameter[] javaParameters = javaMethod.getParameters();

		boolean hasStart = false;
		boolean hasEnd = false;
		for (JavaParameter javaParameter : javaParameters) {
			String type = javaParameter.getType().getValue();

			if (hasStart && hasEnd) {
				break;
			}

			if (type.equals("int")) {
				if (!hasStart && javaParameter.getName().equals("start")) {
					hasStart = true;
				} else if (!hasEnd && javaParameter.getName().equals("end")) {
					hasEnd = true;
				}
			}
		}

		return (hasStart && hasEnd);
	}

	private boolean _isDeprecated(AbstractJavaEntity javaMethod) {

		boolean isDeprecated = false;
		AbstractJavaEntity javaEntity = javaMethod;
		Annotation[] annotations = javaEntity.getAnnotations();
		if (annotations.length > 0) {

			for (int ii=0; ii<annotations.length; ii++) {

				if ("java.lang.Deprecated".equals(
					annotations[ii].getType().getFullyQualifiedName())) {

					isDeprecated = true;

					break;
				}
			}
		}
		return isDeprecated;
	}

	private boolean _isGenerated(String content) {
		if (content.contains("* @generated") || content.contains("$ANTLR")) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isGetterFetcherSearcher(JavaMethod javaMethod) {
		Type returnType = javaMethod.getReturns();

		if ((returnType == null) || returnType.isVoid()) {
			return false;
		}

		if (javaMethod.getName().startsWith("get") ||
			javaMethod.getName().startsWith("fetch") ||
			javaMethod.getName().startsWith("search")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean _isOrdered(JavaMethod javaMethod) {
		JavaParameter[] javaParameters = javaMethod.getParameters();

		for (JavaParameter javaParameter : javaParameters) {
			String paramClass =
				javaParameter.getType().getJavaClass().getName();

			if (paramClass.equals("OrderByComparator")) {
				return true;
			} else if (paramClass.equals("Sort")) {
				return true;
			}
		}

		return false;
	}

	private boolean  _isOverrideMethod(
			JavaClass javaClass, JavaMethod javaMethod,
			Collection<JavaClass> ancestorJavaClasses) {

		if (javaClass.isInterface() ||
			javaMethod.isConstructor() ||
			javaMethod.isPrivate() ||
			javaMethod.isStatic()) {

			return false;
		}

		String methodName = javaMethod.getName();

		JavaParameter[] javaParameters = javaMethod.getParameters();

		Type[] types = new Type[javaParameters.length];

		for (int i = 0; i < javaParameters.length; i++) {
			types[i] = javaParameters[i].getType();
		}

		// Check for matching method in each ancestor

		for (JavaClass ancestorJavaClass : ancestorJavaClasses) {
			JavaMethod ancestorJavaMethod =
				ancestorJavaClass.getMethodBySignature(methodName, types);

			if (ancestorJavaMethod == null) {
				continue;
			}

			boolean samePackage = false;

			JavaPackage ancestorJavaPackage = ancestorJavaClass.getPackage();

			if (ancestorJavaPackage != null) {
				samePackage = ancestorJavaPackage.equals(
					javaClass.getPackage());
			}

			// Check if the method is in scope

			if (samePackage) {
				return !ancestorJavaMethod.isPrivate();
			}
			else {
				if (ancestorJavaMethod.isProtected() ||
					ancestorJavaMethod.isPublic()) {

					return true;
				}
				else {
					return false;
				}
			}
		}

		return false;
	}

	private boolean _isPublicEntity(AbstractJavaEntity javaEntity) {

		boolean isPublic = false;

		String[] modifiers = javaEntity.getModifiers();

		if (modifiers != null) {
			for (int ii=0; ii<modifiers.length; ii++) {
				if (modifiers[ii].equals("public")) {
					isPublic = true;
					break;
				}
			}
		}
		return isPublic;
	}

	private void _process(String fileName) throws Exception {
		FileInputStream fis = new FileInputStream(
				new File(_basedir + fileName));

		byte[] bytes = new byte[fis.available()];

		fis.read(bytes);

		fis.close();

		String originalContent = new String(bytes);

		if (fileName.endsWith("JavadocFormatter.java") ||
			fileName.endsWith("JavadocStubber.java") ||
			fileName.endsWith("JavadocVerifier.java") ||
			fileName.endsWith("SourceFormatter.java") ||
			_isGenerated(originalContent)) {

			return;
		}

		JavaClass javaClass = _getJavaClass(
			fileName, new UnsyncStringReader(originalContent));

		String javadocLessContent = _removeJavadocFromJava(
			javaClass, originalContent);

		Document document = _getJavadocDocument(javaClass);

		_updateJavaFromDocument(
			fileName, originalContent, javadocLessContent, document);
	}

	private String _removeJavadocFromJava(
			JavaClass javaClass, String content) {

		Set<Integer> lineNumbers = new HashSet<Integer>();

		lineNumbers.add(_getJavaClassLineNumber(javaClass));

		JavaMethod[] javaMethods = javaClass.getMethods();

		for (JavaMethod javaMethod : javaMethods) {
			lineNumbers.add(javaMethod.getLineNumber());
		}

		JavaField[] javaFields = javaClass.getFields();

		for (JavaField javaField : javaFields) {
			lineNumbers.add(javaField.getLineNumber());
		}

		String[] lines = StringUtil.splitLines(content);

		for (int lineNumber : lineNumbers) {
			if (lineNumber == 0) {
				continue;
			}

			int pos = lineNumber - 2;

			String line = lines[pos];

			if (line == null) {
				continue;
			}

			line = line.trim();

			if (line.endsWith("*/")) {
				while (true) {
					lines[pos] = null;

					if (line.startsWith("/**")) {
						break;
					}

					line = lines[--pos].trim();
				}
			}
		}

		StringBuilder sb = new StringBuilder(content.length());

		for (String line : lines) {
			if (line != null) {
				sb.append(line);
				sb.append("\n");
			}
		}

		return sb.toString().trim();
	}

	private boolean _returnsBoolean(JavaMethod javaMethod) {
		Type returnType = javaMethod.getReturns();

		if ((returnType != null) &&
			returnType.getJavaClass().isA("boolean")) {

			return true;
		} else {
			return false;
		}
	}

	private boolean _returnsCollection(JavaMethod javaMethod) {

		Type returnType = javaMethod.getReturns();
		if ((returnType == null) ||
			returnType.isVoid() ||
			returnType.isPrimitive()) {

			return false;
		}

		JavaClass[] interfaces =
			returnType.getJavaClass().getImplementedInterfaces();

		if (interfaces == null) {
			return false;
		}

		for (JavaClass theInterface : interfaces) {
			if (theInterface.isA("java.util.Collection")) {
				return true;
			}
		}

		return false;
	}

	private String _trimMultilineText(String text) {
		String[] textArray = StringUtil.splitLines(text);

		for (int i = 0; i < textArray.length; i++) {
			textArray[i] = textArray[i].trim();
		}

		return StringUtil.merge(textArray, " ");
	}

	private void _updateJavaFromDocument(
			String fileName, String originalContent, String javadocLessContent,
			Document document)
		throws Exception {

		String[] lines = StringUtil.splitLines(javadocLessContent);

		JavaClass javaClass = _getJavaClass(
			fileName, new UnsyncStringReader(javadocLessContent));

		List<JavaClass> ancestorJavaClasses = _getAncestorJavaClasses(
			javaClass);

		Element rootElement = document.getRootElement();

		Map<Integer, String> commentsMap = new TreeMap<Integer, String>();

		commentsMap.put(
			_getJavaClassLineNumber(javaClass),
			_getJavaClassComment(rootElement, javaClass));

		Map<String, Element> methodElementsMap =
			new HashMap<String, Element>();

		List<Element> methodElements = rootElement.elements("method");

		for (Element methodElement : methodElements) {
			String methodKey = _getMethodKey(methodElement);

			methodElementsMap.put(methodKey, methodElement);
		}

		JavaMethod[] javaMethods = javaClass.getMethods();

		for (JavaMethod javaMethod : javaMethods) {
			if (commentsMap.containsKey(javaMethod.getLineNumber())) {
				continue;
			}

			String javaMethodComment = _getJavaMethodComment(
				lines, methodElementsMap, javaMethod);

			// Handle override tag insertion

			if (!_hasAnnotation(javaMethod, "Override")) {
				if (_isOverrideMethod(
					javaClass, javaMethod, ancestorJavaClasses)) {

					String overrideLine =
						_getIndent(lines, javaMethod) + "@Override\n";

					if (Validator.isNotNull(javaMethodComment)) {
						javaMethodComment =	javaMethodComment + overrideLine;
					}
					else {
						javaMethodComment = overrideLine;
					}
				}
			}

			commentsMap.put(javaMethod.getLineNumber(), javaMethodComment);
		}

		Map<String, Element> fieldElementsMap = new HashMap<String, Element>();

		List<Element> fieldElements = rootElement.elements("field");

		for (Element fieldElement : fieldElements) {
			String fieldKey = _getFieldKey(fieldElement);

			fieldElementsMap.put(fieldKey, fieldElement);
		}

		JavaField[] javaFields = javaClass.getFields();

		for (JavaField javaField : javaFields) {
			if (commentsMap.containsKey(javaField.getLineNumber())) {
				continue;
			}

			commentsMap.put(
				javaField.getLineNumber(),
				_getJavaFieldComment(lines, fieldElementsMap, javaField));
		}

		StringBuilder sb = new StringBuilder(javadocLessContent.length());

		for (int lineNumber = 1; lineNumber <= lines.length; lineNumber++) {
			String line = lines[lineNumber - 1];

			String comments = commentsMap.get(lineNumber);

			if (comments != null) {
				sb.append(comments);
			}

			sb.append(line);
			sb.append("\n");
		}

		String formattedContent = sb.toString().trim();

		if (!originalContent.equals(formattedContent)) {
			File file = new File(_basedir + fileName);

			_fileUtil.write(file, formattedContent.getBytes());

			System.out.println("Writing " + file);
		}
	}

	private String _wrapText(String text, String indent) {
		int indentLength = _getIndentLength(indent);

		// Do not wrap text inside <pre>

		if (text.contains("<pre>")) {
			Pattern pattern = Pattern.compile(
				"(?<=^|</pre>).+?(?=$|<pre>)", Pattern.DOTALL);

			Matcher matcher = pattern.matcher(text);

			StringBuffer sb = new StringBuffer();

			while (matcher.find()) {
				String wrapped = _formatInlines(matcher.group());

				wrapped = StringUtil.wrap(
					wrapped, 80 - indentLength, "\n");

				matcher.appendReplacement(sb, wrapped);
			}

			matcher.appendTail(sb);

			sb.append("\n");

			text = sb.toString();
		}
		else {
			text = _formatInlines(text);

			text = StringUtil.wrap(text, MAX_COLUMNS - indentLength, "\n");
		}

		text = text.replaceAll("(?m)^", indent);
		text = text.replaceAll("(?m) +$", StringPool.BLANK);

		return text;
	}

	private static final String CLASS_AUTHOR = "TODO your_name";
	private static final String CLASS_DESC_DETAIL =
		" TODO Detailed description to include class's abilities (optional).";
	private static final String CLASS_DESC =
		" TODO Initial description to introduce the class and its purpose.";
	private static final String CLASS_USAGE_EXAMPLE =
		" TODO Example usage expressed explicitly here and/or referred to " +
		"in another class (optional).";

	private static final String FIELD_DESC =
		" TODO Field description. What does field represent or indicate?";

	private static final int MAX_COLUMNS = 79;

	private static final String METHOD_DESC_DETAIL =
		"TODO Detailed description, reference links and/or examples " +
		"(optional).";
	private static final String METHOD_DESC_DETAIL_PARAGRAGH_RANGE =
		"Useful when paginating results. Returns a maximum of <code>end - " +
		"start</code> instances. <code>start</code> and <code>end</code> " +
		"are not primary keys, they are indexes in the result set. Thus, " +
		"<code>0</code> refers to the first result in the set. Setting both " +
		"<code>start</code> and <code>end</code> to " +
		"{@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will " +
		"return the full result set.";

	private static final String METHOD_DESC_GENERAL =
		" TODO Initial description of method's purpose.";
	private static final String METHOD_DESC_GETTER =
		" Returns the TODO something matching condition";
	private static final String METHOD_DESC_RETURNS_BOOLEAN =
		" Returns <code>true</code> if TODO some condition";
	private static final String METHOD_DESC_RETURNS_COLLECTION =
		" Returns all the TODO somethings matching condition";
	private static final String METHOD_DESC_RETURNS_COUNT =
		" Returns the number of TODO somethings matching condition";
	private static final String METHOD_DESC_RETURNS_ORDERED_COLLECTION =
		" Returns an ordered collection of all the TODO somethings matching " +
		"the condition";
	private static final String METHOD_DESC_RETURNS_ORDERED_RANGE =
		" Returns an ordered range of all the TODO somethings matching the " +
		"condition";
	private static final String METHOD_DESC_RETURNS_RANGE =
		" Returns a range of all the TODO somethings matching the condition";

	private static final String PARAM_GENERAL_1 =
		" the TODO something's ";
	private static final String PARAM_GENERAL_2 =
		", acceptable values?";
	private static final String PARAM_BOOLEAN =
		" whether to TODO do something";
	private static final String PARAM_COMPARATOR =
		"the comparator to order the TODO somethings (optionally " +
		"<code>null</code>)";
	private static final String PARAM_SORTER =
		"the field and direction by which to sort TODO (optionally " +
		"<code>null</code>)";
	private static final String PARAM_END =
		" the TODO upper bound of the range of results (not inclusive)";
	private static final String PARAM_ID =
		" the primary key of the TODO something's ";
	private static final String PARAM_IDS =
		" the primary keys of the TODO something's ";
	private static final String PARAM_START =
		" the TODO lower bound of the range of results";

	private static final String RETURN_GENERAL =
		" the TODO something, any special return values?";
	private static final String RETURN_BOOLEAN =
		" <code>true</code> if the TODO something meets some condition; " +
		"<code>false</code> otherwise";
	private static final String RETURN_COLLECTION =
		" the TODO somethings, any special return values?";
	private static final String RETURN_COUNT =
		" the number of TODO somethings matching the condition";
	private static final String RETURN_ORDERED_COLLECTION =
		" the ordered collection of TODO somethings matching the condition " +
		"ordered by <code>someParam</code> or some means";
	private static final String RETURN_ORDERED_RANGE =
		" the ordered range of TODO somethings matching the condition " +
		"ordered by <code>someParam</code> or some means";
	private static final String RETURN_RANGE =
		" the range of TODO somethings matching the condition";

	private static final String THROWS_DESC =
		" TODO list exceptional conditions that could have occurred";
	private static final String THROWS_DESC_SYSTEM_EXCEPTION =
		" if a system exception occurred";

	private static final String SYSTEM_CLASS_NAME =
		SystemException.class.getName();

	private static FileImpl _fileUtil = FileImpl.getInstance();
	private static SAXReaderImpl _saxReaderUtil = SAXReaderImpl.getInstance();

	private String _basedir = "./";

}