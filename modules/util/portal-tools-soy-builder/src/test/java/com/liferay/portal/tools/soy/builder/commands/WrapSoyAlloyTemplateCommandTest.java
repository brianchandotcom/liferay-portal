/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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