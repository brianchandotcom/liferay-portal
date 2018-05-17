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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
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

	public void setPortalBranchName(String portalBranchName) {
		_portalBranchName = portalBranchName;
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

		Document serviceXMLDocument = _getServiceXMLDocument(
			_getPackageName(variableTypeName, importNames));

		if ((serviceXMLDocument == null) || !serviceXMLDocument.hasContent()) {
			return;
		}

		Element rootElement = serviceXMLDocument.getRootElement();

		for (Element entityElement :
				(List<Element>)rootElement.elements("entity")) {

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
			if (importName.endsWith(".kernel.model." + variableTypeName)) {
				return StringUtil.replaceLast(
					importName, "." + variableTypeName, StringPool.BLANK);
			}
		}

		return StringPool.BLANK;
	}

	private Document _getServiceXMLDocument(String packageName) {
		if (Validator.isNull(packageName)) {
			return null;
		}

		if (_serviceXMLDocumentsMap.containsKey(packageName)) {
			return _serviceXMLDocumentsMap.get(packageName);
		}

		String fileLocation = StringBundler.concat(
			"portal-impl/src/",
			packageName.replace(CharPool.PERIOD, CharPool.SLASH),
			"/service.xml");

		fileLocation = StringUtil.replace(
			fileLocation, "/kernel/model", StringPool.BLANK);

		try {
			Document serviceXMLDocument = SourceUtil.readXML(
				SourceFormatterUtil.getPortalContent(
					_baseDirName, _portalBranchName, fileLocation));

			_serviceXMLDocumentsMap.put(packageName, serviceXMLDocument);

			return serviceXMLDocument;
		}
		catch (Exception e) {
			_serviceXMLDocumentsMap.put(
				packageName, DocumentHelper.createDocument());

			return null;
		}
	}

	private static final String _MSG_RENAME_METHOD_CALL = "method.call.rename";

	private String _baseDirName;
	private final Pattern _getterMethodNamePattern = Pattern.compile(
		"^get([A-Z].*)");
	private String _portalBranchName;
	private final Map<String, Document> _serviceXMLDocumentsMap =
		new HashMap<>();

}