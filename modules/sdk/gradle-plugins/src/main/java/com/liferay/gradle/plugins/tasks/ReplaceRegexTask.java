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

package com.liferay.gradle.plugins.tasks;

import com.liferay.gradle.plugins.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class ReplaceRegexTask extends DefaultTask {

	public Iterable<File> getMatchedFiles() {
		return _matchedFiles;
	}

	@Input
	@SkipWhenEmpty
	public Map<String, FileCollection> getMatches() {
		return _matches;
	}

	@Input
	public String getReplacement() {
		return GradleUtil.toString(_replacement);
	}

	public boolean isIgnoreUnmatched() {
		return _ignoreUnmatched;
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

	public ReplaceRegexTask match(String regex, Object ... files) {
		return match(regex, Arrays.asList(files));
	}

	@TaskAction
	public void replaceRegex() throws IOException {
		_matchedFiles.clear();

		Map<String, FileCollection> matches = getMatches();
		String replacement = getReplacement();

		for (Map.Entry<String, FileCollection> entry : matches.entrySet()) {
			Pattern pattern = Pattern.compile(entry.getKey());
			FileCollection fileCollection = entry.getValue();

			for (File file : fileCollection) {
				replaceRegex(file, pattern, replacement);
			}
		}
	}

	public void setIgnoreUnmatched(boolean ignoreUnmatched) {
		_ignoreUnmatched = ignoreUnmatched;
	}

	public void setMatches(Map<String, FileCollection> matches) {
		_matches.clear();

		_matches.putAll(matches);
	}

	public void setReplacement(Object replacement) {
		_replacement = replacement;
	}

	protected void replaceRegex(File file, Pattern pattern, String replacement)
		throws IOException {

		Path path = file.toPath();

		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			String message = "Unable to match " + pattern + " in " + file;

			if (isIgnoreUnmatched()) {
				if (_logger.isInfoEnabled()) {
					_logger.info(message);
				}

				return;
			}

			throw new GradleException(message);
		}

		int groupCount = matcher.groupCount();

		String newContent =
			content.substring(0, matcher.start(groupCount)) + replacement +
				content.substring(matcher.end(groupCount));

		if (content.equals(newContent)) {
			return;
		}

		Files.write(path, newContent.getBytes(StandardCharsets.UTF_8));

		_matchedFiles.add(file);
	}

	private static final Logger _logger = Logging.getLogger(
		ReplaceRegexTask.class);

	private boolean _ignoreUnmatched;
	private final Set<File> _matchedFiles = new LinkedHashSet<>();
	private final Map<String, FileCollection> _matches = new LinkedHashMap<>();
	private Object _replacement;

}