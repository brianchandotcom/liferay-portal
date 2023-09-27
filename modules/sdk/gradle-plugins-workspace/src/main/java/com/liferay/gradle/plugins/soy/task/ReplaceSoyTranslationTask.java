/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.soy.task;

import com.liferay.portal.tools.soy.builder.commands.ReplaceSoyTranslationCommand;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@CacheableTask
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