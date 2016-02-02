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

package com.liferay.gradle.plugins.tlddoc.builder.tasks;

import groovy.lang.Closure;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.AntBuilder;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class ValidateSchemaTask extends SourceTask {

	@Input
	public boolean isDTDDisabled() {
		return _dtdDisabled;
	}

	@Input
	public boolean isFullChecking() {
		return _fullChecking;
	}

	@Input
	public boolean isLenient() {
		return _lenient;
	}

	public void setDTDDisabled(boolean dtdDisabled) {
		_dtdDisabled = dtdDisabled;
	}

	public void setFullChecking(boolean fullChecking) {
		_fullChecking = fullChecking;
	}

	public void setLenient(boolean lenient) {
		_lenient = lenient;
	}

	@TaskAction
	public void validateSchema() {
		Project project = getProject();

		final AntBuilder antBuilder = project.getAnt();

		Map<String, Object> args = new HashMap<>();

		args.put("disableDTD", isDTDDisabled());
		args.put("fullchecking", isFullChecking());
		args.put("lenient", isLenient());

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall() {
				FileTree fileTree = getSource();

				fileTree.addToAntBuilder(
					antBuilder, "fileset", FileCollection.AntType.FileSet);
			}

		};

		antBuilder.invokeMethod("schemavalidate", new Object[] {args, closure});
	}

	private boolean _dtdDisabled;
	private boolean _fullChecking = true;
	private boolean _lenient;

}