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
		pageObjectSet = _getPageObjects();

		fileNameSet = _getFileNameSet();

		fileNameSetActions = _getFileNameSetActions();
		fileNameSetFunctions = _getFileNameSetFunctions();
		fileNameSetMacros = _getFileNameSetMacros();
		fileNameSetPaths = _getFileNameSetPaths();
		fileNameSetTests = _getFileNameSetTests();

		classNameSetAvailable = _getClassNameSetAvailable();

		seleniumMethodParamMap = _getSeleniumMethods();
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

	protected String findRootBlockName(Element block) throws Exception {
		String blockType = block.getName();

		if (blockType.equals("actiondef") || blockType.equals("functiondef") ||
			blockType.equals("macrodef") || blockType.equals("setup") ||
			blockType.equals("steps") || blockType.equals("teardown")) {

			return block.attributeValue("command");
		}
		else {
			return findRootBlockName(block.getParent());
		}
	}

	protected String getActionDefBlock(Element runBlock) throws Exception {
		StringBundler sb = new StringBundler();

		String actionDefCommand = runBlock.attributeValue("command");

		List<Element> commands = runBlock.elements();

		boolean firstConditional = true;

		for (Element command : commands) {
			if (firstConditional) {
				sb.append("if ");

				firstConditional = false;
			}
			else {
				sb.append("else if ");
			}

			sb.append(_getCommandConditional(command));
		}

		sb.append("else {");
		sb.append(_getCommandDefaultCommand(actionDefCommand));
		sb.append("}");

		return sb.toString();
	}

	protected Set<String> getAllObjects(Element runBlock, Set<String> objects) {
		if (runBlock.elements().isEmpty()) {
			return objects;
		}
		else {
			for (Element child : runBlock.elements()) {
				if (isCommand(child)) {
					String type =
						StringUtil.upperCaseFirstLetter(child.getName()) + "s";
					if (child.getName().equals("while") ||
						child.getName().equals("if")) {
						type = "Actions";
					}

					if (child.getName().equals("function")) {
						type = "Functions";
					}

					if (!(child.attributeValue("object") == null)) {
						objects.add(child.attributeValue("object") + type);
					}
				}

				getAllObjects(child, objects);
			}
		}

		return objects;
	}

	protected Set<String> getClassNameSet(Element element) throws Exception {
		Set<String> classNameSet = new TreeSet<String>();

		Set<String> objectSet = getAllObjects(element, new TreeSet<String>());

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

	protected Element getRootElement(String fileName) throws Exception {
		String content = getNormalizedContent(fileName);

		Document document = SAXReaderUtil.read(content, true);

		return document.getRootElement();
	}

	protected Set<String> getSimpleClassNameSet(Element element)
		throws Exception {

		Set<String> objectSet = getAllObjects(element, new TreeSet<String>());
		Set<String> simpleClassNameSet = new TreeSet<String>();

		for (String object : objectSet) {
			simpleClassNameSet.add(object);
		}

		return simpleClassNameSet;
	}

	protected boolean isCommand(Element element) {
		String name = element.getName();

		return (name.equals("macro") || name.equals("action") ||
				name.equals("function") || name.equals("while") ||
				name.equals("if"));
	}

	protected String normalizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);
	}

	protected String processBlock(Element block) throws Exception {
		StringBundler sb = new StringBundler();

		String blockName = block.getName();

		String blockDefName = "";

		if (!blockName.equals("setup") &&
			!blockName.equals("steps") &&
			!blockName.equals("teardown")) {

			blockDefName = findRootBlockName(block);
		}

		if (blockName.equals("actiondef")) {
			sb.append(getActionDefBlock(block));
		}
		else {
			List<Element> commands = block.elements();

			for (Element command : commands) {
				sb.append(processCommand(command, blockName, blockDefName));
			}
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

	protected String processCommand(
		Element command, String blockName, String blockDefName)
			throws Exception {

		String commandName = command.getName();

		if (_isValidCommand(commandName)) {
			if (commandName.equals("action")) {
				return _getCommandActions(command);
			}
			else if (commandName.equals("conditional")) {
				return _getCommandConditional(command);
			}
			else if (commandName.equals("defaultcommand")) {
				return _getCommandDefaultCommand(blockDefName);
			}
			else if (commandName.equals("else")) {
				return _getCommandElse(command);
			}
			else if (commandName.equals("function")) {
				return _getCommandFunctions(command, blockDefName);
			}
			else if (commandName.equals("if")) {
				return _getCommandIf(command);
			}
			else if (commandName.equals("macro")) {
				return _getCommandMacros(command);
			}
			else if (commandName.equals("selenium")) {
				return _getCommandSelenium(command);
			}
			else if (commandName.equals("while")) {
				return _getCommandWhile(command);
			}
			else if (commandName.equals("window")) {
				return _getCommandWindow(command);
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

	protected String processRootElementImports(Element rootElement)
		throws Exception {

		StringBundler sb = new StringBundler();

		Set<String> classNameImportSet = getClassNameSet(rootElement);

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
		File file = new File(basedir + "/" + fileName);

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
	protected ClassTypes classType;
	protected Set<String> fileNameSet;
	protected Set<String> fileNameSetActions;
	protected Set<String> fileNameSetFunctions;
	protected Set<String> fileNameSetMacros;
	protected Set<String> fileNameSetPaths;
	protected Set<String> fileNameSetTests;
	protected String filePath = "";
	protected Set<String> pageObjectSet;
	protected Map<String, Integer> seleniumMethodParamMap;

	private String _combineConditionals(
		List<String> conditionalList, String delimiter) {

		boolean firstConditional = true;

		StringBundler sb = new StringBundler();

		for (String conditional : conditionalList) {
			if (!firstConditional) {
				sb.append(" " + delimiter + " ");
			}

			sb.append(conditional);

			firstConditional = false;
		}

		return sb.toString();
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

	private String _getCommandActions(Element command) {
		StringBundler sb = new StringBundler();

		String actionDefCommand = command.attributeValue("command");
		String actionObjectName = command.attributeValue("object") + "Actions";

		sb.append(StringUtil.lowerCaseFirstLetter(actionObjectName));
		sb.append(StringPool.PERIOD);
		sb.append(actionDefCommand);
		sb.append("(");

		List<String> suffixList = new ArrayList<String>();

		if (actionDefCommand.equals("dragAndDrop")) {
			suffixList.add("1");
			suffixList.add("2");
		}
		else {
			suffixList.add("");
		}

		for (String suffix : suffixList) {
			String actionLocator = command.attributeValue("locator" + suffix);
			String actionPath = command.attributeValue("path" + suffix);

			if (!(actionLocator == null)) {
				sb.append(_replaceVariables("\"" + actionLocator + "\""));
			}
			else if (!(actionPath == null)) {
				sb.append(_replaceVariables("\"" + actionPath + "\""));
			}
			else {
				sb.append("\"\"");
			}

			String actionValue = command.attributeValue("value" + suffix);

			if (!(actionValue == null)) {
				sb.append(", ");
				sb.append(_getCommandAttributeValue(command, "value" + suffix));
			}
			else {
				sb.append(", null");
			}

			if (suffix.equals("1")) {
				sb.append(", ");
			}
		}

		sb.append(");\n");

		return sb.toString();
	}

	private String _getCommandActionsConditional(Element conditional) {
		StringBundler sb = new StringBundler();

		String conditionalName = conditional.attributeValue("object");
		String conditionalCommand = conditional.attributeValue("command");

		if (conditionalCommand.contains("Not")) {
			sb.append("!");

			conditionalCommand = StringUtil.replace(
				conditionalCommand, "Not", "");
		}

		if (conditionalName == null) {
			String seleniumCommand = _getCommandSelenium(conditional);
			seleniumCommand = StringUtil.replace(seleniumCommand, "Not", "");
			seleniumCommand = StringUtil.replace(seleniumCommand, ";\n", "");

			if (seleniumCommand.contains("Not")) {
				seleniumCommand = StringUtil.replace(
					seleniumCommand, "Not", "");
			}

			sb.append(seleniumCommand);
		}
		else {
			String actionCommand = conditionalCommand;
			String actionLocator = conditional.attributeValue("locator");
			String actionPath = conditional.attributeValue("path");
			String actionValue = conditional.attributeValue("value");

			String actionObjectName = conditionalName + "Actions";

			sb.append(StringUtil.lowerCaseFirstLetter(actionObjectName));
			sb.append(StringPool.PERIOD);
			sb.append(actionCommand);

			if (!(actionLocator == null)) {
				sb.append("(");
				sb.append(_replaceVariables("\"" + actionLocator + "\""));
			}
			else if (!(actionPath == null)) {
				sb.append("(");
				sb.append(_replaceVariables("\"" + actionPath + "\""));
			}
			else {
				sb.append("(\"\"");
			}

			if (!(actionValue == null)) {
				sb.append(", ");
				sb.append(_getCommandAttributeValue(conditional));
			}
			else {
				sb.append(", \"\"");
			}

			sb.append(")");
		}

		return sb.toString();
	}

	private String _getCommandAttributeValue(Element command) {
		return _getCommandAttributeValue(command, "value");
	}

	private String _getCommandAttributeValue(
		Element command, String attribute) {

		StringBundler sb = new StringBundler();

		String attributeValue = command.attributeValue(attribute);

		if (!(attributeValue == null)) {
			String firstLetter = command.attributeValue(
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

	private String _getCommandConditional(Element conditional)
		throws Exception {

		List<String> clauseList = new ArrayList<String>();

		String elements = conditional.attributeValue("elements");

		if (!(elements == null) && !elements.equals("")) {
			StringBundler sbConditional = new StringBundler();

			String[] elementArray = elements.split(",");

			List<String> elementList = new ArrayList<String>();

			for (String element : elementArray) {
				elementList.add("param1.equals(\"" + element + "\")");
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

			sbConditional.append("param1.startsWith(\"");
			sbConditional.append(startswith);
			sbConditional.append("\")");

			clauseList.add(sbConditional.toString());
		}

		StringBundler sb = new StringBundler();

		sb.append("(");
		sb.append(_combineConditionals(clauseList, "&&"));
		sb.append(") {");
		sb.append(processBlock(conditional));
		sb.append("}");

		return sb.toString();
	}

	private String _getCommandDefaultCommand(String actionDefName) {
		StringBundler sb = new StringBundler();

		sb.append("super." + actionDefName);
		sb.append("(params[0], params[1]);\n");

		return sb.toString();
	}

	private String _getCommandElse(Element command) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("else {");
		sb.append(processBlock(command));
		sb.append("}");

		return sb.toString();
	}

	private String _getCommandFunctions(Element command, String actionDefName) {
		StringBundler sb = new StringBundler();

		String functionCommandName = command.attributeValue("command");
		String functionObjectName = command.attributeValue("object");

		if (functionObjectName == null) {
			sb.append(actionDefName);
		}
		else {
			sb.append(StringUtil.lowerCaseFirstLetter(functionObjectName));
		}

		sb.append("Functions.");
		sb.append(functionCommandName);

		Element parentElement = command.getParent();

		if (parentElement.getName().equals("conditional")) {
			sb.append("(params[0], params[1]);\n");
		}
		else {
			sb.append("(param1, param2);\n");
		}

		return sb.toString();
	}

	private String _getCommandIf(Element command) throws Exception {
		List<Element> whileElements = command.elements();

		String seleniumCommandName = command.attributeValue("command");

		StringBundler sb = new StringBundler();

		sb.append("if (");
		sb.append(_getCommandActionsConditional(command));
		sb.append(") {");
		sb.append(processBlock(command));
		sb.append("}");

		return sb.toString();
	}

	private String _getCommandMacros(Element command) {
		StringBundler sb = new StringBundler();

		String macroDefCommand = command.attributeValue("command");
		String macroObjectName = command.attributeValue("object") + "Macros";

		sb.append(StringUtil.lowerCaseFirstLetter(macroObjectName));
		sb.append(StringPool.PERIOD);
		sb.append(macroDefCommand);
		sb.append("(");

		List<Element> macroParams = command.elements("param");

		String paramList = "";

		for (Element macroParam : macroParams) {
			paramList += _getCommandAttributeValue(macroParam) + ", ";
		}

		if (paramList.endsWith(", ")) {
			paramList = paramList.substring(0, paramList.length() - 2);
		}

		sb.append(paramList);
		sb.append(");\n");

		return sb.toString();
	}

	private String _getCommandSelenium(Element command) {
		StringBundler sb = new StringBundler();

		String seleniumCommandName = command.attributeValue("command");

		int numParams = 0;

		if (seleniumMethodParamMap.containsKey(seleniumCommandName)) {
			numParams = seleniumMethodParamMap.get(seleniumCommandName);
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

			sb.append("param2");
		}
		else if (numParams > 0) {
			sb.append("param1");
		}

		String seleniumValueName = command.attributeValue("value");

		if (!seleniumCommandName.equals("open")) {
			if (!(seleniumValueName == null)) {
				sb.append(", \"");
				sb.append(_replaceVariables(seleniumValueName));
				sb.append("\"");
			}
			else if (numParams > 1) {
				sb.append(", param2");
			}
		}

		sb.append(");\n");

		return sb.toString();
	}

	private String _getCommandWhile(Element command) throws Exception {
		List<Element> whileElements = command.elements();

		String seleniumCommandName = command.attributeValue("command");

		StringBundler sb = new StringBundler();

		sb.append("while (");
		sb.append(_getCommandActionsConditional(command));
		sb.append(") {");
		sb.append(processBlock(command));
		sb.append("}");

		return sb.toString();
	}

	private String _getCommandWindow(Element command) throws Exception {
		StringBundler sb = new StringBundler();

		String windowObjectName = command.attributeValue("object") + "Actions";

		sb.append(StringUtil.lowerCaseFirstLetter(windowObjectName));
		sb.append(StringPool.PERIOD);
		sb.append("selectWindow(\"PAGE_NAME\", \"\");\n");

		sb.append(processBlock(command));

		sb.append(StringUtil.lowerCaseFirstLetter(windowObjectName));
		sb.append(StringPool.PERIOD);
		sb.append("selectWindow(\"TOP\", \"\");\n");

		return sb.toString();
	}

	private Set<String> _getFileNameSet() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\blocks\\**\\*.actions",
				"**\\portalweb\\blocks\\**\\*.functions",
				"**\\portalweb\\blocks\\**\\*.macros",
				"**\\portalweb\\blocks\\**\\*.paths",
				"**\\portalweb\\tests\\**\\*.test"
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

	private Set<String> _getPageObjects() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\blocks\\**\\BaseActionsImpl.java",
				"**\\portalweb\\blocks\\**\\*.functions",
				"**\\portalweb\\blocks\\**\\*.paths",
				"**\\portalweb\\blocks\\**\\*.macros"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

	private Map<String, Integer> _getSeleniumMethods() throws Exception {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();

		paramMap = _putSeleniumMethods(
			paramMap, "com/liferay/portalweb/portal/util/liferayselenium/" +
				"SeleniumWrapper.java");

		paramMap = _putSeleniumMethods(
			paramMap, "com/liferay/portalweb/portal/util/liferayselenium/" +
				"LiferaySelenium.java");

		paramMap.put("isNotChecked", 1);

		return paramMap;
	}

	private boolean _isValidCommand(String command) {
		boolean isValid = true;

		Set<String> validTestCommands = new TreeSet<String>();
		validTestCommands.add("macro");
		validTestCommands.add("action");
		validTestCommands.add("window");

		Set<String> validMacroCommands = new TreeSet<String>();
		validMacroCommands.add("macro");
		validMacroCommands.add("action");
		validMacroCommands.add("while");
		validMacroCommands.add("if");
		validMacroCommands.add("window");

		Set<String> validActionCommands = new TreeSet<String>();
		validActionCommands.add("function");
		validActionCommands.add("conditional");
		validActionCommands.add("if");
		validActionCommands.add("while");
		validActionCommands.add("defaultCommand");

		Set<String> validFunctionCommands = new TreeSet<String>();
		validFunctionCommands.add("else");
		validFunctionCommands.add("function");
		validFunctionCommands.add("if");
		validFunctionCommands.add("selenium");
		validFunctionCommands.add("while");

		switch(classType) {
			case TEST:
				isValid = validTestCommands.contains(command);
				break;

			case MACROS:
				isValid = validMacroCommands.contains(command);
				break;

			case ACTIONS:
				isValid = validActionCommands.contains(command);
				break;

			case FUNCTIONS:
				isValid = validFunctionCommands.contains(command);
				break;
		}

		return isValid;
	}

	private Map<String, Integer> _putSeleniumMethods(
		Map<String, Integer> paramMap, String file) throws Exception {

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

			paramMap.put(name, numParams);
		}

		return paramMap;
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