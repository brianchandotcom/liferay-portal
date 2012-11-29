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

package com.liferay.portal.tools;

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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumXMLToJavaBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new ActionsXMLToJavaBuilder(args);
		new MacrosXMLToJavaBuilder(args);
		new PathsXMLToJavaBuilder(args);
		new TestXMLToJavaBuilder(args);
		new TestPlanBuilder(args);
		new UnitsXMLToJavaBuilder(args);
	}

	public SeleniumXMLToJavaBuilder(String[] args) throws Exception {
		CmdLineParser cmdLineParser = new CmdLineParser();

		CmdLineParser.Option basedirOption = cmdLineParser.addStringOption(
			"basedir");

		cmdLineParser.parse(args);

		basedir = (String)cmdLineParser.getOptionValue(basedirOption);
		pageObjectSet = getPageObjects();
	}

	protected String getActionDefBlock(Element runBlock, String actionDefName) {
		StringBundler sb = new StringBundler();

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

			sb.append(getCommandConditional(command, actionDefName));
		}

		sb.append("else {");
		sb.append(getCommandDefaultCommand(actionDefName));
		sb.append("}");

		return sb.toString();
	}

	protected String getCommands(Element runBlock) {
		return getCommands(runBlock, "");
	}

	protected String getCommands(Element runBlock, String actionDefName) {
		StringBundler sb = new StringBundler();

		List<Element> commands = runBlock.elements();

		String runBlockName = runBlock.getName();

		if (runBlockName.equals("actiondef")) {
			actionDefName = runBlock.attributeValue("name");

			sb.append(getActionDefBlock(runBlock, actionDefName));
		}
		else {
			for (Element command : commands) {
				String commandName = command.getName();

				if (commandName.equals("action")) {
					sb.append(getCommandActions(command));
				}
				else if (commandName.equals("conditional")) {
					sb.append(getCommandConditional(command, actionDefName));
				}
				else if (commandName.equals("defaultcommand")) {
					sb.append(getCommandDefaultCommand(actionDefName));
				}
				else if (commandName.equals("if")) {
					sb.append(getCommandIf(command));
				}
				else if (commandName.equals("macro")) {
					sb.append(getCommandMacros(command));
				}
				else if (commandName.equals("selenium")) {
					sb.append(getCommandSelenium(command));
				}
				else if (commandName.equals("units")) {
					sb.append(getCommandUnits(command, actionDefName));
				}
				else if (commandName.equals("while")) {
					sb.append(getCommandWhile(command));
				}
			}
		}

		return sb.toString();
	}

	protected Set<String> getImports(
		Set<String> objectPackageSet, Element runBlock) throws Exception {

		return getImports(objectPackageSet, runBlock, "");
	}

	protected Set<String> getImports(
		Set<String> objectPackageSet, Element runBlock, String actionDefName)
			throws Exception {

		List<Element> commands = runBlock.elements();

		String runBlockName = runBlock.getName();

		if (runBlockName.equals("actiondef")) {
			actionDefName = runBlock.attributeValue("name");
		}

		for (Element command : commands) {
			String commandName = command.getName();
			String objectName = command.attributeValue("name");

			if (commandName.equals("action")) {
				objectPackageSet = getImportActions(objectPackageSet, command);
			}
			else if (commandName.equals("conditional")) {
				objectPackageSet = getImportConditional(
					objectPackageSet, command, actionDefName);
			}
			else if (commandName.equals("if")) {
				objectPackageSet = getImportIf(objectPackageSet, command);
			}
			else if (commandName.equals("macro")) {
				objectPackageSet = getImportMacros(objectPackageSet, command);
			}
			else if (commandName.equals("units")) {
				objectPackageSet = getImportUnits(
					objectPackageSet, actionDefName);
			}
			else if (commandName.equals("while")) {
				objectPackageSet = getImportWhile(objectPackageSet, command);
			}
		}

		return objectPackageSet;
	}

	protected String getImportStatements(Element rootElement) throws Exception {

		StringBundler sb = new StringBundler();

		List<Element> runBlocks = rootElement.elements();

		Set<String> objectPackageSet = new TreeSet<String>();

		for (Element runBlock : runBlocks) {
			objectPackageSet = getImports(objectPackageSet, runBlock);
		}

		for (String objectPackage : objectPackageSet) {
			sb.append("import ");
			sb.append(objectPackage);
			sb.append(";\n");
		}

		return sb.toString();
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

	protected String getObjectDeclarations(Element runBlock) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> commands = runBlock.elements();

		Set<String> objectNameSet = new TreeSet<String>();

		String runBlockName = runBlock.getName();

		if (runBlockName.equals("setup") || runBlockName.equals("teardown")) {
			objectNameSet.add("PortletSignInUserMacros");
		}

		objectNameSet = getObjectNames(objectNameSet, runBlock);

		for (String objectName : objectNameSet) {
			sb.append(objectName);
			sb.append(" ");
			sb.append(lowerCaseFirstLetter(objectName));
			sb.append(" = new ");
			sb.append(objectName);
			sb.append("(selenium);\n");
		}

		sb.append("\n");

		return sb.toString();
	}

	protected Set<String> getObjectNames(
		Set<String> objectNameSet, Element runBlock) throws Exception {

		return getObjectNames(objectNameSet, runBlock, null);
	}

	protected Set<String> getObjectNames(
		Set<String> objectNameSet, Element runBlock, String actionDefName)
			throws Exception {

		List<Element> commands = runBlock.elements();

		String runBlockName = runBlock.getName();

		if (runBlockName.equals("actiondef")) {
			actionDefName = runBlock.attributeValue("name");
		}

		for (Element command : commands) {
			String commandName = command.getName();
			String objectName = command.attributeValue("name");

			if (commandName.equals("action")) {
				objectNameSet = getObjectNameActions(objectNameSet, command);
			}
			else if (commandName.equals("conditional")) {
				objectNameSet = getObjectNameConditional(
					objectNameSet, command, actionDefName);
			}
			else if (commandName.equals("if")) {
				objectNameSet = getObjectNameIf(objectNameSet, command);
			}
			else if (commandName.equals("macro")) {
				objectNameSet = getObjectNameMacros(objectNameSet, command);
			}
			else if (commandName.equals("units")) {
				objectNameSet = getObjectNameUnits(
					objectNameSet, actionDefName);
			}
			else if (commandName.equals("while")) {
				objectNameSet = getObjectNameWhile(objectNameSet, command);
			}
		}

		return objectNameSet;
	}

	protected Element getRootElement(String fileName) throws Exception {
		String content = getNormalizedContent(fileName);

		Document document = SAXReaderUtil.read(content, true);

		return document.getRootElement();
	}

	protected String normalizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);
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

	protected Set<String> pageObjectSet;

	private String combineConditionals(
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

	private String getCommandActions(Element command) {
		StringBundler sb = new StringBundler();

		String actionDefName = command.attributeValue("command");
		String actionObjectName = command.attributeValue("name") + "Actions";

		sb.append(lowerCaseFirstLetter(actionObjectName));
		sb.append(StringPool.PERIOD);
		sb.append(actionDefName);

		String actionLocator = command.attributeValue("locator");
		String actionPath = command.attributeValue("path");

		if (!(actionLocator == null)) {
			sb.append("(");
			sb.append(replaceVariables("\"" + actionLocator + "\""));
		}
		else if (!(actionPath == null)) {
			sb.append("(");
			sb.append(replaceVariables("\"" + actionPath + "\""));
		}
		else {
			sb.append("(\"\"");
		}

		String actionValue = command.attributeValue("value");

		if (!(actionValue == null)) {
			sb.append(", ");
			sb.append(getCommandAttributeValue(command));
		}
		else {
			sb.append(", null");
		}

		sb.append(");\n");

		return sb.toString();
	}

	private String getCommandActionsConditional(Element conditional) {
		StringBundler sb = new StringBundler();

		String conditionalName = conditional.attributeValue("name");
		String conditionalCommand = conditional.attributeValue("command");

		if (conditionalCommand.contains("Not")) {
			sb.append("!");

			conditionalCommand = StringUtil.replace(
				conditionalCommand, "Not", "");
		}

		if (conditionalName == null) {
			String seleniumCommand = conditionalCommand;
			String seleniumTarget = conditional.attributeValue("target");
			String seleniumValue = conditional.attributeValue("value");

			sb.append("selenium.");
			sb.append(seleniumCommand);
			sb.append("(");

			if (!(seleniumTarget == null)) {
				sb.append("\"");
				sb.append(seleniumTarget);
				sb.append("\"");
			}

			if (!(seleniumValue == null)) {
				sb.append(", \"");
				sb.append(seleniumValue);
				sb.append("\"");
			}

			sb.append(")");
		}
		else {
			String actionCommand = conditionalCommand;
			String actionLocator = conditional.attributeValue("locator");
			String actionPath = conditional.attributeValue("path");
			String actionValue = conditional.attributeValue("value");

			String actionObjectName = conditionalName + "Actions";

			sb.append(lowerCaseFirstLetter(actionObjectName));
			sb.append(StringPool.PERIOD);
			sb.append(actionCommand);

			if (!(actionLocator == null)) {
				sb.append("(");
				sb.append(replaceVariables("\"" + actionLocator + "\""));
			}
			else if (!(actionPath == null)) {
				sb.append("(");
				sb.append(replaceVariables("\"" + actionPath + "\""));
			}
			else {
				sb.append("(\"\"");
			}

			if (!(actionValue == null)) {
				sb.append(", ");
				sb.append(getCommandAttributeValue(conditional));
			}
			else {
				sb.append(", \"\"");
			}

			sb.append(")");
		}

		return sb.toString();
	}

	private String getCommandAttributeValue(Element command) {
		StringBundler sb = new StringBundler();

		String attributeValue = command.attributeValue("value");

		if (!(attributeValue == null)) {
			String firstLetter = command.attributeValue("value-first-letter");

			if (!(firstLetter == null) && firstLetter.equals("true")) {
				sb.append("LiferaySeleniumHelper.firstLetter(");
				sb.append(replaceVariables("\"" + attributeValue + "\""));
				sb.append(")");
			}
			else {
				sb.append(replaceVariables("\"" + attributeValue + "\""));
			}
		}

		return sb.toString();
	}

	private String getCommandConditional(
		Element conditional, String actionDefName) {

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
			sbConditional.append(combineConditionals(elementList, "||"));
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
		sb.append(combineConditionals(clauseList, "&&"));
		sb.append(") {");
		sb.append(getCommands(conditional, actionDefName));
		sb.append("}");

		return sb.toString();
	}

	private String getCommandDefaultCommand(String actionDefName) {
		StringBundler sb = new StringBundler();

		sb.append("super." + actionDefName);
		sb.append("(params[0], params[1]);\n");

		return sb.toString();
	}

	private String getCommandIf(Element command) {
		List<Element> whileElements = command.elements();

		String seleniumCommandName = command.attributeValue("command");

		StringBundler sb = new StringBundler();

		sb.append("if (");
		sb.append(getCommandActionsConditional(command));
		sb.append(") {");
		sb.append(getCommands(command));
		sb.append("}");

		return sb.toString();
	}

	private String getCommandMacros(Element command) {
		StringBundler sb = new StringBundler();

		String macroDefName = command.attributeValue("command");
		String macroObjectName = command.attributeValue("name") + "Macros";

		sb.append(lowerCaseFirstLetter(macroObjectName));
		sb.append(StringPool.PERIOD);
		sb.append(macroDefName);
		sb.append("(");

		List<Element> macroParams = command.elements("param");

		String paramList = "";

		for (Element macroParam : macroParams) {
			paramList += getCommandAttributeValue(macroParam) + ", ";
		}

		if (paramList.endsWith(", ")) {
			paramList = paramList.substring(0, paramList.length() - 2);
		}

		sb.append(paramList);

		sb.append(");\n");

		return sb.toString();
	}

	private String getCommandSelenium(Element command) {
		StringBundler sb = new StringBundler();

		String seleniumCommandName = command.attributeValue("command");

		sb.append("selenium.");
		sb.append(seleniumCommandName);
		sb.append("(");

		String seleniumTarget = command.attributeValue("target");

		if (!(seleniumTarget == null)) {
			sb.append(replaceVariables("\"" + seleniumTarget + "\""));
		}

		String seleniumValue = command.attributeValue("value");

		if (!(seleniumValue == null)) {
			sb.append(", ");
			sb.append(getCommandAttributeValue(command));
		}

		sb.append(");\n");

		return sb.toString();
	}

	private String getCommandUnits(Element command, String actionDefName) {
		StringBundler sb = new StringBundler();

		String utilCommandName = command.attributeValue("command");

		sb.append(actionDefName);
		sb.append("Units.");
		sb.append(utilCommandName);
		sb.append("(params[0], params[1]);\n");

		return sb.toString();
	}

	private String getCommandWhile(Element command) {
		List<Element> whileElements = command.elements();

		String seleniumCommandName = command.attributeValue("command");

		StringBundler sb = new StringBundler();

		sb.append("while (");
		sb.append(getCommandActionsConditional(command));
		sb.append(") {");
		sb.append(getCommands(command));
		sb.append("}");

		return sb.toString();
	}

	private Set<String> getImportActions(
		Set<String> objectPackageSet, Element command) throws Exception {

		String actionObjectName = command.attributeValue("name");

		for (String pageObject : pageObjectSet) {
			if (pageObject.endsWith("/" + actionObjectName + ".paths")) {
				int x = pageObject.length() - 6;

				String objectPackagePath =
					StringUtil.replace(
						pageObject.substring(0, x), StringPool.SLASH,
						StringPool.PERIOD);

				objectPackagePath = StringUtil.replace(
					objectPackagePath, ".paths.", ".actions.");
				objectPackagePath = StringUtil.replace(
					objectPackagePath, actionObjectName,
					actionObjectName + "Actions");

				objectPackageSet.add(objectPackagePath);
			}
		}

		return objectPackageSet;
	}

	private Set<String> getImportConditional(
		Set<String> objectPackageSet, Element conditional, String actionDefName)
			throws Exception {

		objectPackageSet = getImports(
			objectPackageSet, conditional, actionDefName);

		return objectPackageSet;
	}

	private Set<String> getImportIf(
		Set<String> objectPackageSet, Element command) throws Exception {

		String actionObjectName = command.attributeValue("name");

		for (String pageObject : pageObjectSet) {
			if (pageObject.endsWith("/" + actionObjectName + ".paths")) {
				int x = pageObject.length() - 6;

				String objectPackagePath =
					StringUtil.replace(
						pageObject.substring(0, x), StringPool.SLASH,
						StringPool.PERIOD);

				objectPackagePath = StringUtil.replace(
					objectPackagePath, ".paths.", ".actions.");
				objectPackagePath = StringUtil.replace(
					objectPackagePath, actionObjectName,
					actionObjectName + "Actions");

				objectPackageSet.add(objectPackagePath);
			}
		}

		objectPackageSet = getImports(objectPackageSet, command);

		return objectPackageSet;
	}

	private Set<String> getImportMacros(
		Set<String> objectPackageSet, Element command) throws Exception {

		String macroObjectName = command.attributeValue("name");

		for (String pageObject : pageObjectSet) {
			if (pageObject.endsWith("/" + macroObjectName + ".macros")) {
				int x = pageObject.length() - 7;

				String objectPackagePath =
					StringUtil.replace(
						pageObject.substring(0, x), StringPool.SLASH,
						StringPool.PERIOD);

				objectPackagePath = StringUtil.replace(
					objectPackagePath, macroObjectName,
					macroObjectName + "Macros");

				objectPackageSet.add(objectPackagePath);
			}
		}

		return objectPackageSet;
	}

	private Set<String> getImportUnits(
		Set<String> objectPackageSet, String actionDefName) throws Exception {

		String unitsObjectName = StringUtil.upperCaseFirstLetter(actionDefName);

		for (String pageObject : pageObjectSet) {
			if (pageObject.endsWith("/" + unitsObjectName + ".units")) {
				int x = pageObject.length() - 6;

				String objectPackagePath =
					StringUtil.replace(
						pageObject.substring(0, x), StringPool.SLASH,
						StringPool.PERIOD);

				objectPackagePath = StringUtil.replace(
					objectPackagePath, unitsObjectName,
					unitsObjectName + "Units");

				objectPackageSet.add(objectPackagePath);
			}
		}

		return objectPackageSet;
	}

	private Set<String> getImportWhile(
		Set<String> objectPackageSet, Element command) throws Exception {

		String actionObjectName = command.attributeValue("name");

		for (String pageObject : pageObjectSet) {
			if (pageObject.endsWith("/" + actionObjectName + ".paths")) {
				int x = pageObject.length() - 6;

				String objectPackagePath =
					StringUtil.replace(
						pageObject.substring(0, x), StringPool.SLASH,
						StringPool.PERIOD);

				objectPackagePath = StringUtil.replace(
					objectPackagePath, ".paths.", ".actions.");
				objectPackagePath = StringUtil.replace(
					objectPackagePath, actionObjectName,
					actionObjectName + "Actions");

				objectPackageSet.add(objectPackagePath);
			}
		}

		objectPackageSet = getImports(objectPackageSet, command);

		return objectPackageSet;
	}

	private Set<String> getObjectNameActions(
		Set<String> objectNameSet, Element command) throws Exception {

		String actionObjectName = command.attributeValue("name");

		if (!(actionObjectName == null)) {
			objectNameSet.add(actionObjectName + "Actions");
		}

		return objectNameSet;
	}

	private Set<String> getObjectNameConditional(
		Set<String> objectNameSet, Element conditional, String actionDefName)
			throws Exception {

		objectNameSet = getObjectNames(
			objectNameSet, conditional, actionDefName);

		return objectNameSet;
	}

	private Set<String> getObjectNameIf(
		Set<String> objectNameSet, Element command) throws Exception {

		String actionObjectName = command.attributeValue("name");

		if (!(actionObjectName == null)) {
			objectNameSet.add(actionObjectName + "Actions");
		}

		objectNameSet = getObjectNames(objectNameSet, command);

		return objectNameSet;
	}

	private Set<String> getObjectNameMacros(
		Set<String> objectNameSet, Element command) throws Exception {

		String macroObjectName = command.attributeValue("name");

		if (!(macroObjectName == null)) {
			objectNameSet.add(macroObjectName + "Macros");
		}

		return objectNameSet;
	}

	private Set<String> getObjectNameUnits(
		Set<String> objectNameSet, String actionDefName) throws Exception {

		if (!(actionDefName == null)) {
			objectNameSet.add(
				StringUtil.upperCaseFirstLetter(actionDefName) + "Units");
		}

		return objectNameSet;
	}

	private Set<String> getObjectNameWhile(
		Set<String> objectNameSet, Element command) throws Exception {

		String actionObjectName = command.attributeValue("name");

		if (!(actionObjectName == null)) {
			objectNameSet.add(actionObjectName + "Actions");
		}

		objectNameSet = getObjectNames(objectNameSet, command);

		return objectNameSet;
	}

	private Set<String> getPageObjects() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\blocks\\**\\BaseActionsImpl.java",
				"**\\portalweb\\blocks\\**\\*.paths",
				"**\\portalweb\\blocks\\**\\*.macros",
				"**\\portalweb\\blocks\\**\\*.units"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

	private String lowerCaseFirstLetter(String s) {
		char[] chars = s.toCharArray();

		if ((chars[0] >= 65) && (chars[0] <= 90)) {
			chars[0] = (char)(chars[0] + 32);
		}

		return new String(chars);
	}

	private String replaceVariables(String text) {
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