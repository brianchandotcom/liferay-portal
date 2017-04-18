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

package com.liferay.portal.tools.soy.builder.commands;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class WrapSoyAlloyTemplateCommandTest extends BaseSoyCommandTestCase {

	@Override
	protected String getTestDirName() {
		return "com/liferay/portal/tools/soy/builder/commands/dependencies" +
			"/wrap_soy_alloy_template/";
	}

	@Override
	protected String[] getTestFileNames() {
		return new String[] {"checkbox.soy.js"};
	}

	@Override
	protected void testSoy(File dir) throws Exception {
		wrapAlloyTemplate(
			dir, "liferay-ddm-form-field-checkbox-template", "ddm");
	}

	protected void wrapAlloyTemplate(
			File dir, String moduleName, String namespace)
		throws Exception {

		WrapSoyAlloyTemplateCommand wrapSoyAlloyTemplateCommand =
			new WrapSoyAlloyTemplateCommand();

		wrapSoyAlloyTemplateCommand.setDir(dir);
		wrapSoyAlloyTemplateCommand.setModuleName(moduleName);
		wrapSoyAlloyTemplateCommand.setNamespace(namespace);

		wrapSoyAlloyTemplateCommand.execute();
	}

}