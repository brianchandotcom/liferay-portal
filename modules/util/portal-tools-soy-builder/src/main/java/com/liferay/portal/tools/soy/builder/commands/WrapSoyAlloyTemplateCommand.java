/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.soy.builder.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.liferay.petra.string.StringBundler;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Wrap Soy templates into AlloyUI modules.",
	commandNames = "wrap-alloy-template"
)
public class WrapSoyAlloyTemplateCommand extends BaseSoyJsCommand {

	@Override
	public void execute(Path path) throws IOException {
		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		String namespace = getNamespace();

		content = content.replace(
			StringBundler.concat(
				"(typeof ", namespace, " == 'undefined') { var ", namespace,
				" = {}; }"),
			StringBundler.concat(
				"(typeof ", namespace, " == 'undefined') { window.", namespace,
				" = {}; }"));

		content = getWrapperHeader() + content + getWrapperFooter();

		Files.write(path, content.getBytes(StandardCharsets.UTF_8));
	}

	public String getModuleName() {
		return _moduleName;
	}

	public String getNamespace() {
		return _namespace;
	}

	public void setModuleName(String moduleName) {
		_moduleName = moduleName;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	protected String getWrapperFooter() {
		return "}, '', {requires: ['soyutils']});";
	}

	protected String getWrapperHeader() {
		return "AUI.add('" + getModuleName() + "', function(A) {";
	}

	@Parameter(
		description = "The AlloyUI module name.", names = "--module-name",
		required = true
	)
	private String _moduleName;

	@Parameter(
		description = "The Soy template namespace.", names = "--namespace",
		required = true
	)
	private String _namespace;

}