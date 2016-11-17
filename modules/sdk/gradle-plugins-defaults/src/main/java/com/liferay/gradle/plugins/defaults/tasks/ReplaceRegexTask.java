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

package com.liferay.gradle.plugins.defaults.tasks;

import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class ReplaceRegexTask extends DefaultTask {

	@Input
	@SkipWhenEmpty
	public Map<String, FileCollection> getMatches() {
		return _matches;
	}

	public List<Closure<String>> getPre() {
		return _preClosures;
	}

	@Input
	public Object getReplacement() {
		return _replacement;
	}

	public List<Closure<Boolean>> getReplaceOnlyIf() {
		return _replaceOnlyIfClosures;
	}

	public ReplaceRegexTask match(String regex, Iterable<Object> files) {
		Project project = getProject();

		FileCollection fileCollection = _matches.get(regex);

		FileCollection filesFileCollection = project.files(files);

		if (fileCollection == null) {
			fileCollection = filesFileCollection;
		}
		else {
			fileCollection = fileCollection.plus(filesFileCollection);
		}

		_matches.put(regex, fileCollection);

		return this;
	}

	public ReplaceRegexTask match(String regex, Object... files) {
		return match(regex, Arrays.asList(files));
	}

	public ReplaceRegexTask pre(Closure<String>... preClosures) {
		return pre(Arrays.asList(preClosures));
	}

	public ReplaceRegexTask pre(Iterable<Closure<String>> preClosures) {
		GUtil.addToCollection(_preClosures, preClosures);

		return this;
	}

	public ReplaceRegexTask replaceOnlyIf(
		Closure<Boolean>... replaceOnlyIfClosures) {

		return replaceOnlyIf(Arrays.asList(replaceOnlyIfClosures));
	}

	public ReplaceRegexTask replaceOnlyIf(
		Iterable<Closure<Boolean>> replaceOnlyIfClosures) {

		GUtil.addToCollection(_replaceOnlyIfClosures, replaceOnlyIfClosures);

		return this;
	}

	@TaskAction
	public void replaceRegex() throws IOException {
		Map<String, FileCollection> matches = getMatches();

		for (Map.Entry<String, FileCollection> entry : matches.entrySet()) {
			Pattern pattern = Pattern.compile(entry.getKey());
			FileCollection fileCollection = entry.getValue();

			for (File file : fileCollection) {
				_replaceRegex(file, pattern);
			}
		}
	}

	public void setMatches(Map<String, FileCollection> matches) {
		_matches.clear();

		_matches.putAll(matches);
	}

	public void setPre(Closure<String>... preClosures) {
		setPre(Arrays.asList(preClosures));
	}

	public void setPre(Iterable<Closure<String>> preClosures) {
		_preClosures.clear();

		pre(preClosures);
	}

	public void setReplacement(Object replacement) {
		_replacement = replacement;
	}

	public void setReplaceOnlyIf(Closure<Boolean>... replaceOnlyIfClosures) {
		setReplaceOnlyIf(Arrays.asList(replaceOnlyIfClosures));
	}

	public void setReplaceOnlyIf(
		Iterable<Closure<Boolean>> replaceOnlyIfClosures) {

		_replaceOnlyIfClosures.clear();

		replaceOnlyIf(replaceOnlyIfClosures);
	}

	private void _replaceRegex(File file, Pattern pattern) throws IOException {
		Logger logger = getLogger();

		Path path = file.toPath();

		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		String newContent = content;

		for (Closure<String> closure : getPre()) {
			newContent = closure.call(newContent, file);
		}

		Matcher matcher = pattern.matcher(newContent);

		while (matcher.find()) {
			boolean replace = true;

			int groupCount = matcher.groupCount();

			String group = matcher.group(groupCount);

			String replacement;

			Object replacementObj = getReplacement();

			if (replacementObj instanceof Closure<?>) {
				Closure<String> replacementClosure =
					(Closure<String>)replacementObj;

				replacement = replacementClosure.call(group);
			}
			else {
				replacement = GradleUtil.toString(replacementObj);
			}

			for (Closure<Boolean> closure : getReplaceOnlyIf()) {
				if (!closure.call(group, replacement, newContent)) {
					replace = false;

					break;
				}
			}

			if (replace) {
				newContent =
					newContent.substring(0, matcher.start(groupCount)) +
						replacement +
							newContent.substring(matcher.end(groupCount));
			}
			else if (logger.isInfoEnabled()) {
				logger.info(
					"Skipped replacement of {} to {} in {}", group, replacement,
					file);
			}
		}

		if (!content.equals(newContent)) {
			Files.write(path, newContent.getBytes(StandardCharsets.UTF_8));

			if (logger.isLifecycleEnabled()) {
				Project project = getProject();

				logger.lifecycle("Updated {}", project.relativePath(file));
			}
		}
	}

	private final Map<String, FileCollection> _matches = new LinkedHashMap<>();
	private final List<Closure<String>> _preClosures = new ArrayList<>();
	private Object _replacement;
	private final List<Closure<Boolean>> _replaceOnlyIfClosures =
		new ArrayList<>();

}