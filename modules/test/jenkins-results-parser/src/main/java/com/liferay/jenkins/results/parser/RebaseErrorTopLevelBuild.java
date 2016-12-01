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
import java.io.ByteArrayInputStream;
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
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class RebaseErrorTopLevelBuild extends TopLevelBuild {

	public RebaseErrorTopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);

		_validResult = false;
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

			Map<String, String> stopPropertiesMap = getStopPropertiesMap();

			while (!stopPropertiesMap.containsKey(
						"TOP_LEVEL_GITHUB_COMMENT_ID")) {

				if ((System.currentTimeMillis() - time) > (5 * 60 * 1000)) {
					System.out.println(
						"No entry exists for TOP_LEVEL_GITHUB_COMMENT_ID in " +
							"stop.properties");

					return result;
				}

				JenkinsResultsParserUtil.sleep(10 * 1000);

				stopPropertiesMap = getStopPropertiesMap();
			}

			StringBuilder sb = new StringBuilder();

			sb.append("http://mirrors-no-cache.lax.liferay.com/");
			sb.append("github.com/liferay/liferay-jenkins-ee/tests/");
			sb.append(getJobName());

			String jenkinsJobVariant = getParameterValue("JENKINS_JOB_VARIANT");

			if (jenkinsJobVariant != null) {
				sb.append("/");
				sb.append(jenkinsJobVariant);
			}

			sb.append("/report.html");

			Element rootElement = getElement(
				JenkinsResultsParserUtil.toString(sb.toString()));

			List<String> expectedCommentTokens = getCommentTokens(rootElement);

			sb = new StringBuilder();

			sb.append("https://api.github.com/repos/");
			sb.append(getParameterValue("GITHUB_RECEIVER_USERNAME"));
			sb.append("/");
			sb.append("liferay-portal-ee");
			sb.append("/issues/comments/");

			sb.append(stopPropertiesMap.get("TOP_LEVEL_GITHUB_COMMENT_ID"));

			JSONObject jsonObject = getJSONObjectFromURL(sb.toString());

			String commentBody = jsonObject.getString("body");

			rootElement = getElement(commentBody);

			List<String> actualCommentTokens = getCommentTokens(rootElement);

			boolean matchesTemplate = true;

			for (int i = 0; i < expectedCommentTokens.size(); i++) {
				System.out.println();
				System.out.println("Test " + i);

				Pattern pattern = Pattern.compile(expectedCommentTokens.get(i));

				Matcher matcher = pattern.matcher(actualCommentTokens.get(i));

				System.out.println(expectedCommentTokens.get(i));
				System.out.println(actualCommentTokens.get(i));

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

		tokens.add("text: " + removeWhitespace(element.getText()));

		List<Element> elements = element.elements();

		for (Element childElement : elements) {
			tokens.addAll(getCommentTokens(childElement));
		}

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			tokens.add("attribute: " + removeWhitespace(attribute.getValue()));
		}

		return tokens;
	}

	protected Element getElement(String content) throws Exception {
		SAXReader saxReader = new SAXReader();

		StringBuilder sb = new StringBuilder();

		sb.append("<div>");
		sb.append(content);
		sb.append("</div>");

		content = sb.toString();

		InputStream inputStream = new ByteArrayInputStream(
			content.getBytes("UTF-8"));

		Document document = saxReader.read(inputStream);

		return document.getRootElement();
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

	protected String removeWhitespace(String s) {
		s = s.replaceAll("\n", "");
		s = s.replaceAll("\t", "");

		return s;
	}

	private boolean _validResult;

}