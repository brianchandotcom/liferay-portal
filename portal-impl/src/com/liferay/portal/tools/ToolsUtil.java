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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.xml.SAXReaderFactory;

import de.hunsicker.io.FileFormat;
import de.hunsicker.jalopy.Jalopy;
import de.hunsicker.jalopy.storage.Convention;
import de.hunsicker.jalopy.storage.ConventionKeys;
import de.hunsicker.jalopy.storage.Environment;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Alexander Chow
 * @author Harry Mark
 * @author Tariq Dweik
 * @author Glenn Powell
 * @author Raymond Augé
 * @author Prashant Dighe
 * @author Shuyang Zhou
 * @author James Lefeu
 * @author Miguel Pastor
 * @author Cody Hoag
 * @author James Hinkey
 * @author Hugo Huijser
 */
public class ToolsUtil {

	public static final String AUTHOR = "Brian Wing Shun Chan";

	public static final int PLUGINS_MAX_DIR_LEVEL = 3;

	public static final int PORTAL_MAX_DIR_LEVEL = 7;

	public static String getContent(String fileName) throws Exception {
		Document document = _getContentDocument(fileName);

		Element rootElement = document.getRootElement();

		Element authorElement = null;
		Element namespaceElement = null;
		Map<String, Element> entityElements = new TreeMap<>();
		Map<String, Element> exceptionElements = new TreeMap<>();

		List<Element> elements = rootElement.elements();

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("author")) {
				element.detach();

				if (authorElement != null) {
					throw new IllegalArgumentException(
						"There can only be one author element");
				}

				authorElement = element;
			}
			else if (elementName.equals("namespace")) {
				element.detach();

				if (namespaceElement != null) {
					throw new IllegalArgumentException(
						"There can only be one namespace element");
				}

				namespaceElement = element;
			}
			else if (elementName.equals("entity")) {
				element.detach();

				String name = element.attributeValue("name");

				entityElements.put(StringUtil.toLowerCase(name), element);
			}
			else if (elementName.equals("exceptions")) {
				element.detach();

				List<Element> exceptionElementsList = element.elements(
					"exception");

				for (Element exceptionElement : exceptionElementsList) {
					exceptionElement.detach();

					exceptionElements.put(
						exceptionElement.getText(), exceptionElement);
				}
			}
		}

		if (authorElement != null) {
			rootElement.add(authorElement);
		}

		if (namespaceElement == null) {
			throw new IllegalArgumentException(
				"The namespace element is required");
		}
		else {
			rootElement.add(namespaceElement);
		}

		_addElements(rootElement, entityElements);

		if (!exceptionElements.isEmpty()) {
			Element exceptionsElement = rootElement.addElement("exceptions");

			_addElements(exceptionsElement, exceptionElements);
		}

		return document.asXML();
	}

	public static String getPackagePath(File file) {
		String fileName = StringUtil.replace(
			file.toString(), CharPool.BACK_SLASH, CharPool.SLASH);

		return getPackagePath(fileName);
	}

	public static String getPackagePath(String fileName) {
		int x = Math.max(
			fileName.lastIndexOf("/com/"), fileName.lastIndexOf("/org/"));
		int y = fileName.lastIndexOf(CharPool.SLASH);

		String packagePath = fileName.substring(x + 1, y);

		return StringUtil.replace(packagePath, CharPool.SLASH, CharPool.PERIOD);
	}

	public static boolean isInsideQuotes(String s, int pos) {
		int start = s.lastIndexOf(CharPool.NEW_LINE, pos);

		if (start == -1) {
			start = 0;
		}

		int end = s.indexOf(CharPool.NEW_LINE, pos);

		if (end == -1) {
			end = s.length();
		}

		String line = s.substring(start, end);

		pos -= start;

		char delimeter = CharPool.SPACE;
		boolean insideQuotes = false;

		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);

			if (insideQuotes) {
				if (c == delimeter) {
					int precedingBackSlashCount = 0;

					for (int j = (i - 1); j >= 0; j--) {
						if (line.charAt(j) == CharPool.BACK_SLASH) {
							precedingBackSlashCount += 1;
						}
						else {
							break;
						}
					}

					if ((precedingBackSlashCount == 0) ||
						((precedingBackSlashCount % 2) == 0)) {

						insideQuotes = false;
					}
				}
			}
			else if ((c == CharPool.APOSTROPHE) || (c == CharPool.QUOTE)) {
				delimeter = c;
				insideQuotes = true;
			}

			if (pos == i) {
				return insideQuotes;
			}
		}

		return false;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #stripFullyQualifiedClassNames(String, String)}
	 */
	@Deprecated
	public static String stripFullyQualifiedClassNames(String content)
		throws IOException {

		return stripFullyQualifiedClassNames(content, null);
	}

	public static String stripFullyQualifiedClassNames(
			String content, String packagePath)
		throws IOException {

		String imports = JavaImportsFormatter.getImports(content);

		return stripFullyQualifiedClassNames(content, imports, packagePath);
	}

	public static String stripFullyQualifiedClassNames(
			String content, String imports, String packagePath)
		throws IOException {

		if (Validator.isNull(content) || Validator.isNull(imports)) {
			return content;
		}

		Pattern pattern1 = Pattern.compile(
			"\n(.*)" + StringUtil.replace(packagePath, CharPool.PERIOD, "\\.") +
				"\\.([A-Z]\\w+)\\W");

		outerLoop:
		while (true) {
			Matcher matcher1 = pattern1.matcher(content);

			while (matcher1.find()) {
				String lineStart = StringUtil.trimLeading(matcher1.group(1));

				if (lineStart.startsWith("import ") ||
					lineStart.contains("//") ||
					isInsideQuotes(content, matcher1.start(2))) {

					continue;
				}

				String className = matcher1.group(2);

				Pattern pattern2 = Pattern.compile(
					"import [\\w.]+\\." + className + ";");

				Matcher matcher2 = pattern2.matcher(imports);

				if (matcher2.find()) {
					continue;
				}

				content = StringUtil.replaceFirst(
					content, packagePath + ".", StringPool.BLANK,
					matcher1.start());

				continue outerLoop;
			}

			break;
		}

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(imports));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			int x = line.indexOf("import ");

			if (x == -1) {
				continue;
			}

			String importPackageAndClassName = line.substring(
				x + 7, line.lastIndexOf(StringPool.SEMICOLON));

			if (importPackageAndClassName.contains(StringPool.STAR)) {
				continue;
			}

			Pattern pattern3 = Pattern.compile(
				"\n(.*)(" +
					StringUtil.replace(importPackageAndClassName, ".", "\\.") +
						")\\W");

			outerLoop:
			while (true) {
				Matcher matcher3 = pattern3.matcher(content);

				while (matcher3.find()) {
					String lineStart = StringUtil.trimLeading(
						matcher3.group(1));

					if (lineStart.startsWith("import ") ||
						lineStart.contains("//") ||
						isInsideQuotes(content, matcher3.start(2))) {

						continue;
					}

					String importClassName =
						importPackageAndClassName.substring(
							importPackageAndClassName.lastIndexOf(
								StringPool.PERIOD) + 1);

					content = StringUtil.replaceFirst(
						content, importPackageAndClassName, importClassName,
						matcher3.start());

					continue outerLoop;
				}

				break;
			}
		}

		return content;
	}

	public static void writeFile(
			File file, String content, Set<String> modifiedFileNames)
		throws IOException {

		writeFile(file, content, AUTHOR, modifiedFileNames);
	}

	public static void writeFile(
			File file, String content, String author,
			Map<String, Object> jalopySettings, Set<String> modifiedFileNames)
		throws IOException {

		String packagePath = getPackagePath(file);

		String className = file.getName();

		className = className.substring(0, className.length() - 5);

		ImportsFormatter importsFormatter = new JavaImportsFormatter();

		content = importsFormatter.format(content, packagePath, className);

		File tempFile = new File(_TMP_DIR, "ServiceBuilder.temp");

		_write(tempFile, content);

		// Beautify

		StringBuffer sb = new StringBuffer();

		Jalopy jalopy = new Jalopy();

		jalopy.setFileFormat(FileFormat.UNIX);
		jalopy.setInput(tempFile);
		jalopy.setOutput(sb);

		File jalopyXmlFile = new File("tools/jalopy.xml");

		if (!jalopyXmlFile.exists()) {
			jalopyXmlFile = new File("../tools/jalopy.xml");
		}

		if (!jalopyXmlFile.exists()) {
			jalopyXmlFile = new File("misc/jalopy.xml");
		}

		if (!jalopyXmlFile.exists()) {
			jalopyXmlFile = new File("../misc/jalopy.xml");
		}

		if (!jalopyXmlFile.exists()) {
			jalopyXmlFile = new File("../../misc/jalopy.xml");
		}

		if (jalopyXmlFile.exists()) {
			Jalopy.setConvention(jalopyXmlFile);
		}
		else {
			URL url = _readJalopyXmlFromClassLoader();

			Jalopy.setConvention(url);
		}

		if (jalopySettings == null) {
			jalopySettings = new HashMap<>();
		}

		Environment env = Environment.getInstance();

		// Author

		author = GetterUtil.getString(
			(String)jalopySettings.get("author"), author);

		env.set("author", author);

		// Fail on format error

		boolean failOnFormatError = MapUtil.getBoolean(
			jalopySettings, "failOnFormatError");

		// File name

		env.set("fileName", file.getName());

		Convention convention = Convention.getInstance();

		String classMask = "/**\n * @author $author$\n*/";

		convention.put(
			ConventionKeys.COMMENT_JAVADOC_TEMPLATE_CLASS,
			env.interpolate(classMask));

		convention.put(
			ConventionKeys.COMMENT_JAVADOC_TEMPLATE_INTERFACE,
			env.interpolate(classMask));

		boolean formatSuccess = jalopy.format();

		String newContent = sb.toString();

		// Remove double blank lines after the package or last import

		newContent = newContent.replaceFirst(
			"(?m)^[ \t]*((?:package|import) .*;)\\s*^[ \t]*/\\*\\*",
			"$1\n\n/**");

		/*
		// Remove blank lines after try {

		newContent = StringUtil.replace(newContent, "try {\n\n", "try {\n");

		// Remove blank lines after ) {

		newContent = StringUtil.replace(newContent, ") {\n\n", ") {\n");

		// Remove blank lines empty braces { }

		newContent = StringUtil.replace(newContent, "\n\n\t}", "\n\t}");

		// Add space to last }

		newContent = newContent.substring(0, newContent.length() - 2) + "\n\n}";
		*/

		writeFileRaw(file, newContent, modifiedFileNames);

		tempFile.deleteOnExit();

		if (failOnFormatError && !formatSuccess) {
			throw new IOException("Unable to beautify " + file);
		}
	}

	public static void writeFile(
			File file, String content, String author,
			Set<String> modifiedFileNames)
		throws IOException {

		writeFile(file, content, author, null, modifiedFileNames);
	}

	public static void writeFileRaw(
			File file, String content, Set<String> modifiedFileNames)
		throws IOException {

		// Write file if and only if the file has changed

		if (!file.exists() || !content.equals(_read(file))) {
			_write(file, content);

			if (modifiedFileNames != null) {
				modifiedFileNames.add(file.getAbsolutePath());
			}

			System.out.println("Writing " + file);
		}
	}

	private static void _addElements(
		Element element, Map<String, Element> elements) {

		for (Map.Entry<String, Element> entry : elements.entrySet()) {
			Element childElement = entry.getValue();

			element.add(childElement);
		}
	}

	private static Document _getContentDocument(String fileName)
		throws Exception {

		SAXReader saxReader = _getSAXReader();

		Document document = saxReader.read(new File(fileName));

		Element rootElement = document.getRootElement();

		List<Element> elements = rootElement.elements();

		for (Element element : elements) {
			String elementName = element.getName();

			if (!elementName.equals("service-builder-import")) {
				continue;
			}

			element.detach();

			String dirName = fileName.substring(
				0, fileName.lastIndexOf(StringPool.SLASH) + 1);
			String serviceBuilderImportFileName = element.attributeValue(
				"file");

			Document serviceBuilderImportDocument = _getContentDocument(
				dirName + serviceBuilderImportFileName);

			Element serviceBuilderImportRootElement =
				serviceBuilderImportDocument.getRootElement();

			List<Element> serviceBuilderImportElements =
				serviceBuilderImportRootElement.elements();

			for (Element serviceBuilderImportElement :
					serviceBuilderImportElements) {

				serviceBuilderImportElement.detach();

				rootElement.add(serviceBuilderImportElement);
			}
		}

		return document;
	}

	private static SAXReader _getSAXReader() {
		return SAXReaderFactory.getSAXReader(null, false, false);
	}

	private static String _read(File file) throws IOException {
		String s = new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

		return StringUtil.replace(
			s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
	}

	private static URL _readJalopyXmlFromClassLoader() {
		ClassLoader classLoader = ToolsUtil.class.getClassLoader();

		URL url = classLoader.getResource("jalopy.xml");

		if (url == null) {
			throw new RuntimeException(
				"Unable to load jalopy.xml from the class loader");
		}

		return url;
	}

	private static void _write(File file, String s) throws IOException {
		Path path = file.toPath();

		Files.createDirectories(path.getParent());

		Files.write(path, s.getBytes(StandardCharsets.UTF_8));
	}

	private static final String _TMP_DIR = System.getProperty("java.io.tmpdir");

}