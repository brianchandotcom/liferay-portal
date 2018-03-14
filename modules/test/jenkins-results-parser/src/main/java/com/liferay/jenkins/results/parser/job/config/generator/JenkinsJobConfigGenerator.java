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

package com.liferay.jenkins.results.parser.job.config.generator;

import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Cesar Polanco
 */
public class JenkinsJobConfigGenerator {

	public JenkinsJobConfigGenerator(Map<String, String> properties) {
		_properties = properties;

		_environmentSlavesMap = _getEnvironmentSlavesMap();
		_generatedPropertiesMap = new HashMap<>(_properties);

		_globalProperties = new HashSet<>();
		_jobNamesToJobProperties = new HashMap<>();
		_jobNamesToMasterJobProperties = new HashMap<>();
		_masterHostnames = new HashSet<>();
		_masterHostnamesToJobNames = new HashMap<>();
		_masterHostnamesToMasterProperties = new HashMap<>();
		_slaveHostnames = new HashSet<>();
	}

	public Map<String, String> getJobConfig() throws Exception {
		for (Map.Entry<String, String> entry : _properties.entrySet()) {
			String key = entry.getKey();

			if (key.startsWith("global.property(")) {
				_processGlobalProperty(key);

				continue;
			}

			String value = entry.getValue();

			if (key.startsWith("job.property(")) {
				_processJobProperty(key, value);

				continue;
			}

			if (key.startsWith("master.job.names(")) {
				_processMasterJobName(key, value);

				continue;
			}

			if (key.startsWith("master.job.property(")) {
				_processMasterJobProperty(key, value);

				continue;
			}

			if (key.startsWith("master.property(")) {
				_processMasterProperty(key, value);

				continue;
			}

			if (key.startsWith("master.slaves(")) {
				_processMasterSlaves(key, value);
			}
		}

		_generatedPropertiesMap.put(
			"master.hostnames", _joinStrings(_masterHostnames, ","));

		_generatedPropertiesMap.put(
			"slave.hostnames", _joinStrings(_slaveHostnames, ","));

		int i = 0;

		for (String masterHostname : _masterHostnames) {
			if (masterHostname.startsWith("test-")) {
				_generatedPropertiesMap.put(
					"trigger.day." + masterHostname, Integer.toString(i % 7));

				_generatedPropertiesMap.put(
					"trigger.hour." + masterHostname, Integer.toString(i % 24));

				i++;
			}
		}

		return _generatedPropertiesMap;
	}

	private Element _generateListViewByParent(
		String parentJobName, String[] childJobNames) {

		Element listViewElement = Dom4JUtil.getNewElement("listView", null);

		Dom4JUtil.addToElement(
			listViewElement, Dom4JUtil.getNewElement("owner", null),
			Dom4JUtil.getNewElement("name", null, parentJobName),
			Dom4JUtil.getNewElement("filterExecutors", null, "true"),
			Dom4JUtil.getNewElement("filterQueue", null, "true"),
			Dom4JUtil.getNewElement("properties", null),
			Dom4JUtil.getNewElement("jobNames", null),
			Dom4JUtil.getNewElement("jobFilters", null),
			Dom4JUtil.getNewElement("columns", null),
			Dom4JUtil.getNewElement("recurse", null, "false"));

		Dom4JUtil.addToElement(
			listViewElement.element("jobNames"),
			Dom4JUtil.getNewElement("comparator", null));

		for (String childJob : childJobNames) {
			Dom4JUtil.addToElement(
				listViewElement.element("jobNames"),
				Dom4JUtil.getNewElement("string", null, childJob));
		}

		String descriptionColumnName =
			"jenkins.plugins.extracolumns.DescriptionColumn";

		Dom4JUtil.addToElement(
			listViewElement.element("columns"),
			Dom4JUtil.getNewElement("hudson.views.StatusColumn", null),
			Dom4JUtil.getNewElement("hudson.views.WeatherColumn", null),
			Dom4JUtil.getNewElement("hudson.views.JobColumn", null),
			Dom4JUtil.getNewElement(descriptionColumnName, null),
			Dom4JUtil.getNewElement("hudson.views.LastSuccessColumn", null),
			Dom4JUtil.getNewElement("hudson.views.LastFailureColumn", null),
			Dom4JUtil.getNewElement("hudson.views.LastDurationColumn", null),
			Dom4JUtil.getNewElement("hudson.views.BuildButtonColumn", null));

		Element columnsElement = listViewElement.element("columns");

		Dom4JUtil.addToElement(
			columnsElement.element(descriptionColumnName),
			Dom4JUtil.getNewElement("displayName", null, "false"),
			Dom4JUtil.getNewElement("trim", null, "false"),
			Dom4JUtil.getNewElement("displayLength", null, "1"));

		listViewElement.element("owner").addAttribute("class", "hudson");
		listViewElement.element("owner").addAttribute("reference", "../../..");
		listViewElement.element("properties").addAttribute(
			"class", "hudson.model.View$PropertyList");

		Element jobNamesElement = listViewElement.element("jobNames");

		jobNamesElement.element("comparator").addAttribute(
			"class", "hudson.util.CaseInsensitiveComparator");

		columnsElement.element(descriptionColumnName).addAttribute(
			"plugin", "extra-columns@1.11");

		return listViewElement;
	}

	private Map<String, String> _getEnvironmentSlavesMap() {
		Map<String, String> environmentSlavesMap = new HashMap<>();

		Set<Map.Entry<String, String>> propertiesSet = _properties.entrySet();

		Iterator<Map.Entry<String, String>> propertiesIterator =
			propertiesSet.iterator();

		while (propertiesIterator.hasNext()) {
			Map.Entry<String, String> entry = propertiesIterator.next();

			String key = entry.getKey();

			if (!key.startsWith("environment.slaves(")) {
				continue;
			}

			String propertyName = key.substring(
				key.indexOf("slaves(") + 7, key.indexOf(")"));

			String value = entry.getValue();

			if (value.contains("..")) {
				value = JenkinsResultsParserUtil.expandSlaveRange(value);
			}

			for (String slaveHostname : value.split(",")) {
				environmentSlavesMap.put(slaveHostname, propertyName);
			}
		}

		return environmentSlavesMap;
	}

	private String _getFormattedXML(List<Element> elements) {
		StringBuilder sb = new StringBuilder();

		for (Element el : elements) {
			try {
				sb.append(Dom4JUtil.format(el));

				sb.append("\n");
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	private String _getGlobalProperty(String property) {
		return _getPropertyFromRegex(property, _globalPropertiesPattern);
	}

	private List<String> _getJobNames(String property) {
		List<String> jobNames = new ArrayList<>();

		String jobBranchName = _getPropertyFromRegex(
			property, _masterJobBranchNamePattern);

		String jobVariantName = _getPropertyFromRegex(
			property, _conservativeParensPattern);

		jobNames.add(jobBranchName);
		jobNames.add(jobVariantName);

		return jobNames;
	}

	private List<String> _getJobProperties(String property) {
		return _getPropertiesFromRegex(property, _masterPropertyPattern);
	}

	private String _getJobShortname(String property) {
		return _getPropertyFromRegex(property, _jobShortNamePattern);
	}

	private Map<String, String> _getLinuxEnvironmentVariablesMap() {
		Map<String, String> linuxEnvironmentVariablesMap = new HashMap<>();
		String pathValue = JenkinsResultsParserUtil.combine(
			"/bin:", "/opt/android/sdk:/opt/android/sdk/platform-tools:",
			"/opt/android/sdk/tools:/opt/ibm/db2/V10.5/bin:/opt/java/ant/bin:",
			"/opt/java/jdk/bin:/opt/java/maven/bin:/opt/oracle/bin:",
			"/opt/sybase/OCS-16_0/bin:/sbin:/usr/bin:/usr/local/bin:",
			"/usr/local/sbin:/usr/sbin");

		linuxEnvironmentVariablesMap.put("PATH", pathValue);

		return linuxEnvironmentVariablesMap;
	}

	private String _getMasterJobNameHostname(String property) {
		return _getPropertyFromRegex(property, _conservativeParensPattern);
	}

	private List<String> _getMasterJobProperties(String property) {
		List<String> unorganizedProperties = _getPropertiesFromRegex(
			property, _masterJobPropertyPattern);

		return Arrays.asList(
			unorganizedProperties.get(1), unorganizedProperties.get(0),
			unorganizedProperties.get(2));
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

	private List<String> _getPropertiesFromRegex(
		String property, Pattern regexToUse) {

		Matcher propertyMatcher = regexToUse.matcher(property);

		List<String> matchedGroups = new ArrayList<>();

		while (propertyMatcher.find()) {
			for (int i = 1; i <= propertyMatcher.groupCount(); i++) {
				matchedGroups.add(propertyMatcher.group(i));
			}
		}

		return matchedGroups;
	}

	private String _getPropertyFromRegex(String property, Pattern regex) {
		List<String> properties = _getPropertiesFromRegex(property, regex);

		if (properties.isEmpty()) {
			return null;
		}

		return properties.get(0);
	}

	private String _getShortChildJobname(String property) {
		return _getPropertyFromRegex(property, _shortChildJobNamePattern);
	}

	private Element _getSlaveConfigXMLContent(String slaveHostname) {
		String slaveLabel = slaveHostname;

		Map<String, String> environmentSlavesMap = _getEnvironmentSlavesMap();

		if (environmentSlavesMap.containsKey(slaveHostname)) {
			slaveLabel = environmentSlavesMap.get(slaveHostname);
		}

		Element slaveElement = Dom4JUtil.getNewElement("slave");

		Dom4JUtil.addToElement(
			slaveElement, Dom4JUtil.getNewElement("name", null, slaveHostname),
			Dom4JUtil.getNewElement("description", null),
			Dom4JUtil.getNewElement("remoteFS", null, "/opt/java/jenkins"),
			Dom4JUtil.getNewElement("numExecutors", null, "1"),
			Dom4JUtil.getNewElement("mode", null, "NORMAL"),
			Dom4JUtil.getNewElement("retentionStrategy", null),
			Dom4JUtil.getNewElement("launcher", null),
			Dom4JUtil.getNewElement("label", null, slaveLabel),
			Dom4JUtil.getNewElement("nodeProperties", null));

		Dom4JUtil.addToElement(
			slaveElement.element("nodeProperties"),
			Dom4JUtil.getNewElement(
				"hudson.slaves.EnvironmentVariablesNodeProperty", null,
				Dom4JUtil.getNewElement(
					"envVars", null,
					Dom4JUtil.getNewElement("unserializable-parents", null),
					Dom4JUtil.getNewElement(
						"tree-map", null,
						Dom4JUtil.getNewElement("default", null)))));

		Element nodePropertiesElement = slaveElement.element("nodeProperties");

		Element environmentVariablesNodePropertyElement =
			nodePropertiesElement.element(
				"hudson.slaves.EnvironmentVariablesNodeProperty");

		Element envVarsElement =
			environmentVariablesNodePropertyElement.element("envVars");

		slaveElement.element("retentionStrategy").addAttribute(
			"class", "hudson.slaves.RetentionStrategy$Always");
		slaveElement.element("launcher").addAttribute(
			"class", "hudson.slaves.JNLPLauncher");
		envVarsElement.addAttribute("serialization", "custom");

		if (slaveLabel.contains("osx")) {
			List<Element> osxEnvironmentVariablesElements =
				_getSlaveEnvironmentVariables(
					_getOSXEnvironmentVariablesMap(), slaveHostname);

			Object[] environmentVariables =
				osxEnvironmentVariablesElements.toArray(
					new Element[osxEnvironmentVariablesElements.size()]);

			Dom4JUtil.addToElement(
				envVarsElement.element("tree-map"), environmentVariables);
		}
		else if (slaveLabel.contains("win")) {
			List<Element> windowsEnvironmentVariablesElements =
				_getSlaveEnvironmentVariables(
					_getWindowsEnvironmentVariablesMap(), slaveHostname);

			Object[] environmentVariables =
				windowsEnvironmentVariablesElements.toArray(
					new Element[windowsEnvironmentVariablesElements.size()]);

			Dom4JUtil.addToElement(
				envVarsElement.element("tree-map"), environmentVariables);
		}
		else {
			List<Element> linuxEnvironmentVariablesElements =
				_getSlaveEnvironmentVariables(
					_getLinuxEnvironmentVariablesMap(), slaveHostname);

			Object[] environmentVariables =
				linuxEnvironmentVariablesElements.toArray(
					new Element[linuxEnvironmentVariablesElements.size()]);

			Dom4JUtil.addToElement(
				envVarsElement.element("tree-map"), environmentVariables);
		}

		return slaveElement;
	}

	private List<Element> _getSlaveEnvironmentVariables(
		Map<String, String> environmentVariablesMap, String hostname) {

		environmentVariablesMap.put("HOSTNAME", hostname + ".lax.liferay.com");

		List<Element> environmentVariableElements = new ArrayList<>();

		Element sizeElement = Dom4JUtil.getNewElement(
			"int", null, Integer.toString(environmentVariablesMap.size()));

		environmentVariableElements.add(sizeElement);

		Set<String> environmentVariables = environmentVariablesMap.keySet();

		for (String environmentVariable : environmentVariables) {
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

	private String _getTriggerBuilderChildName(String property) {
		return _getPropertyFromRegex(
			property, _triggerBuilderChildJobNamePattern);
	}

	private Map<String, String> _getWindowsEnvironmentVariablesMap() {
		Map<String, String> windowsEnvironmentVariablesMap = new HashMap<>();

		String pathValue = JenkinsResultsParserUtil.combine(
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
		windowsEnvironmentVariablesMap.put("PATH", pathValue);

		return windowsEnvironmentVariablesMap;
	}

	private String _joinStrings(List<String> inputList, String separator) {
		String conjoinedString = "";

		for (String inputString : inputList) {
			conjoinedString = conjoinedString + inputString + separator;
		}

		if (conjoinedString.length() > 0) {
			conjoinedString = conjoinedString.substring(
				0, conjoinedString.length() - 1);
		}

		return conjoinedString;
	}

	private String _joinStrings(Set<String> inputSet, String separator) {
		String conjoinedString = "";
		Iterator<String> it = inputSet.iterator();

		while (it.hasNext()) {
			conjoinedString = conjoinedString + it.next() + separator;
		}

		conjoinedString = conjoinedString.substring(
			0, conjoinedString.length() - 1);

		return conjoinedString;
	}

	private void _processGlobalProperty(String key) {
		String propertyName = _getGlobalProperty(key);

		_globalProperties.add(propertyName);

		if (_generatedPropertiesMap.containsKey("global.property.names")) {
			propertyName = _generatedPropertiesMap.get(
				"global.property.names") + propertyName + ",";
		}

		_generatedPropertiesMap.put("global.property.names", propertyName);
	}

	private void _processJobProperty(String key, String value) {
		List<String> separatedProperty = _getJobProperties(key);

		String jobName = separatedProperty.get(0);
		String propertyName = separatedProperty.get(1);

		Map<String, String> jobProperties = _jobNamesToJobProperties.get(
			jobName);

		if (jobProperties == null) {
			jobProperties = new HashMap<>();

			_jobNamesToJobProperties.put(jobName, jobProperties);
		}

		jobProperties.put(propertyName, value);

		_generatedPropertiesMap.put(
			"job.properties(" + jobName + ")",
			_joinStrings(jobProperties.keySet(), ","));
	}

	private void _processMasterJobName(String key, String value)
		throws Exception {

		String masterHostname = _getMasterJobNameHostname(key);

		_masterHostnames.add(masterHostname);
		_masterHostnamesToJobNames.put(masterHostname, value);

		Map<String, String> childJobNamesMap = new HashMap<>();
		List<String> parentJobNames = new ArrayList<>();
		Set<String> topLevelJobNames = new HashSet<>();

		String currentParentJobName1 = null;
		String currentParentJobName2 = null;

		for (String jobName : value.split(",")) {
			int x = jobName.indexOf("(");

			if (x != -1) {
				List<String> jobNames = _getJobNames(jobName);

				String jobBranchName = jobNames.get(0);

				_generatedPropertiesMap.put(
					"job.portal.branch.name(" + jobName + ")", jobBranchName);

				String jobVariationName = jobNames.get(1);

				_generatedPropertiesMap.put(
					"job.variation.name(" + jobName + ")", jobVariationName);
			}
			else {
				_generatedPropertiesMap.put(
					"job.variation.name(" + jobName + ")", "");
			}

			String jobShortName = _getJobShortname(jobName);

			_generatedPropertiesMap.put(
				"job.short.name(" + jobName + ")", jobShortName);

			String jobContent = _readFileToString(
				new File("template/jobs/" + jobShortName + "/config.xml"));

			String blockableBuildTriggerConfig =
				"hudson.plugins.parameterizedtrigger." +
					"BlockableBuildTriggerConfig";

			if (jobContent.contains("Build Flow") ||
				jobContent.contains("com.cloudbees.plugins.flow.BuildFlow")) {

				_generatedPropertiesMap.put(
					"job.content.type(" + jobName + ")", "BuildFlow");

				childJobNamesMap.put(jobName, jobName);
				parentJobNames.add(jobName);
			}
			else if ((jobContent.contains(blockableBuildTriggerConfig) ||
					  jobContent.contains(blockableBuildTriggerConfig)) &&
					 (jobName.contains("fixpack") ||
					  jobName.contains("maintenance-tags"))) {

				_generatedPropertiesMap.put(
					"job.content.type(" + jobName + ")", "BuildTrigger");

				parentJobNames.add(jobName);

				Set<String> triggerBuilderChildJobNames = new HashSet<>();

				String patternToFind = JenkinsResultsParserUtil.combine(
					"hudson\\.plugins\\", ".parameterizedtrigger\\",
					".(TriggerBuilder|BuildTrigger)(.*?)/hudson",
					"\\.plugins\\.parameterizedtrigger\\",
					".(TriggerBuilder|BuildTrigger)");

				triggerBuilderChildJobNames.add(jobName);

				Pattern triggerBuilderPattern = Pattern.compile(
					patternToFind, Pattern.DOTALL);

				Matcher triggerBuilderMatcher = triggerBuilderPattern.matcher(
					jobContent);

				while (triggerBuilderMatcher.find()) {
					String triggerBuilder = triggerBuilderMatcher.group(2);

					String triggerBuilderChildJobName =
						_getTriggerBuilderChildName(triggerBuilder);

					if (!triggerBuilderChildJobName.equals(
							"@!child.job.names!@")) {

						triggerBuilderChildJobName =
							triggerBuilderChildJobName.replace(
								"@!job.variation.name!@",
								_generatedPropertiesMap.get(
									"job.variation.name(" + jobName + ")"));

						triggerBuilderChildJobNames.add(
							triggerBuilderChildJobName);
					}
				}

				childJobNamesMap.put(
					jobName, _joinStrings(triggerBuilderChildJobNames, ","));
			}

			if (jobContent.contains("Top Level")) {
				topLevelJobNames.add(jobName);
			}

			if (parentJobNames.contains(jobName) &&
				topLevelJobNames.contains(jobName)) {

				currentParentJobName1 = jobName;
			}
			else if (parentJobNames.contains(jobName)) {
				currentParentJobName2 = jobName;
			}

			String currentParentJobName = null;

			if (currentParentJobName2 != null) {
				String currentParentJobShortName2 = _generatedPropertiesMap.get(
					"job.short.name(" + currentParentJobName2 + ")");
				String currentParentJobVariationName2 =
					_generatedPropertiesMap.get(
						"job.variation.name(" + currentParentJobName2 + ")");
				boolean memberOfCurrentParentJob = false;

				if (jobName.startsWith(currentParentJobShortName2)) {
					if (jobName.endsWith(
							"(" + currentParentJobVariationName2 +
								")") ||
						currentParentJobVariationName2.equals("")) {

						memberOfCurrentParentJob = true;
					}
				}

				if (memberOfCurrentParentJob &&
					!jobName.equals(currentParentJobName2)) {

					currentParentJobName = currentParentJobName2;
				}
				else if (!memberOfCurrentParentJob) {
					currentParentJobName2 = null;
				}
			}

			if ((currentParentJobName == null) &&
				(currentParentJobName1 != null)) {

				String currentParentJobShortName1 = _generatedPropertiesMap.get(
					"job.short.name(" + currentParentJobName1 + ")");
				String currentParentJobVariationName1 =
					_generatedPropertiesMap.get(
						"job.variation.name(" + currentParentJobName1 + ")");
				boolean memberOfCurrentParentJob = false;

				if (jobName.startsWith(currentParentJobShortName1)) {
					if (jobName.endsWith(
							"(" + currentParentJobVariationName1 +
								")") ||
						currentParentJobVariationName1.equals("")) {

						memberOfCurrentParentJob = true;
					}
				}

				if (memberOfCurrentParentJob &&
					!jobName.equals(currentParentJobName1)) {

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

				if (jobName.contains("[component.name]")) {
					String componentNames = _generatedPropertiesMap.get(
						"job.component.names(" + jobName + ")");

					if (componentNames == null) {
						componentNames = "portal-acceptance";
					}

					for (String componentName : componentNames.split(",")) {
						childJobNames.add(
							jobName.replace(
								"[component.name]", "[" + componentName + "]"));
					}
				}
				else {
					childJobNames.add(jobName);
				}

				childJobNamesMap.put(
					currentParentJobName, _joinStrings(childJobNames, ","));
			}
		}

		for (String parentJobName : parentJobNames) {
			String childJobNames = childJobNamesMap.get(parentJobName);

			if (!childJobNames.contains(",")) {
				System.out.println(
					"ERROR: Parent job is missing children jobs: " +
						parentJobName + "\n");

				throw new Exception();
			}

			_generatedPropertiesMap.put(
				"child.job.names(" + parentJobName + ")",
				childJobNamesMap.get(parentJobName));
		}

		StringBuilder sb = new StringBuilder();

		List<Element> topLevelJobNameList = new ArrayList<>();

		for (String topLevelJobName : topLevelJobNames) {
			String excludeTopLevelView = _generatedPropertiesMap.get(
				"job.property(" + topLevelJobName + "/exclude.top.level.view)");

			if ((excludeTopLevelView == null) ||
				!excludeTopLevelView.equals("true")) {

				topLevelJobNameList.add(
					Dom4JUtil.getNewElement("string", null, topLevelJobName));
			}
		}

		_generatedPropertiesMap.put(
			"master.top.level.jobs.xml(" + masterHostname + ")",
			_getFormattedXML(topLevelJobNameList));

		String masterPrimaryView = _generatedPropertiesMap.get(
			"master.primary.view(" + masterHostname + ")");

		if (masterPrimaryView == null) {
			if (topLevelJobNames.isEmpty()) {
				_generatedPropertiesMap.put(
					"master.primary.view(" + masterHostname + ")", "All");
			}
			else {
				_generatedPropertiesMap.put(
					"master.primary.view(" + masterHostname + ")", "Top Level");
			}
		}

		List<Element> listViewList = new ArrayList<>();

		for (String parentJobName : parentJobNames) {
			String childJobNames = _generatedPropertiesMap.get(
				"child.job.names(" + parentJobName + ")");

			String[] childJobNamesArray = childJobNames.split(",");

			Arrays.sort(childJobNamesArray);

			listViewList.add(
				_generateListViewByParent(parentJobName, childJobNamesArray));
		}

		_generatedPropertiesMap.put(
			"master.list.views.xml(" + masterHostname + ")",
			_getFormattedXML(listViewList));

		for (String parentJobName : parentJobNames) {
			sb = new StringBuilder();

			String childJobNames = _generatedPropertiesMap.get(
				"child.job.names(" + parentJobName + ")");
			List<String> childJobNamesList = new ArrayList<>();

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

				String jobContentType = _generatedPropertiesMap.get(
					"job.content.type(" + parentJobName + ")");

				if (jobContentType.equals("BuildTrigger")) {
					String shortParentJobName = _generatedPropertiesMap.get(
						"job.short.name(" + parentJobName + ")");

					String shortChildJobName = _getShortChildJobname(
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

				String componentNamesIgnore = _generatedPropertiesMap.get(
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
				sb.append(childJobNamesList.size());
				sb.append(" = build(\"");
				sb.append(childJobName);

				String customJobParameters = _generatedPropertiesMap.get(
					"job.property(" + jobName + "/custom.job.parameters)");

				if (customJobParameters != null) {
					sb.append("\", @!custom.job.parameters!@)\n");

					_generatedPropertiesMap.put(
						"custom.job.parameters(" + parentJobName + ")",
						customJobParameters);
				}
				else {
					sb.append("\", @!job.parameters!@)\n");
				}

				if (ignoreChildJobName) {
					sb.append("\t\t}\n");
				}

				sb.append("\t},");

				childJobNamesList.add(childJobName);
			}

			if (sb.length() != 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			_generatedPropertiesMap.put(
				"build.flow.downstream.jobs.xml(" + parentJobName + ")",
				sb.toString());
			_generatedPropertiesMap.put(
				"child.job.names(" + parentJobName + ")",
				_joinStrings(childJobNamesList, ","));

			sb = new StringBuilder();

			for (int i = 0; i < childJobNamesList.size(); i++) {
				sb.append("JOB_INVOCATION_");
				sb.append(i);
				sb.append(" = build\n");
			}

			String jobInvocationList = "job.invocation.instantiate.list";

			_generatedPropertiesMap.put(
				JenkinsResultsParserUtil.combine(
					jobInvocationList, parentJobName, ")"),
				sb.toString());

			sb = new StringBuilder();

			for (int i = 0; i < childJobNamesList.size(); i++) {
				sb.append("JOB_INVOCATIONS += JOB_INVOCATION_");
				sb.append(i);
				sb.append("\n");
			}

			_generatedPropertiesMap.put(
				"job.invocation.make.list(" + parentJobName + ")",
				sb.toString());
		}
	}

	private void _processMasterJobProperty(String key, String value) {
		List<String> separatedProperty = _getMasterJobProperties(key);

		String jobName = separatedProperty.get(0);
		String masterHostname = separatedProperty.get(1);
		String propertyName = separatedProperty.get(2);

		Map<String, String> masterJobProperties =
			_jobNamesToMasterJobProperties.get(jobName);

		if (masterJobProperties == null) {
			masterJobProperties = new HashMap<>();

			_jobNamesToMasterJobProperties.put(jobName, masterJobProperties);
		}

		masterJobProperties.put(propertyName, value);

		_generatedPropertiesMap.put(
			"master.job.properties(" + masterHostname + "/" + jobName +
				")",
			_joinStrings(masterJobProperties.keySet(), ","));
	}

	private void _processMasterProperty(String key, String value) {
		List<String> separatedProperty = _getJobProperties(key);

		String masterHostname = separatedProperty.get(0);
		String propertyName = separatedProperty.get(1);

		Map<String, String> masterProperties =
			_masterHostnamesToMasterProperties.get(masterHostname);

		if (masterProperties == null) {
			masterProperties = new HashMap<>();

			_masterHostnamesToMasterProperties.put(
				masterHostname, masterProperties);
		}

		masterProperties.put(propertyName, value);

		_generatedPropertiesMap.put(
			"master.properties(" + masterHostname + ")",
			_joinStrings(masterProperties.keySet(), ","));
	}

	private void _processMasterSlaves(String key, String value)
		throws IOException {

		String masterHostname = _getMasterJobNameHostname(key);

		_masterHostnames.add(masterHostname);

		if (value.contains("..")) {
			StringBuilder sb = new StringBuilder();

			value = JenkinsResultsParserUtil.expandSlaveRange(value);

			for (String slaveHostname : value.split(",")) {
				sb.append(slaveHostname);
				sb.append("(");

				String environmentSlaveOSType = "";

				if (_environmentSlavesMap.containsKey(slaveHostname)) {
					String environmentSlaveKey = _environmentSlavesMap.get(
						slaveHostname);

					environmentSlaveOSType = environmentSlaveKey.substring(
						0, environmentSlaveKey.indexOf("."));
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

			value = sb.toString();
		}

		_generatedPropertiesMap.put(
			"master.slaves.txt(" + masterHostname + ")",
			value.replaceAll(",", "\n").trim());

		Element slaves = Dom4JUtil.getNewElement("slaves", null);

		for (String slaveHostname : value.split(",")) {
			if (slaveHostname.contains("(")) {
				slaveHostname = slaveHostname.substring(
					0, slaveHostname.indexOf("("));
			}

			Element slaveConfigXML = _getSlaveConfigXMLContent(slaveHostname);

			_generatedPropertiesMap.put(
				slaveHostname + ".config.xml.content",
				Dom4JUtil.format(slaveConfigXML));

			Dom4JUtil.addToElement(slaves, slaveConfigXML);

			_slaveHostnames.add(slaveHostname);

			_generatedPropertiesMap.put(
				"slave.master(" + slaveHostname + ")", masterHostname);
		}

		_generatedPropertiesMap.put(
			"master.slaves.xml(" + masterHostname + ")",
			Dom4JUtil.format(slaves));

		List<Element> slaveHostnameList = new ArrayList<>();

		for (String slaveHostname : value.split(",")) {
			if (slaveHostname.contains("(")) {
				slaveHostname = slaveHostname.substring(
					0, slaveHostname.indexOf("("));
			}

			Element slaveHostnameElement = Dom4JUtil.getNewElement(
				"string", null, slaveHostname);

			slaveHostnameList.add(slaveHostnameElement);
		}

		_generatedPropertiesMap.put(
			"master.axes.label.slaves.xml(" + masterHostname + ")",
			_getFormattedXML(slaveHostnameList));
	}

	private String _readFileToString(File fileToRead) {
		String result = "";

		try {
			List<String> lines = Files.readAllLines(fileToRead.toPath());

			for (String line : lines) {
				result = JenkinsResultsParserUtil.combine(result, line, "\n");
			}

			result = result.substring(0, result.length() - 1);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return result;
	}

	private static final Pattern _conservativeParensPattern = Pattern.compile(
		"\\((.+?)\\)");
	private static final Pattern _globalPropertiesPattern = Pattern.compile(
		"\\((.+)\\)");
	private static final Pattern _jobShortNamePattern = Pattern.compile(
		"([^\\(]+)\\(?");
	private static final Pattern _masterJobBranchNamePattern = Pattern.compile(
		"\\((.+?)[_|\\)]");
	private static final Pattern _masterJobPropertyPattern = Pattern.compile(
		"\\((.+?)/(.+?)/(.+)\\)");
	private static final Pattern _masterPropertyPattern = Pattern.compile(
		"\\((.+?)/(.+)\\)");
	private static final Pattern _shortChildJobNamePattern = Pattern.compile(
		"(.+?)[\\[|\\(]?");
	private static final Pattern _triggerBuilderChildJobNamePattern =
		Pattern.compile("<projects>(.+)</projects>");

	private final Map<String, String> _environmentSlavesMap;
	private final Map<String, String> _generatedPropertiesMap;
	private final Set<String> _globalProperties;
	private final Map<String, Map<String, String>> _jobNamesToJobProperties;
	private final Map<String, Map<String, String>>
		_jobNamesToMasterJobProperties;
	private final Set<String> _masterHostnames;
	private final Map<String, String> _masterHostnamesToJobNames;
	private final Map<String, Map<String, String>>
		_masterHostnamesToMasterProperties;
	private final Map<String, String> _properties;
	private final Set<String> _slaveHostnames;

}