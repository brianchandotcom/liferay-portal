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

		_basedir = (String)cmdLineParser.getOptionValue(basedirOption);

		_fileNameSet = _initFileNameSet();

		_fileNameSetActions = _initFileNameSetActions();
		_fileNameSetFunctions = _initFileNameSetFunctions();
		_fileNameSetMacros = _initFileNameSetMacros();
		_fileNameSetPaths = _initFileNameSetPaths();
		_fileNameSetTests = _initFileNameSetTests();

		_classNameSetAvailable = _initClassNameSetAvailable();

		_functionMethodParamListMap = _initFunctionMethodParamListMap();
		_functionMethodReturnTypeMap = _initFunctionMethodReturnTypeMap();
		_seleniumMethodParamMap = _initSeleniumMethodParamMap();
	}

	public String getBasedir() throws Exception {
		return _basedir;
	}

	public Set<String> getClassNameSetAvailable() throws Exception {
		return _classNameSetAvailable;
	}

	public Set<String> getFileNameSet() throws Exception {
		return _fileNameSet;
	}

	public Set<String> getFileNameSetActions() throws Exception {
		return _fileNameSetActions;
	}

	public Set<String> getFileNameSetFunctions() throws Exception {
		return _fileNameSetFunctions;
	}

	public Set<String> getFileNameSetMacros() throws Exception {
		return _fileNameSetMacros;
	}

	public Set<String> getFileNameSetPaths() throws Exception {
		return _fileNameSetPaths;
	}

	public Set<String> getFileNameSetTests() throws Exception {
		return _fileNameSetTests;
	}

	public Map<String, List<String>> getFunctionMethodParamListMap()
		throws Exception {

		return _functionMethodParamListMap;
	}

	public Map<String, String> getFunctionMethodReturnTypeMap()
		throws Exception {

		return _functionMethodReturnTypeMap;
	}

	public Map<String, Integer> getSeleniumMethodParamMap() throws Exception {
		return _seleniumMethodParamMap;
	}

	protected Set<String> addClassName(
		Set<String> classNameSet, String simpleClassName) throws Exception {

		for (String className : _classNameSetAvailable) {
			if (className.endsWith("." + simpleClassName)) {
				classNameSet.add(className);
			}
		}

		return classNameSet;
	}

	protected Set<String> getChildClassNameSet(Element element)
		throws Exception {

		Set<String> classNameSet = new TreeSet<String>();

		Set<String> childSimpleClassNameSet = getChildSimpleClassNameSet(
			element);

		for (String childSimpleClassName : childSimpleClassNameSet) {
			classNameSet = addClassName(classNameSet, childSimpleClassName);
		}

		return classNameSet;
	}

	protected Set<String> getChildSimpleClassNameSet(Element element)
		throws Exception {

		return _getChildSimpleClassNameSet(element, new TreeSet<String>());
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

	protected Element getRootElement(String fileName) throws Exception {
		String content = getNormalizedContent(fileName);

		Document document = SAXReaderUtil.read(content, true);

		return document.getRootElement();
	}

	protected Element getRootElementByElement(Element element)
		throws Exception {

		if (element.isRootElement()) {
			return element;
		}
		else {
			return getRootElementByElement(element.getParent());
		}
	}

	protected void isValidName(String fileName) throws Exception {
		Element rootElement = getRootElement(fileName);

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String objectName = "";
		String objectFileName = fileName.substring(x + 1, y);

		if (fileName.endsWith(".test")) {
			objectName = rootElement.attributeValue("name");
		}
		else {
			objectName = rootElement.attributeValue("object");
		}

		if ((objectName == null) || !objectName.equals(objectFileName)) {
			System.out.println(fileName + " has an invalid name");
		}
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

		Set<String> simpleClassNameSet = getChildSimpleClassNameSet(element);

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

		Set<String> classNameImportSet = getChildClassNameSet(element);

		for (String className : classNameImportSet) {
			sb.append("import ");
			sb.append(className);
			sb.append(";\n");
		}

		return sb.toString();
	}

	protected String readFile(String fileName) throws Exception {
		return FileUtil.read(_basedir + "/" + fileName);
	}

	protected void writeFile(String fileName, String content, boolean format)
		throws Exception {

		File file = new File(_basedir + "-generate/" + fileName);

		if (format) {
			ServiceBuilder.writeFile(file, content);
		}
		else {
			System.out.println("Writing " + file);

			FileUtil.write(file, content);
		}
	}

	private Set<String> _getChildSimpleClassNameSet(
		Element element, Set<String> simpleClassNameSet) {

		List<Element> children = element.elements();

		if (children.isEmpty()) {
			return simpleClassNameSet;
		}
		else {
			for (Element child : children) {
				String childName = child.getName();
				String childObjectName = child.attributeValue("object");

				if (!(childObjectName == null)) {
					if (childName.equals("action") || childName.equals("if") ||
						childName.equals("while")) {

						simpleClassNameSet.add(childObjectName + "Actions");
					}
					else if (childName.equals("function")) {
						simpleClassNameSet.add(childObjectName + "Functions");
					}
					else if (childName.equals("macro")) {
						simpleClassNameSet.add(childObjectName + "Macros");
					}
				}

				_getChildSimpleClassNameSet(child, simpleClassNameSet);
			}
		}

		return simpleClassNameSet;
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

	private String _getFileNameByElement(Element command) throws Exception {
		Element rootElement = getRootElementByElement(command);

		String rootElementName = rootElement.getName();
		String objectName = "";

		if (rootElementName.equals("test")) {
			objectName = rootElement.attributeValue("name");
		}
		else {
			objectName = rootElement.attributeValue("object");
		}

		String simpleFileName = objectName + "." + rootElementName;

		for (String fileName : _fileNameSet) {
			if (fileName.endsWith("/" + simpleFileName)) {
				return fileName;
			}
		}

		return "";
	}

	private Set<String> _initClassNameSetAvailable() throws Exception {
		Set<String> classNameSet = new TreeSet<String>();

		for (String fileName : _fileNameSet) {
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

	private Set<String> _initFileNameSet() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(_basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\**\\*.actions",
				"**\\portalweb\\**\\*.functions", "**\\portalweb\\**\\*.macros",
				"**\\portalweb\\**\\*.paths", "**\\portalweb\\**\\*.test"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = _normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

	private Set<String> _initFileNameSetActions() throws Exception {
		return _initFileNameSetType(".actions");
	}

	private Set<String> _initFileNameSetFunctions() throws Exception {
		return _initFileNameSetType(".functions");
	}

	private Set<String> _initFileNameSetMacros() throws Exception {
		return _initFileNameSetType(".macros");
	}

	private Set<String> _initFileNameSetPaths() throws Exception {
		return _initFileNameSetType(".paths");
	}

	private Set<String> _initFileNameSetTests() throws Exception {
		return _initFileNameSetType(".test");
	}

	private Set<String> _initFileNameSetType(String suffix) throws Exception {
		Set<String> fileNameTypeSet = new TreeSet<String>();

		for (String fileName : _fileNameSet) {
			if (fileName.endsWith(suffix)) {
				fileNameTypeSet.add(fileName);
			}
		}

		return fileNameTypeSet;
	}

	private Map<String, List<String>> _initFunctionMethodParamListMap()
		throws Exception {

		Map<String, List<String>> hashMap = new HashMap<String, List<String>>();

		for (String fileName : _fileNameSetFunctions) {
			Element functions = getRootElement(fileName);

			String functionsObject = functions.attributeValue("object");
			String functionsParams = functions.attributeValue("params");

			List<String> arrayList = new ArrayList<String>();

			if (!(functionsParams == null)) {
				int functionsParamsInteger = GetterUtil.getInteger(
					functionsParams);

				for (int i = 1; i <= functionsParamsInteger; i++) {
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

	private Map<String, String> _initFunctionMethodReturnTypeMap()
		throws Exception {

		Map<String, String> hashMap = new HashMap<String, String>();

		for (String fileName : _fileNameSetFunctions) {
			Element functions = getRootElement(fileName);

			String functionsObject = functions.attributeValue("object");
			String functionsReturn = functions.attributeValue("return");

			if (functionsReturn == null) {
				hashMap.put(functionsObject, "void");
			}
			else {
				hashMap.put(functionsObject, functionsReturn);
			}
		}

		return hashMap;
	}

	private Map<String, Integer> _initSeleniumMethodParamMap()
		throws Exception {

		Map<String, Integer> hashMap = new HashMap<String, Integer>();

		hashMap = _putSeleniumMethodParamMapFile(
			hashMap, "com/liferay/portalweb/portal/util/liferayselenium/" +
				"SeleniumWrapper.java");

		hashMap = _putSeleniumMethodParamMapFile(
			hashMap, "com/liferay/portalweb/portal/util/liferayselenium/" +
				"LiferaySelenium.java");

		hashMap.put("open", 1);
		hashMap.put("isNotChecked", 1);
		hashMap.put("isNotText", 1);
		hashMap.put("isNotVisible", 1);

		return hashMap;
	}

	private String _normalizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);
	}

	private String _processCommand(Element command, Set<String> validCommands)
		throws Exception {

		String commandName = command.getName();
		String fileName = _getFileNameByElement(command);

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

	private Map<String, Integer> _putSeleniumMethodParamMapFile(
		Map<String, Integer> hashMap, String file) throws Exception {

		String content = getNormalizedContent(file);

		Pattern pattern = Pattern.compile(
			"public (boolean|String|void) [A-Za-z0-9_]*\\(.*?\\)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String methodDeclaraction = matcher.group();

			int x = methodDeclaraction.indexOf("(");
			int y = methodDeclaraction.indexOf(")");

			String name = null;

			if (methodDeclaraction.startsWith("public boolean")) {
				name = methodDeclaraction.substring(15, x);
			}
			else if (methodDeclaraction.startsWith("public String")) {
				name = methodDeclaraction.substring(14, x);
			}
			else if (methodDeclaraction.startsWith("public void")) {
				name = methodDeclaraction.substring(12, x);
			}

			String params = methodDeclaraction.substring(x + 1, y);

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
	private Set<String> _fileNameSetActions;
	private Set<String> _fileNameSetFunctions;
	private Set<String> _fileNameSetMacros;
	private Set<String> _fileNameSetPaths;
	private Set<String> _fileNameSetTests;
	private Map<String, List<String>> _functionMethodParamListMap;
	private Map<String, String> _functionMethodReturnTypeMap;
	private Map<String, Integer> _seleniumMethodParamMap;

}