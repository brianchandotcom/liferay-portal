/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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