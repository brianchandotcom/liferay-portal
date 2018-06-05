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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class GetterMethodCallCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF, TokenTypes.INTERFACE_DEF
		};
	}

	public void setBaseDirName(String baseDirName) {
		_baseDirName = baseDirName;
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		if (parentAST != null) {
			return;
		}

		Map<DetailAST, String> getterMethodCallMap = _getGetterMethodCallMap(
			detailAST);

		if (getterMethodCallMap.isEmpty()) {
			return;
		}

		List<String> importNames = DetailASTUtil.getImportNames(detailAST);

		for (Map.Entry<DetailAST, String> entry :
				getterMethodCallMap.entrySet()) {

			DetailAST variableNameAST = entry.getKey();

			String variableName = variableNameAST.getText();

			String variableTypeName = DetailASTUtil.getVariableTypeName(
				variableNameAST, variableName, true);

			_checkGetterCall(
				entry.getValue(), variableName, variableTypeName, importNames,
				variableNameAST.getLineNo());
		}
	}

	private void _checkGetterCall(
		String getterObjectName, String variableName, String variableTypeName,
		List<String> importNames, int lineNo) {

		if (!Validator.isVariableName(variableTypeName)) {
			return;
		}

		String packageName = _getPackageName(variableTypeName, importNames);

		if (Validator.isNull(packageName)) {
			return;
		}

		Element serviceXMLElement = _getServiceXMLElement(packageName);

		if (serviceXMLElement == null) {
			return;
		}

		for (Element entityElement :
				(List<Element>)serviceXMLElement.elements("entity")) {

			if (!variableTypeName.equals(
					entityElement.attributeValue("name"))) {

				continue;
			}

			for (Element columnElement :
					(List<Element>)entityElement.elements("column")) {

				if (getterObjectName.equals(
						columnElement.attributeValue("name")) &&
					Objects.equals(
						columnElement.attributeValue("type"), "boolean")) {

					String s = TextFormatter.format(
						getterObjectName, TextFormatter.G);

					log(
						lineNo, _MSG_RENAME_METHOD_CALL,
						StringBundler.concat(variableName, ".is", s, "()"),
						StringBundler.concat(variableName, ".get" + s, "()"));
				}
			}
		}
	}

	private Map<DetailAST, String> _getGetterMethodCallMap(
		DetailAST detailAST) {

		Map<DetailAST, String> getterMethodCallMap = new HashMap<>();

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

			if (dotAST == null) {
				continue;
			}

			DetailAST elistAST = methodCallAST.findFirstToken(TokenTypes.ELIST);

			if (elistAST.getChildCount() > 0) {
				continue;
			}

			List<DetailAST> nameASTList = DetailASTUtil.getAllChildTokens(
				dotAST, false, TokenTypes.IDENT);

			if (nameASTList.size() != 2) {
				continue;
			}

			DetailAST methodNameAST = nameASTList.get(1);

			Matcher matcher = _getterMethodNamePattern.matcher(
				methodNameAST.getText());

			if (matcher.find()) {
				String getterObjectName = TextFormatter.format(
					matcher.group(1), TextFormatter.I);

				getterMethodCallMap.put(nameASTList.get(0), getterObjectName);
			}
		}

		return getterMethodCallMap;
	}

	private String _getPackageName(
		String variableTypeName, List<String> importNames) {

		for (String importName : importNames) {
			if (importName.startsWith("com.liferay.") &&
				importName.endsWith(".model." + variableTypeName)) {

				return StringUtil.replaceLast(
					importName, "." + variableTypeName, StringPool.BLANK);
			}
		}

		return StringPool.BLANK;
	}

	private Element _getServiceXMLElement(String packageName) {
		if (_serviceXMLElementsMap != null) {
			return _serviceXMLElementsMap.get(packageName);
		}

		_serviceXMLElementsMap = new HashMap<>();

		try {
			_populateServiceXMLElements("modules/apps", 6);
			_populateServiceXMLElements("portal-impl/src/com/liferay", 4);
		}
		catch (Exception e) {
			return null;
		}

		return _serviceXMLElementsMap.get(packageName);
	}

	private void _populateServiceXMLElements(String dirName, int maxDepth)
		throws Exception {

		File directory = SourceFormatterUtil.getFile(
			_baseDirName, dirName, ToolsUtil.PORTAL_MAX_DIR_LEVEL);

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

			Element serviceXMLElement = serviceXMLDocument.getRootElement();

			String packagePath = serviceXMLElement.attributeValue(
				"api-package-path");

			if (packagePath == null) {
				packagePath = serviceXMLElement.attributeValue("package-path");
			}

			if (packagePath != null) {
				_serviceXMLElementsMap.put(
					packagePath + ".model", serviceXMLElement);
			}
		}
	}

	private static final String _MSG_RENAME_METHOD_CALL = "method.call.rename";

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".settings", "bin", "build",
		"classes", "dependencies", "node_modules", "sql", "src", "test",
		"test-classes", "test-coverage", "test-results", "tmp"
	};

	private String _baseDirName;
	private final Pattern _getterMethodNamePattern = Pattern.compile(
		"^get([A-Z].*)");
	private Map<String, Element> _serviceXMLElementsMap;

}