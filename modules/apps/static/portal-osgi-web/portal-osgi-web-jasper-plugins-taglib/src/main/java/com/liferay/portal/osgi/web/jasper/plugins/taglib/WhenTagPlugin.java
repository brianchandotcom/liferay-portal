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
 * @see    ChooseTagPlugin
 * @see    OtherwiseTagPlugin
 */
public class WhenTagPlugin implements TagPlugin {

	@Override
	public void doTag(TagPluginContext tagPluginContext) {
		TagPluginContext parentTagPluginContext =
			tagPluginContext.getParentContext();

		if (parentTagPluginContext == null) {
			tagPluginContext.dontUseTagPlugin();

			return;
		}

		Object first = parentTagPluginContext.getPluginAttribute("first");

		if (first == null) {
			tagPluginContext.generateJavaSource("if (");

			parentTagPluginContext.setPluginAttribute("first", "false");
		}
		else {
			tagPluginContext.generateJavaSource("}\nelse if (");
		}

		tagPluginContext.generateAttribute("test");
		tagPluginContext.generateJavaSource(") {");
		tagPluginContext.generateBody();
	}

}