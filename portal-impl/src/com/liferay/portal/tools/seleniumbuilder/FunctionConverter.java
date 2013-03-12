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
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class FunctionConverter extends BaseConverter {

	public FunctionConverter(SeleniumBuilderContext seleniumBuilderContext) {
		super(seleniumBuilderContext);
	}

	public void convert(String functionName) throws Exception {
		Map<String, Object> context = getContext();

		context.put("functionConverter", this);
		context.put("functionName", functionName);

		String content = processTemplate("function.ftl", context);

		seleniumBuilderFileUtil.writeFile(
			seleniumBuilderContext.getFunctionJavaFileName(functionName),
			content, true);
	}

	public String convertBlockElement(Element blockElement) {
		StringBundler sb = new StringBundler();

		List<Element> commandElements = blockElement.elements();

		for (Element commandElement : commandElements) {
			String commandType = commandElement.getName();

			if (commandType.equals("execute")) {
				String function = commandElement.attributeValue("function");
				String selenium = commandElement.attributeValue("selenium");

				if (!(function == null)) {
					int x = function.lastIndexOf(StringPool.POUND);

					if (!(x == -1)) {
						String functionCommand = function.substring(x + 1);
						String functionName = function.substring(0, x);

						sb.append(
							seleniumBuilderFileUtil.getVariableName(
								functionName));
						sb.append("Function.");
						sb.append(functionCommand);
						sb.append("(");

						int count =
							seleniumBuilderContext.getFunctionTargetCount(
								functionName);

						for (int i = 1; i <= count; i++) {
							sb.append("target" + i);
							sb.append(", value" + i);

							if (i < count) {
								sb.append(", ");
							}
						}

						sb.append(");");
					}
				}
				else if (!(selenium == null)) {
					if (selenium.startsWith("is")) {
						sb.append("return ");
					}

					sb.append(_convertSeleniumElement(commandElement));

					sb.append(";\n");
				}
			}
			else if (commandType.equals("if")) {
				Element conditionElement = commandElement.element("condition");

				sb.append("if (");
				sb.append(_convertSeleniumElement(conditionElement));
				sb.append(") ");

				Element thenElement = commandElement.element("then");

				sb.append("{");
				sb.append(convertBlockElement(thenElement));
				sb.append("}");

				Element elseElement = commandElement.element("else");

				if (!(elseElement == null)) {
					sb.append("else {");
					sb.append(convertBlockElement(elseElement));
					sb.append("}");
				}
			}
		}

		return sb.toString();
	}

	private String _convertSeleniumElement(Element seleniumElement) {
		StringBundler sb = new StringBundler();

		String selenium = seleniumElement.attributeValue("selenium");

		sb.append("liferaySelenium.");
		sb.append(selenium);
		sb.append("(");

		int count = seleniumBuilderContext.getSeleniumParameterCount(selenium);

		if (count == 1) {
			sb.append("target1");
		}
		else if (count == 2) {
			sb.append("target1, value1");
		}

		sb.append(")");

		return sb.toString();
	}

}