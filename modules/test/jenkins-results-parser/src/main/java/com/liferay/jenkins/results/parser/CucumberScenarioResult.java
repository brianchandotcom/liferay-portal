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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Michael Hashimoto
 */
public class CucumberScenarioResult {

	public String getBackgroundName() {
		if (_backgroundDocument == null) {
			return null;
		}

		return "Background: " + _getName(_backgroundDocument);
	}

	public String getBackgroundStatus() {
		return _getStatus(_backgroundDocument);
	}

	public CucumberFeatureResult getCucumberFeatureResult() {
		return _cucumberFeatureResult;
	}

	public long getDuration() {
		return _getDuration(_backgroundDocument) +
			_getDuration(_scenarioDocument);
	}

	public String getErrorDetails() {
		String status = getStatus();

		if (status.equals("PASSED")) {
			return null;
		}

		for (Step step : getSteps()) {
			String errorDetails = step.getErrorDetails();

			if (errorDetails != null) {
				return errorDetails;
			}
		}

		return null;
	}

	public String getErrorStacktrace() {
		String status = getStatus();

		if (status.equals("PASSED")) {
			return null;
		}

		for (Step step : getSteps()) {
			String errorStacktrace = step.getErrorStacktrace();

			if (errorStacktrace != null) {
				return errorStacktrace;
			}
		}

		return null;
	}

	public String getScenarioName() {
		return "Scenario: " + _getName(_scenarioDocument);
	}

	public String getScenarioStatus() {
		return _getStatus(_scenarioDocument);
	}

	public String getStatus() {
		String backgroundStatus = getBackgroundStatus();
		String scenarioStatus = getScenarioStatus();

		if (backgroundStatus.equals("PASSED") &&
			scenarioStatus.equals("PASSED")) {

			return "PASSED";
		}

		return "FAILED";
	}

	public List<Step> getSteps() {
		List<Step> steps = new ArrayList<>();

		List<Node> nodes = new ArrayList<>();

		if (_backgroundDocument != null) {
			nodes.addAll(
				Dom4JUtil.getNodesByXPath(
					_backgroundDocument, "//div[@class='step']"));
		}

		nodes.addAll(
			Dom4JUtil.getNodesByXPath(
				_scenarioDocument, "//div[@class='step']"));

		for (Node node : nodes) {
			Document document;

			try {
				document = Dom4JUtil.parse(node.asXML());
			}
			catch (DocumentException documentException) {
				continue;
			}

			steps.add(new Step(document));
		}

		return steps;
	}

	public static class Step {

		public String getErrorDetails() {
			String status = getStatus();

			if (status.equals("PASSED")) {
				return null;
			}

			StringBuilder sb = new StringBuilder();

			sb.append("//div[@class='step']/div[@class='inner-level']");
			sb.append("/div[contains(@class,'message')]/div/a");

			Element element = (Element)Dom4JUtil.getNodeByXPath(
				_document, sb.toString());

			if (element == null) {
				return null;
			}

			return element.getText();
		}

		public String getErrorStacktrace() {
			String status = getStatus();

			if (status.equals("PASSED")) {
				return null;
			}

			StringBuilder sb = new StringBuilder();

			sb.append("//div[@class='step']/div[@class='inner-level']");
			sb.append("/div[contains(@class,'message')]/div/pre");

			Element element = (Element)Dom4JUtil.getNodeByXPath(
				_document, sb.toString());

			if (element == null) {
				return null;
			}

			return element.getText();
		}

		public String getStatus() {
			Element element = (Element)Dom4JUtil.getNodeByXPath(
				_document,
				"//div[@class='step']/div[contains(@class,'brief')]");

			if (element == null) {
				return "PASSED";
			}

			String classAttributeValue = element.attributeValue("class");

			if (classAttributeValue.contains("passed")) {
				return "PASSED";
			}

			return "FAILED";
		}

		private Step(Document document) {
			_document = document;
		}

		private final Document _document;

	}

	protected CucumberScenarioResult(
		CucumberFeatureResult cucumberFeatureResult, Document scenarioDocument,
		Document backgroundDocument) {

		_cucumberFeatureResult = cucumberFeatureResult;
		_scenarioDocument = scenarioDocument;
		_backgroundDocument = backgroundDocument;

		if (_scenarioDocument == null) {
			throw new RuntimeException("Scenario Document is null");
		}
	}

	private long _getDuration(Document document) {
		Element element = (Element)Dom4JUtil.getNodeByXPath(
			document,
			"//div[@class='element']/span[contains(@class,'duration')]");

		if (element == null) {
			return 0;
		}

		Matcher matcher = _pattern.matcher(element.getTextTrim());

		if (!matcher.find()) {
			return 0;
		}

		long duration = Long.valueOf(matcher.group("ms"));

		String secs = matcher.group("secs");

		if ((secs != null) && !secs.isEmpty()) {
			duration += Long.valueOf(secs) * 1000;
		}

		String mins = matcher.group("mins");

		if ((mins != null) && !mins.isEmpty()) {
			duration += Long.valueOf(mins) * 1000 * 60;
		}

		return duration;
	}

	private String _getName(Document document) {
		if (document == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("//div[@class='element']/span/div[contains(@class,'brief')]");
		sb.append("/span[@class='name']");

		Node node = Dom4JUtil.getNodeByXPath(document, sb.toString());

		if (node == null) {
			return null;
		}

		return node.getText();
	}

	private String _getStatus(Document document) {
		if (document == null) {
			return "PASSED";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("//div[@class='element']/span[@data-toggle='collapse']");
		sb.append("/div[contains(@class,'brief')]");

		Element statusElement = (Element)Dom4JUtil.getNodeByXPath(
			document, sb.toString());

		if (statusElement == null) {
			return "FAILED";
		}

		String classes = statusElement.attributeValue("class");

		if (classes.contains("passed")) {
			return "PASSED";
		}

		return "FAILED";
	}

	private final Document _backgroundDocument;
	private final CucumberFeatureResult _cucumberFeatureResult;
	private Pattern _pattern = Pattern.compile(
		"((?<mins>\\d+)\\:)?(?<secs>\\d+)\\.(?<ms>\\d{3})");
	private final Document _scenarioDocument;

}