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

		String objectName = fileName.substring(x + 1, y);
		String objectXMLFilePath = fileName.substring(0, x);
		String objectFilePath = fileName.substring(0, x) + "/rc";

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
		sb.append("BaseActionsImpl;\n");

		sb.append("import com.liferay.portalweb.blocks.base.actions.rc.");
		sb.append("LiferayActions;\n");

		sb.append("import com.liferay.portalweb.portal.util.");
		sb.append("liferayselenium.LiferaySelenium;\n");

		sb.append("import com.liferay.portalweb.portal.util.");
		sb.append("liferayselenium.LiferaySeleniumHelper;\n");

		if (!(rootElement == null)) {
			sb.append(processBlocksImports(rootElement));
		}

		sb.append("public class ");
		sb.append(actionsSimpleClassName);
		sb.append(" extends BaseActionsImpl implements");
		sb.append(" LiferayActions {\n\n");

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

	private String _processActionDef(Element actionDef) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> conditionals = actionDef.elements("conditional");

		boolean firstConditional = true;

		for (Element conditional : conditionals) {
			if (firstConditional) {
				firstConditional = false;

				sb.append("if (");
			}
			else {
				sb.append("else if (");
			}

			sb.append(_processConditional(conditional));

			sb.append(") {");
			sb.append(processBlockCommands(conditional, _validCommands));
			sb.append("}");
		}

		sb.append("else {");

		String actionDefCommand = actionDef.attributeValue("command");

		sb.append("super.");
		sb.append(actionDefCommand);
		sb.append("(params[0], params[1]);\n");

		sb.append("}");

		return sb.toString();
	}

	private String _processActionDefs(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> actionDefs = rootElement.elements("actiondef");

		for (Element actionDef : actionDefs) {
			String actionDefCommand = actionDef.attributeValue("command");

			sb.append("public void ");
			sb.append(actionDefCommand);
			sb.append("(String param1, String param2) throws Exception {\n");

			sb.append("String[] params = getParams(param1, param2);\n\n");

			sb.append(processBlockObjectDeclaractions(actionDef));

			sb.append(_processActionDef(actionDef));

			sb.append("}\n");
		}

		return sb.toString();
	}

	private String _processConditional(Element conditional) throws Exception {
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

		sb.append(_combineConditionals(clauseList, "&&"));

		return sb.toString();
	}

	private Set<String> _validCommands = new TreeSet<String>();

}