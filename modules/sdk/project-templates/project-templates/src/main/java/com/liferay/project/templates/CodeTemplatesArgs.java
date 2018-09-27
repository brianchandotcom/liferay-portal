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

package com.liferay.project.templates;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gregory Amerson
 * @author Simon Jiang
 */
public class CodeTemplatesArgs {

	public Map<String, String> getAddtionalParameters() {
		return _additionalParameters;
	}

	public List<File> getArchetypesDirs() {
		return _archetypesDirs;
	}

	public String getAuthor() {
		return _author;
	}

	public String getClassName() {
		return _className;
	}

	public File getDestinationDir() {
		return _destinationDir;
	}

	public String getPackageName() {
		return _packageName;
	}

	public String getTemplate() {
		return _template;
	}

	public void setAddtionalParameters(
		Map<String, String> additionalParameters) {

		_additionalParameters = additionalParameters;
	}

	public void setArchetypesDirs(List<File> archetypesDirs) {
		_archetypesDirs = archetypesDirs;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setDestinationDir(File destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setPackageName(String packageName) {
		_packageName = packageName;
	}

	public void setTemplate(String template) {
		_template = template;
	}

	@DynamicParameter(
		description = "Addtional parameters to use when generating from code template.",
		names = {"-D", "--define"}
	)
	private Map<String, String> _additionalParameters = new HashMap<>();

	@Parameter(hidden = true, names = {"--archetypes-dir", "--archetypes-dirs"})
	private List<File> _archetypesDirs = new ArrayList<>();

	@Parameter(
		description = "The name of the user associated with the code.",
		names = "--author"
	)
	private String _author;

	@Parameter(
		description = "Provide the name of the class to be generated.",
		names = "--class-name", required = true
	)
	private String _className;

	@Parameter(
		description = "The directory where to generate the code.",
		names = "--destination"
	)
	private File _destinationDir;

	@Parameter(
		description = "The destination package for the code generator.",
		names = "--package-name"
	)
	private String _packageName;

	@Parameter(
		description = "The code template to generate into destination dir.",
		names = "--template"
	)
	private String _template;

}