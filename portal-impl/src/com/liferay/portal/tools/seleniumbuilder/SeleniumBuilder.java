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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.InitUtil;

import jargs.gnu.CmdLineParser;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new ActionsXMLToJavaBuilder(args);
		new MacrosXMLToJavaBuilder(args);
		new PathsXMLToJavaBuilder(args);
		new TestXMLToJavaBuilder(args);
		new TestPlanBuilder(args);
		new FunctionsXMLToJavaBuilder(args);
	}

	public SeleniumBuilder(String[] args) throws Exception {
		CmdLineParser cmdLineParser = new CmdLineParser();

		CmdLineParser.Option basedirOption = cmdLineParser.addStringOption(
			"basedir");

		cmdLineParser.parse(args);

		basedir = (String)cmdLineParser.getOptionValue(basedirOption);

		fileNameSet = _getFileNameSet();

		fileNameSetActions = _getFileNameSetActions();
		fileNameSetFunctions = _getFileNameSetFunctions();
		fileNameSetMacros = _getFileNameSetMacros();
		fileNameSetPaths = _getFileNameSetPaths();
		fileNameSetTests = _getFileNameSetTests();

		classNameSetAvailable = _getClassNameSetAvailable();

		functionMethodParamListMap = _getFunctionMethodParamListMap();
		functionMethodReturnTypeMap = _getFunctionMethodReturnTypeMap();
		seleniumMethodParamMap = _getSeleniumMethodParamMap();
	}

	protected Set<String> addClassName(
		Set<String> classNameSet, String simpleClassName) throws Exception {

		for (String className : classNameSetAvailable) {
			if (className.endsWith("." + simpleClassName)) {
				classNameSet.add(className);
			}
		}

		return classNameSet;
	}

	protected Set<String> getChildObjectSet(Element element) {
		return _getChildObjectSet(element, new TreeSet<String>());
	}

	protected Set<String> getClassNameSet(Element element) throws Exception {
		Set<String> classNameSet = new TreeSet<String>();

		Set<String> objectSet = getChildObjectSet(element);

		for (String object : objectSet) {
			classNameSet = addClassName(classNameSet, object);
		}

		return classNameSet;
	}

	protected String getNormalizedContent(String fileName) throws Exception {
		String content = readFile(fileName);

		if (content != null) {
			content = content.trim();
			content = StringUtil.replace(content, "\n", "");
			content = StringUtil.replace(content, "\r\n", "");
			content = StringUtil.replace(content, "\t", " ");
			content = content.replaceAll(" +", " ");
		}

		return content;
	}

	protected void getObject(String fileName) throws Exception {
		Element rootElement = getRootElement(fileName);

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String object = rootElement.attributeValue("object");

		if ((object == null) || !object.equals(fileName.substring(x + 1, y))) {
			System.out.println(fileName + " has an invalid object name");
		}
	}

	protected Element getParentElementByName(
		Element element, String parentElementName) throws Exception {

		String elementName = element.getName();

		if (elementName.equals(parentElementName)) {
			return element;
		}
		else {
			return getParentElementByName(
				element.getParent(), parentElementName);
		}
	}

	protected Element getRootElement(String fileName) throws Exception {
		String content = getNormalizedContent(fileName);

		Document document = SAXReaderUtil.read(content, true);

		return document.getRootElement();
	}

	protected Set<String> getSimpleClassNameSet(Element element)
		throws Exception {

		Set<String> objectSet = getChildObjectSet(element);
		Set<String> simpleClassNameSet = new TreeSet<String>();

		for (String object : objectSet) {
			simpleClassNameSet.add(object);
		}

		return simpleClassNameSet;
	}

	protected String normalizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);
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

		Set<String> simpleClassNameSet = getSimpleClassNameSet(element);

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

		Set<String> classNameImportSet = getClassNameSet(element);

		for (String className : classNameImportSet) {
			sb.append("import ");
			sb.append(className);
			sb.append(";\n");
		}

		return sb.toString();
	}

	protected String readFile(String fileName) throws Exception {
		return FileUtil.read(basedir + "/" + fileName);
	}

	protected void writeFile(String fileName, String content, boolean format)
		throws Exception {

		File file = new File(basedir + "-generate/" + fileName);

		if (format) {
			ServiceBuilder.writeFile(file, content);
		}
		else {
			System.out.println("Writing " + file);

			FileUtil.write(file, content);
		}
	}

	protected String basedir;
	protected Set<String> classNameSetAvailable;
	protected Set<String> fileNameSet;
	protected Set<String> fileNameSetActions;
	protected Set<String> fileNameSetFunctions;
	protected Set<String> fileNameSetMacros;
	protected Set<String> fileNameSetPaths;
	protected Set<String> fileNameSetTests;
	protected String filePath = "";
	protected Map<String, List<String>> functionMethodParamListMap;
	protected Map<String, String> functionMethodReturnTypeMap;
	protected Map<String, Integer> seleniumMethodParamMap;

	private String _getActionsConditional(Element conditional) {
		String conditionalObjectName = conditional.attributeValue("object");
		String conditionalCommandName = conditional.attributeValue("command");

		String actionClassName = conditionalObjectName + "Actions";

		StringBundler sb = new StringBundler();

		if (conditionalCommandName.contains("Not")) {
			sb.append("!");
		}

		if (conditionalObjectName == null) {
			String seleniumCommand = _processCommandSelenium(conditional);

			seleniumCommand = StringUtil.replace(seleniumCommand, "Not", "");
			seleniumCommand = StringUtil.replace(seleniumCommand, ";\n", "");

			sb.append(seleniumCommand);
		}
		else {
			conditionalCommandName = StringUtil.replace(
				conditionalCommandName, "Not", "");

			String actionCommand = conditionalCommandName;

			sb.append(StringUtil.lowerCaseFirstLetter(actionClassName));
			sb.append(StringPool.PERIOD);
			sb.append(actionCommand);

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

			String actionValue = conditional.attributeValue("value");

			if (!(actionValue == null)) {
				sb.append(", ");
				sb.append(_getElementValue(conditional));
			}
			else {
				sb.append(", \"\"");
			}

			sb.append(")");
		}

		return sb.toString();
	}

	private Set<String> _getChildObjectSet(
		Element element, Set<String> objects) {

		List<Element> children = element.elements();

		if (children.isEmpty()) {
			return objects;
		}
		else {
			for (Element child : children) {
				String childName = child.getName();
				String childObjectName = child.attributeValue("object");

				if (!(childObjectName == null)) {
					if (childName.equals("action") || childName.equals("if") ||
						childName.equals("while")) {

						objects.add(childObjectName + "Actions");
					}
					else if (childName.equals("function")) {
						objects.add(childObjectName + "Functions");
					}
					else if (childName.equals("macro")) {
						objects.add(childObjectName + "Macros");
					}
				}

				_getChildObjectSet(child, objects);
			}
		}

		return objects;
	}

	private Set<String> _getClassNameSetAvailable() throws Exception {
		Set<String> classNameSet = new TreeSet<String>();

		for (String fileName : fileNameSet) {
			if (fileName.endsWith(".functions")) {
				String className = StringUtil.replace(
					fileName, ".functions", "Functions");

				className = StringUtil.replace(
					className, StringPool.SLASH, StringPool.PERIOD);

				classNameSet.add(className);
			}
			else if (fileName.endsWith(".macros")) {
				String className = StringUtil.replace(
					fileName, ".macros", "Macros");

				className = StringUtil.replace(
					className, StringPool.SLASH, StringPool.PERIOD);

				classNameSet.add(className);

			}
			else if (fileName.endsWith(".paths")) {
				String className = StringUtil.replace(
					fileName, ".paths", "Actions");

				className = StringUtil.replace(
					className, StringPool.SLASH, StringPool.PERIOD);

				className = StringUtil.replace(
					className, ".paths.", ".actions.");

				classNameSet.add(className);
			}
		}

		return classNameSet;
	}

	private String _getElementValue(Element element) {
		return _getElementValue(element, "value");
	}

	private String _getElementValue(Element element, String attribute) {
		StringBundler sb = new StringBundler();

		String attributeValue = element.attributeValue(attribute);

		if (!(attributeValue == null)) {
			String firstLetter = element.attributeValue(
				attribute + "-first-letter");

			if (!(firstLetter == null) && firstLetter.equals("true")) {
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

	private Set<String> _getFileNameSet() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\**\\*.actions",
				"**\\portalweb\\**\\*.functions",
				"**\\portalweb\\**\\*.macros",
				"**\\portalweb\\**\\*.paths",
				"**\\portalweb\\**\\*.test"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

	private Set<String> _getFileNameSetActions() throws Exception {
		return _getFileNameSetType(".actions");
	}

	private Set<String> _getFileNameSetFunctions() throws Exception {
		return _getFileNameSetType(".functions");
	}

	private Set<String> _getFileNameSetMacros() throws Exception {
		return _getFileNameSetType(".macros");
	}

	private Set<String> _getFileNameSetPaths() throws Exception {
		return _getFileNameSetType(".paths");
	}

	private Set<String> _getFileNameSetTests() throws Exception {
		return _getFileNameSetType(".test");
	}

	private Set<String> _getFileNameSetType(String suffix) throws Exception {
		Set<String> fileNameTypeSet = new TreeSet<String>();

		for (String fileName : fileNameSet) {
			if (fileName.endsWith(suffix)) {
				fileNameTypeSet.add(fileName);
			}
		}

		return fileNameTypeSet;
	}

	private Map<String, List<String>> _getFunctionMethodParamListMap()
		throws Exception {

		Map<String, List<String>> hashMap = new HashMap<String, List<String>>();

		for (String fileName : fileNameSetFunctions) {
			Element functions = getRootElement(fileName);

			String functionsObject = functions.attributeValue("object");
			String functionsParams = functions.attributeValue("params");

			List<String> arrayList = new ArrayList<String>();

			if (!(functionsParams == null)) {
				int functionsParamsInt = GetterUtil.getInteger(functionsParams);

				for (int i = 1; i <= functionsParamsInt; i++) {
					arrayList.add(String.valueOf(i));
				}
			}
			else {
				arrayList.add("");
			}

			hashMap.put(functionsObject, arrayList);
		}

		return hashMap;
	}

	private Map<String, String> _getFunctionMethodReturnTypeMap()
		throws Exception {

		Map<String, String> hashMap = new HashMap<String, String>();

		for (String fileName : fileNameSetFunctions) {
			Element functions = getRootElement(fileName);

			String functionsObject = functions.attributeValue("object");
			String functionsReturn = functions.attributeValue("return");

			List<String> arrayList = new ArrayList<String>();

			if (functionsReturn == null) {
				hashMap.put(functionsObject, "void");
			}
			else {
				hashMap.put(functionsObject, functionsReturn);
			}
		}

		return hashMap;
	}

	private Map<String, Integer> _getSeleniumMethodParamMap() throws Exception {
		Map<String, Integer> hashMap = new HashMap<String, Integer>();

		hashMap = _getSeleniumMethodParamMapFile(
			hashMap, "com/liferay/portalweb/portal/util/liferayselenium/" +
				"SeleniumWrapper.java");

		hashMap = _getSeleniumMethodParamMapFile(
			hashMap, "com/liferay/portalweb/portal/util/liferayselenium/" +
				"LiferaySelenium.java");

		hashMap.put("isNotChecked", 1);
		hashMap.put("isNotVisible", 1);

		return hashMap;
	}

	private Map<String, Integer> _getSeleniumMethodParamMapFile(
		Map<String, Integer> hashMap, String file) throws Exception {

		String content = getNormalizedContent(file);

		Pattern pattern = Pattern.compile(
			"public (boolean|String|void) [A-Za-z0-9_]*\\(.*?\\)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String methodDec = matcher.group();

			int x = methodDec.indexOf("(");
			int y = methodDec.indexOf(")");

			String name = null;

			if (methodDec.startsWith("public boolean")) {
				name = methodDec.substring(15, x);
			}
			else if (methodDec.startsWith("public String")) {
				name = methodDec.substring(14, x);
			}
			else if (methodDec.startsWith("public void")) {
				name = methodDec.substring(12, x);
			}

			String params = methodDec.substring(x + 1, y);

			String[] paramsArray = params.split(",");

			int numParams;

			if (params.equals("")) {
				numParams = 0;
			}
			else {
				numParams = paramsArray.length;
			}

			hashMap.put(name, numParams);
		}

		return hashMap;
	}

	private String _processCommand(Element command, Set<String> validCommands)
		throws Exception {

		String commandName = command.getName();

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
				message.append(" used in " + filePath);
				message.append(" does not exist in vocabulary");

				throw new Exception(message.toString());
			}
		}
		else {
			StringBundler message = new StringBundler();

			message.append("Invalid command '" + commandName + "'");
			message.append(" used in " + filePath);

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

		List<String> paramSuffixList = functionMethodParamListMap.get(
			functionObjectName);

		String parameters = "";

		for (String paramSuffix : paramSuffixList) {
			String actionLocator = action.attributeValue(
				"locator" + paramSuffix);
			String actionTarget = action.attributeValue("target" + paramSuffix);

			if (!(actionLocator == null)) {
				parameters += _replaceVariables("\"" + actionLocator + "\"");
			}
			else if (!(actionTarget == null)) {
				parameters += _replaceVariables("\"" + actionTarget + "\"");
			}
			else {
				parameters += "\"\"";
			}

			String actionValue = action.attributeValue("value" + paramSuffix);

			if (!(actionValue == null)) {
				parameters += ", ";
				parameters += _getElementValue(action, "value" + paramSuffix);
			}
			else {
				parameters += ", null";
			}

			parameters += ", ";
		}

		parameters = parameters.substring(0, parameters.length() - 2);

		sb.append(parameters);

		sb.append(");\n");

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

		String functionReturnType = functionMethodReturnTypeMap.get(
			functionObjectName);

		if (!functionReturnType.equals("void")) {
			sb.append("return ");
		}

		sb.append(StringUtil.lowerCaseFirstLetter(functionClassName));
		sb.append(StringPool.PERIOD);
		sb.append(functionCommandName);

		Element parentElement = function.getParent();

		String parentElementName = parentElement.getName();

		List<String> paramSuffixList = functionMethodParamListMap.get(
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
		sb.append(_getActionsConditional(command));
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
		sb.append("(");

		List<Element> macroParams = command.elements("param");

		String paramList = "";

		for (Element macroParam : macroParams) {
			paramList += _getElementValue(macroParam) + ", ";
		}

		if (paramList.endsWith(", ")) {
			paramList = paramList.substring(0, paramList.length() - 2);
		}

		sb.append(paramList);
		sb.append(");\n");

		return sb.toString();
	}

	private String _processCommandSelenium(Element command) {
		String seleniumCommandName = command.attributeValue("command");
		String seleniumCommandReturn = command.attributeValue("return");

		int numParams = 0;

		if (seleniumMethodParamMap.containsKey(seleniumCommandName)) {
			numParams = seleniumMethodParamMap.get(seleniumCommandName);
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

		if (!seleniumCommandName.equals("open")) {
			if (!(seleniumValueName == null)) {
				sb.append(", \"");
				sb.append(_replaceVariables(seleniumValueName));
				sb.append("\"");
			}
			else if (numParams > 1) {
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
		sb.append(_getActionsConditional(command));
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

}