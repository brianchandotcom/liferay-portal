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

package com.liferay.jenkins.results.parser.job.config.properties.map.generator;

import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.dom4j.Element;

/**
 * @author Cesar Polanco
 */
public class JenkinsJobConfigPropertiesMapGenerator {

	public JenkinsJobConfigPropertiesMapGenerator(
		Map<String, String> basePropertiesMap) {

		_basePropertiesMap = basePropertiesMap;

		_environmentSlavesPropertiesMap = _getEnvironmentSlavesMap();
		_generatedJenkinsJobConfigPropertiesMap = new HashMap<>();
	}

	public Map<String, String> generateJenkinsJobConfigPropertiesMap()
		throws Exception {

		Set<String> masterHostNamesSet = new HashSet<>();

		for (String basePropertyName : _basePropertiesMap.keySet()) {
			if (basePropertyName.startsWith("global.property(")) {
				_processGlobalProperty(basePropertyName);

				continue;
			}

			if (basePropertyName.startsWith("job.property(")) {
				_processJobProperty(basePropertyName);

				continue;
			}

			if (basePropertyName.startsWith("master.job.names(")) {
				masterHostNamesSet.add(_getMasterHostName(basePropertyName));

				_processMasterJobNames(basePropertyName);

				continue;
			}

			if (basePropertyName.startsWith("master.job.property(")) {
				_processMasterJobProperty(basePropertyName);

				continue;
			}

			if (basePropertyName.startsWith("master.property(")) {
				_processMasterProperty(basePropertyName);

				continue;
			}

			if (basePropertyName.startsWith("master.slaves(")) {
				_processMasterSlaves(basePropertyName);
			}
		}

		_putGeneratedJenkinsJobConfigProperty(
			"master.hostnames", masterHostNamesSet);

		_putGeneratedJenkinsJobConfigProperty(
			"slave.hostnames", _slaveHostNamesSet);

		int i = 0;

		for (String masterHostName : masterHostNamesSet) {
			if (masterHostName.startsWith("test-")) {
				_generatedJenkinsJobConfigPropertiesMap.put(
					"trigger.day." + masterHostName, Integer.toString(i % 7));

				_generatedJenkinsJobConfigPropertiesMap.put(
					"trigger.hour." + masterHostName, Integer.toString(i % 24));

				i++;
			}
		}

		return _generatedJenkinsJobConfigPropertiesMap;
	}

	private void _appendToGeneratedJenkinsJobConfigPropertyValue(
		String generatedJenkinsJobConfigPropertyName,
		String additionalGeneratedJenkinsJobConfigPropertyValue) {

		Set<String> generatedJenkinsJobConfigPropertyValuesSet =
			_getGeneratedJenkinsJobConfigPropertyValuesSet(
				generatedJenkinsJobConfigPropertyName);

		generatedJenkinsJobConfigPropertyValuesSet.add(
			additionalGeneratedJenkinsJobConfigPropertyValue);

		_putGeneratedJenkinsJobConfigProperty(
			generatedJenkinsJobConfigPropertyName,
			generatedJenkinsJobConfigPropertyValuesSet);
	}

	private Element _generateJobListViewElement(
		String parentJobName, String[] childJobNames) {

		Element listViewElement = Dom4JUtil.getNewElement("listView", null);

		Element ownerElement = Dom4JUtil.getNewElement(
			"owner", listViewElement);

		ownerElement.addAttribute("class", "hudson");
		ownerElement.addAttribute("reference", "../../..");

		Dom4JUtil.addToElement(
			listViewElement,
			Dom4JUtil.getNewElement("name", null, parentJobName),
			Dom4JUtil.getNewElement("filterExecutors", null, "true"),
			Dom4JUtil.getNewElement("filterQueue", null, "true"));

		Element propertiesElement = Dom4JUtil.getNewElement(
			"properties", listViewElement);

		propertiesElement.addAttribute(
			"class", "hudson.model.View$PropertyList");

		Element jobNamesElement = Dom4JUtil.getNewElement(
			"jobNames", listViewElement);

		Element comparatorElement = Dom4JUtil.getNewElement(
			"comparator", jobNamesElement);

		comparatorElement.addAttribute(
			"class", "hudson.util.CaseInsensitiveComparator");

		Arrays.sort(childJobNames);

		for (String childJobName : childJobNames) {
			Dom4JUtil.getNewElement("string", jobNamesElement, childJobName);
		}

		Dom4JUtil.getNewElement("jobFilters", listViewElement);

		Element columnsElement = Dom4JUtil.getNewElement(
			"columns", listViewElement);

		Dom4JUtil.getNewElement("recurse", listViewElement, "false");

		Dom4JUtil.addToElement(
			columnsElement,
			Dom4JUtil.getNewElement("hudson.views.StatusColumn", null),
			Dom4JUtil.getNewElement("hudson.views.WeatherColumn", null),
			Dom4JUtil.getNewElement("hudson.views.JobColumn", null));

		Element descriptiveColumnNameElement = Dom4JUtil.getNewElement(
			"jenkins.plugins.extracolumns.DescriptionColumn", columnsElement);

		descriptiveColumnNameElement.addAttribute(
			"plugin", "extra-columns@1.11");

		Dom4JUtil.addToElement(
			descriptiveColumnNameElement,
			Dom4JUtil.getNewElement("displayName", null, "false"),
			Dom4JUtil.getNewElement("trim", null, "false"),
			Dom4JUtil.getNewElement("displayLength", null, "1"));

		Dom4JUtil.addToElement(
			columnsElement,
			Dom4JUtil.getNewElement("hudson.views.LastSuccessColumn", null),
			Dom4JUtil.getNewElement("hudson.views.LastFailureColumn", null),
			Dom4JUtil.getNewElement("hudson.views.LastDurationColumn", null),
			Dom4JUtil.getNewElement("hudson.views.BuildButtonColumn", null));

		return listViewElement;
	}

	private Map<String, String> _getEnvironmentSlavesMap() {
		Map<String, String> environmentSlavesMap = new HashMap<>();

		for (Map.Entry<String, String> entry : _basePropertiesMap.entrySet()) {
			String key = entry.getKey();

			if (!key.startsWith("environment.slaves(")) {
				continue;
			}

			String environmentName = _getFirstRegexMatchGroup(
				key, _reluctantParenthesesPattern);

			String slaveHostNames = JenkinsResultsParserUtil.expandSlaveRange(
				_basePropertiesMap.get(key));

			for (String slaveHostName : slaveHostNames.split(",")) {
				environmentSlavesMap.put(slaveHostName, environmentName);
			}
		}

		return environmentSlavesMap;
	}

	private String _getFirstRegexMatchGroup(String string, Pattern regex) {
		List<String> properties = _getRegexMatchGroupList(string, regex);

		if (properties.isEmpty()) {
			return null;
		}

		return properties.get(0);
	}

	private String _getFormattedXML(Element element) {
		String formattedXML = null;

		try {
			formattedXML = Dom4JUtil.format(element, true);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		formattedXML = formattedXML.replaceAll(
			"(\\<[\\w\\-\\.]+)(/\\>)", "$1 $2");

		if (formattedXML.startsWith("\n")) {
			formattedXML = formattedXML.substring(1);
		}

		return formattedXML;
	}

	private String _getFormattedXML(List<Element> elements) {
		StringBuilder sb = new StringBuilder();

		for (Element element : elements) {
			if (sb.length() > 0) {
				sb.append("\n");
			}

			sb.append(_getFormattedXML(element));
		}

		return sb.toString();
	}

	private Set<String> _getGeneratedJenkinsJobConfigPropertyValuesSet(
		String generatedJenkinsJobConfigPropertyName) {

		Set<String> generatedJenkinsJobConfigPropertyValuesSet =
			new HashSet<>();

		String generatedJenkinsJobConfigPropertyValue =
			_generatedJenkinsJobConfigPropertiesMap.get(
				generatedJenkinsJobConfigPropertyName);

		if (generatedJenkinsJobConfigPropertyValue == null) {
			return generatedJenkinsJobConfigPropertyValuesSet;
		}

		Collections.addAll(
			generatedJenkinsJobConfigPropertyValuesSet,
			StringUtils.split(generatedJenkinsJobConfigPropertyValue, ','));

		return generatedJenkinsJobConfigPropertyValuesSet;
	}

	private String _getJobBranchName(String fullJobName) {
		return _getFirstRegexMatchGroup(
			fullJobName, _masterJobBranchNamePattern);
	}

	private String _getJobShortName(String fullJobName) {
		return _getFirstRegexMatchGroup(fullJobName, _jobShortNamePattern);
	}

	private String _getJobVariantName(String fullJobName) {
		return _getFirstRegexMatchGroup(
			fullJobName, _reluctantParenthesesPattern);
	}

	private Map<String, String> _getLinuxEnvironmentVariablesMap() {
		Map<String, String> linuxEnvironmentVariablesMap = new HashMap<>();
		String pathValue = JenkinsResultsParserUtil.combine(
			"/bin:/opt/android/sdk:/opt/android/sdk/platform-tools:",
			"/opt/android/sdk/tools:/opt/ibm/db2/V10.5/bin:/opt/java/ant/bin:",
			"/opt/java/jdk/bin:/opt/java/maven/bin:/opt/oracle/bin:",
			"/opt/sybase/OCS-16_0/bin:/sbin:/usr/bin:/usr/local/bin:",
			"/usr/local/sbin:/usr/sbin");

		linuxEnvironmentVariablesMap.put("PATH", pathValue);

		return linuxEnvironmentVariablesMap;
	}

	private String _getMasterHostName(String propertyName) {
		return _getFirstRegexMatchGroup(
			propertyName, _reluctantParenthesesPattern);
	}

	private List<String> _getMasterJobPropertiesList(String basePropertyName) {
		List<String> regextMatchGroupList = _getRegexMatchGroupList(
			basePropertyName, _masterJobPropertyPattern);

		return Arrays.asList(
			regextMatchGroupList.get(1), regextMatchGroupList.get(0),
			regextMatchGroupList.get(2));
	}

	private Map<String, String> _getOSXEnvironmentVariablesMap() {
		Map<String, String> osxEnvironmentVariablesMap = new HashMap<>();

		osxEnvironmentVariablesMap.put("HOME", "/Users/administrator");

		String pathValue = JenkinsResultsParserUtil.combine(
			"/bin:/opt/java/ant/bin:/opt/java/jdk/bin:/opt/java/maven/bin:",
			"/sbin:/usr/bin:/usr/local/bin:/usr/local/sbin:/usr/sbin");

		osxEnvironmentVariablesMap.put("PATH", pathValue);

		return osxEnvironmentVariablesMap;
	}

	private List<String> _getRegexMatchGroupList(String string, Pattern regex) {
		Matcher propertyMatcher = regex.matcher(string);

		List<String> matchedGroups = new ArrayList<>();

		while (propertyMatcher.find()) {
			for (int i = 1; i <= propertyMatcher.groupCount(); i++) {
				matchedGroups.add(propertyMatcher.group(i));
			}
		}

		return matchedGroups;
	}

	private String _getShortChildJobName(String childJobName) {
		return _getFirstRegexMatchGroup(
			childJobName, _shortChildJobNamePattern);
	}

	private Element _getSlaveConfigElement(String slaveHostName) {
		String slaveLabel = slaveHostName;

		Map<String, String> environmentSlavesMap = _getEnvironmentSlavesMap();

		if (environmentSlavesMap.containsKey(slaveHostName)) {
			slaveLabel = environmentSlavesMap.get(slaveHostName);
		}

		Element slaveElement = Dom4JUtil.getNewElement(
			"slave", null, Dom4JUtil.getNewElement("name", null, slaveHostName),
			Dom4JUtil.getNewElement("description", null),
			Dom4JUtil.getNewElement("remoteFS", null, "/opt/java/jenkins"),
			Dom4JUtil.getNewElement("numExecutors", null, "1"),
			Dom4JUtil.getNewElement("mode", null, "NORMAL"));

		Element rententionStrategyElement = Dom4JUtil.getNewElement(
			"retentionStrategy", slaveElement);

		rententionStrategyElement.addAttribute(
			"class", "hudson.slaves.RetentionStrategy$Always");

		Element launcherElement = Dom4JUtil.getNewElement(
			"launcher", slaveElement);

		launcherElement.addAttribute("class", "hudson.slaves.JNLPLauncher");

		Dom4JUtil.getNewElement("label", slaveElement, slaveLabel);

		Element nodePropertiesElement = Dom4JUtil.getNewElement(
			"nodeProperties", slaveElement);

		Element environmentVariablesNodePropertyElement =
			Dom4JUtil.getNewElement(
				"hudson.slaves.EnvironmentVariablesNodeProperty",
				nodePropertiesElement);

		Element envVarsElement = Dom4JUtil.getNewElement(
			"envVars", environmentVariablesNodePropertyElement,
			Dom4JUtil.getNewElement("unserializable-parents", null));

		envVarsElement.addAttribute("serialization", "custom");

		Element treeMapElement = Dom4JUtil.getNewElement(
			"tree-map", envVarsElement,
			Dom4JUtil.getNewElement("default", null));

		if (slaveLabel.contains("osx")) {
			List<Element> osxEnvironmentVariablesElements =
				_getSlaveEnvironmentVariableElementsList(
					_getOSXEnvironmentVariablesMap(), slaveHostName);

			Dom4JUtil.addToElement(
				treeMapElement, osxEnvironmentVariablesElements.toArray());

			return slaveElement;
		}

		if (slaveLabel.contains("win")) {
			List<Element> windowsEnvironmentVariablesElements =
				_getSlaveEnvironmentVariableElementsList(
					_getWindowsEnvironmentVariablesMap(), slaveHostName);

			Dom4JUtil.addToElement(
				treeMapElement, windowsEnvironmentVariablesElements.toArray());

			return slaveElement;
		}

		List<Element> linuxEnvironmentVariablesElements =
			_getSlaveEnvironmentVariableElementsList(
				_getLinuxEnvironmentVariablesMap(), slaveHostName);

		Dom4JUtil.addToElement(
			treeMapElement, linuxEnvironmentVariablesElements.toArray());

		return slaveElement;
	}

	private List<Element> _getSlaveEnvironmentVariableElementsList(
		Map<String, String> environmentVariablesMap, String hostName) {

		environmentVariablesMap.put("HOSTNAME", hostName + ".lax.liferay.com");

		List<Element> environmentVariableElements = new ArrayList<>();

		Element sizeElement = Dom4JUtil.getNewElement(
			"int", null, Integer.toString(environmentVariablesMap.size()));

		environmentVariableElements.add(sizeElement);

		List<String> environmentVariablesList = new ArrayList<>();

		environmentVariablesList.addAll(environmentVariablesMap.keySet());

		Collections.sort(environmentVariablesList);

		for (String environmentVariable : environmentVariablesList) {
			Element nameElement = Dom4JUtil.getNewElement(
				"string", null, environmentVariable);
			Element valueElement = Dom4JUtil.getNewElement(
				"string", null,
				environmentVariablesMap.get(environmentVariable));

			environmentVariableElements.add(nameElement);
			environmentVariableElements.add(valueElement);
		}

		return environmentVariableElements;
	}

	private String _getTriggerBuilderChildJobName(String triggerBuilder) {
		return _getFirstRegexMatchGroup(
			triggerBuilder, _triggerBuilderChildJobNamePattern);
	}

	private Map<String, String> _getWindowsEnvironmentVariablesMap() {
		Map<String, String> windowsEnvironmentVariablesMap = new HashMap<>();

		String path = JenkinsResultsParserUtil.combine(
			"/c/app/Administrator/product/12.2.0/dbhome_1/bin",
			"/c/ant/bin:/c/Perl64/bin:/c/Program Files/7-Zip:",
			"/c/Program Files/IBM/SQLLIB/BIN:",
			"/c/Program Files/Java/jdk1.7.0_55/bin:",
			"/c/Program Files/MySQL/MySQL Server 5.6/bin:",
			"/c/Program Files/Microsoft SQL Server/100/Tools/Binn:",
			"/c/Program Files/Microsoft SQL Server/110/Tools/Binn:",
			"/c/Program Files/Microsoft SQL Server/120/Tools/Binn:",
			"/c/Program Files/Microsoft SQL Server/130/Tools/Binn:",
			"/c/Program Files/Microsoft SQL Server/Client ",
			"SDK/ODBC/110/Tools/Binn:",
			"/c/Program Files/Microsoft SQL Server/Client ",
			"SDK/ODBC/130/Tools/Binn:/c/Windows:/c/Windows/system32");

		windowsEnvironmentVariablesMap.put("HOME", "/c/Users/Administrator");
		windowsEnvironmentVariablesMap.put(
			"JAVA_HOME", "/c/Program Files/Java/jdk1.7.0_55");
		windowsEnvironmentVariablesMap.put("PATH", path);

		return windowsEnvironmentVariablesMap;
	}

	private void _processGlobalProperty(String basePropertyName) {
		String globalPropertyName = _getFirstRegexMatchGroup(
			basePropertyName, _greedyParenthesesPattern);

		_appendToGeneratedJenkinsJobConfigPropertyValue(
			"global.property.names", globalPropertyName);
	}

	private void _processJobProperty(String basePropertyName) {
		List<String> regexMatchGroupList = _getRegexMatchGroupList(
			basePropertyName, _masterPropertyPattern);

		String jobName = regexMatchGroupList.get(0);
		String jobPropertyName = regexMatchGroupList.get(1);

		String generatedPropertyName = JenkinsResultsParserUtil.combine(
			"job.properties(", jobName, ")");

		_appendToGeneratedJenkinsJobConfigPropertyValue(
			generatedPropertyName, jobPropertyName);
	}

	private void _processMasterJobNames(String basePropertyName)
		throws Exception {

		String masterHostName = _getMasterHostName(basePropertyName);

		String basePropertyValue = _basePropertiesMap.get(basePropertyName);
		Map<String, String> childJobNamesMap = new HashMap<>();
		String currentParentJobName1 = null;
		String currentParentJobName2 = null;
		List<String> parentJobNames = new ArrayList<>();
		Set<String> topLevelJobNames = new HashSet<>();

		for (String fullJobName : basePropertyValue.split(",")) {
			if (fullJobName.contains("(")) {
				_generatedJenkinsJobConfigPropertiesMap.put(
					"job.portal.branch.name(" + fullJobName + ")",
					_getJobBranchName(fullJobName));
				_generatedJenkinsJobConfigPropertiesMap.put(
					"job.variation.name(" + fullJobName + ")",
					_getJobVariantName(fullJobName));
			}
			else {
				_generatedJenkinsJobConfigPropertiesMap.put(
					"job.variation.name(" + fullJobName + ")", "");
			}

			String jobShortName = _getJobShortName(fullJobName);

			_generatedJenkinsJobConfigPropertiesMap.put(
				"job.short.name(" + fullJobName + ")", jobShortName);

			String jobConfigTemplateFileContent = JenkinsResultsParserUtil.read(
				new File("template/jobs/" + jobShortName + "/config.xml"));

			if (jobConfigTemplateFileContent.contains("Build Flow") ||
				jobConfigTemplateFileContent.contains(
					"com.cloudbees.plugins.flow.BuildFlow")) {

				_generatedJenkinsJobConfigPropertiesMap.put(
					"job.content.type(" + fullJobName + ")", "BuildFlow");

				childJobNamesMap.put(fullJobName, fullJobName);
				parentJobNames.add(fullJobName);
			}
			else if (jobConfigTemplateFileContent.contains(
						"hudson.plugins.parameterizedtrigger." +
							"BlockableBuildTriggerConfig") &&
					 (fullJobName.contains("fixpack") ||
					  fullJobName.contains("maintenance-tags"))) {

				_generatedJenkinsJobConfigPropertiesMap.put(
					"job.content.type(" + fullJobName + ")", "BuildTrigger");

				parentJobNames.add(fullJobName);

				Set<String> triggerBuilderChildJobNames = new HashSet<>();

				triggerBuilderChildJobNames.add(fullJobName);

				Pattern triggerBuilderPattern = Pattern.compile(
					JenkinsResultsParserUtil.combine(
						"hudson\\.plugins\\.parameterizedtrigger\\",
						".(TriggerBuilder|BuildTrigger)(.*?)/hudson",
						"\\.plugins\\.parameterizedtrigger\\",
						".(TriggerBuilder|BuildTrigger)"),
					Pattern.DOTALL);

				Matcher triggerBuilderMatcher = triggerBuilderPattern.matcher(
					jobConfigTemplateFileContent);

				while (triggerBuilderMatcher.find()) {
					String triggerBuilder = triggerBuilderMatcher.group(2);

					String triggerBuilderChildJobName =
						_getTriggerBuilderChildJobName(triggerBuilder);

					if (!triggerBuilderChildJobName.equals(
							"@!child.job.names!@")) {

						triggerBuilderChildJobName =
							triggerBuilderChildJobName.replace(
								"@!job.variation.name!@",
								_generatedJenkinsJobConfigPropertiesMap.get(
									"job.variation.name(" + fullJobName + ")"));

						triggerBuilderChildJobNames.add(
							triggerBuilderChildJobName);
					}
				}

				childJobNamesMap.put(
					fullJobName,
					StringUtils.join(triggerBuilderChildJobNames, ','));
			}

			if (jobConfigTemplateFileContent.contains("Top Level")) {
				topLevelJobNames.add(fullJobName);
			}

			if (parentJobNames.contains(fullJobName) &&
				topLevelJobNames.contains(fullJobName)) {

				currentParentJobName1 = fullJobName;
			}
			else if (parentJobNames.contains(fullJobName)) {
				currentParentJobName2 = fullJobName;
			}

			String currentParentJobName = null;

			if (currentParentJobName2 != null) {
				String currentParentJobShortName2 =
					_generatedJenkinsJobConfigPropertiesMap.get(
						"job.short.name(" + currentParentJobName2 + ")");
				String currentParentJobVariationName2 =
					_generatedJenkinsJobConfigPropertiesMap.get(
						"job.variation.name(" + currentParentJobName2 + ")");
				boolean memberOfCurrentParentJob = false;

				if (fullJobName.startsWith(currentParentJobShortName2)) {
					if (fullJobName.endsWith(
							"(" + currentParentJobVariationName2 +
								")") ||
						currentParentJobVariationName2.equals("")) {

						memberOfCurrentParentJob = true;
					}
				}

				if (memberOfCurrentParentJob &&
					!fullJobName.equals(currentParentJobName2)) {

					currentParentJobName = currentParentJobName2;
				}
				else if (!memberOfCurrentParentJob) {
					currentParentJobName2 = null;
				}
			}

			if ((currentParentJobName == null) &&
				(currentParentJobName1 != null)) {

				String currentParentJobShortName1 =
					_generatedJenkinsJobConfigPropertiesMap.get(
						"job.short.name(" + currentParentJobName1 + ")");
				String currentParentJobVariationName1 =
					_generatedJenkinsJobConfigPropertiesMap.get(
						"job.variation.name(" + currentParentJobName1 + ")");
				boolean memberOfCurrentParentJob = false;

				if (fullJobName.startsWith(currentParentJobShortName1)) {
					if (fullJobName.endsWith(
							"(" + currentParentJobVariationName1 +
								")") ||
						currentParentJobVariationName1.equals("")) {

						memberOfCurrentParentJob = true;
					}
				}

				if (memberOfCurrentParentJob &&
					!fullJobName.equals(currentParentJobName1)) {

					currentParentJobName = currentParentJobName1;
				}
				else if (!memberOfCurrentParentJob) {
					currentParentJobName1 = null;
				}
			}

			if (currentParentJobName != null) {
				Set<String> childJobNames = new HashSet<>();

				String childJobNamesString = childJobNamesMap.get(
					currentParentJobName);

				for (String childJobName : childJobNamesString.split(",")) {
					childJobNames.add(childJobName);
				}

				if (fullJobName.contains("[component.name]")) {
					String componentNames =
						_generatedJenkinsJobConfigPropertiesMap.get(
							"job.component.names(" + fullJobName + ")");

					if (componentNames == null) {
						componentNames = "portal-acceptance";
					}

					for (String componentName : componentNames.split(",")) {
						childJobNames.add(
							fullJobName.replace(
								"[component.name]", "[" + componentName + "]"));
					}
				}
				else {
					childJobNames.add(fullJobName);
				}

				childJobNamesMap.put(
					currentParentJobName, StringUtils.join(childJobNames, ','));
			}
		}

		for (String parentJobName : parentJobNames) {
			String childJobNames = childJobNamesMap.get(parentJobName);

			if (!childJobNames.contains(",")) {
				throw new Exception(
					"Unable to find child jobs for the parent job " +
						parentJobName);
			}

			_generatedJenkinsJobConfigPropertiesMap.put(
				"child.job.names(" + parentJobName + ")",
				childJobNamesMap.get(parentJobName));
		}

		StringBuilder sb = new StringBuilder();

		List<String> topLevelJobNamesList = new ArrayList<>();

		topLevelJobNamesList.addAll(topLevelJobNames);

		Collections.sort(topLevelJobNamesList);

		List<Element> topLevelJobNameElementsList = new ArrayList<>(
			topLevelJobNamesList.size());

		for (String topLevelJobName : topLevelJobNamesList) {
			String excludeTopLevelView =
				_generatedJenkinsJobConfigPropertiesMap.get(
					"job.property(" + topLevelJobName +
						"/exclude.top.level.view)");

			if ((excludeTopLevelView == null) ||
				!excludeTopLevelView.equals("true")) {

				topLevelJobNameElementsList.add(
					Dom4JUtil.getNewElement("string", null, topLevelJobName));
			}
		}

		_generatedJenkinsJobConfigPropertiesMap.put(
			"master.top.level.jobs.xml(" + masterHostName + ")",
			_getFormattedXML(topLevelJobNameElementsList));

		String masterPrimaryView = _generatedJenkinsJobConfigPropertiesMap.get(
			"master.primary.view(" + masterHostName + ")");

		if (masterPrimaryView == null) {
			if (topLevelJobNames.isEmpty()) {
				_generatedJenkinsJobConfigPropertiesMap.put(
					"master.primary.view(" + masterHostName + ")", "All");
			}
			else {
				_generatedJenkinsJobConfigPropertiesMap.put(
					"master.primary.view(" + masterHostName + ")", "Top Level");
			}
		}

		List<Element> listViewList = new ArrayList<>();

		for (String parentJobName : parentJobNames) {
			String childJobNames = _generatedJenkinsJobConfigPropertiesMap.get(
				"child.job.names(" + parentJobName + ")");

			String[] childJobNamesArray = childJobNames.split(",");

			Arrays.sort(childJobNamesArray);

			listViewList.add(
				_generateJobListViewElement(parentJobName, childJobNamesArray));
		}

		_generatedJenkinsJobConfigPropertiesMap.put(
			"master.list.views.xml(" + masterHostName + ")",
			_getFormattedXML(listViewList));

		for (String parentJobName : parentJobNames) {
			sb = new StringBuilder();

			String childJobNames = _generatedJenkinsJobConfigPropertiesMap.get(
				"child.job.names(" + parentJobName + ")");
			Set<String> childJobNamesSet = new HashSet<>();

			for (String childJobName : childJobNames.split(",")) {
				if (childJobName.equals(parentJobName)) {
					continue;
				}

				if (childJobName.contains("-githubpost-start") ||
					childJobName.contains("-githubpost-stop") ||
					childJobName.contains("-patcherpost-start") ||
					childJobName.contains("-patcherpost-stop") ||
					childJobName.contains("-prepare") ||
					childJobName.contains("-source") ||
					childJobName.contains("-test-results")) {

					continue;
				}

				String jobContentType =
					_generatedJenkinsJobConfigPropertiesMap.get(
						"job.content.type(" + parentJobName + ")");

				if (jobContentType.equals("BuildTrigger")) {
					String shortParentJobName =
						_generatedJenkinsJobConfigPropertiesMap.get(
							"job.short.name(" + parentJobName + ")");

					String shortChildJobName = _getShortChildJobName(
						childJobName);

					if (!shortChildJobName.contains(shortParentJobName)) {
						continue;
					}
				}

				boolean ignoreChildJobName = false;

				String jobName = childJobName;

				if (jobName.matches(".*[^\\[]+\\[[a-z\\-]+\\].*")) {
					jobName = jobName.replaceAll(
						"\\[[a-z\\-]+\\]", "[component.name]");
				}

				String componentNamesIgnore =
					_generatedJenkinsJobConfigPropertiesMap.get(
						"job.component.names.ignore(" + jobName + ")");

				if (componentNamesIgnore != null) {
					for (String componentNameIgnore :
							componentNamesIgnore.split(",")) {

						if (childJobName.contains(
								"[" + componentNameIgnore + "]")) {

							ignoreChildJobName = true;

							break;
						}
					}
				}

				sb.append("\n");
				sb.append("\t{\n");

				if (ignoreChildJobName) {
					sb.append("\t\tignore(ABORTED) {\n\t");
				}

				sb.append("\t\tJOB_INVOCATION_");
				sb.append(childJobNamesSet.size());
				sb.append(" = build(\"");
				sb.append(childJobName);

				String customJobParametersGeneratedPropertyValue =
					_generatedJenkinsJobConfigPropertiesMap.get(
						"job.property(" + jobName + "/custom.job.parameters)");

				if (customJobParametersGeneratedPropertyValue != null) {
					sb.append("\", @!custom.job.parameters!@)\n");

					_generatedJenkinsJobConfigPropertiesMap.put(
						"custom.job.parameters(" + parentJobName + ")",
						customJobParametersGeneratedPropertyValue);
				}
				else {
					sb.append("\", @!job.parameters!@)\n");
				}

				if (ignoreChildJobName) {
					sb.append("\t\t}\n");
				}

				sb.append("\t},");

				childJobNamesSet.add(childJobName);
			}

			if (sb.length() != 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			_generatedJenkinsJobConfigPropertiesMap.put(
				"build.flow.downstream.jobs.xml(" + parentJobName + ")",
				sb.toString());

			_putGeneratedJenkinsJobConfigProperty(
				"child.job.names(" + parentJobName + ")", childJobNamesSet);

			sb = new StringBuilder();

			for (int i = 0; i < childJobNamesSet.size(); i++) {
				sb.append("JOB_INVOCATION_");
				sb.append(i);
				sb.append(" = build\n");
			}

			_generatedJenkinsJobConfigPropertiesMap.put(
				JenkinsResultsParserUtil.combine(
					"job.invocation.instantiate.list", parentJobName, ")"),
				sb.toString());

			sb = new StringBuilder();

			for (int i = 0; i < childJobNamesSet.size(); i++) {
				sb.append("JOB_INVOCATIONS += JOB_INVOCATION_");
				sb.append(i);
				sb.append("\n");
			}

			_generatedJenkinsJobConfigPropertiesMap.put(
				"job.invocation.make.list(" + parentJobName + ")",
				sb.toString());
		}
	}

	private void _processMasterJobProperty(String basePropertyName) {
		List<String> masterJobPropertiesList = _getMasterJobPropertiesList(
			basePropertyName);

		String jobName = masterJobPropertiesList.get(0);
		String masterHostName = masterJobPropertiesList.get(1);

		String generatedPropertyName = JenkinsResultsParserUtil.combine(
			"master.job.properties(", masterHostName, "/", jobName, ")");

		_appendToGeneratedJenkinsJobConfigPropertyValue(
			generatedPropertyName, masterJobPropertiesList.get(2));
	}

	private void _processMasterProperty(String basePropertyName) {
		List<String> regexMatchGroupList = _getRegexMatchGroupList(
			basePropertyName, _masterPropertyPattern);

		String masterHostName = regexMatchGroupList.get(0);
		String masterHostPropertyName = regexMatchGroupList.get(1);

		String generatedPropertyName = JenkinsResultsParserUtil.combine(
			"master.properties(", masterHostName, ")");

		_appendToGeneratedJenkinsJobConfigPropertyValue(
			generatedPropertyName, masterHostPropertyName);
	}

	private void _processMasterSlaves(String basePropertyName)
		throws IOException {

		String masterHostName = _getMasterHostName(basePropertyName);

		String basePropertyValue = _basePropertiesMap.get(basePropertyName);

		if (basePropertyValue.contains("..")) {
			StringBuilder sb = new StringBuilder();

			basePropertyValue = JenkinsResultsParserUtil.expandSlaveRange(
				basePropertyValue);

			for (String slaveHostName : basePropertyValue.split(",")) {
				sb.append(slaveHostName);
				sb.append("(");

				String environmentSlaveOSType = "";

				if (_environmentSlavesPropertiesMap.containsKey(
						slaveHostName)) {

					String environmentSlavePropertyValue =
						_environmentSlavesPropertiesMap.get(slaveHostName);

					environmentSlaveOSType =
						environmentSlavePropertyValue.substring(
							0, environmentSlavePropertyValue.indexOf("."));
				}

				if (environmentSlaveOSType.contains("osx")) {
					environmentSlaveOSType = "osx";
				}
				else if (environmentSlaveOSType.contains("solaris")) {
					environmentSlaveOSType = "solaris";
				}
				else if (environmentSlaveOSType.contains("win")) {
					environmentSlaveOSType = "windows";
				}
				else {
					environmentSlaveOSType = "linux";
				}

				sb.append(environmentSlaveOSType);

				sb.append("),");
			}

			basePropertyValue = sb.toString();
		}

		_generatedJenkinsJobConfigPropertiesMap.put(
			"master.slaves.txt(" + masterHostName + ")",
			basePropertyValue.replaceAll(",", "\n").trim());

		Element slavesElement = Dom4JUtil.getNewElement("slaves", null);

		for (String slaveHostName : basePropertyValue.split(",")) {
			if (slaveHostName.contains("(")) {
				slaveHostName = slaveHostName.substring(
					0, slaveHostName.indexOf("("));
			}

			Element slaveConfigElement = _getSlaveConfigElement(slaveHostName);

			_generatedJenkinsJobConfigPropertiesMap.put(
				slaveHostName + ".config.xml.content",
				_getFormattedXML(slaveConfigElement));

			Dom4JUtil.addToElement(slavesElement, slaveConfigElement);

			_slaveHostNamesSet.add(slaveHostName);

			_generatedJenkinsJobConfigPropertiesMap.put(
				"slave.master(" + slaveHostName + ")", masterHostName);
		}

		_generatedJenkinsJobConfigPropertiesMap.put(
			"master.slaves.xml(" + masterHostName + ")",
			_getFormattedXML(slavesElement));

		List<Element> slaveHostNamesList = new ArrayList<>();

		for (String slaveHostName : basePropertyValue.split(",")) {
			if (slaveHostName.contains("(")) {
				slaveHostName = slaveHostName.substring(
					0, slaveHostName.indexOf("("));
			}

			Element slaveHostNameElement = Dom4JUtil.getNewElement(
				"string", null, slaveHostName);

			slaveHostNamesList.add(slaveHostNameElement);
		}

		_generatedJenkinsJobConfigPropertiesMap.put(
			"master.axes.label.slaves.xml(" + masterHostName + ")",
			_getFormattedXML(slaveHostNamesList));
	}

	private void _putGeneratedJenkinsJobConfigProperty(
		String generatedJenkinsJobConfigPropertyName, Set<String> valuesSet) {

		_generatedJenkinsJobConfigPropertiesMap.put(
			generatedJenkinsJobConfigPropertyName,
			StringUtils.join(valuesSet, ','));
	}

	private static final Pattern _greedyParenthesesPattern = Pattern.compile(
		"\\((.+)\\)");
	private static final Pattern _jobShortNamePattern = Pattern.compile(
		"([^\\(]+)\\(?");
	private static final Pattern _masterJobBranchNamePattern = Pattern.compile(
		"\\((.+?)[_|\\)]");
	private static final Pattern _masterJobPropertyPattern = Pattern.compile(
		"\\((.+?)/(.+?)/(.+)\\)");
	private static final Pattern _masterPropertyPattern = Pattern.compile(
		"\\((.+?)/(.+)\\)");
	private static final Pattern _reluctantParenthesesPattern = Pattern.compile(
		"\\((.+?)\\)");
	private static final Pattern _shortChildJobNamePattern = Pattern.compile(
		"(.+?)[\\[|\\(]?");
	private static final Pattern _triggerBuilderChildJobNamePattern =
		Pattern.compile("<projects>(.+)</projects>");

	private final Map<String, String> _basePropertiesMap;
	private final Map<String, String> _environmentSlavesPropertiesMap;
	private final Map<String, String> _generatedJenkinsJobConfigPropertiesMap;
	private final Set<String> _slaveHostNamesSet = new HashSet<>();

}