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

package com.liferay.portal.osgi.web.jasper.plugins.taglib;

import org.apache.jasper.compiler.tagplugin.TagPlugin;
import org.apache.jasper.compiler.tagplugin.TagPluginContext;

/**
 * @author Preston Crary
 */
public class IfTagPlugin implements TagPlugin {

	@Override
	public void doTag(TagPluginContext tagPluginContext) {
		if (tagPluginContext.isAttributeSpecified("var")) {
			String variableName = tagPluginContext.getTemporaryVariableName();

			tagPluginContext.generateJavaSource("boolean ");
			tagPluginContext.generateJavaSource(variableName);
			tagPluginContext.generateJavaSource(" = ");
			tagPluginContext.generateAttribute("test");
			tagPluginContext.generateJavaSource(";");

			String scope = "PageContext.PAGE_SCOPE";

			if (tagPluginContext.isAttributeSpecified("scope")) {
				String scopeAttribute = tagPluginContext.getConstantAttribute(
					"scope");

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

			tagPluginContext.generateJavaSource(
				"_jspx_page_tagPluginContext.setAttribute(");
			tagPluginContext.generateAttribute("var");
			tagPluginContext.generateJavaSource(", ");
			tagPluginContext.generateJavaSource(variableName);
			tagPluginContext.generateJavaSource(", ");
			tagPluginContext.generateJavaSource(scope);
			tagPluginContext.generateJavaSource(");");

			tagPluginContext.generateJavaSource("if (");
			tagPluginContext.generateJavaSource(variableName);
		}
		else {
			tagPluginContext.generateJavaSource("if (");
			tagPluginContext.generateAttribute("test");
		}

		tagPluginContext.generateJavaSource(") {");
		tagPluginContext.generateBody();
		tagPluginContext.generateJavaSource("}");
	}

}