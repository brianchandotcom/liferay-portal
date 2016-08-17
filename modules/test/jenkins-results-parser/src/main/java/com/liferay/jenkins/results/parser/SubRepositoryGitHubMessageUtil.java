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

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Project;

/**
 * @author Leslie Wong
 */
public class SubrepositoryGitHubMessageUtil {

	public static void getGitHubMessage(Project project) throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("<h1>");
		sb.append(project.getProperty("top.level.result.message"));
		sb.append("</h1>");

		sb.append("<p>Build Time: ");
		sb.append(project.getProperty("top.level.build.time"));
		sb.append("</p>");

		String rebaseBranchGitCommit = project.getProperty(
			"rebase.branch.git.commit");

		if (!rebaseBranchGitCommit.equals("")) {
			sb.append("<h4>Base Branch:</h4>");
			sb.append("<p>Branch Name: ");
			sb.append("<a href=\"https://github.com/liferay/");
			sb.append(project.getProperty("repository"));
			sb.append("/tree/");
			sb.append(project.getProperty("branch.name"));
			sb.append("\">");
			sb.append(project.getProperty("branch.name"));
			sb.append("</a><br />");
			sb.append("Branch GIT ID: <a href=\"https://github.com/liferay/");
			sb.append(project.getProperty("repository"));
			sb.append("/commit/");
			sb.append(rebaseBranchGitCommit);
			sb.append("\">");
			sb.append(rebaseBranchGitCommit);
			sb.append("</a></p>");
		}

		sb.append("<h4>Task Summary:</h4>");
		sb.append("<ul>");

		String buildURL = project.getProperty("build.url");

		String progressiveText = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(
				buildURL + "/logText/progressiveText"),
			false);

		Matcher matcher = _taskNameConsolePattern.matcher(progressiveText);

		List<Integer> indexes = new ArrayList<>();

		while (matcher.find()) {
			indexes.add(matcher.start());
		}

		ListIterator<Integer> listIterator = indexes.listIterator();

		while (listIterator.hasNext()) {
			sb.append("<li><strong><a href=\"");
			sb.append(project.getProperty("top.level.shared.dir.url"));
			sb.append("/");

			int x = listIterator.next();

			String console;

			if (!listIterator.hasNext()) {
				console = progressiveText.substring(x);
			}
			else {
				console = progressiveText.substring(
					x, indexes.get(listIterator.nextIndex()));
			}

			matcher = _taskNameConsolePattern.matcher(console);

			matcher.find();

			String taskName = "";

			if (matcher.find()) {
				taskName = matcher.group(1);
			}

			JenkinsResultsParserUtil.write(
				new File(
					project.getProperty("top.level.shared.dir") + "/" +
						taskName + ".log"),
				console);

			sb.append(taskName);
			sb.append(".log");
			sb.append("\">");
			sb.append(taskName);
			sb.append("</a></strong> ");
			sb.append("- ");

			SubrepositoryTask subRepositoryTask;

			if (console.contains(
					"A report with all the test results can be found at " +
						"test-results/html/index.html")) {

				subRepositoryTask = new SubrepositoryTaskReport(
					buildURL, taskName);
			}
			else {
				subRepositoryTask = new SubrepositoryTaskNoReport(
					console, taskName);
			}

			String result = subRepositoryTask.getResult();

			sb.append(result);

			if (result.equals("SUCCESS")) {
				sb.append(" :white_check_mark:");
			}
			else {
				if (result.equals("ABORTED")) {
					sb.append(" :no_entry:");
				}
				else if (result.equals("FAILURE")) {
					sb.append(" :x:");
				}

				sb.append(subRepositoryTask.getGitHubMessage());
			}

			sb.append("</li>");
		}

		sb.append("</ul><h5>For more details click <a href=\"");
		sb.append(buildURL);
		sb.append("\">here</a>.</h5>");

		project.setProperty("report.html.content", sb.toString());
	}

	private static final Pattern _taskNameConsolePattern = Pattern.compile(
		"Executing task ([\\w-]+)");

}