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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
public class RebaseErrorTopLevelBuild extends TopLevelBuild {

	public RebaseErrorTopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	public String getResult() {
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

			long time = System.currentTimeMillis();

			Map<String, String> stopPropertiesTempMap =
				getStopPropertiesTempMap();

			while (!stopPropertiesTempMap.containsKey(
						"TOP_LEVEL_GITHUB_COMMENT_ID")) {

				if ((System.currentTimeMillis() - time) > (5 * 60 * 1000)) {
					System.out.println(
						"No entry exists for TOP_LEVEL_GITHUB_COMMENT_ID in " +
							"stop.properties");

					return result;
				}

				JenkinsResultsParserUtil.sleep(10 * 1000);

				stopPropertiesTempMap = getStopPropertiesTempMap();
			}

			Element rootElement = null;

			File reportFile = new File(
				JenkinsResultsParserUtil.combine(
					"/opt/dev/projects/github/liferay-jenkins-ee/tests/",
					"test-portal-acceptance-pullrequest(ee-6.2.x)/",
					"rebase-error/report2.html"));

			if (reportFile.exists()) {
				rootElement = getElement(
					JenkinsResultsParserUtil.read(reportFile));
			}

			if (rootElement == null) {
				StringBuilder sb = new StringBuilder();

				sb.append("http://mirrors-no-cache.lax.liferay.com/");
				sb.append("github.com/liferay/liferay-jenkins-ee/tests/");
				sb.append(getJobName());

				String jenkinsJobVariant = getParameterValue(
					"JENKINS_JOB_VARIANT");

				if (jenkinsJobVariant != null) {
					sb.append("/");
					sb.append(jenkinsJobVariant);
				}

				sb.append("/report.html");

				rootElement = getElement(
					JenkinsResultsParserUtil.toString(sb.toString()));
			}

			List<String> expectedCommentTokens = getCommentTokens(rootElement);

			StringBuilder sb = new StringBuilder();

			sb.append("https://api.github.com/repos/");
			sb.append(getParameterValue("GITHUB_RECEIVER_USERNAME"));
			sb.append("/");
			sb.append("liferay-portal-ee");
			sb.append("/issues/comments/");

			sb.append(stopPropertiesTempMap.get("TOP_LEVEL_GITHUB_COMMENT_ID"));

			JSONObject jsonObject = getJSONObjectFromURL(sb.toString());

			String commentBody = jsonObject.getString("body");

			rootElement = getElement(commentBody);

			List<String> actualCommentTokens = getCommentTokens(rootElement);

			boolean matchesTemplate = true;

			for (int i = 0; i < expectedCommentTokens.size(); i++) {
				System.out.println();
				System.out.println("Test " + i);

				Pattern pattern = Pattern.compile(
					expectedCommentTokens.get(i).replaceAll("\\s+", "\\\\s+"));

				Matcher matcher = pattern.matcher(actualCommentTokens.get(i));

				System.out.println("'" + expectedCommentTokens.get(i) + "'");
				System.out.println("pattern: " + pattern.pattern());
				System.out.println("'" + actualCommentTokens.get(i) + "'");

				if (matcher.find()) {
					System.out.println("Tokens matched.");
				}
				else {
					System.out.println("Tokens mismatched.");

					return result;
				}
			}

			if (matchesTemplate) {
				result = "SUCCESS";
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

	protected List<String> getCommentTokens(Element element) {
		List<String> tokens = new ArrayList<>();

		tokens.add("tag: " + element.getName() + " text: " + element.getText());

		List<?> elementObjects = element.elements();

		for (Object childElementObject : elementObjects) {
			tokens.addAll(getCommentTokens((Element)childElementObject));
		}

		List<?> attributeObjects = element.attributes();

		for (Object attributeObject : attributeObjects) {
			Attribute attribute = (Attribute)attributeObject;

			tokens.add(
				"tag: " + element.getName() + " attribute: " +
					attribute.getName() + " text: " + attribute.getValue());
		}

		return tokens;
	}

	protected Element getElement(String content) {
		try {
			Document document = Dom4JUtil.parse(
				JenkinsResultsParserUtil.combine("<div>", content, "</div>"));

			return document.getRootElement();
		}
		catch (DocumentException de) {
			throw new RuntimeException("Unable to parse XML", de);
		}
	}

	protected JSONObject getJSONObjectFromURL(String url) throws Exception {
		Properties properties = JenkinsResultsParserUtil.getBuildProperties();

		StringBuilder sb = new StringBuilder();

		URL urlObject = new URL(url);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)urlObject.openConnection();

		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setRequestProperty(
			"Authorization",
			"token " + properties.getProperty("github.access.token"));
		httpURLConnection.setRequestProperty(
			"Content-Type", "application/json");

		InputStream inputStream = httpURLConnection.getInputStream();

		InputStreamReader inputStreamReader = new InputStreamReader(
			inputStream);

		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}

		bufferedReader.close();

		return new JSONObject(sb.toString());
	}

	private boolean _validResult;

}