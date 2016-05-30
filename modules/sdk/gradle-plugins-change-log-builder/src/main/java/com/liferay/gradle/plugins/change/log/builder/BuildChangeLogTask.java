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

package com.liferay.gradle.plugins.change.log.builder;

import com.liferay.gradle.plugins.change.log.builder.util.GitUtil;
import com.liferay.gradle.plugins.change.log.builder.util.NaturalOrderStringComparator;
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
public class BuildChangeLogTask extends DefaultTask {

	public BuildChangeLogTask() {
		setChangeLogHeader(
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
	public void buildChangeLog() throws Exception {
		Project project = getProject();

		File changeLogFile = getChangeLogFile();

		Path changeLogPath = changeLogFile.toPath();

		String changeLogContent = null;

		if (changeLogFile.exists()) {
			changeLogContent = new String(
				Files.readAllBytes(changeLogPath), StandardCharsets.UTF_8);
		}

		String range;
		Set<String> ticketIds;

		try (Repository repository = GitUtil.openRepository(project)) {
			String rangeEnd = GitUtil.getHashHead(repository);
			String rangeStart = getRangeStart(changeLogContent, repository);

			range = rangeStart + ".." + rangeEnd;
			ticketIds = getTicketIds(rangeStart, rangeEnd, repository);
		}

		if (ticketIds.isEmpty()) {
			throw new StopExecutionException(
				project + " does not have changes for range " + range);
		}

		File changeLogDir = changeLogFile.getParentFile();

		changeLogDir.mkdirs();

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				changeLogPath, StandardCharsets.UTF_8,
				StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {

			if (Validator.isNotNull(changeLogContent)) {
				bufferedWriter.newLine();
				bufferedWriter.newLine();
			}

			bufferedWriter.append('#');
			bufferedWriter.newLine();

			bufferedWriter.append("# ");
			bufferedWriter.append(getChangeLogHeader());
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
	public File getChangeLogFile() {
		return GradleUtil.toFile(getProject(), _changeLogFile);
	}

	@Input
	public String getChangeLogHeader() {
		return GradleUtil.toString(_changeLogHeader);
	}

	@Input
	public Set<String> getTicketIdPrefixes() {
		return _ticketIdPrefixes;
	}

	public void setChangeLogFile(Object changeLogFile) {
		_changeLogFile = changeLogFile;
	}

	public void setChangeLogHeader(Object changeLogHeader) {
		_changeLogHeader = changeLogHeader;
	}

	public void setTicketIdPrefixes(Iterable<String> ticketIdPrefixes) {
		_ticketIdPrefixes.clear();

		ticketIdPrefixes(ticketIdPrefixes);
	}

	public void setTicketIdPrefixes(String... ticketIdPrefixes) {
		setTicketIdPrefixes(Arrays.asList(ticketIdPrefixes));
	}

	public BuildChangeLogTask ticketIdPrefixes(
		Iterable<String> ticketIdPrefixes) {

		GUtil.addToCollection(_ticketIdPrefixes, ticketIdPrefixes);

		return this;
	}

	public BuildChangeLogTask ticketIdPrefixes(String... ticketIdPrefixes) {
		return ticketIdPrefixes(Arrays.asList(ticketIdPrefixes));
	}

	protected String getRangeStart(
			String changeLogContent, Repository repository)
		throws Exception {

		String rangeStart;

		if (Validator.isNotNull(changeLogContent)) {
			Matcher matcher = _lastRangeEndPattern.matcher(changeLogContent);

			if (!matcher.find()) {
				throw new GradleException("Unable to find the range start");
			}

			rangeStart = matcher.group(1);
		}
		else {
			Calendar calendar = Calendar.getInstance();

			calendar.add(Calendar.YEAR, -2);

			rangeStart = GitUtil.getHashBefore(calendar.getTime(), repository);
		}

		return rangeStart + "^";
	}

	protected Set<String> getTicketIds(
			String rangeStart, String rangeEnd, Repository repository)
		throws Exception {

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

	private Object _changeLogFile;
	private Object _changeLogHeader;
	private final Set<String> _ticketIdPrefixes = new HashSet<>();

}