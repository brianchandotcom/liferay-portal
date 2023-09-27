/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class JavaUpgradeProcessFactoryCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		return _sortMethodCalls(javaTerm.getContent());
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private int _getColumnIndex(Element entityElement, String columnName) {
		int i = 0;

		for (Element columnElement :
				(List<Element>)entityElement.elements("column")) {

			if (StringUtil.equalsIgnoreCase(
					columnName, columnElement.attributeValue("name"))) {

				return i;
			}

			i++;
		}

		return -1;
	}

	private synchronized List<Element> _getServiceXMLElements() {
		if (_serviceXMLElements != null) {
			return _serviceXMLElements;
		}

		_serviceXMLElements = new ArrayList<>();

		try {
			_populateServiceXMLElements("modules/apps", 6);
			_populateServiceXMLElements("modules/dxp/apps", 6);
			_populateServiceXMLElements("portal-impl/src/com/liferay", 4);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}

			return null;
		}

		return _serviceXMLElements;
	}

	private int _getTableIndex(Element serviceXMLElement, String tableName) {
		int i = 0;

		for (Element entityElement :
				(List<Element>)serviceXMLElement.elements("entity")) {

			if (tableName.equals(entityElement.attributeValue("name"))) {
				return i;
			}

			i++;
		}

		return -1;
	}

	private void _populateServiceXMLElements(String dirName, int maxDepth)
		throws IOException {

		File directory = getFile(dirName, getMaxDirLevel());

		if (directory == null) {
			return;
		}

		final List<File> serviceXMLFiles = new ArrayList<>();

		Files.walkFileTree(
			directory.toPath(), EnumSet.noneOf(FileVisitOption.class), maxDepth,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					String dirName = String.valueOf(dirPath.getFileName());

					if (ArrayUtil.contains(_SKIP_DIR_NAMES, dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path path = dirPath.resolve("service.xml");

					if (Files.exists(path)) {
						serviceXMLFiles.add(path.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		for (File serviceXMLFile : serviceXMLFiles) {
			Document serviceXMLDocument = SourceUtil.readXML(
				FileUtil.read(serviceXMLFile));

			if (serviceXMLDocument == null) {
				continue;
			}

			Element serviceXMLElement = serviceXMLDocument.getRootElement();

			_serviceXMLElements.add(serviceXMLElement);
		}
	}

	private String _sortMethodCalls(String content) {
		Matcher matcher1 = _methodCallsPattern.matcher(content);

		while (matcher1.find()) {
			String methodCallsCodeBlock = matcher1.group();

			String previousColumnName = null;
			String previousMatch = null;
			String previousMethodName = null;
			String previousTableName = null;

			Matcher matcher2 = _methodCallPattern.matcher(methodCallsCodeBlock);

			while (matcher2.find()) {
				String match = matcher2.group();

				String methodName = matcher2.group(1);

				if (!methodName.equals("alterColumnName") &&
					!methodName.equals("alterColumnType")) {

					continue;
				}

				List<String> parameterList = JavaSourceUtil.getParameterList(
					methodCallsCodeBlock.substring(matcher2.start()));

				String columnName = StringUtil.unquote(parameterList.get(1));
				String tableName = StringUtil.unquote(parameterList.get(0));

				if (!methodName.equals(previousMethodName)) {
					previousColumnName = columnName;
					previousMatch = match;
					previousMethodName = methodName;
					previousTableName = tableName;

					continue;
				}

				List<Element> serviceXMLElements = _getServiceXMLElements();

				if (tableName.equals(previousTableName)) {
					for (Element serviceXMLElement : serviceXMLElements) {
						int index1 = -1;
						int index2 = -1;

						for (Element entityElement :
								(List<Element>)serviceXMLElement.elements(
									"entity")) {

							if (!tableName.equals(
									entityElement.attributeValue("name"))) {

								continue;
							}

							index1 = _getColumnIndex(
								entityElement, previousColumnName);
							index2 = _getColumnIndex(entityElement, columnName);
						}

						if ((index2 != -1) && (index1 > index2)) {
							int x = matcher2.start();

							int y = content.lastIndexOf(previousMatch, x);

							content = StringUtil.replaceFirst(
								content, match, previousMatch,
								matcher1.start() + x);

							return StringUtil.replaceFirst(
								content, previousMatch, match,
								matcher1.start() + y);
						}
					}
				}
				else {
					for (Element serviceXMLElement : serviceXMLElements) {
						int index1 = _getTableIndex(
							serviceXMLElement, previousTableName);
						int index2 = _getTableIndex(
							serviceXMLElement, tableName);

						if ((index2 != -1) && (index1 > index2)) {
							int x = matcher2.start();

							int y = content.lastIndexOf(previousMatch, x);

							content = StringUtil.replaceFirst(
								content, match, previousMatch,
								matcher1.start() + x);

							return StringUtil.replaceFirst(
								content, previousMatch, match,
								matcher1.start() + y);
						}
					}
				}

				previousColumnName = columnName;
				previousMatch = match;
				previousMethodName = methodName;
				previousTableName = tableName;
			}
		}

		return content;
	}

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".settings", "bin", "build",
		"classes", "dependencies", "node_modules", "node_modules_cache", "sql",
		"src", "test", "test-classes", "test-coverage", "test-results", "tmp"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		JavaUpgradeProcessFactoryCheck.class);

	private static final Pattern _methodCallPattern = Pattern.compile(
		"UpgradeProcessFactory\\.(\\w+)\\([^;]+?\\)(?=,|\\))");
	private static final Pattern _methodCallsPattern = Pattern.compile(
		"([ \\t]*UpgradeProcessFactory\\.\\w+\\([^;]+?\\)(,|\\))\\s*){2,}");

	private List<Element> _serviceXMLElements;

}