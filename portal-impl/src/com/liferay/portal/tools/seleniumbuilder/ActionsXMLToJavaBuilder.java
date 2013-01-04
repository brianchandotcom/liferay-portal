/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.InitUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionsXMLToJavaBuilder extends SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new ActionsXMLToJavaBuilder(args);
	}

	public ActionsXMLToJavaBuilder(String[] args) throws Exception {
		super(args);

		_validCommands = new TreeSet<String>();

		_validCommands.add("function");

		for (String fileName : fileNameSetPaths) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			generateActions(fileName);
		}
	}

	protected void generateActions(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String objectFilePath = fileName.substring(0, x) + "/rc";
		String objectName = fileName.substring(x + 1, y);
		String objectXMLFilePath = fileName.substring(0, x);

		String actionsSimpleClassName = objectName + "Actions";
		String actionsXMLFileName =
			objectXMLFilePath + "/" + objectName + ".actions";
		String objectPackagePath = StringUtil.replace(
			objectFilePath, StringPool.SLASH, StringPool.PERIOD);
		String pathsSimpleClassName = objectName + "Paths";

		String actionsFileName =
			objectFilePath + "/" + actionsSimpleClassName + ".java";

		Element rootElement = null;

		if (FileUtil.exists(basedir + "/" + actionsXMLFileName)) {
			getObject(actionsXMLFileName);

			rootElement = getRootElement(actionsXMLFileName);
		}

		filePath = actionsFileName;

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(objectPackagePath);
		sb.append(";\n\n");

		sb.append("import com.liferay.portalweb.blocks.base.actions.rc.");
		sb.append("ActionsUtil;\n");

		sb.append("import com.liferay.portalweb.blocks.base.actions.rc.");
		sb.append("BaseLiferayActions;\n");

		sb.append("import com.liferay.portalweb.portal.util.");
		sb.append("liferayselenium.LiferaySelenium;\n");

		sb.append("import com.liferay.portalweb.portal.util.");
		sb.append("liferayselenium.LiferaySeleniumHelper;\n");

		if (!(rootElement == null)) {
			sb.append(processBlocksImports(rootElement));
		}

		sb.append("public class ");
		sb.append(actionsSimpleClassName);

		if (objectName.equals("BaseLiferay")) {
			sb.append(" extends BaseActions {\n\n");
		}
		else {
			sb.append(" extends BaseLiferayActions {\n\n");
		}

		sb.append("public ");
		sb.append(actionsSimpleClassName);
		sb.append("(LiferaySelenium liferaySelenium) {\n");

		sb.append("super(liferaySelenium);\n");

		sb.append("paths = ");
		sb.append(pathsSimpleClassName);
		sb.append(".getPaths();\n");

		sb.append("}\n");

		if (!(rootElement == null)) {
			sb.append(_processActionDefs(rootElement));
		}

		sb.append("}");

		writeFile(actionsFileName, sb.toString(), true);
	}

	private String _combineConditionals(
		List<String> conditionalList, String delimiter) {

		boolean isFirstConditional = true;

		StringBundler sb = new StringBundler();

		for (String conditional : conditionalList) {
			if (!isFirstConditional) {
				sb.append(" " + delimiter + " ");
			}

			sb.append(conditional);

			isFirstConditional = false;
		}

		return sb.toString();
	}

	private boolean _isDefaultConditional(Element conditional) {
		String defaultConditional = conditional.attributeValue("default");

		return (!(defaultConditional == null) &&
				defaultConditional.equals("true"));
	}

	private String _processActionDef(Element actionDef) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> conditionals = actionDef.elements("conditional");

		boolean isFirstConditional = true;
		boolean hasDefaultConditional = false;

		for (Element conditional : conditionals) {
			boolean isDefaultConditional = _isDefaultConditional(conditional);

			if (isFirstConditional && isDefaultConditional) {
				hasDefaultConditional = true;
				isFirstConditional = false;

				sb.append(processBlockCommands(conditional, _validCommands));

				break;
			}
			else if (isDefaultConditional) {
				hasDefaultConditional = true;

				sb.append("else {");
				sb.append(processBlockCommands(conditional, _validCommands));
				sb.append("}");
			}
			else if (isFirstConditional) {
				isFirstConditional = false;

				sb.append("if (");
				sb.append(_processConditional(conditional));
				sb.append(") {");
				sb.append(processBlockCommands(conditional, _validCommands));
				sb.append("}");
			}
			else {
				sb.append("else if (");
				sb.append(_processConditional(conditional));
				sb.append(") {");
				sb.append(processBlockCommands(conditional, _validCommands));
				sb.append("}");
			}
		}

		if (!hasDefaultConditional) {
			sb.append("else {");
			sb.append(_processCommandSuper(actionDef));
			sb.append("}");
		}

		return sb.toString();
	}

	private String _processActionDefs(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> actionDefs = rootElement.elements("actiondef");

		for (Element actionDef : actionDefs) {
			String actionDefCommand = actionDef.attributeValue("command");

			String functionObjectName = StringUtil.upperCaseFirstLetter(
				actionDefCommand);

			String functionReturnType = functionMethodReturnTypeMap.get(
				functionObjectName);

			sb.append("public ");
			sb.append(functionReturnType);
			sb.append(" ");
			sb.append(actionDefCommand);

			List<String> paramSuffixList = functionMethodParamListMap.get(
				functionObjectName);

			String paramList = "";

			for (String paramSuffix : paramSuffixList) {
				String paramPair = "String target, String value, ";

				paramPair = StringUtil.replace(
					paramPair, "target", "target" + paramSuffix);
				paramPair = StringUtil.replace(
					paramPair, "value", "value" + paramSuffix);

				paramList += paramPair;
			}

			paramList = paramList.substring(0, paramList.length() - 2);

			sb.append("(");
			sb.append(paramList);
			sb.append(") throws Exception {\n");

			for (String paramSuffix : paramSuffixList) {
				String paramsDeclaration =
					"String[] params = ActionsUtil.getParams(paths, target, " +
						"value);\n";

				paramsDeclaration = StringUtil.replace(
					paramsDeclaration, "params", "params" + paramSuffix);
				paramsDeclaration = StringUtil.replace(
					paramsDeclaration, "target", "target" + paramSuffix);
				paramsDeclaration = StringUtil.replace(
					paramsDeclaration, "value", "value" + paramSuffix);

				sb.append(paramsDeclaration);
			}

			sb.append("\n");

			sb.append(processBlockObjectDeclaractions(actionDef));

			sb.append(_processActionDef(actionDef));

			sb.append("}\n");
		}

		return sb.toString();
	}

	private String _processCommandSuper(Element actionDef) throws Exception {
		StringBundler sb = new StringBundler();

		String actionDefCommand = actionDef.attributeValue("command");

		String functionObjectName = StringUtil.upperCaseFirstLetter(
			actionDefCommand);

		String functionReturnType = functionMethodReturnTypeMap.get(
			functionObjectName);

		if (!functionReturnType.equals("void")) {
			sb.append("return ");
		}

		sb.append("super.");
		sb.append(actionDefCommand);

		List<String> paramSuffixList = functionMethodParamListMap.get(
			functionObjectName);

		String paramList = "";

		for (String paramSuffix : paramSuffixList) {
			String paramPair = "params[0], params[1], ";

			paramPair = StringUtil.replace(
				paramPair, "params", "params" + paramSuffix);

			paramList += paramPair;
		}

		paramList = paramList.substring(0, paramList.length() - 2);

		sb.append("(");
		sb.append(paramList);
		sb.append(");\n");

		return sb.toString();
	}

	private String _processConditional(Element conditional) throws Exception {
		List<String> clauseList = new ArrayList<String>();

		String contains = conditional.attributeValue("contains");

		if (!(contains == null) && !contains.equals("")) {
			StringBundler sbConditional = new StringBundler();

			sbConditional.append("params[0].contains(\"");
			sbConditional.append(contains);
			sbConditional.append("\")");

			clauseList.add(sbConditional.toString());
		}

		String elements = conditional.attributeValue("elements");

		if (!(elements == null) && !elements.equals("")) {
			StringBundler sbConditional = new StringBundler();

			String[] elementArray = elements.split(",");

			List<String> elementList = new ArrayList<String>();

			for (String element : elementArray) {
				elementList.add("target.equals(\"" + element + "\")");
			}

			sbConditional.append("(");
			sbConditional.append(_combineConditionals(elementList, "||"));
			sbConditional.append(")");

			clauseList.add(sbConditional.toString());
		}

		String isselenium = conditional.attributeValue("isselenium");

		if (!(isselenium == null) && isselenium.equals("true")) {
			StringBundler sbConditional = new StringBundler();

			sbConditional.append("LiferaySeleniumHelper.isSelenium()");

			clauseList.add(sbConditional.toString());
		}

		String startswith = conditional.attributeValue("startswith");

		if (!(startswith == null) && !startswith.equals("")) {
			StringBundler sbConditional = new StringBundler();

			sbConditional.append("params[0].startsWith(\"");
			sbConditional.append(startswith);
			sbConditional.append("\")");

			clauseList.add(sbConditional.toString());
		}

		StringBundler sb = new StringBundler();

		sb.append(_combineConditionals(clauseList, "&&"));

		return sb.toString();
	}

	private Set<String> _validCommands = new TreeSet<String>();

}