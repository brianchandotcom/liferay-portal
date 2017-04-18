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

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

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
			"(typeof " + namespace + " == 'undefined') { var " + namespace +
				" = {}; }",
			"(typeof " + namespace + " == 'undefined') { window." + namespace +
				" = {}; }");

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