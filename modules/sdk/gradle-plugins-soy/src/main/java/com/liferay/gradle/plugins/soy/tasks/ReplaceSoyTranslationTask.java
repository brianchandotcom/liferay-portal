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

package com.liferay.gradle.plugins.soy.tasks;

import com.liferay.portal.tools.soy.builder.commands.ReplaceSoyTranslationCommand;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ReplaceSoyTranslationTask extends SourceTask {

	@Input
	@Optional
	public Closure<String> getReplacementClosure() {
		return _replacementClosure;
	}

	@TaskAction
	public void replaceSoyTranslation() throws IOException {
		for (File file : getSource()) {
			_replaceSoyTranslationCommand.execute(file.toPath());
		}
	}

	public void setReplacementClosure(Closure<String> replacementClosure) {
		_replacementClosure = replacementClosure;
	}

	private Closure<String> _replacementClosure;

	private final ReplaceSoyTranslationCommand _replaceSoyTranslationCommand =
		new ReplaceSoyTranslationCommand() {

			@Override
			protected String getReplacement(
				String variableName, String languageKey,
				String argumentsObject) {

				Closure<String> closure = getReplacementClosure();

				if (closure != null) {
					return closure.call(
						variableName, languageKey, argumentsObject);
				}

				return super.getReplacement(
					variableName, languageKey, argumentsObject);
			}

		};

}