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
 * @author Michael Hashimoto
 */
public class BaseXMLToJavaBuilder extends BaseJavaBuilder {

	public BaseXMLToJavaBuilder(Map<String, Object> context) throws Exception {
		super();

		_basedir = (String)context.get("basedir");
		_functionParameterNumberMap = (Map<String, Integer>)context.get(
			"functionParameterNumberMap");
		_functionReturnTypeMap = (Map<String, String>)context.get(
			"functionReturnTypeMap");
		_seleniumParameterNumberMap = (Map<String, Integer>)context.get(
			"seleniumParameterNumberMap");
		_seleniumDataUtil = new SeleniumDataUtil(context);
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

	protected String processBlockImports(Element block) throws Exception {
		StringBundler sb = new StringBundler();

		Set<String> classNameSet = _seleniumDataUtil.getChildClassNameSet(
			block);

		for (String className : classNameSet) {
			sb.append("import ");
			sb.append(className);
			sb.append(";\n");
		}

		return sb.toString();
	}

	protected String processBlockObjectDeclaractions(Element block)
		throws Exception {

		StringBundler sb = new StringBundler();

		Set<String> simpleClassNameSet =
			_seleniumDataUtil.getChildSimpleClassNameSet(block);

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

	private String _processCommandActions(Element command) throws Exception {
		String actionCommandName = command.attributeValue("command");
		String actionObjectName = command.attributeValue("object");

		String actionSimpleClassName = actionObjectName + "Actions";

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.lowerCaseFirstLetter(actionSimpleClassName));
		sb.append(StringPool.PERIOD);
		sb.append(actionCommandName);
		sb.append("(");

		String functionObjectName = StringUtil.upperCaseFirstLetter(
			actionCommandName);

		int functionParamNumber = _functionParameterNumberMap.get(
			functionObjectName);

		List<String> functionNumberSuffixList =
			_seleniumDataUtil.getNumberListByInteger(functionParamNumber);

		String parameters = "";

		for (String suffix : functionNumberSuffixList) {
			String actionTarget = command.attributeValue("target" + suffix);

			if (!(actionTarget == null)) {
				parameters += _replaceVariables("\"" + actionTarget + "\"");
			}
			else {
				parameters += "\"\"";
			}

			parameters += ", ";

			String actionValue = command.attributeValue("value" + suffix);

			if (!(actionValue == null)) {
				parameters += _processElementValue(command, "value" + suffix);
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

	private String _processCommandConditional(Element command)
		throws Exception {

		String actionObjectName = command.attributeValue("object");
		String actionCommandName = command.attributeValue("command");

		StringBundler sb = new StringBundler();

		if (actionObjectName == null) {
			String seleniumCommand = _processCommandSelenium(command);

			seleniumCommand = StringUtil.replace(seleniumCommand, ";\n", "");

			sb.append(seleniumCommand);
		}
		else {
			String actionCommand = _processCommandActions(command);

			actionCommand = StringUtil.replace(actionCommand, ";\n", "");

			sb.append(actionCommand);
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

	private String _processCommandFunctions(Element command) throws Exception {
		String functionCommandName = command.attributeValue("command");
		String functionObjectName = command.attributeValue("object");

		String functionSimpleClassName = functionObjectName + "Functions";

		StringBundler sb = new StringBundler();

		String functionReturnType = _functionReturnTypeMap.get(
			functionObjectName);

		if (!functionReturnType.equals("void")) {
			sb.append("return ");
		}

		sb.append(StringUtil.lowerCaseFirstLetter(functionSimpleClassName));
		sb.append(StringPool.PERIOD);
		sb.append(functionCommandName);

		Element parentElement = command.getParent();

		String parentElementName = parentElement.getName();

		int functionParamNumber = _functionParameterNumberMap.get(
			functionObjectName);

		List<String> functionNumberSuffixList =
			_seleniumDataUtil.getNumberListByInteger(functionParamNumber);

		String paramList = "";

		if (parentElementName.equals("conditional")) {
			for (String suffix : functionNumberSuffixList) {
				String paramPair = "params[0], params[1], ";

				paramPair = StringUtil.replace(
					paramPair, "params", "params" + suffix);

				paramList += paramPair;
			}
		}
		else {
			for (String suffix : functionNumberSuffixList) {
				String paramPair = "target, value, ";

				paramPair = StringUtil.replace(
					paramPair, "target", "target" + suffix);
				paramPair = StringUtil.replace(
					paramPair, "value", "value" + suffix);

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
		String macroCommandName = command.attributeValue("command");
		String macroObjectName = command.attributeValue("object");

		String macroSimpleClassName = macroObjectName + "Macros";

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.lowerCaseFirstLetter(macroSimpleClassName));
		sb.append(StringPool.PERIOD);
		sb.append(macroCommandName);

		List<Element> macroParams = command.elements("param");

		String paramList = "";

		for (Element macroParam : macroParams) {
			paramList += _processElementValue(macroParam) + ", ";
		}

		if (paramList.endsWith(", ")) {
			paramList = paramList.substring(0, paramList.length() - 2);
		}

		sb.append("(");
		sb.append(paramList);
		sb.append(");\n");

		return sb.toString();
	}

	private String _processCommandSelenium(Element command) throws Exception {
		String seleniumCommandName = command.attributeValue("command");

		StringBundler sb = new StringBundler();

		String seleniumCommandReturn = command.attributeValue("return");

		if (!(seleniumCommandReturn == null)) {
			sb.append("return ");
		}

		sb.append("selenium.");
		sb.append(seleniumCommandName);
		sb.append("(");

		int numParams = _seleniumParameterNumberMap.get(seleniumCommandName);

		if (numParams > 0) {
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
			else {
				sb.append("target");
			}
		}

		if (numParams > 1) {
			String seleniumValueName = command.attributeValue("value");

			if (!(seleniumValueName == null)) {
				sb.append(", \"");
				sb.append(_replaceVariables(seleniumValueName));
				sb.append("\"");
			}
			else {
				sb.append(", value");
			}
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

		String actionObjectName = command.attributeValue("object");

		String actionSimpleClassName = actionObjectName + "Actions";

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.lowerCaseFirstLetter(actionSimpleClassName));
		sb.append(StringPool.PERIOD);
		sb.append("selectWindow(\"PAGE_NAME\", \"\");\n");

		sb.append(processBlockCommands(command, validCommands));

		sb.append(StringUtil.lowerCaseFirstLetter(actionSimpleClassName));
		sb.append(StringPool.PERIOD);
		sb.append("selectWindow(\"TOP\", \"\");\n");

		return sb.toString();
	}

	private String _processElementValue(Element element) {
		return _processElementValue(element, "value");
	}

	private String _processElementValue(Element element, String attribute) {
		StringBundler sb = new StringBundler();

		String attributeValue = element.attributeValue(attribute);

		if (!(attributeValue == null)) {
			String firstLetter = attribute + "-first-letter";

			String firstLetterValue = element.attributeValue(firstLetter);

			if (!(firstLetterValue == null) &&
				firstLetterValue.equals("true")) {

				sb.append("LiferayActionsHelper.firstLetter(");
				sb.append(_replaceVariables("\"" + attributeValue + "\""));
				sb.append(")");
			}
			else {
				sb.append(_replaceVariables("\"" + attributeValue + "\""));
			}
		}

		return sb.toString();
	}

	private String _replaceVariables(String text) {
		if (text.startsWith("\"${") && text.endsWith("}\"")) {
			return text.substring(3, text.length() - 2);
		}
		else if (text.contains("${") && text.contains("}")) {
			text = StringUtil.replace(text, "${", "\" + ");
			text = StringUtil.replace(text, "}", " + \"");
		}

		return text;
	}

	private String _basedir;
	private Map<String, Integer> _functionParameterNumberMap;
	private Map<String, String> _functionReturnTypeMap;
	private SeleniumDataUtil _seleniumDataUtil;
	private Map<String, Integer> _seleniumParameterNumberMap;

}