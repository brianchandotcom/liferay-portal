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

package com.liferay.gradle.plugins.changelog.builder;

import com.liferay.gradle.plugins.changelog.builder.util.GitUtil;
import com.liferay.gradle.plugins.changelog.builder.util.NaturalOrderStringComparator;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.BufferedWriter;
import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.StopExecutionException;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class BuildChangelogTask extends DefaultTask {

	public BuildChangelogTask() {
		setChangelogHeader(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					Project project = getProject();

					return "Bundle Version " + project.getVersion();
				}

			});

		setTicketIdPrefixes("CLDSVCS", "LPS", "SOS", "SYNC");
	}

	@TaskAction
	public void buildChangelog() throws Exception {
		Project project = getProject();

		File changelogFile = getChangelogFile();

		Path changelogPath = changelogFile.toPath();

		String changelogContent = null;

		if (changelogFile.exists()) {
			changelogContent = new String(
				Files.readAllBytes(changelogPath), StandardCharsets.UTF_8);
		}

		String range;
		Set<String> ticketIds;

		try (Repository repository = GitUtil.openRepository(project)) {
			String rangeEnd = GitUtil.getHashHead(repository);
			String rangeStart = getRangeStart(changelogContent, repository);

			range = rangeStart + ".." + rangeEnd;
			ticketIds = getTicketIds(rangeStart, rangeEnd, repository);
		}

		if (ticketIds.isEmpty()) {
			throw new StopExecutionException(
				project + " does not have changes for range " + range);
		}

		File changelogDir = changelogFile.getParentFile();

		changelogDir.mkdirs();

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				changelogPath, StandardCharsets.UTF_8,
				StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {

			if (Validator.isNotNull(changelogContent)) {
				bufferedWriter.newLine();
				bufferedWriter.newLine();
			}

			bufferedWriter.append('#');
			bufferedWriter.newLine();

			bufferedWriter.append("# ");
			bufferedWriter.append(getChangelogHeader());
			bufferedWriter.newLine();

			bufferedWriter.append('#');
			bufferedWriter.newLine();

			bufferedWriter.append(range);
			bufferedWriter.append('=');

			boolean firstTicket = true;

			for (String ticketId : ticketIds) {
				if (firstTicket) {
					firstTicket = false;
				}
				else {
					bufferedWriter.append(' ');
				}

				bufferedWriter.append(ticketId);
			}
		}
	}

	@Input
	public File getChangelogFile() {
		return GradleUtil.toFile(getProject(), _changelogFile);
	}

	@Input
	public String getChangelogHeader() {
		return GradleUtil.toString(_changelogHeader);
	}

	@Input
	public Set<String> getTicketIdPrefixes() {
		return _ticketIdPrefixes;
	}

	public void setChangelogFile(Object changelogFile) {
		_changelogFile = changelogFile;
	}

	public void setChangelogHeader(Object changelogHeader) {
		_changelogHeader = changelogHeader;
	}

	public void setTicketIdPrefixes(Iterable<String> ticketIdPrefixes) {
		_ticketIdPrefixes.clear();

		ticketIdPrefixes(ticketIdPrefixes);
	}

	public void setTicketIdPrefixes(String ... ticketIdPrefixes) {
		setTicketIdPrefixes(Arrays.asList(ticketIdPrefixes));
	}

	public BuildChangelogTask ticketIdPrefixes(
		Iterable<String> ticketIdPrefixes) {

		GUtil.addToCollection(_ticketIdPrefixes, ticketIdPrefixes);

		return this;
	}

	public BuildChangelogTask ticketIdPrefixes(String ... ticketIdPrefixes) {
		return ticketIdPrefixes(Arrays.asList(ticketIdPrefixes));
	}

	protected String getRangeStart(
			String changelogContent, Repository repository)
		throws Exception {

		String rangeStart;

		if (Validator.isNotNull(changelogContent)) {
			Matcher matcher = _lastRangeEndPattern.matcher(changelogContent);

			if (!matcher.find()) {
				throw new GradleException("Unable to find the range start");
			}

			rangeStart = matcher.group(1);
		}
		else {
			Calendar calendar = Calendar.getInstance();

			calendar.add(Calendar.YEAR, -1);

			rangeStart = GitUtil.getHashBefore(calendar.getTime(), repository);
		}

		return rangeStart + "^";
	}

	protected Set<String> getTicketIds(
		String rangeStart, String rangeEnd,
		Repository repository) throws Exception {

		Set<String> ticketIds = new TreeSet<>(
			new NaturalOrderStringComparator());

		Project project = getProject();
		Set<String> ticketIdPrefixes = getTicketIdPrefixes();

		Iterable<RevCommit> revCommits = GitUtil.getCommits(
			project.getProjectDir(), rangeStart, rangeEnd, repository);

		for (RevCommit revCommit : revCommits) {
			String message = revCommit.getShortMessage();

			int index = message.indexOf('-');

			if (index == -1) {
				continue;
			}

			String prefix = message.substring(0, index);

			if (!ticketIdPrefixes.contains(prefix)) {
				continue;
			}

			index = message.indexOf(' ');

			if (index == -1) {
				index = message.length();
			}

			ticketIds.add(message.substring(0, index));
		}

		return ticketIds;
	}

	private static final Pattern _lastRangeEndPattern = Pattern.compile(
		"\\.\\.([0-9a-f]{40})=.*$");

	private Object _changelogFile;
	private Object _changelogHeader;
	private final Set<String> _ticketIdPrefixes = new HashSet<>();

}