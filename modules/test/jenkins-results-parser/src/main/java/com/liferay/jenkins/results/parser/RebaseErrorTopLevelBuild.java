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

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class RebaseErrorTopLevelBuild extends DefaultTopLevelBuild {

	public RebaseErrorTopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	public String getResult() {
		String result = super.getResult();

		if (_validResult) {
			return result;
		}

		super.getResult();

		if (result == null) {
			return result;
		}

		try {
			if (result.equals("SUCCESS")) {
				result = "FAILURE";

				return result;
			}

			if (!result.equals("FAILURE")) {
				return result;
			}

			waitForNonzeroDuration();

			Map<String, String> buildEnvMap = new HashMap<>();
			int retries = 0;
			long time = System.currentTimeMillis();

			if (fromArchive) {
				buildEnvMap = getStopPropertiesTempMap();
			}

			while (!buildEnvMap.containsKey("TOP_LEVEL_GITHUB_COMMENT_ID")) {
				if (retries > 2) {
					throw new RuntimeException(
						"Unable to get TOP_LEVE_GITHUB_COMMENT_ID from stop " +
							"properties temp map");
				}

				if ((System.currentTimeMillis() - time) > (5 * 60 * 1000)) {
					System.out.println(
						"No entry exists for TOP_LEVEL_GITHUB_COMMENT_ID in " +
							"stop.properties");

					return result;
				}

				buildEnvMap.putAll(getInjectedEnvironmentVariablesMap());

				retries++;

				JenkinsResultsParserUtil.sleep(10 * 1000);
			}

			if (matchCommentTokens(
					getActualCommentTokens(buildEnvMap),
					getExpectedCommentTokens())) {

				setResult("SUCCESS");

				result = super.getResult();
			}

			return result;
		}
		catch (Exception e) {
			throw new RuntimeException(
				"An exception occurred while trying to match the actual " +
					"output with the expected output",
				e);
		}
		finally {
			_validResult = true;
		}
	}

	protected List<String> getActualCommentTokens(
			Map<String, String> stopPropertiesTempMap)
		throws IOException {

		String url = JenkinsResultsParserUtil.getGitHubApiUrl(
			"liferay-portal-ee", getParameterValue("GITHUB_RECEIVER_USERNAME"),
			"issues/comments/" +
				stopPropertiesTempMap.get("TOP_LEVEL_GITHUB_COMMENT_ID"));

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(url);

		String commentBody = jsonObject.getString("body");

		return getCommentTokens(getRootElement(commentBody));
	}

	protected List<String> getCommentTokens(Element element) {
		List<String> tokens = new ArrayList<>();

		tokens.add("tag: " + element.getName() + " text: " + element.getText());

		for (Element childElement : element.elements()) {
			tokens.addAll(getCommentTokens(childElement));
		}

		for (Attribute attribute : element.attributes()) {
			tokens.add(
				"tag: " + element.getName() + " attribute: " +
					attribute.getName() + " text: " + attribute.getValue());
		}

		return tokens;
	}

	protected List<String> getExpectedCommentTokens() throws IOException {
		String resource = JenkinsResultsParserUtil.getResourceFileContent(
			"dependencies/RebaseErrorTopLevelBuildTemplate.html");

		return getCommentTokens(getRootElement(resource));
	}

	protected Element getRootElement(String content) {
		try {
			Document document = Dom4JUtil.parse(
				JenkinsResultsParserUtil.combine("<div>", content, "</div>"));

			return document.getRootElement();
		}
		catch (DocumentException de) {
			throw new RuntimeException("Unable to parse XML", de);
		}
	}

	protected boolean matchCommentTokens(
		List<String> actualCommentTokens, List<String> expectedCommentTokens) {

		for (int i = 0; i < expectedCommentTokens.size(); i++) {
			System.out.println();
			System.out.println("Test " + i);

			String expectedCommentToken = expectedCommentTokens.get(i);

			Pattern pattern = Pattern.compile(
				expectedCommentToken.replaceAll("\\s+", "\\\\s*"));

			Matcher matcher = pattern.matcher(actualCommentTokens.get(i));

			System.out.println("'" + expectedCommentToken + "'");
			System.out.println("pattern: " + pattern.pattern());
			System.out.println("'" + actualCommentTokens.get(i) + "'");

			if (matcher.find()) {
				System.out.println("Tokens matched");
			}
			else {
				System.out.println("Tokens mismatched");

				return false;
			}
		}

		return true;
	}

	protected void waitForNonzeroDuration() throws IOException {
		long maxWaitTime = 300 * 1000;
		long startTime = System.currentTimeMillis();

		String localBuildURL = JenkinsResultsParserUtil.getLocalURL(
			getBuildURL());

		while ((System.currentTimeMillis() - startTime) < maxWaitTime) {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				localBuildURL + "/api/json?tree=duration", false);

			int duration = jsonObject.getInt("duration");

			if (duration > 0) {
				return;
			}

			JenkinsResultsParserUtil.sleep(10 * 1000);
		}
	}

	private boolean _validResult;

}