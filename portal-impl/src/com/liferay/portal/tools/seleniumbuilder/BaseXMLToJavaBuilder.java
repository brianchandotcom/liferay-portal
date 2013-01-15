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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class BaseXMLToJavaBuilder extends BaseJavaBuilder {

	public BaseXMLToJavaBuilder(Map<String, Object> context) throws Exception {
		super(context);

		_basedir = (String)context.get("basedir");
		_classNameSetAvailable = (Set<String>)context.get(
			"classNameSetAvailable");
		_fileNameSet = (Set<String>)context.get("fileNameSet");
		_functionMethodParamListMap = (Map<String, List<String>>)context.get(
			"functionMethodParamListMap");
		_functionMethodReturnTypeMap = (Map<String, String>)context.get(
			"functionMethodReturnTypeMap");
		_seleniumMethodParamMap = (Map<String, Integer>)context.get(
			"seleniumMethodParamMap");

		_seleniumDataUtil = new SeleniumDataUtil(context);

		_seleniumFileUtil = new SeleniumFileUtil(_basedir);
	}

	protected String processBlockCommands(
		Element block, Set<String> validCommands) throws Exception {

		StringBundler sb = new StringBundler();

		List<Element> commands = block.elements();

		for (Element command : commands) {
			sb.append(_processCommand(command, validCommands));
		}

		return sb.toString();
	}

	protected String processBlockObjectDeclaractions(Element element)
		throws Exception {

		StringBundler sb = new StringBundler();

		Set<String> simpleClassNameSet =
			_seleniumDataUtil.getChildSimpleClassNameSet(element);

		for (String simpleClassName : simpleClassNameSet) {
			sb.append(simpleClassName);
			sb.append(" ");
			sb.append(StringUtil.lowerCaseFirstLetter(simpleClassName));
			sb.append(" = new ");
			sb.append(simpleClassName);
			sb.append("(selenium);\n");
		}

		sb.append("\n");

		return sb.toString();
	}

	protected String processBlocksImports(Element element) throws Exception {
		StringBundler sb = new StringBundler();

		Set<String> classNameImportSet = _seleniumDataUtil.getChildClassNameSet(
			element);

		for (String className : classNameImportSet) {
			sb.append("import ");
			sb.append(className);
			sb.append(";\n");
		}

		return sb.toString();
	}

	private String _getElementValue(Element element) {
		return _getElementValue(element, "value");
	}

	private String _getElementValue(Element element, String attribute) {
		StringBundler sb = new StringBundler();

		String attributeValue = element.attributeValue(attribute);

		if (!(attributeValue == null)) {
			String firstLetter = attribute + "-first-letter";

			String firstLetterValue = element.attributeValue(firstLetter);

			if (!(firstLetterValue == null) &&
				firstLetterValue.equals("true")) {

				sb.append("LiferaySeleniumHelper.firstLetter(");
				sb.append(_replaceVariables("\"" + attributeValue + "\""));
				sb.append(")");
			}
			else {
				sb.append(_replaceVariables("\"" + attributeValue + "\""));
			}
		}

		return sb.toString();
	}

	private String _processCommand(Element command, Set<String> validCommands)
		throws Exception {

		String commandName = command.getName();
		String fileName = _seleniumDataUtil.getFileNameByElement(command);

		if (validCommands.contains(commandName)) {
			if (commandName.equals("action")) {
				return _processCommandActions(command);
			}
			else if (commandName.equals("else")) {
				return _processCommandElse(command, validCommands);
			}
			else if (commandName.equals("function")) {
				return _processCommandFunctions(command);
			}
			else if (commandName.equals("if")) {
				return _processCommandIf(command, validCommands);
			}
			else if (commandName.equals("macro")) {
				return _processCommandMacros(command);
			}
			else if (commandName.equals("selenium")) {
				return _processCommandSelenium(command);
			}
			else if (commandName.equals("while")) {
				return _processCommandWhile(command, validCommands);
			}
			else if (commandName.equals("window")) {
				return _processCommandWindow(command, validCommands);
			}
			else {
				StringBundler message = new StringBundler();

				message.append("Command '" + commandName + "'");
				message.append(" used in " + fileName);
				message.append(" does not exist in vocabulary");

				throw new Exception(message.toString());
			}
		}
		else {
			StringBundler message = new StringBundler();

			message.append("Invalid command '" + commandName + "'");
			message.append(" used in " + fileName);

			throw new Exception(message.toString());
		}
	}

	private String _processCommandActions(Element action) {
		String actionCommandName = action.attributeValue("command");
		String actionObjectName = action.attributeValue("object");

		String actionClassName = actionObjectName + "Actions";

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.lowerCaseFirstLetter(actionClassName));
		sb.append(StringPool.PERIOD);
		sb.append(actionCommandName);
		sb.append("(");

		String functionObjectName = StringUtil.upperCaseFirstLetter(
			actionCommandName);

		List<String> paramSuffixList = _functionMethodParamListMap.get(
			functionObjectName);

		String parameters = "";

		for (String paramSuffix : paramSuffixList) {
			String locator = "locator" + paramSuffix;
			String target = "target" + paramSuffix;

			String actionLocator = action.attributeValue(locator);
			String actionTarget = action.attributeValue(target);

			if (!(actionLocator == null)) {
				parameters += _replaceVariables("\"" + actionLocator + "\"");
			}
			else if (!(actionTarget == null)) {
				parameters += _replaceVariables("\"" + actionTarget + "\"");
			}
			else {
				parameters += "\"\"";
			}

			parameters += ", ";

			String value = "value" + paramSuffix;

			String actionValue = action.attributeValue(value);

			if (!(actionValue == null)) {
				parameters += _getElementValue(action, value);
			}
			else {
				parameters += "\"\"";
			}

			parameters += ", ";
		}

		parameters = parameters.substring(0, parameters.length() - 2);

		sb.append(parameters);

		sb.append(");\n");

		return sb.toString();
	}

	private String _processCommandConditional(Element conditional) {
		String conditionalObjectName = conditional.attributeValue("object");
		String conditionalCommandName = conditional.attributeValue("command");

		StringBundler sb = new StringBundler();

		if (conditionalCommandName.contains("Not")) {
			sb.append("!");

			conditionalCommandName = StringUtil.replace(
				conditionalCommandName, "Not", "");
		}

		if (conditionalObjectName == null) {
			String seleniumCommand = _processCommandSelenium(conditional);

			seleniumCommand = StringUtil.replace(seleniumCommand, "Not", "");
			seleniumCommand = StringUtil.replace(seleniumCommand, ";\n", "");

			sb.append(seleniumCommand);
		}
		else {
			String actionClassName = conditionalObjectName + "Actions";
			String actionCommandName = conditionalCommandName;

			sb.append(StringUtil.lowerCaseFirstLetter(actionClassName));
			sb.append(StringPool.PERIOD);
			sb.append(actionCommandName);

			String actionLocator = conditional.attributeValue("locator");
			String actionTarget = conditional.attributeValue("target");

			if (!(actionLocator == null)) {
				sb.append("(");
				sb.append(_replaceVariables("\"" + actionLocator + "\""));
			}
			else if (!(actionTarget == null)) {
				sb.append("(");
				sb.append(_replaceVariables("\"" + actionTarget + "\""));
			}
			else {
				sb.append("(\"\"");
			}

			sb.append(", ");

			String actionValue = conditional.attributeValue("value");

			if (!(actionValue == null)) {
				sb.append(_getElementValue(conditional));
			}
			else {
				sb.append("\"\"");
			}

			sb.append(")");
		}

		return sb.toString();
	}

	private String _processCommandElse(
		Element command, Set<String> validCommands) throws Exception {

		StringBundler sb = new StringBundler();

		sb.append("else {");
		sb.append(processBlockCommands(command, validCommands));
		sb.append("}");

		return sb.toString();
	}

	private String _processCommandFunctions(Element function) throws Exception {
		String functionCommandName = function.attributeValue("command");
		String functionObjectName = function.attributeValue("object");

		String functionClassName = functionObjectName + "Functions";

		StringBundler sb = new StringBundler();

		String functionReturnType = _functionMethodReturnTypeMap.get(
			functionObjectName);

		if (!functionReturnType.equals("void")) {
			sb.append("return ");
		}

		sb.append(StringUtil.lowerCaseFirstLetter(functionClassName));
		sb.append(StringPool.PERIOD);
		sb.append(functionCommandName);

		Element parentElement = function.getParent();

		String parentElementName = parentElement.getName();

		List<String> paramSuffixList = _functionMethodParamListMap.get(
			functionObjectName);

		String paramList = "";

		if (parentElementName.equals("conditional")) {
			for (String paramSuffix : paramSuffixList) {
				String paramPair = "params[0], params[1], ";

				paramPair = StringUtil.replace(
					paramPair, "params", "params" + paramSuffix);

				paramList += paramPair;
			}
		}
		else {
			for (String paramSuffix : paramSuffixList) {
				String paramPair = "target, value, ";

				paramPair = StringUtil.replace(
					paramPair, "target", "target" + paramSuffix);
				paramPair = StringUtil.replace(
					paramPair, "value", "value" + paramSuffix);

				paramList += paramPair;
			}
		}

		paramList = paramList.substring(0, paramList.length() - 2);

		sb.append("(");
		sb.append(paramList);
		sb.append(");\n");

		return sb.toString();
	}

	private String _processCommandIf(Element command, Set<String> validCommands)
		throws Exception {

		StringBundler sb = new StringBundler();

		sb.append("if (");
		sb.append(_processCommandConditional(command));
		sb.append(") {");
		sb.append(processBlockCommands(command, validCommands));
		sb.append("}");

		return sb.toString();
	}

	private String _processCommandMacros(Element command) {
		String macroDefCommand = command.attributeValue("command");
		String macroObjectName = command.attributeValue("object");

		String macroClassName = macroObjectName + "Macros";

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.lowerCaseFirstLetter(macroClassName));
		sb.append(StringPool.PERIOD);
		sb.append(macroDefCommand);

		List<Element> macroParams = command.elements("param");

		String paramList = "";

		for (Element macroParam : macroParams) {
			paramList += _getElementValue(macroParam) + ", ";
		}

		if (paramList.endsWith(", ")) {
			paramList = paramList.substring(0, paramList.length() - 2);
		}

		sb.append("(");
		sb.append(paramList);
		sb.append(");\n");

		return sb.toString();
	}

	private String _processCommandSelenium(Element command) {
		String seleniumCommandName = command.attributeValue("command");
		String seleniumCommandReturn = command.attributeValue("return");

		int numParams = 0;

		if (_seleniumMethodParamMap.containsKey(seleniumCommandName)) {
			numParams = _seleniumMethodParamMap.get(seleniumCommandName);
		}

		StringBundler sb = new StringBundler();

		if (!(seleniumCommandReturn == null)) {
			sb.append("return ");
		}

		sb.append("selenium.");
		sb.append(seleniumCommandName);
		sb.append("(");

		String seleniumTargetName = command.attributeValue("target");

		if (!(seleniumTargetName == null)) {
			sb.append("\"");
			sb.append(_replaceVariables(seleniumTargetName));
			sb.append("\"");
		}
		else if (seleniumCommandName.equals("assertConfirmation") ||
				 seleniumCommandName.equals("assertTextNotPresent") ||
				 seleniumCommandName.equals("assertTextPresent") ||
				 seleniumCommandName.equals("waitForConfirmation") ||
				 seleniumCommandName.equals("waitForTextNotPresent") ||
				 seleniumCommandName.equals("waitForTextPresent")) {

			sb.append("value");
		}
		else if (numParams > 0) {
			sb.append("target");
		}

		String seleniumValueName = command.attributeValue("value");

		if (!(seleniumValueName == null)) {
			sb.append(", \"");
			sb.append(_replaceVariables(seleniumValueName));
			sb.append("\"");
		}
		else if (numParams > 1) {
			sb.append(", value");
		}

		sb.append(");\n");

		return sb.toString();
	}

	private String _processCommandWhile(
		Element command, Set<String> validCommands) throws Exception {

		StringBundler sb = new StringBundler();

		sb.append("while (");
		sb.append(_processCommandConditional(command));
		sb.append(") {");
		sb.append(processBlockCommands(command, validCommands));
		sb.append("}");

		return sb.toString();
	}

	private String _processCommandWindow(
		Element command, Set<String> validCommands) throws Exception {

		String windowObjectName = command.attributeValue("object");

		String actionClassName = windowObjectName + "Actions";

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.lowerCaseFirstLetter(actionClassName));
		sb.append(StringPool.PERIOD);
		sb.append("selectWindow(\"PAGE_NAME\", \"\");\n");

		sb.append(processBlockCommands(command, validCommands));

		sb.append(StringUtil.lowerCaseFirstLetter(actionClassName));
		sb.append(StringPool.PERIOD);
		sb.append("selectWindow(\"TOP\", \"\");\n");

		return sb.toString();
	}

	private String _replaceVariables(String text) {
		if (text.startsWith("\"${") && text.endsWith("}\"")) {
			return text.substring(3, text.length() - 2);
		}
		else {
			text = StringUtil.replace(text, "${", "\" + ");
			text = StringUtil.replace(text, "}", " + \"");
		}

		return text;
	}

	private String _basedir;
	private Set<String> _classNameSetAvailable;
	private Set<String> _fileNameSet;
	private Map<String, List<String>> _functionMethodParamListMap;
	private Map<String, String> _functionMethodReturnTypeMap;
	private SeleniumDataUtil _seleniumDataUtil;
	private SeleniumFileUtil _seleniumFileUtil;
	private Map<String, Integer> _seleniumMethodParamMap;

}