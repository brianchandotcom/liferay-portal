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

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class ReplaceSoyTranslationTask extends SourceTask {

	@Input
	public Closure<String> getReplacementClosure() {
		return _replacementClosure;
	}

	@TaskAction
	public void replaceSoyTranslation() throws IOException {
		for (File file : getSource()) {
			_replaceSoyTranslation(file.toPath());
		}
	}

	public void setReplacementClosure(Closure<String> replacementClosure) {
		_replacementClosure = replacementClosure;
	}

	private void _replaceSoyTranslation(Path path) throws IOException {
		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		Matcher matcher = _pattern.matcher(content);

		StringBuffer sb = new StringBuffer();

		boolean found = false;

		while (matcher.find()) {
			found = true;

			String replacement = _replacementClosure.call(
				matcher.group(1), matcher.group(2), matcher.group(3));

			matcher.appendReplacement(sb, replacement);
		}

		matcher.appendTail(sb);

		if (found) {
			content = sb.toString();

			Files.write(path, content.getBytes(StandardCharsets.UTF_8));
		}
	}

	private static final Pattern _pattern = Pattern.compile(
		"var (MSG_EXTERNAL_\\d+) = goog\\.getMsg\\(\\s*'([\\w-\\{\\}\\$]+)'" +
			"\\s*(?:,\\s*\\{([\\s\\S]+?)\\})?\\);");

	private Closure<String> _replacementClosure;

}