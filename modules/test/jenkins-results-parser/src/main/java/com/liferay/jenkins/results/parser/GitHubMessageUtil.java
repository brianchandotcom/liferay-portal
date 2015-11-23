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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Project;

/**
 * @author Peter Yoo
 */
public class GitHubMessageUtil {

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
			sb.append("<a href=\\\"https://github.com/liferay/");
			sb.append(project.getProperty("repository"));
			sb.append("/tree/");
			sb.append(project.getProperty("branch.name"));
			sb.append("\\\">");
			sb.append(project.getProperty("branch.name"));
			sb.append("</a><br />");
			sb.append("Branch GIT ID: <a href=\\\"https://github.com/liferay/");
			sb.append(project.getProperty("repository"));
			sb.append("/commit/");
			sb.append(rebaseBranchGitCommit);
			sb.append("\\\">");
			sb.append(rebaseBranchGitCommit);
			sb.append("</a></p>");
		}

		sb.append("<h4>Job Summary:</h4>");
		sb.append("<ul>");
		sb.append("<li>");

		String topLevelResult = project.getProperty("top.level.result");

		if (!topLevelResult.equals("SUCCESS")) {
			sb.append("<strike><strong>");
		}

		sb.append("<a href=\\\"");
		sb.append(project.getProperty("env.BUILD_URL"));
		sb.append("\\\">");
		sb.append(project.getProperty("top.level.build.name"));
		sb.append("</a>");

		if (!topLevelResult.equals("SUCCESS")) {
			sb.append("</strong></strike>");
		}

		sb.append("</li>");

		String reportFileNames = project.getProperty("top.level.report.files");

		for (String reportFileName : reportFileNames.split(" ")) {
			File file = new File(reportFileName);

			String content = JenkinsResultsParserUtil.read(file);

			Matcher matcher = _pattern.matcher(content);

			if (matcher.find()) {
				if (content.contains("job-result=\\\"SUCCESS\\\"")) {
					sb.append("<li>");
					sb.append(matcher.group(1));
					sb.append("</li>");
				}
				else {
					sb.append("<li><strike><strong>");
					sb.append(matcher.group(1));
					sb.append("</strong></strike></li>");
				}
			}
		}

		sb.append("</ul>");

		sb.append("<h5>For more details click <a href=\\\"");
		sb.append(project.getProperty("top.level.shared.dir.url"));
		sb.append("/jenkins-report.html\\\">here</a>.</h5>");

		if (!topLevelResult.equals("SUCCESS")) {
			sb.append("<hr />");
			sb.append("<h4>Failed Jobs:</h4>");
			sb.append("<ol>");
			sb.append("<li><h5><a href=\\\"");
			sb.append(project.getProperty("env.BUILD_URL"));
			sb.append("\\\">");
			sb.append(project.getProperty("top.level.build.name"));
			sb.append("</a></h5>");
			sb.append("<h6>Job Results:</h6>");

			int topLevelPassCount = Integer.parseInt(
				project.getProperty("top.level.pass.count"));

			sb.append("<p>");
			sb.append(topLevelPassCount);
			sb.append(" Job");

			if (topLevelPassCount != 1) {
				sb.append("s");
			}

			sb.append(" Passed.<br />");

			int topLevelFailCount = Integer.parseInt(
				project.getProperty("top.level.fail.count")) + 1;

			sb.append(topLevelFailCount);
			sb.append(" Job");

			if (topLevelFailCount != 1) {
				sb.append("s");
			}

			sb.append(" Failed.</p><pre>Completed with the status of ");
			sb.append(topLevelResult);
			sb.append(".</pre></li>");

			
			List<String> errorReportsList = new ArrayList<>();
			List<String> highPriorityList = new ArrayList<>();

			for (String reportFileName : reportFileNames.split(" ")) {
				File file = new File(reportFileName);

				String content = JenkinsResultsParserUtil.read(file);

				if (content.contains("job-result=\\\"SUCCESS\\\"")) {
					continue;
				}
				if (isHighPriority(content)) {
					highPriorityList.add("<li>" + content + "</li>");
				}
				else {
					errorReportsList.add("<li>" + content + "</li>");
				}
			}
			
			errorReportsList.addAll(0, highPriorityList);

			for (int i = 0; i < errorReportsList.size(); i++) {
				if (i == 4) {
					sb.append("<li>...</li>");
					break;
				}
				
				sb.append(errorReportsList.get(i));
			}
			
			sb.append("</ol>");
		}

		project.setProperty("github.post.comment.body", sb.toString());
	}
	
	protected static boolean isHighPriority(String content) {
		String[] contentFlags = new String[] {
			"compileJSP",
			"Unable to compile JSPs"
		};
		
		for (String contentFlag : contentFlags) {
			if (content.contains(contentFlag)) {
				return true;
			}
		}
		return false;
	}

	private static final Pattern _pattern = Pattern.compile(
		"\\<h5[^\\>]*\\>(.+)\\<\\/h5\\>.*");

}