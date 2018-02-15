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
import java.io.IOException;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Cesar Polanco
 */
public class JenkinsJobTemplate {

	public Map generateTemplate(Map<String, String> properties)
		throws Exception {

		Map<String, String> generatedPropertiesMap = new HashMap(properties);
		Map<String, String> environmentSlavesMap = _getEnvironmentSlavesMap(
			properties);
		Set globalProperties = new TreeSet();
		Map jobNamesToJobProperties = new TreeMap();
		Map jobNamesToMasterJobProperties = new TreeMap();
		Map<String, String> linuxEnvironmentVariablesMap =
			_getLinuxEnvironmentVariablesMap();
		Set<String> masterHostnames = new TreeSet();
		Map masterHostnamesToJobNames = new TreeMap();
		Map masterHostnamesToMasterProperties = new TreeMap();
		Map<String, String> osxEnvironmentVariablesMap =
			_getOSXEnvironmentVariablesMap();
		Set slaveHostnames = new TreeSet();
		Map<String, String> windowsEnvironmentVariablesMap =
			_getWindowsEnvironmentVariablesMap();

		Set<Map.Entry<String, String>> propertiesSet = properties.entrySet();

		Iterator<Map.Entry<String, String>> propertiesIterator =
			propertiesSet.iterator();

		while (propertiesIterator.hasNext()) {
			Map.Entry<String, String> entry = propertiesIterator.next();

			String key = entry.getKey();
			String value = entry.getValue();

			if (key.startsWith("global.property(")) {
				String propertyName = key.substring(
					key.indexOf("(") + 1, key.lastIndexOf(")"));

				globalProperties.add(propertyName);

				if (generatedPropertiesMap.containsKey(
						"global.property.names")) {

					propertyName = generatedPropertiesMap.get(
						"global.property.names") + propertyName + ",";
				}

				generatedPropertiesMap.put(
					"global.property.names", propertyName);
			}
			else if (key.startsWith("master.job.names(")) {
				String masterHostname = key.substring(
					key.indexOf("(") + 1, key.indexOf(")"));

				masterHostnames.add(masterHostname);
				masterHostnamesToJobNames.put(masterHostname, value);

				Map<String, String> childJobNamesMap = new HashMap();
				List<String> parentJobNames = new ArrayList();
				Set<String> topLevelJobNames = new TreeSet();

				String currentParentJobName1 = null;
				String currentParentJobName2 = null;

				for (String jobName : value.split(",")) {

					int x = jobName.indexOf("(");

					if (x != -1) {
						int y = jobName.indexOf("_");

						if (y == -1) {
							y = jobName.indexOf(")");
						}

						String jobBranchName = jobName.substring(x + 1, y);

						generatedPropertiesMap.put(
							"job.portal.branch.name(" + jobName + ")",
							jobBranchName);

						y = jobName.indexOf(")");

						String jobVariationName = jobName.substring(x + 1, y);

						generatedPropertiesMap.put(
							"job.variation.name(" + jobName + ")",
							jobVariationName);
					}
					else {
						generatedPropertiesMap.put(
							"job.variation.name(" + jobName + ")", "");
					}

					String jobShortName = jobName;

					int j = jobShortName.indexOf("(");

					if (j != -1) {
						jobShortName = jobShortName.substring(0, j);
					}

					generatedPropertiesMap.put(
						"job.short.name(" + jobName + ")", jobShortName);

					String jobContent = _readFileToString(
						new File(
							"template/jobs/" + jobShortName + "/config.xml"));

					String blockableBuildTriggerConfig =
						"hudson.plugins.parameterizedtrigger." +
							"BlockableBuildTriggerConfig";

					if (jobContent.contains("Build Flow") ||
						jobContent.contains(
							"com.cloudbees.plugins.flow.BuildFlow")) {

						generatedPropertiesMap.put(
							"job.content.type(" + jobName + ")", "BuildFlow");

						childJobNamesMap.put(jobName, jobName);
						parentJobNames.add(jobName);
					}
					else if ((jobContent.contains(
								blockableBuildTriggerConfig) ||
							  jobContent.contains(
								  blockableBuildTriggerConfig)) &&
							 (jobName.contains("fixpack") ||
							  jobName.contains("maintenance-tags"))) {

						generatedPropertiesMap.put(
							"job.content.type(" + jobName + ")",
							"BuildTrigger");

						parentJobNames.add(jobName);

						Set triggerBuilderChildJobNames = new TreeSet();

						String patternToFind = JenkinsResultsParserUtil.combine(
							"hudson\\.plugins\\", ".parameterizedtrigger\\",
							".(TriggerBuilder|BuildTrigger)(.*?)/hudson",
							"\\.plugins\\.parameterizedtrigger\\",
							".(TriggerBuilder|BuildTrigger)");

						triggerBuilderChildJobNames.add(jobName);

						Pattern triggerBuilderPattern = Pattern.compile(
							patternToFind, Pattern.DOTALL);

						Matcher triggerBuilderMatcher =
							triggerBuilderPattern.matcher(jobContent);

						while (triggerBuilderMatcher.find()) {
							String triggerBuilder = triggerBuilderMatcher.group(
								2);

							int z = triggerBuilder.indexOf("<projects>");

							int y = triggerBuilder.indexOf(
								"</projects>", z + 1);

							String triggerBuilderChildJobName =
								triggerBuilder.substring(z + 10, y);

							if (!triggerBuilderChildJobName.equals(
									"@!child.job.names!@")) {

								triggerBuilderChildJobName =
									triggerBuilderChildJobName.replace(
										"@!job.variation.name!@",
										generatedPropertiesMap.get(
											"job.variation.name(" + jobName +
												")"));

								triggerBuilderChildJobNames.add(
									triggerBuilderChildJobName);
							}
						}

						childJobNamesMap.put(
							jobName,
							_joinStrings(triggerBuilderChildJobNames, ","));
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
						String currentParentJobShortName2 = generatedPropertiesMap.get(
							"job.short.name(" + currentParentJobName2 + ")");
						String currentParentJobVariationName2 = generatedPropertiesMap.get(
							"job.variation.name(" + currentParentJobName2 +
								")");
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

						String currentParentJobShortName1 = generatedPropertiesMap.get(
							"job.short.name(" + currentParentJobName1 + ")");
						String currentParentJobVariationName1 = generatedPropertiesMap.get(
							"job.variation.name(" + currentParentJobName1 +
								")");
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
						Set childJobNames = new TreeSet();

						String childJobNamesString = childJobNamesMap.get(
							currentParentJobName);

						for (String childJobName :
								childJobNamesString.split(",")) {

							childJobNames.add(childJobName);
						}

						if (jobName.contains("[component.name]")) {
							String componentNames = generatedPropertiesMap.get(
								"job.component.names(" + jobName + ")");

							if (componentNames == null) {
								componentNames = "portal-acceptance";
							}

							for (String componentName :
									componentNames.split(",")) {

								childJobNames.add(
									jobName.replace(
										"[component.name]",
										"[" + componentName + "]"));
							}
						}
						else {
							childJobNames.add(jobName);
						}

						childJobNamesMap.put(
							currentParentJobName,
							_joinStrings(childJobNames, ","));
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

					generatedPropertiesMap.put(
						"child.job.names(" + parentJobName + ")",
						childJobNamesMap.get(parentJobName));
				}

				StringBuilder sb = new StringBuilder();

				for (String topLevelJobName : topLevelJobNames) {
					String excludeTopLevelView = generatedPropertiesMap.get(
						"job.property(" + topLevelJobName +
							"/exclude.top.level.view)");

					if ((excludeTopLevelView == null) ||
						!excludeTopLevelView.equals("true")) {

						sb.append("<string>");
						sb.append(topLevelJobName);
						sb.append("</string>");
					}
				}

				generatedPropertiesMap.put(
					"master.top.level.jobs.xml(" + masterHostname + ")",
					sb.toString());

				String masterPrimaryView = generatedPropertiesMap.get(
					"master.primary.view(" + masterHostname + ")");

				if (masterPrimaryView == null) {
					if (topLevelJobNames.isEmpty()) {
						generatedPropertiesMap.put(
							"master.primary.view(" + masterHostname + ")",
							"All");
					}
					else {
						generatedPropertiesMap.put(
							"master.primary.view(" + masterHostname + ")",
							"Top Level");
					}
				}

				sb = new StringBuilder();

				for (String parentJobName : parentJobNames) {
					if (sb.length() != 0) {
						sb.append("\t\t");
					}

					sb.append("<listView>\n");
					sb.append(
						"\t\t\t<owner class=\"hudson\" " +
							"reference=\"../../..\" />\n");
					sb.append("\t\t\t<name>" + parentJobName + "</name>\n");
					sb.append(
						"\t\t\t<filterExecutors>true</filterExecutors>\n");
					sb.append("\t\t\t<filterQueue>true</filterQueue>\n");
					sb.append(
						"\t\t\t<properties class=\"" +
							"hudson.model.View$PropertyList\" />\n");
					sb.append("\t\t\t<jobNames>\n");
					sb.append(
						"\t\t\t\t<comparator class=\"" +
							"hudson.util.CaseInsensitiveComparator\" />\n");
					sb.append("\t\t\t\t");

					String childJobNames = generatedPropertiesMap.get(
						"child.job.names(" + parentJobName + ")");

					String[] childJobNamesArray = childJobNames.split(",");

					Arrays.sort(childJobNamesArray);

					for (String childJobName : childJobNamesArray) {
						sb.append("<string>");
						sb.append(childJobName);
						sb.append("</string>");
					}

					sb.append("\n\t\t\t</jobNames>\n");
					sb.append("\t\t\t<jobFilters />\n");
					sb.append("\t\t\t<columns>\n");
					sb.append("\t\t\t\t<hudson.views.StatusColumn />\n");
					sb.append("\t\t\t\t<hudson.views.WeatherColumn />\n");
					sb.append("\t\t\t\t<hudson.views.JobColumn />\n");
					sb.append(
						"\t\t\t\t<jenkins.plugins.extracolumns." +
							"DescriptionColumn plugin=\"extra-" +
								"columns@1.11\">\n");
					sb.append("\t\t\t\t\t<displayName>false</displayName>\n");
					sb.append("\t\t\t\t\t<trim>false</trim>\n");
					sb.append("\t\t\t\t\t<displayLength>1</displayLength>\n");
					sb.append(
						"\t\t\t\t</jenkins.plugins.extracolumns." +
							"DescriptionColumn>\n");
					sb.append("\t\t\t\t<hudson.views.LastSuccessColumn />\n");
					sb.append("\t\t\t\t<hudson.views.LastFailureColumn />\n");
					sb.append("\t\t\t\t<hudson.views.LastDurationColumn />\n");
					sb.append("\t\t\t\t<hudson.views.BuildButtonColumn />\n");
					sb.append("\t\t\t</columns>\n");
					sb.append("\t\t\t<recurse>false</recurse>\n");
					sb.append("\t\t</listView>\n");
				}

				if (sb.length() != 0) {
					sb.deleteCharAt(sb.length() - 1);
				}

				generatedPropertiesMap.put(
					"master.list.views.xml(" + masterHostname + ")",
					sb.toString());

				for (String parentJobName : parentJobNames) {
					sb = new StringBuilder();

					String childJobNames = generatedPropertiesMap.get(
						"child.job.names(" + parentJobName + ")");
					List childJobNamesList = new ArrayList();

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

						String jobContentType = generatedPropertiesMap.get(
							"job.content.type(" + parentJobName + ")");

						if (jobContentType.equals("BuildTrigger")) {
							String shortParentJobName = generatedPropertiesMap.get(
								"job.short.name(" + parentJobName + ")");

							String shortChildJobName = childJobName;

							int x = shortChildJobName.indexOf("[");

							if (x != -1) {
								shortChildJobName = shortChildJobName.substring(
									0, x);
							}

							x = shortChildJobName.indexOf("(");

							if (x != -1) {
								shortChildJobName = shortChildJobName.substring(
									0, x);
							}

							if (!shortChildJobName.contains(
									shortParentJobName)) {

								continue;
							}
						}

						boolean ignoreChildJobName = false;

						String jobName = childJobName;

						if (jobName.matches(".*[^\\[]+\\[[a-z\\-]+\\].*")) {
							jobName = jobName.replaceAll(
								"\\[[a-z\\-]+\\]", "[component.name]");
						}

						String componentNamesIgnore = generatedPropertiesMap.get(
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

						String customJobParameters = generatedPropertiesMap.get(
							"job.property(" + jobName +
								"/custom.job.parameters)");

						if (customJobParameters != null) {
							sb.append("\", @!custom.job.parameters!@)\n");

							generatedPropertiesMap.put(
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

					generatedPropertiesMap.put(
						"build.flow.downstream.jobs.xml(" + parentJobName + ")",
						sb.toString());
					generatedPropertiesMap.put(
						"child.job.names(" + parentJobName + ")",
						_joinStrings(childJobNamesList, ","));

					sb = new StringBuilder();

					for (int i = 0; i < childJobNamesList.size(); i++) {
						sb.append("JOB_INVOCATION_");
						sb.append(i);
						sb.append(" = build\n");
					}

					String jobInvocationList =
						"job.invocation.instantiate.list";

					generatedPropertiesMap.put(
						JenkinsResultsParserUtil.combine(
							jobInvocationList, parentJobName, ")"),
						sb.toString());

					sb = new StringBuilder();

					for (int i = 0; i < childJobNamesList.size(); i++) {
						sb.append("JOB_INVOCATIONS += JOB_INVOCATION_");
						sb.append(i);
						sb.append("\n");
					}

					generatedPropertiesMap.put(
						"job.invocation.make.list(" + parentJobName + ")",
						sb.toString());
				}
			}
			else if (key.startsWith("master.job.property(")) {
				int x = key.indexOf("/");

				int y = key.indexOf("/", x + 1);

				String jobName = key.substring(x + 1, y);
				String masterHostname = key.substring(key.indexOf("(") + 1, x);
				String propertyName = key.substring(
					y + 1, key.lastIndexOf(")"));

				Map masterJobProperties =
					(Map)jobNamesToMasterJobProperties.get(jobName);

				if (masterJobProperties == null) {
					masterJobProperties = new TreeMap();

					jobNamesToMasterJobProperties.put(
						jobName, masterJobProperties);
				}

				masterJobProperties.put(propertyName, value);

				generatedPropertiesMap.put(
					"master.job.properties(" + masterHostname + "/" + jobName +
						")",
					_joinStrings(masterJobProperties.keySet(), ","));
			}
			else if (key.startsWith("master.property(")) {
				String masterHostname = key.substring(
					key.indexOf("(") + 1, key.indexOf("/"));
				String propertyName = key.substring(
					key.indexOf("/") + 1, key.lastIndexOf(")"));

				Map masterProperties =
					(Map)masterHostnamesToMasterProperties.get(masterHostname);

				if (masterProperties == null) {
					masterProperties = new TreeMap();

					masterHostnamesToMasterProperties.put(
						masterHostname, masterProperties);
				}

				masterProperties.put(propertyName, value);

				generatedPropertiesMap.put(
					"master.properties(" + masterHostname + ")",
					_joinStrings(masterProperties.keySet(), ","));
			}
			else if (key.startsWith("master.slaves(")) {
				String masterHostname = key.substring(
					key.indexOf("(") + 1, key.indexOf(")"));

				masterHostnames.add(masterHostname);

				if (value.contains("..")) {
					StringBuilder sb = new StringBuilder();

					value = JenkinsResultsParserUtil.expandSlaveRange(value);

					for (String slaveHostname : value.split(",")) {
						sb.append(slaveHostname);
						sb.append("(");

						String environmentSlaveOSType = "";

						if (environmentSlavesMap.containsKey(slaveHostname)) {
							String environmentSlaveKey =
								environmentSlavesMap.get(slaveHostname);

							environmentSlaveOSType =
								environmentSlaveKey.substring(
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

				generatedPropertiesMap.put(
					"master.slaves.txt(" + masterHostname + ")",
					value.replaceAll(",", "\n").trim());

				StringBuilder sb = new StringBuilder();

				sb.append("<slaves>\n");

				for (String slaveHostname : value.split(",")) {
					if (slaveHostname.contains("(")) {
						slaveHostname = slaveHostname.substring(
							0, slaveHostname.indexOf("("));
					}

					String slaveConfigXMLContent = _getSlaveConfigXMLContent(
						slaveHostname, generatedPropertiesMap);

					generatedPropertiesMap.put(
						slaveHostname + ".config.xml.content",
						slaveConfigXMLContent);

					sb.append(slaveConfigXMLContent);

					sb.append("\n");

					slaveHostnames.add(slaveHostname);

					generatedPropertiesMap.put(
						"slave.master(" + slaveHostname + ")", masterHostname);
				}

				sb.append("\t</slaves>");

				generatedPropertiesMap.put(
					"master.slaves.xml(" + masterHostname + ")", sb.toString());

				sb = new StringBuilder();

				for (String slaveHostname : value.split(",")) {
					if (slaveHostname.contains("(")) {
						slaveHostname = slaveHostname.substring(
							0, slaveHostname.indexOf("("));
					}

					sb.append("<string>");
					sb.append(slaveHostname);
					sb.append("</string>");
				}

				generatedPropertiesMap.put(
					"master.axes.label.slaves.xml(" + masterHostname + ")",
					sb.toString());
			}
			else if (key.startsWith("job.property(")) {
				String jobName = key.substring(
					key.indexOf("(") + 1, key.indexOf("/"));
				String propertyName = key.substring(
					key.indexOf("/") + 1, key.lastIndexOf(")"));

				Map jobProperties = (Map)jobNamesToJobProperties.get(jobName);

				if (jobProperties == null) {
					jobProperties = new TreeMap();

					jobNamesToJobProperties.put(jobName, jobProperties);
				}

				jobProperties.put(propertyName, value);

				generatedPropertiesMap.put(
					"job.properties(" + jobName + ")",
					_joinStrings(jobProperties.keySet(), ","));
			}
		}

		generatedPropertiesMap.put(
			"master.hostnames", _joinStrings(masterHostnames, ","));

		generatedPropertiesMap.put(
			"slave.hostnames", _joinStrings(slaveHostnames, ","));

		int i = 0;

		for (String masterHostname : masterHostnames) {
			if (masterHostname.startsWith("test-")) {
				generatedPropertiesMap.put(
					"trigger.day." + masterHostname, Integer.toString(i % 7));

				generatedPropertiesMap.put(
					"trigger.hour." + masterHostname, Integer.toString(i % 24));

				i++;
			}
		}

		return generatedPropertiesMap;
	}

	private Map<String, String> _getEnvironmentSlavesMap(
		Map<String, String> properties) {

		Map environmentSlavesMap = new TreeMap();

		Set<Map.Entry<String, String>> propertiesSet = properties.entrySet();

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

	private Map<String, String> _getLinuxEnvironmentVariablesMap() {
		Map<String, String> linuxEnvironmentVariablesMap = new TreeMap();

		String pathValue = JenkinsResultsParserUtil.combine(
			"/bin:", "/opt/android/sdk:/opt/android/sdk/platform-tools:",
			"/opt/android/sdk/tools:/opt/ibm/db2/V10.5/bin:/opt/java/ant/bin:",
			"/opt/java/jdk/bin:/opt/java/maven/bin:/opt/oracle/bin:",
			"/opt/sybase/OCS-16_0/bin:/sbin:/usr/bin:/usr/local/bin:",
			"/usr/local/sbin:/usr/sbin");

		linuxEnvironmentVariablesMap.put("PATH", pathValue);

		return linuxEnvironmentVariablesMap;
	}

	private Map<String, String> _getOSXEnvironmentVariablesMap() {
		Map<String, String> osxEnvironmentVariablesMap = new TreeMap();

		String pathValue = JenkinsResultsParserUtil.combine(
			"/bin:/opt/java/ant/bin:/opt/java/jdk/bin:/opt/java/maven/bin:",
			"/sbin:/usr/bin:/usr/local/bin:/usr/local/sbin:/usr/sbin");

		osxEnvironmentVariablesMap.put("HOME", "/Users/administrator");
		osxEnvironmentVariablesMap.put("PATH", pathValue);

		return osxEnvironmentVariablesMap;
	}

	private String _getSlaveConfigXMLContent(
		String slaveHostname, Map<String, String> properties) {

		String slaveLabel = slaveHostname;

		Map<String, String> environmentSlavesMap = _getEnvironmentSlavesMap(
			properties);
		Map<String, String> linuxEnvironmentVariablesMap =
			_getLinuxEnvironmentVariablesMap();
		Map<String, String> osxEnvironmentVariablesMap =
			_getOSXEnvironmentVariablesMap();
		Map<String, String> windowsEnvironmentVariablesMap =
			_getWindowsEnvironmentVariablesMap();

		if (environmentSlavesMap.containsKey(slaveHostname)) {
			slaveLabel = environmentSlavesMap.get(slaveHostname);
		}

		String slaveConfigXMLContent = JenkinsResultsParserUtil.combine(
			"<slave>\n\t<name>", slaveHostname, "</name>\n\t<description>",
			"</description>\n\t<remoteFS>/opt/java/jenkins</remoteFS>\n",
			"\t<numExecutors>1</numExecutors>\n\t<mode>NORMAL</mode>\n",
			"\t<retentionStrategy ",
			"class=\"hudson.slaves.RetentionStrategy$Always\"",
			" />\n\t<launcher class=\"hudson.slaves.JNLPLauncher\" />\n",
			"\t<label>", slaveLabel, "</label>\n\t<nodeProperties>\n",
			"\t\t<hudson.slaves.EnvironmentVariablesNodeProperty>\n",
			"\t\t\t<envVars serialization=\"custom\">\n",
			"\t\t\t\t<unserializable-parents />\n",
			"\t\t\t\t<tree-map>\n\t\t\t\t\t<default />\n");

		if (slaveLabel.contains("osx")) {
			slaveConfigXMLContent = JenkinsResultsParserUtil.combine(
				slaveConfigXMLContent,
				_getSlaveEnvironmentVariables(
					osxEnvironmentVariablesMap, slaveHostname));
		}
		else if (slaveLabel.contains("win")) {
			slaveConfigXMLContent = JenkinsResultsParserUtil.combine(
				slaveConfigXMLContent,
				_getSlaveEnvironmentVariables(
					windowsEnvironmentVariablesMap, slaveHostname));
		}
		else {
			slaveConfigXMLContent = JenkinsResultsParserUtil.combine(
				slaveConfigXMLContent,
				_getSlaveEnvironmentVariables(
					linuxEnvironmentVariablesMap, slaveHostname));
		}

		slaveConfigXMLContent = JenkinsResultsParserUtil.combine(
			slaveConfigXMLContent, "\t\t\t\t</tree-map>\n\t\t\t</envVars>\n",
			"\t\t</hudson.slaves.EnvironmentVariablesNodeProperty>\n",
			"\t</nodeProperties>\n</slave>");

		return slaveConfigXMLContent;
	}

	private String _getSlaveEnvironmentVariables(
		Map<String, String> environmentVariablesMap, String hostname) {

		environmentVariablesMap.put("HOSTNAME", hostname + ".lax.liferay.com");

		String environmentVariablesString = JenkinsResultsParserUtil.combine(
			"\t\t\t\t\t<int>", Integer.toString(environmentVariablesMap.size()),
			"</int>\n");

		Set<String> environmentVariables = environmentVariablesMap.keySet();

		for (String environmentVariable : environmentVariables) {
			environmentVariablesString = JenkinsResultsParserUtil.combine(
				environmentVariablesString, "\t\t\t\t\t<string>",
				environmentVariable, "</string>\n\t\t\t\t\t<string>",
				environmentVariablesMap.get(environmentVariable),
				"</string>\n");
		}

		return environmentVariablesString;
	}

	private Map<String, String> _getWindowsEnvironmentVariablesMap() {
		Map<String, String> windowsEnvironmentVariablesMap = new TreeMap();

		String pathValue = JenkinsResultsParserUtil.combine(
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

}