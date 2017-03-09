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

package com.liferay.portal.osgi.web.jasper.plugins;

import org.apache.jasper.compiler.tagplugin.TagPlugin;
import org.apache.jasper.compiler.tagplugin.TagPluginContext;

/**
 * @author Preston Crary
 */
public class IfTagPlugin implements TagPlugin {

	@Override
	public void doTag(TagPluginContext context) {
		if (context.isAttributeSpecified("var")) {
			String variableName = context.getTemporaryVariableName();

			context.generateJavaSource("boolean ");
			context.generateJavaSource(variableName);
			context.generateJavaSource(" = ");
			context.generateAttribute("test");
			context.generateJavaSource(";");

			String scope = "PageContext.PAGE_SCOPE";

			if (context.isAttributeSpecified("scope")) {
				String scopeAttribute = context.getConstantAttribute("scope");

				if (scopeAttribute != null) {
					scopeAttribute = scopeAttribute.toLowerCase();

					if (scopeAttribute.equals("application")) {
						scope = "PageContext.APPLICATION_SCOPE";
					}
					else if (scopeAttribute.equals("request")) {
						scope = "PageContext.REQUEST_SCOPE";
					}
					else if (scopeAttribute.equals("session")) {
						scope = "PageContext.SESSION_SCOPE";
					}
				}
			}

			context.generateJavaSource("_jspx_page_context.setAttribute(");
			context.generateAttribute("var");
			context.generateJavaSource(", ");
			context.generateJavaSource(variableName);
			context.generateJavaSource(", ");
			context.generateJavaSource(scope);
			context.generateJavaSource(");");

			context.generateJavaSource("if (");
			context.generateJavaSource(variableName);
		}
		else {
			context.generateJavaSource("if (");
			context.generateAttribute("test");
		}

		context.generateJavaSource(") {");

		context.generateBody();

		context.generateJavaSource("}");
	}

}