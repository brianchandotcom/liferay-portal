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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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
public class CucumberScenarioResult implements Serializable {

	public long getBackgroundDuration() {
		return _getDuration(_getBackgroundDocument());
	}

	public String getBackgroundName() {
		Document backgroundDocument = _getBackgroundDocument();

		if (backgroundDocument == null) {
			return null;
		}

		return "Background: " + _getName(backgroundDocument);
	}

	public String getBackgroundStatus() {
		return _getStatus(_getBackgroundDocument());
	}

	public List<Step> getBackgroundSteps() {
		return _getSteps(_getBackgroundDocument());
	}

	public CucumberFeatureResult getCucumberFeatureResult() {
		return _cucumberFeatureResult;
	}

	public long getDuration() {
		return getBackgroundDuration() + getScenarioDuration();
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

	public long getScenarioDuration() {
		return _getDuration(_getScenarioDocument());
	}

	public String getScenarioName() {
		return JenkinsResultsParserUtil.combine(
			_getKeyword(_getScenarioDocument()), ": ",
			_getName(_getScenarioDocument()));
	}

	public String getScenarioStatus() {
		return _getStatus(_getScenarioDocument());
	}

	public List<Step> getScenarioSteps() {
		return _getSteps(_getScenarioDocument());
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

		steps.addAll(getBackgroundSteps());
		steps.addAll(getScenarioSteps());

		return steps;
	}

	public static class Step implements Serializable {

		public long getDuration() {
			Element element = (Element)Dom4JUtil.getNodeByXPath(
				_getDocument(),
				"//div[@class='step']/div/span[contains(@class,'duration')]");

			if (element == null) {
				return 0;
			}

			return CucumberScenarioResult._getDurationFromString(
				element.getTextTrim());
		}

		public String getErrorDetails() {
			String status = getStatus();

			if (status.equals("PASSED")) {
				return null;
			}

			StringBuilder sb = new StringBuilder();

			sb.append("//div[@class='step']/div[@class='inner-level']");
			sb.append("/div[contains(@class,'message')]/div/a");

			Element element = (Element)Dom4JUtil.getNodeByXPath(
				_getDocument(), sb.toString());

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
				_getDocument(), sb.toString());

			if (element == null) {
				return null;
			}

			return element.getText();
		}

		public String getName() {
			Element element = (Element)Dom4JUtil.getNodeByXPath(
				_getDocument(), "//div[@class='step']/div/span[@class='name']");

			if (element == null) {
				return "";
			}

			return element.getStringValue();
		}

		public String getStatus() {
			Element element = (Element)Dom4JUtil.getNodeByXPath(
				_getDocument(),
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

		private Document _getDocument() {
			return _document;
		}

		private void readObject(ObjectInputStream objectInputStream)
			throws ClassNotFoundException, IOException {

			objectInputStream.defaultReadObject();

			try {
				_document = Dom4JUtil.parse(objectInputStream.readUTF());
			}
			catch (DocumentException documentException) {
				throw new RuntimeException(
					"Unable to deserialize document", documentException);
			}
		}

		private void writeObject(ObjectOutputStream objectOutputStream)
			throws IOException {

			objectOutputStream.defaultWriteObject();

			objectOutputStream.writeUTF(_document.asXML());
		}

		private transient Document _document;

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

	private static long _getDurationFromString(String durationString) {
		Matcher matcher = _durationPattern.matcher(durationString);

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

	private Document _getBackgroundDocument() {
		return _backgroundDocument;
	}

	private long _getDuration(Document document) {
		Element element = (Element)Dom4JUtil.getNodeByXPath(
			document,
			"//div[@class='element']/span[contains(@class,'duration')]");

		if (element == null) {
			return 0;
		}

		return _getDurationFromString(element.getTextTrim());
	}

	private String _getKeyword(Document document) {
		if (document == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("//div[@class='element']/span/div[contains(@class,'brief')]");
		sb.append("/span[contains(@class,'keyword')]");

		Node node = Dom4JUtil.getNodeByXPath(document, sb.toString());

		if (node == null) {
			return null;
		}

		return node.getText();
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

	private Document _getScenarioDocument() {
		return _scenarioDocument;
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

	private List<Step> _getSteps(Document document) {
		List<Step> steps = new ArrayList<>();

		List<Node> nodes = Dom4JUtil.getNodesByXPath(
			document, "//div[@class='step']");

		for (Node node : nodes) {
			Document stepDocument;

			try {
				stepDocument = Dom4JUtil.parse(node.asXML());
			}
			catch (DocumentException documentException) {
				continue;
			}

			steps.add(new Step(stepDocument));
		}

		return steps;
	}

	private void readObject(ObjectInputStream objectInputStream)
		throws ClassNotFoundException, IOException {

		objectInputStream.defaultReadObject();

		try {
			_backgroundDocument = Dom4JUtil.parse(objectInputStream.readUTF());

			_scenarioDocument = Dom4JUtil.parse(objectInputStream.readUTF());
		}
		catch (DocumentException documentException) {
			throw new RuntimeException(documentException);
		}
	}

	private void writeObject(ObjectOutputStream objectOutputStream)
		throws IOException {

		objectOutputStream.defaultWriteObject();

		objectOutputStream.writeUTF(_backgroundDocument.asXML());

		objectOutputStream.writeUTF(_scenarioDocument.asXML());
	}

	private static final Pattern _durationPattern = Pattern.compile(
		"((?<mins>\\d+)\\:)?(?<secs>\\d+)\\.(?<ms>\\d{3})");

	private transient Document _backgroundDocument;
	private final CucumberFeatureResult _cucumberFeatureResult;
	private transient Document _scenarioDocument;

}